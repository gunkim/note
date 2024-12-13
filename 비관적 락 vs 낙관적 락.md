---
tag:
---

## 비관적 락
충돌이 예상되는 자원에 대해 미리 Lock을 걸고 시작하는 방법이다.
말 그대로 충돌에 대해 비관적으로 발생할 가능성이 높다고 가정하는 방법이다. 고로 충돌이 빈번하다면 고려해보아야 한다. Lock을 먼저 걸 경우 성능에 악영향을 주어 처리량이 감소할 수 있다.
## 낙관적 락
충돌이 예상되는 자원에 대해 Lock을 미리 걸지 않고, 자원에 쓰기를 할 경우 충돌을 감지하는 방법이다.

말 그대로 충돌에 대해 낙관적으로 자주 발생하지 않을거라고 가정하는 방법이다. 고로 충돌이 빈번하지 않다면 비관적 락에 비해 성능이 더 좋을 수 있다. 하지만 자주 충돌이 발생한다면 비관적 락에 비해 비효율적일 수 있다.
## 비관적 락 구현
MySQL 8.0 InnoDB에서 비관적 락은 **배타적 락(Exclusive Lock)**과 **공유 락(Shared Lock)** 2가지 방법으로 구현될 수 있다.

### 배타적 락(Exclusive Lock)
쓰기 락(Write Lock)이라고도 불리며 select 쿼리에 `FOR UPDATE`키워드를 추가하여 배타 락을 획득하는 식으로 구현한다. 이 경우 다른 트랜잭션의 **읽기, 쓰기 작업 모두 차단된다.**
```sql
START TRANSACTION;

-- 특정 행에(record) 수준의 배타적 락을 걸고 데이터를 조회함
SELECT id, balance FROM account WHERE id = 1 FOR UPDATE;

-- 데이터를 수정함 (다른 트랜잭션은 이 행을 수정할 수 없음)
UPDATE account SET balance = 1500 WHERE id = 1;

-- 트랜잭션을 완료하여 락을 해제
COMMIT;
```
### 공유 락 (Shared Lock)
읽기 락(Read Lock)이라고도 불리며 select 쿼리에 `FOR SHARE` 키워드를 추가하여 공유 락을 획득하는 식으로 구현한다.
```sql
START TRANSACTION;

-- 특정 행에 공유 락을 걸고 데이터를 조회함
SELECT id, balance FROM account WHERE id = 1 FOR SHARE;

-- 데이터를 수정하는 작업은 불가능 (다른 트랜잭션이 이 행을 수정하지 못하게 하고 읽기만 허용함)
-- 이 시점에서는 읽기만 가능하고, 해당 행에 대해 수정이나 삭제는 차단됨

-- 만약 데이터를 수정하려고 하면, 다른 트랜잭션이 공유 락을 해제할 때까지 대기해야 함
-- 하지만 아래 UPDATE 문은 이 트랜잭션 내에서 발생하면 성공할 수 있음
UPDATE account SET balance = 1500 WHERE id = 1;

-- 트랜잭션을 완료하여 락을 해제
COMMIT;
```
## 낙관적 락 구현
낙관적 락은 테이블에 version 컬럼을 추가하여 구현된다.
select 쿼리 시 version 컬럼도 함께 조회한다.

이후 update 쿼리에 version 컬럼을 조건으로 추가해, update된 행이 없으면 다른 요청이 먼저 수정된 것으로 판단하여 충돌이 발생한 것으로 간주한다. (즉, 나보다 먼저 수정한 다른 트랜잭션이 있다는 의미다.)
MySQL InnoDB는 Update 시 배타적 락을 걸기 때문에 update 자체에서 충돌이 일어나지는 않는다.

```sql
-- version 컬럼 추가
ALTER TABLE account ADD COLUMN version INT NOT NULL DEFAULT 0;

-- 낙관적 락을 사용한 트랜잭션
START TRANSACTION;

-- 특정 행을 조회하고, version 값을 함께 가져옴 version = 3
SELECT id, balance, version FROM account WHERE id = 1;

-- 데이터를 수정할 때 version 값을 조건으로 사용하여 충돌 감지
UPDATE account 
SET balance = 1500, version = version + 1 
WHERE id = 1 AND version = 3;  -- 조회한 version이 3

-- 트랜잭션 완료
COMMIT;
```