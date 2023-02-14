---
layout: default
title: 정수 표현과 덧셈
parent: Computer Science
nav_order: 2
---

# 정수 표현과 덧셈

우리가 아는 10진수 13은 1101(2)로 표현이 가능하다.

## 양의 정수
### 10진수 -> 2진수 변환

2로 나눈 나머지를 이용해 계산해주면 된다. 

35(10) -> 100011(2)

<img src="/assets/images/img/computer-science/2jinsu.png" width="200">

2진수를 10진수로 변환할 때는 2진수 값에 2진수 제곱근을 곱해 서로 더해주면 된다.

<img src="/assets/images/img/computer-science/2jinsu2.png" width="400">

### 2진수 -> 8진수 변환

8은 2의 3승이기 때문에 2진수를 3자리씩 끊어서 2진수 값에 2진수 제곱근을 더해 각각 계산해주면 된다.

<img src="/assets/images/img/computer-science/2jinsu3.png" width="400">

### 2진수 -> 16

8진수 변환과 똑같이 16은 2의 3승이기 때문에 2진수를 4자리씩 끊어서 8진수 변환과 같이 계산해주면 된다.

<img src="/assets/images/img/computer-science/2jinsu4.png" width="400">

### 덧셈

그냥 더해주면 된다.

<img src="/assets/images/img/computer-science/2jinsu5.png">

## 음의 정수

### 부호와 크기 표현법

음의 정수를 표현할 때 맨 앞 비트(MSB)를 부호(sign) 비트로 사용하는 방법이다. 이 방법은 부호를 표현하는데 1비트를 낭비하고, 
XOR과 AND를 이용한 덧셈을 사용할 수 없다는 단점 때문에 널리 사용되지는 않는다.

- +4 -> 0100(2)  
- -4 -> 1100(2)

### 1의 보수 표현법

양수의 모든 비트를 뒤집는 방식이다. 부호와 크기 표현법과 비슷하게 맨 앞 비트(MSB)를 부호 비트로 사용한다.

- +4 -> 0100(2)
- -4 -> 1011(2)

이 방식도 덧셈은 쉽지 않다. 맨 앞 비트(MSB)에서 올림이 발생하는 순환올림이 발생한다.
맨 뒤 비트(LSB)로 올림 비트를 전달하기 위한 특정 하드웨어를 추가해야 이 방식이 구현 가능하므로 쉽지 않다.

<img src="/assets/images/img/computer-science/2jinsu6.png" width="300"/>

### 2의 보수 표현법

양수에서 1의 보수를 구한 후 1을 더하는 방식이다. 이 방식은 특정 하드웨어를 추가할 수 없고, AND와 XOR을 통해 덧셈을 수행해야할 경우 용이하다. 
맨 앞 비트(MSB)에서 올림이 발생할 경우 버린다. 가장 널리 쓰이는 방식이기도 하다.

- +4 -> 0100(2)
- -4 -> 1011(2) + 0001(2) = 1100(2)
