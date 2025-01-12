Manjaro Linux 기준으로 Firefox와 Chrome에서 발생하는 터치패드 제스처와 한영 전환 문제를 해결하는 방법을 소개한다.

## 한영 전환 문제 해결
### Wayland IME 활성화
Wayland를 사용 중이라면, Chrome에서 한영 전환 문제를 다음과 같이 해결할 수 있다. X11을 사용한다면 [여기](https://xangmin.tistory.com/78) 블로그를 참고하면 된다.
1. Chrome 주소창에 `chrome://flags`를 입력한다.
2. 검색란에 `wayland-text-input-v3`를 입력해 해당 플래그를 찾는다.
3. 'Enabled'로 설정한다.
4. Chrome을 재시작한다.
이렇게 하면 Wayland IME가 활성화되어 한영 전환이 정상 작동한다.
## 터치패드 제스처 문제 해결
### Ozone 플랫폼 설정
터치패드 제스처 기능을 활성화하려면 다음 단계를 수행한다:
1. Chrome의 실행 옵션에 `--ozone-platform=wayland`를 추가해 Preferred Ozone 플랫폼을 Wayland로 변경한다.
2. Chrome 주소창에 `chrome://flags`를 다시 입력한다.
3. 검색란에 `TouchpadOverscrollHistoryNavigation`을 입력해 해당 플래그를 찾는다.
4. 'Enabled'로 설정한다.
5. Chrome을 재시작해 변경 사항을 적용한 후, 터치패드 제스처가 정상 작동하는지 확인한다.
### Apps에 Chrome 실행 시 자동 옵션 넣는 방법
```sh
sudo vim /usr/share/applications/google-chrome.desktop
```
Exec= ~~ 부분들을 모두 찾아 --enable-features=TouchpadOverscrollHistoryNavigation을 추가해주면 된다. 본인은 chrome://flags 에 `TouchpadOverscrollHistoryNavigation` 옵션이 보이지 않았음.

이 방법들을 통해 Manjaro Linux에서 Firefox의 터치패드 제스처와 Chrome의 한영 전환 문제를 해결할 수 있다.