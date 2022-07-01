# System Structure & Program Execution

![](https://user-images.githubusercontent.com/64277114/105965201-79e87280-60c6-11eb-9fd7-ac780e772b43.png)

## Mode Bit

사용자 프로그램의 잘못된 수행으로 다른 프로그램 및 운영체제에 피해가 가지 않도록 하기 위한 보호 장치

#### Operation 모드

Mode Bit을 통해 하드웨어적으로 두 가지 모드의 Operation 지원

- 1 사용자 모드: 사용자 프로그램 수행
- 2 커널(모니터) 모드: OS 코드 수행

보안을 해칠 수 있는 중요한 명령어는 커널(모니터) 모드에서만 수행 가능한 *특권 명령*으로 규정
Interrupt나 Exception 발생 시 하드웨어가 Mode Bit을 0으로 바꿈
사용자 프로그램에게 CPU를 넘기기 전에 Mode Bit을 1로 세팅

## Timer

- 정해진 시간이 흐른 뒤 운영체제에게 제어권이 넘어가도록 인터럽트를 발생
- 타이머는 매 클럭 틱 때마다 1씩 감소
- 타이머 값이 0이 되면 타이머 인터럽트 발생
- CPU를 특정 프로그램이 독점하는 것으로부터 보호

Time Sharing을 구현하기 위해 널리 이용되며 현재 시간을 계산하기 위해서도 사용된다.

## I/O Device Controller

- 해당 I/O 장치 유형을 관리하는 일종의 작은 CPU
- 제어 정보를 위해 Control Register, Status Register를 가짐
- Local Buffer를 가짐 (일종의 Data Register)

I/O는 실제 Device와 Local Buffer 사이에서 일어남
Device Controller는 I/O가 끝났을 경우 Interrupt로 CPU에 그 사실을 알림

### Device Driver (장치구동기)

OS 코드 중 각 장치별 처리루틴 -> Software

### Device Controller (장치제어기)

각 장치를 통제하는 일종의 작은 CPU -> Hardware

## 입출력(I/O)의 수행

- 모든 입출력 명령은 특권 명령
- 사용자 프로그램은 어떻게 I/O를 하는가?
    - 시스템 콜(System Call)
        - 사용자 프로그램은 운영체제에게 I/O 요청
    - trap을 사용하여 인터럽트 벡터의 특정 위치로 이동
    - 제어권을 인터럽트 벡터가 가리키는 인터럽트 서비스 루틴으로 이동
    - 올바른 I/O 요청인지 확인 후 수행
    - I/O 완료 시 제어권을 시스템 콜(System Call) 다음 명령으로 옮김

## 인터럽트(Interrupt)

인터럽트 당한 시점의 레지스터와 Program Counter를 save한 후 CPU 제어를 인터럽트 처리 루틴에 넘긴다

### 넓은 의미에서의 인터럽트

- Interrupt(하드웨어 인터럽트)
    - 하드웨어가 발생시킨 인터럽트
- Trap(소프트웨어 인터럽트)
    - Exception: 프로그램이 오류를 범한 경우
    - System Call: 프로그램이 커널 함수를 호출하는 경우

### 관련 용어

- 인터럽트 벡터
    - 해당 인터럽트의 처리 루틴 주소를 가지고 있음
- 인터럽트 처리 루틴(Interrupt Service Routine)
    - 해당 인터럽트를 처리하는 커널 함수