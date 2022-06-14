> Mysql 8.X 버전 기준

# DB 생성

```sql
CREATE DATABASE {DB이름};
```

ex)

```sql
CREATE DATABASE TESTDB;
```

# 사용자 생성

```sql
CREATE USER '{username}'@'localhost' IDENTIFIED BY '{password}';
CREATE USER '{username}'@'%' IDENTIFIED BY '{password}';
```

- `%`는 외부 접속 허용
- `localhost`는 local에서만 접속 허용

ex)

```sql
mysql> CREATE USER 'TESTUSER'@'localhost' IDENTIFIED BY '1234';
```

# 사용자 권한 부여

```sql
GRANT ALL PRIVILEGES ON {database}.* TO '{username}'@'localhost';
FLUSH PRIVILEGES;
```

`{database}`에 대한 모든 권한을 `{username}`에 부여한다.

### 권한 종류

> 관리자급 권한은 줄 일이 거의 없어서 제외했다.
> 권한은 모든 권한을 줄 수도 있지만, 아래와 같은 세부 권한으로도 설정할 수 있다.

| 권한                           | 내용                                   |
| ------------------------------ | -------------------------------------- |
| CREATE, ALTER, DROP            | 테이블 생성, 수정, 삭제                |
| SELECT, INSERT, UPDATE, DELETE | 테이블의 레코드 조회, 입력, 수정, 삭제 |

ex)

1. TESTDB에 대한 모든 권한을 TESTUSER에 부여한다.

```sql
GRANT ALL PRIVILEGES ON TESTDB.* TO 'TESTUSER'@'localhost';
FLUSH PRIVILEGES;
```

2.TESTUSER에게 모든 권한을 부여한다.

```sql
GRANT ALL PRIVILEGES ON *.* TO 'TESTUSER'@'localhost';
FLUSH PRIVILEGES;
```

3. TESTUSER에게 TESTDB에 대한 레코드 조회 권한만을 허용한다.

```sql
grant select on TESTDB.* to 'TESTUSER'@'localhost';
FLUSH PRIVILEGES;
```

4. TESTDB의 MEMBER 테이블에 대한 모든 권한을 TESTUSER에 부여한다.

```sql
GRANT ALL PRIVILEGES ON TESTDB.MEMBER TO 'TESTUSER'@'localhost';
FLUSH PRIVILEGES;
```
