# 코틀린 컨벤션
- Property declarations and initializer blocks (속성 선언들과 초기화 블록)
- Secondary constructors (부생성자들)
- Method declarations (메서드들)
- Companion object (컴패니언 오브젝트)

이너 클래스의 경우, 내부에서 사용한다면 사용하는 메소드 바로 아래애 두거나, 외부에서만 사용하는 이너 클래스라면 컴패니언 오브젝트 밑에 위치하게 두면 된다.

## 예시 코드

```kt
class Person {
    // 속성
    private var name: String? = null

    // 초기화 블록
    init {
        name = "gunkim"
    }

    // 부 생성자
    constructor(name: String) {
        this.name = name
    }

    // 메소드
    fun printName() = println(name)

    fun useNested() =

    // 이너 클래스
    class NestedForInternal {
        NestedForInternal()
    }
    
    class NestedForInternal {

    }

    // 컴패니언 오브젝트
    companion object {
        const val DURATION = 300
    }

    class NestedForExternalClient {

    }
}
```
# 참고
[코틀린 공식 문서](https://kotlinlang.org/docs/coding-conventions.html#class-layout)