# L2 Layer Switch

## L2 단계에서의 Switch
Access Switch, Distribution Switch 크게 두 종류의 Switch가 존재한다.

<img src="/assets/images/img/network/2022-12-23/access-distribution.png" width="500">

## L2 Access Switch
<img src="/assets/images/img/network/2022-12-23/access.png" width="500">


EndPoint(일반적으로 PC)와 직접 연결되는 스위치로 [Mac 주소](https://ko.wikipedia.org/wiki/MAC_%EC%A3%BC%EC%86%8C)를 기반으로 스위칭된다. EndPoint와 연결될 경우 Link-Up, 연결이 불량하거나 해제됐을 경우 Link-Down이라고 한다.

EndPoint와의 연결을 Link-Up이라고 하지만 L2 Distribution Switch와 연결될 때는 UpLink라고 한다.

## L2 Distribution Switch

<img src="/assets/images/img/network/2022-12-23/distribution.jpg" width="500">


EndPoint와 직접 연결될 수 있지만 일반적으로 Access Switch 간 브리지 및 링크 역할을 수행한다.
또한 VLAN(Virtual LAN) 기능 제공을 위해 사용된다.
