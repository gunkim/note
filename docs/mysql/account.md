---
layout: default
title: 사용자 계정
parent: 🐬 Mysql
---

# 사용자 계정

## 사용자 식별

> '사용자 ID'@'호스트' 예) 'root'@'localhost'

사용자 계정은 사용자 ID와 호스트까지 포함해서 식별한다.

모든 호스트에 대해서 허용해주고 싶다면 호스트 부분을 '%'을 사용해주면 된다.

### 사용자 ID 중복

```
'gunny'@'localhost' # 패스워드 abc
'gunny'@'%' # 패스워드 123
```

만약 동일한 사용자 ID가 여러 호스트에서 사용된다면 mysql 접속 시 더 좁은 범위의 호스트를 선택한다.

```shell
$ mysql -ugunny -p 123 # 우선 더 좁은 범위의 'gunny'@'localhost' 계정의 비밀번호와 비교하게 된다. -> 접속 실패
$ mysql -ugunny -p abc # -> 접속 성공
```

중첩된 계정은 이 같은 상황이 발생할 가능성이 있으므로 생성 시 주의해야 한다.

## 시스템 & 유저 계정

Mysql 8.0부터는 SYSTEM_USER 권한을 가지고 있냐에 따라 시스템 계정(system account)과 유저 계정(user account)으로 나뉜다.

시스템 계정은 DBA 계정에만 SYSTEM.USER 권한을 할당하기 위해서 도입되었다. 아래와 같은 중요 작업은 시스템 계정으로만 수행 가능하다.
- 계정 관리(계정 생성 및 삭제, 계정 권한 부여 및 제거)
- 다른 세션 또는 그 세션에서 실행 중인 쿼리를 강제 종료
- 스토어드 프로그램 생성 시 DEFINER를 타 사용자로 설정

## 기본적으로 내장된 계정들

Mysql 서버에는 기본적으로 아래와 같이 잠겨있는 계정들이 내장되어 있다.

- 'mysql.sys'@'localhost' - Mysql 8.0부터 기본으로 내장된 sys 스키마의 객체(뷰나 함수, 프로시저)들의 DEFINER로 사용된다.
- 'mysql.session'@'localhost' - Mysql 플러그인이 서버로 접근할 때 사용되는 계정
- 'mysql.infoschema'@'localhost' - information_schema에 정의된 뷰의 DEFINER로 사용된다.

## 계정 생성

Mysql 8.0 부터는 USER 생성 권한과 GRANT 생성 권한의 분리를 위해 CREATE USER, GRANT 명령으로 나뉘어 졌으므로 계정 생성을 위해서 CREATE USER 명령을 사용하면 된다.

```sql
mysql> CREATE USER 'user'@'%'
         IDENTIFIED WITH 'mysql_native_password' BY 'password'
         REQUIRE NONE
         PASSWORD EXPIRE INTERVAL 30 DAY
         ACCOUNT UNLOCK
         PASSWORD HISTORY DEFAULT
         PASSWORD REUSE INTERVAL DEFAULT
         PASSWORD REQUIRE CURRENT DEFAULT
```

### IDENTIFIED WITH, IDENTIFIED BY - 계정 인증 방식과 비밀번호

```
IDENTIFIED WITH '비밀번호 인증 방식' BY '비밀번호'
IDENTIFIED BY '비밀번호'
```

- Native Pluggable Authentication

Mysql 5.7까지 사용되던 방식, 비밀번호에 대한 해시(SHA-1) 값을 저장해두고 클라이언트가 보낸 해시값과 일치 여부를 비교하는 인증 방식
동일한 키 값에 대해 동일한 해시값을 만들어 낸다.

- Caching SHA-2 Pluggable Authentication

Mysql 5.6 버전에 도입되고 8.0 버전에서는 조금 더 보완된 인증 방식으로, 암호화 해시(SHA-2, 256비트)를 사용한다. Native Pluggable Authentication과는 암호화 해시 알고리즘의 차이이다.
해당 인증 방식은 내부적으로 Salt 키를 활용하며 수천 번의 해시 계산을 수행해 결과를 만들어 성능이 매우 떨어지는데 이를 보완하기 위해 메모리에 캐시해서 사용하게 된다.

