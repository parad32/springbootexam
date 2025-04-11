package com.example.db_test.domain;

import com.example.db_test.dto.MemberDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * 요구사항 1: 회원 정보를 저장하는 엔티티 클래스
 * - 회원 번호, 아이디, 이름, 나이, 비밀번호를 저장
 * - Postman에서 사용 예시:
 *   POST http://localhost:8080/members
 *   {
 *     "userId": "test123",
 *     "userName": "홍길동",
 *     "age": 25,
 *     "password": "1234"
 *   }
 */
@Entity
@Table(name="member_test")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemberEntity {
    /**
     * 요구사항 1-1: 회원 번호 (자동 생성)
     * - @Id: 기본키 지정
     * - @GeneratedValue: 자동 증가
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long number;

    /**
     * 요구사항 1-2: 회원 아이디
     * - @NotNull: 필수 입력
     * - @Column: 데이터베이스 컬럼명 매핑
     * - unique: 중복 불가
     * - Postman에서 사용 예시:
     *   GET http://localhost:8080/members/{userId}
     */
    @NotNull
    @Column(name = "user_id", unique = true, length = 20)
    private String userId;//user_id

    /**
     * 요구사항 1-3: 회원 이름
     * - @Column: 데이터베이스 컬럼명 매핑
     * - nullable: 필수 입력
     */
    @Column(name="user_name", nullable = false, length = 30)
    private String userName;

    /**
     * 요구사항 1-4: 회원 나이
     * - @Column: 데이터베이스 컬럼명 매핑
     */
    @Column(name="age")
    private int age;

    /**
     * 요구사항 1-5: 회원 비밀번호
     * - @Column: 데이터베이스 컬럼명 매핑
     * - nullable: 필수 입력
     * - Postman에서 사용 예시:
     *   POST http://localhost:8080/members/login
     *   {
     *     "userId": "test123",
     *     "password": "1234"
     *   }
     */
    @Column(name="password", nullable = false, length = 100)
    private String password;

    /**
     * 요구사항 1-6: DTO를 엔티티로 변환하는 생성자
     * - MemberDTO의 데이터를 MemberEntity로 변환
     */
    public MemberEntity(MemberDTO dto){
        this.userId = dto.getUserId();
        this.userName = dto.getUserName();
        this.age = dto.getAge();
        this.password = dto.getPassword();
    }
}