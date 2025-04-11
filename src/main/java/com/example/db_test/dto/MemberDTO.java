package com.example.db_test.dto;

import com.example.db_test.domain.MemberEntity;
import lombok.*;

/**
 * 요구사항 2: 회원 정보를 전송하는 DTO 클래스
 * - 클라이언트와 서버 간의 데이터 전송에 사용
 * - Postman에서 사용 예시:
 *   POST http://localhost:8080/members
 *   {
 *     "userId": "test123",
 *     "userName": "홍길동",
 *     "age": 25,
 *     "password": "1234"
 *   }
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    /**
     * 요구사항 2-1: 회원 번호
     * - 데이터베이스에서 자동 생성되는 값
     */
    private Long number;

    /**
     * 요구사항 2-2: 회원 아이디
     * - 중복 불가
     * - 최대 20자
     * - Postman에서 사용 예시:
     *   GET http://localhost:8080/members/{userId}
     */
    private String userId;

    /**
     * 요구사항 2-3: 회원 이름
     * - 필수 입력
     * - 최대 30자
     */
    private String userName;

    /**
     * 요구사항 2-4: 회원 나이
     */
    private int age;

    /**
     * 요구사항 2-5: 회원 비밀번호
     * - 필수 입력
     * - 최대 100자
     * - Postman에서 사용 예시:
     *   POST http://localhost:8080/members/login
     *   {
     *     "userId": "test123",
     *     "password": "1234"
     *   }
     */
    private String password;

    public MemberDTO(MemberEntity entity){
        this.number = entity.getNumber();
        this.userId = entity.getUserId();
        this.userName = entity.getUserName();
        this.age = entity.getAge();
        this.password = entity.getPassword();
    }
}
