# mysql 패키지 삭제

apt-get purge를 하게 되면 패키지와 함께 설정 파일도 삭제가 된다.

```sh
sudo apt-get purge mysql-server
sudo apt-get purge mysql-common
```

# mysql 찌꺼기 제거

패키지가 삭제되더라도 찌꺼기가 남는데 클린 삭제를 위해 삭제해준다.

```
sudo rm -rf /var/log/mysql
sudo rm -rf /var/log/mysql.*
sudo rm -rf /var/lib/mysql
sudo rm -rf /etc/mysql
```

# 참고

[https://velog.io/@michael00987/MYSQL-%EC%84%A4%EC%B9%98-%EC%9E%AC%EC%84%A4%EC%B9%98](https://velog.io/@michael00987/MYSQL-%EC%84%A4%EC%B9%98-%EC%9E%AC%EC%84%A4%EC%B9%98)
