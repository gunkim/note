# JVM(Java Virtual Machine)

## Java 프로그램 수행 과정

JVM 상에서 Class 파일들을 로딩시키고, 로딩된 Class 파일들은 Execute Engine을 통해 해석되는 과정을 거친다.

헤석된 프로그램은 Runtime Data Area에 배치되어 실질적으로 실행되게 된다.

![jvm](/java/img/jvm.png)

- Source: 사용자가 작성한 소스 코드
- Compiler: 소스 코드를 JVM이 해석할 수 있는 Java Byte Code로 변환한다.
- Java Byte Code: Java Compiler에 의해 수행될 결과물
- Class Loader: JVM 내로 .class 파일들을 Load하여 Loading된 클래스들을 Runtime Data Area에 배치된다.
- Execution Engine: Loading된 클래스의 Byte Code를 해석(Interpret)한다.
- Runtime Data Area: JVM 프로세스가 프로그램을 수행하기 위해 OS에서 할당 받은 메모리 공간이다.

## 참고
- JVM Performance Optimizing 성능 분석 사례