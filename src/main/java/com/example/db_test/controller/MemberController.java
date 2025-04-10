package com.example.db_test.controller;

import com.example.db_test.dto.MemberDTO;
import com.example.db_test.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MemberController {
    private final MemberService ms;
    @PostMapping("members")
    public ResponseEntity insert(@RequestBody MemberDTO dto){
        log.info("ctrl dto : {}",dto);
        int result = ms.insert(dto);
        if(result == 0)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @GetMapping("members")
    public ResponseEntity list(){
        List<MemberDTO> list = ms.getList();
        return ResponseEntity.ok().body( list );
    }
    @GetMapping("/members/{number}")
    public ResponseEntity getData(@PathVariable("number") long number){
        MemberDTO dto = ms.getData(number);
        if( dto != null)
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @DeleteMapping("members/{num}")
    public ResponseEntity deleteData(@PathVariable("num") Long num){
        int result = ms.deleteData(num);
        if(result == 1)
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PutMapping("/members/{username}")
    public ResponseEntity update(@PathVariable("username") String userId,
                                 @RequestBody MemberDTO dto){
        int result = ms.updateData(userId, dto);
        if(result == 1)
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @GetMapping("list")
    public ResponseEntity list_2(
            @RequestParam( defaultValue = "0") int start){
        log.info("ctrl start : {}", start);
        List<MemberDTO> list = ms.getList();
        return ResponseEntity.ok().body( list );
    }
}


