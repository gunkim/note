# Kotlin의 원시 - 참조 타입 전략

## 변수 선언 시
컴파일 시점에 값을 값을 확정할 수 있으면 자바로 변환될 때 long(원시타입), 아니면 Long(참조타입)으로 변환된다.

```kotlin
val number1: Long = 1L // long
val number2: Long? = 1L // long
var number3: Long? = null
number3 = 1L // Long
```

## 배열

메모리 최적화가 필요하면 `IntArray`를 쓰고 평상시에는 `List`를 쓰면 될 듯 하다.

~Array 시리즈는 다른 원시타입 애들도 제공해주니 알맞게 쓰면 될 것 같다.
- IntArray
- DoubleArray
- ...

```kotlin
val arr1: IntArray = intArrayOf(1, 2, 3) // int[]
val arr2: Array<Int> = arrayOf(1, 2, 3) // Integer[]
val arr3: List<Int> = listOf(1, 2, 3) // List<Integer>
```