# Introduction to Operating Systems

## 운영체제란?

하드웨어 위에 설치됨.

```
좁은 의미 = 커널
넓은 의미 = 유틸리티까지 포함
```

## 운영체제의 목적

1. 컴퓨터의 자원을 효율적으로 관리
    1. 주어진 하드웨어에서 최대한의 성능을 이끌어냄
    2. 사용자 간 형평성 있는 자원 분배
    3. 사용자 및 운영체제 자신 보호
2. 컴퓨터를 편리하게 사용할 수 있는 환경 제공
    1. 동시 사용지/프로그램들이 각각 독자적으로 실행되는 것처럼
    2. 하드웨어를 직접 조작하는 복잡한 부분을 대신함

## 운영체제의 분류

### 동시 작업 가능 여부

- 단일 작업(Single Tasking)
    - MS-DOS
- 다중 작업(Multi Tasking)
    - UNIX, Windows

### 사용자 수

- 단일 사용자(Single User)
    - MS-DOS, Windows
- 다중 사용자(Multi User)
    - UNIX, NT Server

### 처리 방식
- 시분할(Time Sharing)
  - 여러 작업 수행 시 컴퓨터 처리 능력을 일정 시간 단위로 분할하여 사용
  - 일괄처리에 비해 짧은 응답시간
  - 인터랙티브한 방식
- 실시간(Realtime)
  - 정해진 시간 안에 어떠한 일이 반드시 종료됨(Deadline)을 보장
  - Hard Realtime System(경성 실시간 시스템) - Deadline을 지키지 못했을 시 완전한 실패로 판정한다
  - Soft Realtime System(연성 실시간 시스템) - Deadline을 지키지 못하고 시간을 초과하더라도 중대한 문제가 발생하지 않는다
- 일괄 처리(Batch Processing)
  - 작업 요청을 일정량 모아서 한꺼번에 처리
  - 작업 완료까지 기다려야 함

## 운영체제 예시
### 유닉스
- 코드 대부분이 C언어
- 높은 이식성
- 최소한의 커널 구조
- 복잡한 시스템에 맞게 확장 용이
- 소스코드 공개
- 프로그램 개발 용이
- 다양한 버전(Linux, Solaris, SunOS)

### DOS(Disk Operating System)
- MS사가 1981년 IBM PC를 위해 개발
- 단일 사용자용 OS, 메모리 관리 능력 한계(주기억 장치 640kb)

### Windows
- MS사의 다중 작업용 GUI 기반 OS
- Plug and Play, 네트워크 환경 강화
- Dos용 프로그램과 호환성 제공
- 불안정성(예전 얘기인 듯 함)
- 풍부한 지원 S/W

### Handheld Device를 위한 OS
- Palm OS, Pocket PC, Tiny OS
