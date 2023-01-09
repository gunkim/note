---
layout: default
title: Proxy Pattern
parent: design-pattern
---

# Proxy Pattern


프록시는 대리자를 말하는 걸로 실제 객체의 대리자로 가짜 객체를 내세우는 것을 말한다.

가짜 객체는 흐름제어만 하고, 실제 객체의 응답값을 핸들링해선 안된다.

<img src="/assets/images/img/design-pattern/2023-01-09/proxy-pattern.png" width="300">

## 쓰임새

실제 객체에 접근해도 되는지 권한 검사, 캐싱, 로깅 등 다양한 방면으로 활용될 수 있다.



## code 

<script src="https://gist.github.com/gunkim/28ec64f25c23e7a1e0d5c8d32045bda3.js"></script>