이 인증 방식을 사용하기 위해 SSL/TLS 또는 RSA 키페어를 반드시 사용해야 한다.

- PAM Pluggable Authentication

유닉스, 리눅스 패스워드 또는 LDAP 같은 외부 인증을 사용할 수 있게 해주는 인증 방식으로 Mysql 엔터프라이즈 에디션에서만 사용 가능하다.

- LDAP Pluggable Authentication

LDAP를 이용한 외부 인증을 사용할 수 있게 해주는 인증 방식으로 Mysql 엔터프라이즈 에디션에서만 사용 가능하다.

### REQUIRE - SSL옵션

Mysql 서버에 접속할 때 암호화된 SSL/TLS 채널을 사용할지 결정하는 옵션이다. 별도로 설정하지 않으면 비암호화 채널로 연결하게 된다.

하지만 Caching SHA-2 Authentication 인증 방식을 사용한다면 암호화된 채널만으로 접속하게 된다.

### PASSWORD EXPIRE

```
PASSWORD EXPIRE # 계정 생성과 동시에 비밀번호 만료 처리
PASSWORD EXPIRE NEVER # 계정 비밀번호의 만료 기간 없음
PASSWORD EXPIRE DEFAULT # default_password_lifetime 시스템 변수에 저장된 기간으로 비밀번호 유효기간 설정
PASSWORD EXPIRE INTERVAL n DAY # 비밀번호 유효기간을 n일 동안으로 설정
```

비밀번호의 유효기간을 설정하는 옵션이다. 별도로 명시하지 않으면 `default_password_lifetime` 시스템 변수에 저장된 기간으로 자동 설정된다.

### PASSWORD HISTORY

```
PASSWORD HISTORY DEFAULT # password_history 시스템 변수에 저장된 개수만큼 비밀번호 이력을 저장한다.
PASSWORD HISTORY n # 비밀번호 이력을 최근 n개까지만 저장한다.
```

한 번 사용했던 비밀번호를 재사용하지 못하게 하는 옵션이다. 비밀번호 이력 관리를 위해 `password_history` 테이블을 사용한다.

### PASSWORD REUSE INTERVAL

```
PASSWORD REUSE INTERVAL DEFAULT # password_reuse_interval 변수에 저장된 기간으로 설정
PASSWORD REUSE INTERVAL n DAY # n일 이후에 비밀번호를 재사용할 수 있게 설정
```

한번 사용했던 비밀번호의 재사용 금지 기간을 설정하는 옵션이다. 명시하지 않으면 `password_reuse_interval` 시스템 변수에 저장된 기간으로 설정된다.

### PASSWORD REQUIRE

```
PASSWORD REQUIRE CURRENT # 비밀번호 변경 시 현재 비밀번호를 먼저 입력하도록 설정
PASSWORD REQUIRE OPTIONAL # 비밀번호 변경 시 현재 비밀번호를 입력하지 않아도 되도록 설정
PASSWORD REQUIRE DEFAULT # password_require_current 시스템 변수 값으로 설정
```

비밀번호가 만료되어 새로운 비밀번호로 변경할 때 현재 비밀번호를 필요로 할지 결정하는 옵션이다. 별도로 명시하지 않으면 `password_require_current` 시스템 변수의 값으로 설정된다.

### ACCOUNT LOCK / UNLOCK

```
ACCOUNT LOCK # 계정을 사용하지 못하게 잠금
ACCOUNT UNLOCK # 잠긴 계정을 다시 사용 가능 상태로 잠금 해제
```

계정 생성 시 ALTER TABLE 명령을 사용해 계정 정보를 변경할 때 계정을 사용하지 못하게 잠글지 여부를 설정하는 옵션이다.

## 참고

Real Mysql 1권