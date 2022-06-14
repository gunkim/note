# M1 맥북에서 Docker 이미지 받기

`--platform linux/amd64` 옵션을 추가하여 ARM 환경이라는 것을 명시해주면 된다.

```shell
docker pull --platform linux/x86_64 mysql
```
