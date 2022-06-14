# github 403에러

## 문제 상황
git push를 하려고 하는데, 403 에러가 발생함. 이유는 최근에 다른 git 계정도 함께 사용하게 된게 문제로, SSH로 두 계정을 등록해서 관리/사용하는 방식으로 문제를 해결하려고 한다.
```sh
remote: Permission to gunkim/main-blog.git denied to soda-neil.
fatal: unable to access 'https://github.com/gunkim/main-blog.git/': The requested URL returned error: 403
```

## SSH 키 생성하기 

`/.ssh` 디렉토리에 SSH키를 생성해준다.  

`id_rsa`를 묻는 질문에 고유한 파일명을 지정해줄 것. 예를 들면 계정명(gunkim)을 사용해주면 좋다. 내 경우 업무용 계정이 있어 SSH키를 2개 생성해주었다.

```sh
ssh-keygen -t rsa -b 4096 -C "{이메일}"
```

## Github 공개키 등록하기

[여기](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/adding-a-new-ssh-key-to-your-github-account)를 확인하면 깃헙에서 친절히 설명해주니 SSH 키를 계정에 등록해주면 된다.

## SSH config 파일 생성하기

`~/.ssh` 디렉토리에 `config` 파일을 생성해준다.  

`Host`는 자유롭게 해도 되고, `~/.ssh/{계정명}` 아까 생성한 SSH 경로만 잘 맞춰준다. 여러 개 등록이 필요하면 추가로 입력해주면 된다.

```sh
#user1 account
Host github.{계정명}
   HostName github.com
   User git
   IdentityFile ~/.ssh/{계정명}
   IdentitiesOnly yes
```

## 완료

로컬 레포지토리 경로에 들어가 origin 경로만 SSH 경로로 수정해주면 해결이다.
```sh
git remote set-url origin git@github.{계정명}:{계정명}/TIL.git
```