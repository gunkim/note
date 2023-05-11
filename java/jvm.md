# JVM(Java Virtual Machine)

Java 프로그램은 JVM(Java Virtual Machine)에서 실행됩니다. JVM은 Java 프로그램을 실행하기 위한 가상의 컴퓨터입니다. JVM은 Java 언어의 특징인 "Write once, run anywhere"를 실현하기 위해 사용됩니다. 즉, Java 프로그램은 특정 운영체제나 하드웨어에 종속되지 않고, JVM이 설치된 어떤 시스템에서도 동일하게 실행될 수 있습니다.

## Java 프로그램 수행 과정

Java 프로그램의 수행 과정은 다음과 같습니다.

![jvm](/java/img/jvm.png)

- Source: 사용자가 소스 코드를 작성합니다.
- Compiler: 컴파일러는 소스 코드를 바이트 코드(Bytecode)로 변환합니다. 이 바이트 코드는 JVM이 이해할 수 있는 형식입니다.
- Java Byte Code: Java Compiler에 의해 수행될 결과물
- Class Loader: JVM의 클래스 로더는 컴파일된 바이트 코드 파일을 로드합니다. 클래스 로더는 필요한 클래스를 동적으로 로드하고 링크합니다.
- Execution Engine: 검증된 바이트 코드는 실행 엔진에 의해 해석되거나 JIT(Just-In-Time) 컴파일러에 의해 기계어로 변환됩니다. 실행 엔진은 프로그램을 명령어 단위로 실행합니다.
- Runtime Data Area: JVM은 실행 중인 프로그램에 대한 데이터를 관리하기 위해 런타임 데이터 영역을 사용합니다. 이 영역에는 메모리, 스택, 힙 등의 다양한 영역이 있으며, 프로그램의 상태와 실행 중인 스레드 등을 관리합니다.

## Class Loader

JVM 내의 중요한 구성 요소로서, 실행 중에 필요한 클래스를 동적으로 로드하고 링크하는 역할을 담당합니다. 클래스 로더를 통해 JVM은 런타임에 클래스를 로드할 수 있습니다.

### 주요 역할

- 로딩(Loading): 클래스 로더는 클래스 파일(.class)을 로드하여 JVM 내부로 가져옵니다.
- 링크(Linking): 로드된 클래스 파일의 검증, 준비, 해결 단계를 수행합니다. 이 단계에서는 상수 풀의 심볼릭 레퍼런스를 실제 메모리 상의 레퍼런스로 변경하고, 필요한 클래스나 인터페이스를 찾아 연결합니다.
- 초기화(Initalization): 클래스 변수(static 변수)의 초기화를 수행하고, 클래스의 정적 블록(static block)을 실행합니다. 이 단계에서는 클래스의 초기화에 필요한 다른 클래스들도 초기화될 수 있습니다.
 
### 종류

클래스 로더는 다양한 클래스 로더들 사이에 계층 구조를 형성합니다. 이 계층 구조에서 클래스 로더는 부모-자식 관계를 가지며, 로드되는 클래스는 상위 클래스 로더에게 위임됩니다. 일반적으로 다음과 같은 클래스 로더 계층 구조가 있습니다.

![jvm](/java/img/class%20loader.png)

- Bootstrap Class Loader: JVM의 최상위 클래스 로더로서, JDK 내부 클래스들을 로드합니다. 보통 환경 변수나 시스템 설정으로 지정되며, Java API 클래스들이 여기에 해당합니다. 일반적으로 $JAVA_HOME/jre/lib 디렉토리에 있는 rt.jar 및 기타 핵심 라이브러리를 로드합니다.
- Extension Class Loader: 플랫폼의 확장 기능을 제공하는 클래스를 로드합니다.
- System Class Loader: 애플리케이션 클래스들을 로드하며, 클래스 패스(classpath)에 지정된 디렉토리 및 JAR 파일을 검색하여 클래스를 찾습니다. 클래스 경로 환경 변수, -classpath 또는 -cp 명령줄 옵션 에 있는 파일을 로드합니다.

### 클래스를 로드하는 방법

클래스 로더는 위임 모델을 따르기 때문에 클래스를 로드하는 과정은 일반적으로 아래와 같습니다.

![jvm](/java/img/class%20loader%20process.png)

즉 A라는 애플리케이션 클래스를 로드한다고 가정했을 때 흐름은 다음과 같습니다.
1. `System Class Loader`는 `Extension Class Loader`에게 위임합니다.
2. `Extension Class Loader`는 다시 `Bootstrap Class Loader`로 위임합니다.
3. 상위 클래스에서 클래스 로드에 실패할 경우 마지막에 `System Class Loader`가 클래스 로드를 시도합니다. 
4. 3번까지 시도했을 때 찾지 못한 경우 `ClassNotFoundException`가 발생합니다.

## 참고
- JVM Performance Optimizing 성능 분석 사례
- https://www.baeldung.com/java-classloaders