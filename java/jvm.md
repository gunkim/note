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

## Class Loader

- 자바는 런타임에 클래스를 동적으로 로드하는데, 그 역할을 담당하는 것이 클래스 로더이다.  
- 클래스 로더 덕분에 JVM은 Java 프로그램을 실행하기 위해 기본 파일이나 파일 시스템에 대해 알 필요가 없다는 장점이 있다. 
- Java 클래스는 한 번에 모두 메모리에 로드되지 않고 애플리케이션에서 필요할 때 로드된다.

### 종류

기본적으로 클래스 로더는 관심사에 따라 3가지로 분류되며 각 클래스 로더는 부모-자식 관계를 갖는다.

클래스 로더는 위임 모델을 따르며 클래스를 로드하기 위해 클래스 로더 인스턴스는 상위 클래스 로더에 위임한다. 즉 A라는 애플리케이션 클래스를 로드한다고 가정했을 때 `System Class Loader`는 `Extension Class Loader`에게 위임하고, `Extension Class Loader`는 다시 `Bootstrap Class Loader`로 위임한다. 상위 클래스에서 클래스 로드에 실패할 경우 마지막에 `System Class Loader`가 클래스 로드를 시도한다. 이 때도 찾지 못할 경우 `ClassNotFoundException`가 발생한다.

![jvm](/java/img/class%20loader.png)

- Bootstrap Class Loader: JDK 내부 클래스, 일반적으로 $JAVA_HOME/jre/lib 디렉토리 에 있는 rt.jar 및 기타 핵심 라이브러리를 로드한다.
- Extension Class Loader: 플랫폼에서 실행 중인 모든 애플리케이션에서 사용할 수 있도록 표준 핵심 Java 클래스의 확장 로드를 처리한다.
- System Class Loader: 모든 애플리케이션 레벨 클래스를 JVM으로 로드한다. 클래스 경로 환경 변수, -classpath 또는 -cp 명령줄 옵션 에 있는 파일을 로드한다.

## 참고
- JVM Performance Optimizing 성능 분석 사례
- https://www.baeldung.com/java-classloaders