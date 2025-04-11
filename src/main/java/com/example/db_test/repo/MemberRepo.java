package com.example.db_test.repo;

import com.example.db_test.domain.MemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * 요구사항 3: 회원 정보를 관리하는 레포지토리 인터페이스
 * - JPA를 사용하여 데이터베이스 작업 수행
 * - Postman에서 사용 예시:
 *   - 회원 조회: GET http://localhost:8080/members/{userId}
 *   - 회원 수정: PUT http://localhost:8080/members/{userId}
 *   - 회원 삭제: DELETE http://localhost:8080/members/{userId}
 */
public interface MemberRepo extends JpaRepository<MemberEntity, Long> {
    /**
     * 요구사항 3-1: 회원 아이디로 회원 정보 조회
     * - Postman에서 사용 예시:
     *   GET http://localhost:8080/members/{userId}
     */
    MemberEntity findByUserId(String userId);

    @Query(value="select * from member_test where number=:n",
            nativeQuery = true)
    MemberEntity findByContent(@Param("n") long num);

    @Modifying
    @Transactional
    @Query(value="insert into member_test(user_id, user_name, age, password)" +
            " values(:id, :name, :age, :password)", nativeQuery = true)
    int insertContent(@Param("id")String userId,
                      @Param("name") String userName,
                      @Param("age") int age,
                      @Param("password") String password);

    /**
     * 요구사항 3-2: 회원 정보 수정 (네이티브 쿼리)
     * - Postman에서 사용 예시:
     *   PUT http://localhost:8080/members/{userId}
     *   {
     *     "userName": "홍길동",
     *     "age": 25,
     *     "password": "1234"
     *   }
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE member_test SET user_name = :userName, age = :age, password = :password WHERE user_id = :userId", nativeQuery = true)
    void updateMember(@Param("userId") String userId, @Param("userName") String userName, @Param("age") int age, @Param("password") String password);

    Optional<MemberEntity> findById(long number);
}

