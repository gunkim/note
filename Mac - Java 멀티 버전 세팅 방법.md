# Mac Java 멀티 버전 세팅

### 필요한 jdk 버전 설치

추가 버전에 대한 패키지 명은 [이곳](https://github.com/mdogan/homebrew-zulu?tab=readme-ov-file)에서 확인

```sh
brew tap mdogan/zulu

brew install zulu-jdk11
brew install zulu-jdk-17
brew install zulu-jdk-21
```

### 설치된 jdk 버전 확인

```sh
❯ /usr/libexec/java_home -VCopy
java_home: unrecognized option `-Copy'
Matching Java Virtual Machines (3):
    21.0.2 (arm64) "Azul Systems, Inc." - "Zulu 21.32.17" /Library/Java/JavaVirtualMachines/zulu-21.jdk/Contents/Home
    17.0.10 (arm64) "Azul Systems, Inc." - "Zulu 17.48.15" /Library/Java/JavaVirtualMachines/zulu-17.jdk/Contents/Home
    11.0.22 (arm64) "Azul Systems, Inc." - "Zulu 11.70.15" /Library/Java/JavaVirtualMachines/zulu-11.jdk/Contents/Home

```

```
> sudo vi ~/.zshrc
# 맨 아래에 추가
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
export PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/sbin:$JAVA_HOME"

alias ch-java-11='export JAVA_HOME=$(/usr/libexec/java_home -v 11); export PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/sbin:$JAVA_HOME"'
alias ch-java-17='export JAVA_HOME=$(/usr/libexec/java_home -v 17); export PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/sbin:$JAVA_HOME"'
alias ch-java-21='export JAVA_HOME=$(/usr/libexec/java_home -v 21); export PATH="/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/usr/local/sbin:$JAVA_HOME"'
```
