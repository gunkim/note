## Shared Lock (S-Lock, 공유 잠금) & Exclusive Lock (X-Lock, 배타 잠금)
행(Record) 수준의 Lock인 것은 동일하나 **Shared Lock**은 다른 트랜잭션에서의 읽기를 허용하고, **Exclusive Lock**은 읽기를 불가능하게 한다는 차이가 있다.

**Shared Lock**은 `SELECT ... FOR UPDATE`를 사용하고, **Exclusive Lock**은 `SELECT ... FOR SHARE` 구문을 사용한다.

## Intention Locks (의도 잠금)
**S-Lock**과 **X-Lock**을 테이블 수준에서 설정하는 경우를 **Intention Lock**이라고 표현한다. 예를 들어, S-Lock을 설정할 의도가 있는 경우 이를 **Intention Shared Lock (IS Lock)** 이라고 부르며, X-Lock을 설정할 의도가 있는 경우 이를 **Intention Exclusive Lock (IX Lock)** 이라고 부른다.

**Intention Lock**의 동작은 S, X Lock을 획득하기 위해 더 강한 Intention Lock을 획득해야 한다.
- 트랜잭션이 테이블의 레코드에 대해 S-Lock을 획득하기 전에 먼저 테이블에 대한 IS-Lock 또는 그보다 강한 Lock을 획득해야 한다.
- 트랜잭션이 테이블의 레코드에 대해 X-Lock을 획득하기 전에 먼저 테이블에 대한 IX-Lock을 획득해야 한다.

|     | X (Exclusive) | IX (Intention Exclusive) | S (Shared) | IS (Intention Shared) |
| --- | -------------- | ------------------------ | ----------- | ---------------------- |
| X   | 충돌           | 충돌                      | 충돌        | 충돌                   |
| IX  | 충돌           | 호환                      | 충돌        | 호환                   |
| S   | 충돌           | 충돌                      | 호환        | 호환                   |
| IS  | 충돌           | 호환                      | 호환        | 호환                   |

Lock 획득 요청이 기존 Lock과 호환(Compatible)될 경우 요청된 트랜잭션이 Lock을 획득하지만, 기존 Lock과 충돌(Conflict)할 경우에는 획득할 수 없다. 트랜잭션은 충돌하는 기존 Lock이 해제될 때까지 대기해야 하며, 만약 Lock 획득 요청이 기존 Lock과 충돌하고 Dead Lock(교착 상태)을 발생시킬 수 있는 경우에는 오류가 발생한다.

Intention Lock은 전체 테이블 요청(예: `LOCK TABLES ... WRITE`)을 제외하고는 어떤 것도 차단하지 않는다. Intention Lock의 주요 목적은 누군가가 테이블의 레코드를 잠그거나 잠글 예정임을 나타내는 것이다.

Lock 상태 확인을 해보면 아래와 같이 나온다.
```sql
TABLE LOCK table `test`.`t` trx id 10080 lock mode IX
```

## Record Lock (레코드 잠금)
Record Lock은 Index 레코드에 대한 Lock이다. S-Lock, X-Lock을 통해 설정된다.

예를 들어, X-Lock을 건다고 가정한다면 `SELECT c1 FROM t WHERE c1 = 10 FOR UPDATE;` 쿼리는 다른 트랜잭션이 `t.c1 = 10`인 레코드를 삽입, 업데이트, 삭제하지 못하도록 한다. 읽기는 S, X Lock 여부에 따라 달라진다.

Lock 상태 확인을 해보면 아래와 같이 나온다. S-Lock과 X-Lock이 Record Lock에 해당하기 때문에 Record Lock이라고 표현되며 S-Lock과 X-Lock이 추가로 표현된다.
```sql
RECORD LOCKS space id 58 page no 3 n bits 72 index `PRIMARY` of table `test`.`t` trx id 10078 lock_mode X locks rec but not gap Record lock, heap no 2 PHYSICAL RECORD: n_fields 3; compact format; info bits 0 0: len 4; hex 8000000a; asc ;; 1: len 6; hex 00000000274f; asc 'O;; 2: len 7; hex b60000019d0110; asc ;;
```

