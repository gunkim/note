## Shared Lock(S-Lock,공유 락) & Exclusive Lock(X-Lock,배타 락)
행(Record) 수준의 Lock인 것은 동일하나 **Shared Lock**은 다른 트랜잭션에서의 읽기 허용, **Exclusive Lock**은 읽기 불가능하다는 차이가 있다.

Shared Lock은 `Select ... for update`을 사용하고, Exclusive Lock은 `SELECT ... for share` 구문을 사용한다.
## Intention Locks(의도 락)
**S-Lock**과 **X-Lock**을 테이블 수준에서 설정하는 경우를 **Intention Lock**이라고 표현한다. 예를 들어 S- Lock을 설정할 의도가 있는 경우 이를 **Intention Shared Lock (IS Lock)** 이라고 부르며, X-Lock을 설정할 의도가 있는 경우 이를 **Intention Exclusive Lock (IX Lock)** 이라고 부른다.

Intention Lock의 동작은 S,X Lock을 획득하기 위해 더 강한 Intention Lock을 획득해야 한다.
- 트랜잭션이 테이블의 행에 대해 S-Lock을 획득하기 전에 먼저 테이블에 대한 Intention Shared(IS) Lock 또는 그보다 강한 Lock을 획득해야 한다.
- 트랜잭션이 테이블의 행에 대해 X-Lock을 획득하기 전에 먼저 테이블에 대한 Intention Exclusive(IX) Lock을 획득해야 합니다.

|     | X(Exclusive) | IX(Intention Exclusive) | S(Shared) | IS(Intention Shared) |
| --- | ------------ | ----------------------- | --------- | -------------------- |
| X   | 충돌           | 충돌                      | 충돌        | 충돌                   |
| IX  | 충돌           | 호환                      | 충돌        | 호환                   |
| S   | 충돌           | 충돌                      | 호환        | 호환                   |
| IS  | 충돌           | 호환                      | 호환        | 호환                   |
Lock 획득 요청이 기존 Lock과 호환(Compatible)될 경우 요청된 트랜잭션이 Lock을 획득하지만, 기존 Lock과 충돌(Conflict)할 경우에는 획득할 수 없다. 트랜잭션은 충돌하는 기존 Lock이 해제될 때까지 대기한다. 만약 Lock 획득 요청이 기존 Lockl과 충돌하고 Dead Lock(교착 상태)을 발생시킬 수 있는 경우에는 오류가 발생한다.

Intention Lock은 전체 테이블 요청(예: `LOCK TABLES ... WRITE`)을 제외하고 어떤 것도 차단하지 않는다. Intention Lock의 주요 목적은 누군가가 테이블의 행을 잠그거나 잠글 예정임을 나타내는 것이다.

Lock 상태 확인을 해보면 아래와 같이 나온다.
```sql
TABLE LOCK table `test`.`t` trx id 10080 lock mode IX
```
## Record Lock(레코드 락)
Record Lock은 Index 레코드에 대한 Lock이다. S-Lock, X-Lock을 통해 설정한다.

예를 들어 X-Lock을 건다고 가정한다면 `SELECT c1 FROM t WHERE c1 = 10 FOR UPDATE;` 다른 트랜잭션이 `t.c1 = 10`인 행을 삽입, 업데이트,삭제하지 못하도록 한다. 읽기는 S, X Lock 여부에 따라 달라진다.

Lock 상태 확인을 해보면 아래와 같이 나온다. S Lock과 X Lock이 Record Lock에 해당하기 때문에 Record Lock이라고 표현되며 X, S Lock이 추가로 표현된다.
```sql
RECORD LOCKS space id 58 page no 3 n bits 72 index `PRIMARY` of table `test`.`t` trx id 10078 lock_mode X locks rec but not gap Record lock, heap no 2 PHYSICAL RECORD: n_fields 3; compact format; info bits 0 0: len 4; hex 8000000a; asc ;; 1: len 6; hex 00000000274f; asc 'O;; 2: len 7; hex b60000019d0110; asc ;;
```
## Gap Lock(간격 락)
**Gap Lock**은 인덱스의 특정 간격이나 이전 간격에 대한 잠금이다. S-Lock과 X-Lock을 통해 설정된다. Gap Lock은 성능과 동시성 간의 절충의 일부로, 일부 트랜잭션 격리 수준에서는 사용되지만, 다른 수준에서는 사용되지 않는다.

