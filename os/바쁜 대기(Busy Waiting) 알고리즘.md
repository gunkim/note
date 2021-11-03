# 바쁜 대기 알고리즘이란?

- OS에서는 원하는 자원을 얻기 위해 기다리는 것이 아니라 권한을 얻을 때까지 확인하는 것을 의미한다.
- CPU의 자원을 쓸데 없이 낭비하기 때문에 좋지 않은 쓰레드 동기화 방식이다.

이름처럼 자원을 얻기 위해 바쁘게 계속 확인하기 때문에 `바쁜 대기`라고 기억하면 될 것 같다. 바쁜 만큼 CPU 자원을 사용&낭비하기 때문에 쓰레드 동기화를 위해서는 뮤텍스 세마포어(Mutual Exclusion) 또는 Monitor를 사용하는 것이 좋다고 한다.

## 그럼 어떨 때 사용하는 게 좋을까?

- 자원의 권한을 얻는데 많은 시간이 소요되지 않는 상황인 경우
- 문맥 교환(Context Switching) 비용보다 성능적으로 더 우수한 상황인 경우

# 다른 방법 `Sleeping`

- 자원의 권한을 얻기 위해 기다리는 시간을 `wait queue`에 실행 중인 Thread 정보를 담고 다른 Thread에게 CPU를 양보하는 것을 의미
- 커널은 권한 이벤트가 발생하면 `wait queue`에 담긴 Thread를 깨워 CPU를 부여

Busy Wating는 권한을 얻을 때까지 계속 확인하는 반면 Sleeping은 말 그대로 원하는 자원을 얻기 위해 그냥 계속 기다리는 방법이다.

## Sleeping은 어떨 때 사용할까?

기다리는 시간이 예측 불가능한 경우에 사용한다.

## 단점

wait queue에 넣는 비용 + Context Switching 비용이 든다.

# 비유

형제가 있는 집안에 컴퓨터가 하나 있을 때, 동생이 형보고 언제 나오냐고 계속 확인하는 방법이 바쁜 대기이고, 형이 컴퓨터에서 비킬 때까지 동생은 침대 가서 자고 있는 걸 Sleeping이다~ 외우면 될 것 같다.

마찬가지로 형이 금방 나올 거 같을 때 옆에서 계속 찡얼대는 거지, 오래 걸릴 거 같으면 동생은 그냥 자기 방가서 자다가 오는 게 더 효율적이다...

# 자바로 구현한 예제

어떤 식으로 구현할까 궁금해서 찾아봤는데, [StackOverFlow](https://stackoverflow.com/questions/24948791/what-is-fast-wait-notify-or-busy-wait-in-java)에서 찾을 수 있었다. 쓰레드 내에 플래그 변수가 false가 될 때까지 계속 반복하는 식으로 구현하는 것 같다.

## Busy Waiting

```java
public class BusyWait {
    private static class Shared {
        public long setAt;
        public long seenAt;
        public volatile boolean flag = false;
    }
    public static void main(String[] args) {
        final Shared shared = new Shared();
        Thread notifier = new Thread(new Runnable() {
            public void run() {
                System.out.println("Running");
                try {
                    Thread.sleep(500);
                    System.out.println("Setting flag");
                    shared.setAt = System.nanoTime();
                    shared.flag = true;
                }
                catch (Exception e) {
                }
            }
        });
        notifier.start();
        while (!shared.flag) {
        }
        shared.seenAt = System.nanoTime();
        System.out.println("Delay between set and seen: " + (shared.seenAt - shared.setAt));
    }
}
```

## Sleeping

```java
public class WaitAndNotify {

    private static class Shared {
        public long setAt;
        public long seenAt;
        public boolean flag = false;
    }

    public static void main(String[] args) {
        (new WaitAndNotify()).test();
    }
    private void test() {
        final Shared shared = new Shared();
        final WaitAndNotify instance = this;
        Thread notifier = new Thread(new Runnable() {
            public void run() {
                System.out.println("Running");
                try {
                    Thread.sleep(500);
                    System.out.println("Setting flag");
                    shared.setAt = System.nanoTime();
                    shared.flag = true;
                    synchronized (instance) {
                        instance.notify();
                    }
                }
                catch (Exception e) {
                }
            }
        });
        notifier.start();
        while (!shared.flag) {
            try {
                synchronized (this) {
                    wait();
                }
            }
            catch (InterruptedException ie) {
            }
        }
        shared.seenAt = System.nanoTime();
        System.out.println("Delay between set and seen: " + (shared.seenAt - shared.setAt));
    }
}
```

# 참고

[https://en.wikipedia.org/wiki/Busy_waiting](https://en.wikipedia.org/wiki/Busy_waiting)