## Gap Lock (간격 잠금)
**Gap Lock**은 인덱스의 특정 간격이나 이전 간격에 대한 잠금이다. S-Lock과 X-Lock을 통해 설정된다. Gap Lock은 성능과 동시성 간의 절충의 일부로, 일부 트랜잭션 격리 수준에서는 사용되지만, 다른 수준에서는 사용되지 않는다.

예를 들어, `SELECT c1 FROM t WHERE c1 BETWEEN 10 AND 20 FOR UPDATE;` 쿼리를 실행하면, 다른 트랜잭션이 열 `t.c1`에 값 15를 삽입하지 못하게 된다.

**Gap Locking**은 Unique Index를 사용해 단일 레코드를 조회하는 쿼리에는 필요하지 않지만, 다중 레코드를 조회하는 경우에 발생한다. 예를 들어, `id` 열이 **Unique Index**를 가지고 있다면 `SELECT * FROM child WHERE id = 100;` 쿼리는 `id` 값이 100인 레코드에 대한 **Record Lock**만 사용하며, 다른 트랜잭션이 `id`가 100보다 작은 값(예: 99)으로 새로운 레코드를 삽입하는 것은 허용된다.

반면에, `id`가 **Index**가 아니거나 **Non-Unique Index**인 경우 이 쿼리는 이전 간격인 100보다 작은 99 이하의 값의 삽입이 모두 차단된다.

여기서 주목할 점은 서로 다른 트랜잭션이 동일한 간격에 대해 충돌하는 잠금을 가질 수 있다는 것이다. 예를 들어, 트랜잭션 A가 1~100 사이에 대한 Gap S-Lock을 설정하고, 트랜잭션 B가 동일한 간격에 대해 Gap X-Lock을 설정할 수 있다. 충돌이 허용되는 이유는 인덱스에서 레코드가 삭제되면 서로 다른 트랜잭션이 보유한 Gap Lock을 병합해야 하기 때문이다.

InnoDB의 Gap Lock의 유일한 목적은 **순수한 방지(purely inhibitive)** 로, 다른 트랜잭션이 간격에 삽입하는 것을 방지하는 것이다. 하지만 Gap Lock은 공존할 수 있다. 한 트랜잭션이 보유한 Gap Lock은 다른 트랜잭션이 동일한 간격에 대해 Gap Lock을 획득하는 것을 방해하지 않는다. Gap S-Lock과 Gap X-Lock 간에 차이는 없으며, 이들은 서로 충돌하지 않고 동일한 기능을 수행한다.

트랜잭션 격리 레벨을 `READ COMMITTED`로 변경할 경우 검색 및 인덱스 스캔에 대해 Gap Locking이 비활성화되며, 외래 키 제약 조건 검사 및 중복 키 검사에 대해서만 사용된다.

`READ COMMITTED` 격리 수준을 사용할 때 다른 효과도 있다. 일치하지 않는 레코드에 대한 Record Lock은 MySQL이 WHERE 조건을 평가한 후 해제된다. `UPDATE`에 대해 InnoDB는 **반일관성(semi-consistent)** 읽기를 수행하여 가장 최근에 커밋된 버전을 MySQL에 반환하여 MySQL이 해당 레코드가 UPDATE의 WHERE 조건과 일치하는지 확인할 수 있도록 한다.

## Next-Key Locks (다음 키 잠금)
**Next-Key Lock**은 Index 레코드에 대한 Record Lock과 그 Index 레코드 앞 간격에 대한 Gap Lock의 조합이다.

