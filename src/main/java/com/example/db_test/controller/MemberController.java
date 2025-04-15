package com.example.db_test.controller;

import com.example.db_test.dto.MemberDTO;
import com.example.db_test.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 요구사항 5: 회원 정보를 처리하는 컨트롤러 클래스
 * - REST API 엔드포인트 제공
 * - Postman에서 사용법에 대한 주석을 추가합니다.
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberController {
    private final MemberService ms;

    /**
     * 요구사항 5-1: 회원 등록
     * - Postman에서 사용 예시:
     *   POST http://localhost:8080/members
     *   {
     *     "userId": "test123",
     *     "userName": "홍길동",
     *     "age": 25,
     *     "password": "1234"
     *   }
     */
    @PostMapping("members")
    public ResponseEntity insert(@RequestBody MemberDTO dto){
        log.info("ctrl dto : {}",dto);
        int result = ms.insert(dto);
        if(result == 0)
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("회원가입이 성공적으로 완료되었습니다.");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("이미 존재하는 회원입니다.");
    }

    /**
     * 요구사항 5-2: 전체 회원 목록 조회
     * - Postman에서 사용 예시:
     *   GET http://localhost:8080/members
     */
    @GetMapping("members")
    public ResponseEntity list(){
        List<MemberDTO> list = ms.getList();
        if (list != null && !list.isEmpty()) {
            return ResponseEntity.ok()
                    .body("회원 목록 조회 성공\n" + list);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("조회할 회원이 없습니다.");
    }

    /**
     * 요구사항 5-3: 회원 정보 조회
     * - Postman에서 사용 예시:
     *   GET http://localhost:8080/members/{number}
     */
    @GetMapping("/members/{number}")
    public ResponseEntity getData(@PathVariable("number") long number){
        MemberDTO dto = ms.getData(number);
        if (dto != null) {
            return ResponseEntity.ok()
                    .body("회원 정보 조회 성공\n" + dto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("해당 회원을 찾을 수 없습니다.");
    }

    /**
     * 요구사항 5-4: 회원 정보 삭제
     * - Postman에서 사용 예시:
     *   DELETE http://localhost:8080/members/{userId}
     */
    @DeleteMapping("members/{userId}")
    public ResponseEntity deleteData(@PathVariable String userId){
        int result = ms.deleteData(userId);
        if(result == 1)
            return ResponseEntity.ok()
                    .body("회원 정보가 성공적으로 삭제되었습니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("삭제할 회원을 찾을 수 없습니다.");
    }

    /**
     * 요구사항 5-5: 회원 정보 수정
     * - Postman에서 사용 예시:
     *   PUT http://localhost:8080/members/{userId}
     *   {
     *     "userName": "홍길동",
     *     "age": 25,
     *     "password": "1234"
     *   }
     */
    @PutMapping("members/{userId}")
    public ResponseEntity update(@PathVariable String userId, @RequestBody MemberDTO dto){
        dto.setUserId(userId);
        int result = ms.updateData(userId, dto);
        if( result > 0 )
            return ResponseEntity.ok()
                    .body("회원 정보가 성공적으로 수정되었습니다.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("수정할 회원을 찾을 수 없습니다.");
    }

    /**
     * 요구사항 5-6: 로그인
     * - Postman에서 사용 예시:
     *   POST http://localhost:8080/members/login
     *   {
     *     "userId": "test123",
     *     "password": "1234"
     *   }
     */
    @PostMapping("/members/login")
    public ResponseEntity login(@RequestBody MemberDTO dto) {
        MemberDTO result = ms.login(dto);
        if (result != null) {
            return ResponseEntity.ok()
                    .body("로그인이 성공적으로 완료되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    @GetMapping("list")
    public ResponseEntity list_2(
            @RequestParam( defaultValue = "0") int start ,
            @RequestParam( defaultValue = "3") int page){
        List<MemberDTO> list = ms.getListPage( start , page );
        if (list != null && !list.isEmpty()) {
            return ResponseEntity.ok()
                    .body("페이지별 회원 목록 조회 성공\n" + list);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("조회할 회원이 없습니다.");
    }

}


