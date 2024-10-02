## Shared Lock(공유 락) & Exclusive Lock(배타 락)
행(Record) 수준의 Lock인 것은 동일하나 Shared Lock은 다른 트랜잭션에서의 읽기 허용, Exclusive Lock은 읽기 불가능하다는 차이가 있다.

Shared Lock과 Exclusive Lock은 MySQL 시스템에 의해 잠금이 설정될 수도, 직접 설정할 수도 있다. 직접 설정하는 경우는 후술한다.
## Intention Locks(의도 락)
앞서 설명된 Shared Lock, Exclusive Lock을 직접 거는 경우를 Intention Lock이라고 표현한다. 예를 들어 Shared Lock을 Intention Lock을 한다고 하면 Intention Shared Lock이라고 부른다.

Intention Lock의 동작은 Shared, Exclusive Lock을 획득하기 위해 더 강한 Intention Lock을 획득해야 한다.
- 트랜잭션이 테이블의 행에 대해 Shared Lock을 획득하기 전에 먼저 테이블에 대한 Intention Shared(IS) Lock 또는 그보다 강한 Lock을 획득해야 한다.
- 트랜잭션이 테이블의 행에 대해 Exclusive Lock을 획득하기 전에 먼저 테이블에 대한 Intention Exclusive(IX) Lock을 획득해야 합니다.

|     | X(Exclusive) | IX(Intention Exclusive) | S(Shared) | IS(Intention Shared) |
| --- | ------------ | ----------------------- | --------- | -------------------- |
| X   | 충돌           | 충돌                      | 충돌        | 충돌                   |
| IX  | 충돌           | 호환                      | 충돌        | 호환                   |
| S   | 충돌           | 충돌                      | 호환        | 호환                   |
| IS  | 충돌           | 호환                      | 호환        | 호환                   |
Lock 획득 요청이 기존 Lock과 호환(Compatible)될 경우 요청된 트랜잭션이 Lock을 획득하지만, 기존 Lock과 충돌(Conflict)할 경우에는 획득할 수 없다. 트랜잭션은 충돌하는 기존 Lock이 해제될 때까지 대기한다. 만약 Lock 획득 요청이 기존 Lockl과 충돌하고 Dead Lock(교착 상태)을 발생시킬 수 있는 경우에는 오류가 발생한다.

Intention Lock은 전체 테이블 요청(예: `LOCK TABLES ... WRITE`)을 제외하고 어떤 것도 차단하지 않는다. Intention Lock의 주요 목적은 누군가가 테이블의 행을 잠그거나 잠글 예정임을 나타내는 것이다.
## 이 글의 기준 기준
- [Mysql InnoDB Locking 8.4 Document](https://dev.mysql.com/doc/refman/8.4/en/innodb-locking.html)에 소개된 공식 문서 내용을 토대로 이해한 내용을 정리한다.
- 어색한 영단어는 의역해서 본인이 이해하기 쉬운 방향으로 정리한다.
- 명사는 문단 시작할 때 한번 한글명만 보여주고 영어로 통일해서 적는다.