예를 들어, `SELECT c1 FROM t WHERE c1 BETWEEN 10 AND 20 FOR UPDATE;`를 실행하면, 다른 트랜잭션이 열 `t.c1`에 값 15를 삽입하지 못하게 된다.

**Gap Locking**은 Unique Index를 사용해 단일 레코드를 조회하는 쿼리에 대해서는 필요하지 않고, 다중 레코드를 조회하는 경우에 발생한다. 예를 들어, `id` 열이 **Unique Index**를 가지고 있다면, `SELECT * FROM child WHERE id = 100;` 쿼리는 `id` 값이 100인 행에 대한 **Record Lock**만 사용하고, 다른 트랜잭션이 `id`가 100보다 작은 값(예: 99)으로 새로운 레코드를 삽입하는 것은 허용된다.

반면에, `id`가 **Index**가 아니거나 **Non-Unique Index**인 경우 이 쿼리는 이전 간격인 100보다 작은 99 이하의 값의 삽입이 모두 차단된다.

여기서 주목할 점은 서로 다른 트랜잭션이 동일한 간격에 대해 충돌하는 잠금을 가질 수 있다는 것이다. 예를 들어, 트랜잭션 A가 1~100 사이에 대한 Gap S-Lock을 설정하고, 트랜잭션 B가 동일한 간격에 대해 Gap X-Lock을 설정할 수 있다. 충돌이 허용되는 이유는 인덱스에서 레코드가 삭제되면 서로 다른 트랜잭션이 보유한 Gap Lock을 병합해야 하기 때문이다.

InnoDB의 Gap Lock의 유일한 목적은 **순수한 방지(purely inhibitive)** 로, 다른 트랜잭션이 간격에 삽입하는 것을 방지하는 것이다. 하지만 Gap Lock은 공존할 수 있다. 한 트랜잭션이 보유한 Gap Lock은 다른 트랜잭션이 동일한 간격에 대해 Gap Lock을 획득하는 것을 방해하지 않는다. Gap S-Lock과 Gap X-Lock 간에 차이는 없다. 이들은 서로 충돌하지 않으며 동일한 기능을 수행한다.

Gap Locking은 명시적으로 비활성화할 수 있다. 이는 트랜잭션 격리 수준을 `READ COMMITTED`로 변경할 경우 발생한다. 이 경우, Gap Locking은 검색 및 인덱스 스캔에 대해 비활성화되며 외래 키 제약 조건 검사 및 중복 키 검사에 대해서만 사용된다.

`READ COMMITTED` 격리 수준을 사용할 때 다른 효과도 있다. 일치하지 않는 행에 대한 레코드 잠금은 MySQL이 WHERE 조건을 평가한 후 해제된다. `UPDATE` 문장에 대해 InnoDB는 **반일관성(semi-consistent)** 읽기를 수행하여 가장 최근에 커밋된 버전을 MySQL에 반환하여 MySQL이 해당 행이 UPDATE의 WHERE 조건과 일치하는지 확인할 수 있도록 한다.
## Lock 상태 확인 방법
```sql
SHOW ENGINE INNODB STATUS
```
## 이 글의 기준 기준
- [Mysql InnoDB Locking 8.4 Document](https://dev.mysql.com/doc/refman/8.4/en/innodb-locking.html)에 소개된 공식 문서 내용을 토대로 이해한 내용을 정리한다.
- 어색한 영단어는 의역해서 본인이 이해하기 쉬운 방향으로 정리한다.
- 명사는 문단 시작할 때 한번 한글명만 보여주고 영어로 통일해서 적는다.