InnoDB는 테이블 Index를 검색하거나 스캔할 때, 만나는 Index 레코드에 대해 S-Lock, X-Lock을 설정하여 행 수준의 잠금을 수행한다. 따라서 행 수준의 잠금은 실제로 Index Record Lock이다. Index 레코드에 대한 Next-Key Lock은 해당 Index 레코드 앞의 간격에도 영향을 미친다. 즉, Next-Key Lock은 Index Record Lock과 그 Index 레코드 앞의 Gap Lock을 포함한다. 한 트랜잭션이 Index에서 레코드 R에 대해 S-Lock, X-Lock을 보유하고 있다면, 다른 트랜잭션은 인덱스 순서에서 R 앞의 간격에 새로운 Index 레코드를 삽입할 수 없다.

예를 들어, Index가 10, 11, 13, 20의 값을 포함한다고 가정하면, 이 Index에 대한 가능한 Next-Key Lock은 다음과 같은 간격을 포함한다. 원형 괄호는 구간 끝점을 제외하고, 대괄호는 끝점을 포함한다.
```
(negative infinity, 10] 
(10, 11]
(11, 13]
(13, 20]
(20, positive infinity)
```

마지막 간격의 경우, Next-Key Lock은 Index의 가장 큰 값 위의 간격과 Index에 실제로 존재하는 어떤 값보다 높은 초과(superum)

 가상의 레코드를 잠근다. 초과는 실제 인덱스 레코드가 아니므로, 효과적으로 이 Next-Key Lock은 가장 큰 Index 값 다음의 간격만 잠근다.

기본적으로 InnoDB는 **REPEATABLE READ** 트랜잭션 격리 수준에서 작동한다. 이 경우 InnoDB는 검색 및 Index 스캔에 대해 Next-Key Lock을 사용하여 팬텀 레코드를 방지한다.

Lock 상태 확인을 해보면 아래와 같이 나온다.
```sql
RECORD LOCKS space id 58 page no 3 n bits 72 index `PRIMARY` of table `test`.`t`
trx id 10080 lock_mode X
Record lock, heap no 1 PHYSICAL RECORD: n_fields 1; compact format; info bits 0
 0: len 8; hex 73757072656d756d; asc supremum;;
Record lock, heap no 2 PHYSICAL RECORD: n_fields 3; compact format; info bits 0
 0: len 4; hex 8000000a; asc     ;;
 1: len 6; hex 00000000274f; asc     'O;;
 2: len 7; hex b60000019d0110; asc        ;;
```

## Insert Intention Locks (의도 잠금 삽입)
**Insert Intention Lock**은 행 삽입 전에 설정되는 Gap Lock의 일종이다. 이 Lock은 같은 Index 간격에 여러 트랜잭션이 삽입할 때, 서로 같은 위치에 삽입하지 않는다면 대기할 필요가 없음을 나타낸다. 예를 들어, Index 레코드가 4와 7이 있다고 가정했을 때, 각각 5와 6을 삽입하려는 별도의 트랜잭션은 삽입하기 전에 4와 7 사이의 간격을 Insert Intention Lock으로 잠그게 되며, 서로 충돌하지 않기 때문에 대기하지 않는다.

다음 예시는 Insert Intention Lock을 설정한 후에 삽입된 레코드에 대해 X-Lock을 얻는 트랜잭션을 보여준다. 이 예시에는 두 클라이언트 A와 B가 포함된다.

클라이언트 A는 두 개의 Index 레코드(90과 102)를 포함하는 테이블을 생성하고, 그 후 ID가 100보다 큰 Index 레코드에 대해 X-Lock을 설정하는 트랜잭션을 시작한다. 이 X-Lock은 레코드 102 앞의 Gap Lock도 포함된다.
```sql
mysql> CREATE TABLE child (id int(11) NOT NULL, PRIMARY KEY(id)) ENGINE=InnoDB;
mysql> INSERT INTO child (id) VALUES (90), (102);

mysql> START TRANSACTION;
mysql> SELECT * FROM child WHERE id > 100 FOR UPDATE;
+-----+
| id  |
+-----+
| 102 |
+-----+
```
클라이언트 B는 갭에 레코드를 삽입하는 트랜잭션을 시작한다. 이 트랜잭션은 **Insert Intention Lock**을 설정하면서 **X-Lock**을 얻기 위해 대기한다.
```sql
mysql> START TRANSACTION;
mysql> INSERT INTO child (id) VALUES (101);
```

