# Pair와 Triple

두개의 객체를 전달할 때 쓰는 `Pair`와 세개의 객체를 전달할 때 쓰는 `Triple` 객체에 대해서 알게 되어 정리한다.

## Pair

```kt
val pair1 = Pair("Hello", "World!") // Pair<String, String>
val pair2 = "Hello" to "World!" // Pair<String, String>
//구조분해 할당 가능

val (hello, world) = pair2
val list = pair2.toList() // List로도 변환 가능
```

## Triple

```kt
val triple = Triple<String, String, Int>("Hello", "World", "!") // Triple<String, String, String>
val (hello, world, dummy) = triple
val list = triple.toList() // List로도 변환 가능
```
