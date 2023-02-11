---
layout: default
title: 논리 연산
parent: Computer Science
nav_order: 2
---

# 논리 연산

0과 1로 표현되는 비트는 참/거짓으로 분명히 나타낼 수 있는 명제를 나타내는데 사용할 수 있다.

## 불리언 대수

1800년대 영국 수학자 조지 불이 만들어 냈다.

기본적인 불리언 연산자는 NOT, AND, OR 세가지이고, 추가로 XOR이라는 합성 연산까지 존재한다.

- NOT: 논리적 반대를 의미한다. 참인 비트에 NOT 연산을 하면 거짓이 된다. (예: NOT(거짓) = 참)
- AND: 두 비트 모두 참일 경우에만 결과가 참이 된다. (예: 참 AND 참 = 참)
- OR: 두 비트 중 하나만 참이어도 결과가 참이 된다. (예: 참 OR 거짓 = 참)
- XOR(Exclusive OR): 두 비트가 다른 값인 경우에만 참이 된다. (예: 참 XOR 거짓 = 참, 참 XOR 참 = 거짓)

## 드모르간 법칙
- a AND b = NOT(NOT a OR NOT b)와 같다.

1800년대 영국 수학자 오거스터스 드모르간은 불리언 대수에 적용할 수 있는 법칙을 추가로 알아냈다.

이 법칙을 충분히 사용하면 AND 연산을 OR 연산으로, OR 연산을 AND 연산으로 대신할 수 있다는 것을 의미한다. 이는 NAND 게이트 구조 설계에 활용되며 그렇게 만들어진 메모리가 플래시 메모리(NAND FLASY MEMORY)이다.

## 기본 공식
- 교환법칙 : A + B = B + A
- 결합법칙 : A + (B + C) = (A + B) + C
- 분배법칙 : A * (B + C) =  A * B + A * C // A + B * C = (A + B) * (A + C)
- 멱등법칙 : A + A = A  // A * A = A
- 보수법칙 : A + A' = 1  // A * A' = 0
- 항등법칙 : A + 0 = A // A + 1 = A // A * 0 = 0 // A * 1 = A
- 드모르간 법칙 : (A + B)' = A' · B' // (A · B)' = A' + B'

## 참고
[위키피디아 논리 연산](https://ko.wikipedia.org/wiki/%EB%85%BC%EB%A6%AC_%EC%97%B0%EC%82%B0)  
[위키피디아 드모르간 법칙](https://ko.wikipedia.org/wiki/%EB%93%9C_%EB%AA%A8%EB%A5%B4%EA%B0%84%EC%9D%98_%EB%B2%95%EC%B9%99)