Lock 상태 확인을 해보면 아래와 같이 나온다.
```
RECORD LOCKS space id 31 page no 3 n bits 72 index `PRIMARY` of table `test`.`child`
trx id 8731 lock_mode X locks gap before rec insert intention waiting
Record lock, heap no 3 PHYSICAL RECORD: n_fields 3; compact format; info bits 0
 0: len 4; hex 80000066; asc    f;;
 1: len 6; hex 000000002215; asc     " ;;
 2: len 7; hex 9000000172011c; asc     r  ;;
```

## AUTO-INC Locks (자동 증가 잠금)
**AUTO-INC Lock**은 **AUTO_INCREMENT** 열을 가진 테이블에 삽입하는 트랜잭션에 의해 설정되는 특별한 테이블 수준의 잠금이다. 가장 단순한 경우, 하나의 트랜잭션이 테이블에 값을 삽입하고 있다면, 다른 트랜잭션은 해당 테이블에 자신들의 삽입을 위해 대기해야 한다. 이는 첫 번째 트랜잭션이 삽입한 행들이 연속적인 기본 키 값을 받도록 하기 위함이다.

**innodb_autoinc_lock_mode** 변수는 AUTO-INC Lock에 사용되는 알고리즘을 제어한다. 이 변수는 예측 가능한 자동 증가 값의 순서와 삽입 작업의 최대 동시성 간의 균형을 선택할 수 있게 해준다.

자세한 내용은 [Section 17.6.1.6, “AUTO_INCREMENT Handling in InnoDB](https://dev.mysql.com/doc/refman/8.4/en/innodb-auto-increment-handling.html)를 참고.

## Predicate Locks for Spatial Indexes (공간 인덱스를 위한 술어 잠금)
InnoDB는 공간 데이터가 포함된 열에 대해 **SPATIAL** 인덱싱을 지원한다. (자세한 내용은 [Section 13.4.9, “Optimizing Spatial Analysis”](https://dev.mysql.com/doc/refman/8.4/en/optimizing-spatial-analysis.html) 를 참고)

**SPATIAL** 인덱스를 포함하는 작업에 대한 잠금을 처리하기 위해, Next-Key Lock은 **REPEATABLE READ** 또는 **SERIALIZABLE** 트랜잭션 격리 수준을 지원하는 데 잘 작동하지 않는다. 다차원 데이터에는 절대적인 순서 개념이 없기 때문에, 어떤 것이 "다음" 키인지 명확하지 않다.

**SPATIAL** 인덱스를 가진 테이블의 격리 수준을 지원하기 위해, InnoDB는 **Predicate locks**를 사용한다. **SPATIAL** 인덱스는 최소 경계 사각형 (MBR) 값을 포함하므로, InnoDB는 쿼리에 사용된 MBR 값에 대해 **Predicate lock**을 설정하여 인덱스에서 일관된 읽기를 강제한다. 다른 트랜잭션은 쿼리 조건과 일치하는 행을 삽입하거나 수정할 수 없다.

## Lock 상태 확인 방법
```sql
SHOW ENGINE INNODB STATUS
```

## 이 글의 작성 기준
- [MySQL InnoDB Locking 8.4 Document](https://dev.mysql.com/doc/refman/8.4/en/innodb-locking.html)에 소개된 공식 문서 내용을 토대로 이해한 내용을 정리한다.
- 어색한 영단어는 의역해서 본인이 이해하기 쉬운 방향으로 정리한다.
- 영어 명사는 왠만해선 있는 그대로 표현한다.