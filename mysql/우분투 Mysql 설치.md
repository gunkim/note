# Mysql 패키지 설치

### 1. apt 업데이트

```sh
sudo apt-get update
```

### 2. mysql 패키지 설치

설치 중간에 비밀번호 물어보는데 설정 안할 경우 Ubuntu 비밀번호로 자동 설정됨

```sh
sudo apt-get install mysql-server
```

# Mysql 실행

```sh
sudo systemctl start mysql
```

# Ubuntu 재시작 시 Mysql 자동실행 설정

```sh
sudo systemctl enable mysql
```

# 외부 접속을 위한 방화벽 허용

외부 접속 안할거라면 안해도 됨

```sh
sudo ufw allow mysql
```

# Mysql 접속

```sh
sudo mysql -u root -p
```

or

```sh
sudo /usr/bin/mysql -u root -p
```
