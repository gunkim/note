---
layout: default
title: 집에서 쓰는 장난감 라즈베리파이 세팅
parent: DevOps
---

# 집에서 쓰는 장난감 라즈베리파이 세팅

## 사양
- 라즈베리파이 4B 본체
- Argon One M.2 SSD 케이스
    - 파워 버튼 지원
    - 팬 속 지원
- 마이크론 M.2 SSD 250GB

과하네...?

## 운영체제 설치

1. [Raspberry Pi OS Installer 설치](https://www.raspberrypi.com/software/)
2. 운영체제 종류 선택 후 완료
3. 라즈베리파이 스토리지에 `ssh`라는 이름의 파일 생성 (ssh 접속을 위해)

## SSH 접속

공유기에서 라즈베리파이에 할당된 IP 확인 후 접속 (만약 무선랜이라면 따로 설정해줘야 함)

```sh
$ ssh ubuntu@IP
```

## 케이스 설정

### 쿨링팬/전원 버튼 구동 스크립트
```sh
$ curl https://download.argon40.com/argon1.sh|bash
```

설치 완료 후 아래 세팅 값으로 동작한다.

|CPU 온도|팬 속도|
|-|-|
|55℃|10%|
|60℃|50%|
|65℃|100%|

### 커스텀 팬 속도 설정

```sh
$ argonone-config
```

```sh
--------------------------------------
Argon One Fan Speed Configuration Tool
--------------------------------------
WARNING: This will remove existing configuration.
Press Y to continue:Y
Thank you.

Select fan mode:
1. Always on # 항상 켜두기
2. Adjust to temperatures (55C, 60C, and 65C) # 55-60-65도일 때 팬 속도 지정
3. Customize behavior # 온도에 따른 팬 속도 커스텀
4. Cancel # 취소
Enter Number (1-4):
```

### 전원 버튼 기능

라즈베리파이 본체만 쓰면 불편한 점이 전원 관리인데 편하다

|Argon State|Action|Function|
|-|-|-|
|OFF|Short Press|Turn ON|
|ON|Long Press (>= 3s)|Soft Shutdown and Power Cut|
|ON|Short Press (<3s)|Nothing|
|ON|Double tap|Reboot|
|ON|Long Press (>= 5s)|Forced Shutdown|

## 초기 설정

### 서버 세션 타임아웃 설정

```sh
$ sudo vi ~/.profile
HISTTIMEFORMAT="%F %T -- "    ## history 명령 결과에 시간값 추가
export HISTTIMEFORMAT
export TMOUT=600              ## 세션 타임아웃 설정

$ source ~/.profile
```

### shell 로깅

```sh
$ sudo vi ~/.bashrc
tty=`tty | awk -F"/dev/" '{print $2}'`
IP=`w | grep "$tty" | awk '{print $3}'`
export PROMPT_COMMAND='logger -p local0.debug "[USER]$(whoami) [IP]$IP [PID]$$ [PWD]`pwd` [COMMAND] $(history 1 | sed "s/^[ ]*[0-9]\+[ ]*//" )"'

$ source  ~/.bashrc
```

```sh
$ sudo vi /etc/rsyslog.d/50-default.conf
local0.*                        /var/log/command.log
# 원격지에 로그를 남길 경우
local0.*                        @원격지서버IP

$ sudo service rsyslog restart
$ tail -f /var/log/command.log # 로그 확인
```

### shell prompt 설정

```sh
$ sudo vi ~/.bashrc
USERNAME=GUNNY
PS1='[\e[1;31m$USERNAME\e[0m][\e[1;32m\t\e[0m][\e[1;33m\u\e[0m@\e[1;36m\h\e[0m \w] \n\$ \[\033[00m\]'

$ source ~/.bashrc
```

### Timezone 설정

```sh
$ sudo timedatectl set-timezone Asia/Seoul
```