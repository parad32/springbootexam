package com.example.db_test.service;

import com.example.db_test.domain.MemberEntity;
import com.example.db_test.dto.MemberDTO;
import com.example.db_test.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 요구사항 4: 회원 정보를 처리하는 서비스 클래스
 * - 비즈니스 로직 처리
 * - Postman에서 사용법에 대한 주석을 추가합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepo repo;

    /**
     * 요구사항 4-1: 회원 정보 저장
     * - Postman에서 사용 예시:
     *   POST http://localhost:8080/members
     *   {
     *     "userId": "test123",
     *     "userName": "홍길동",
     *     "age": 25,
     *     "password": "1234"
     *   }
     */
    public int insert(MemberDTO dto){
        int result =0;
        try {//insert, update
            MemberEntity entity = repo.save( new MemberEntity(dto)  );
            log.info("service entity : {}",entity);
        } catch (Exception e) {
            result = 1;
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 요구사항 4-2: 전체 회원 목록 조회
     * - Postman에서 사용 예시:
     *   GET http://localhost:8080/members
     */
    public List<MemberDTO> getList(){
        List<MemberDTO> list = null;
        List<MemberEntity> listE = repo.findAll();
        if( listE.size() != 0 ){
            list = listE.stream()
                    .map(m -> new MemberDTO(m) )
                    .toList();
        }
        /*
        List<MemberDTO> list = new ArrayList<>();
        for(MemberEntity e : listE){
            list.add( new MemberDTO(e) );
        }
        */
        log.info("list entity : {}", listE );
        return list;
    }

    /**
     * 요구사항 4-3: 회원 아이디로 회원 정보 조회
     * - Postman에서 사용 예시:
     *   GET http://localhost:8080/members/{userId}
     */
    public MemberDTO getData(long number){
        Optional<MemberEntity> opM = repo.findById(number);
        MemberEntity entity = opM.orElse(null);
        if( entity != null )
            return new MemberDTO( entity );
        return null;
    }

    /**
     * 요구사항 4-4: 회원 정보 삭제
     * - Postman에서 사용 예시:
     *   DELETE http://localhost:8080/members/{userId}
     */
    public int deleteData(String userId){
        int result = 0;
        MemberEntity entity = repo.findByUserId(userId);
        if(entity != null){
            repo.delete(entity);
            result = 1;
        }
        return result;
    }

    /**
     * 요구사항 4-5: 회원 정보 수정
     * - Postman에서 사용 예시:
     *   PUT http://localhost:8080/members/{userId}
     *   {
     *     "userName": "홍길동",
     *     "age": 25,
     *     "password": "1234"
     *   }
     */
    public List<MemberDTO> getListPage( int start, int page ){
        //int page = 3;
        Pageable pageable = PageRequest.of( start, page,
                                Sort.by(Sort.Order.desc("number")) );
        Page<MemberEntity> pageEntity = repo.findAll( pageable );
        List<MemberEntity> listE = pageEntity.getContent();
        List<MemberDTO> list = listE.stream()
                                    .map( m -> new MemberDTO(m))
                                    .toList();
        return list;
    }
    public MemberDTO getContent(long number){
        MemberEntity entity = repo.findByContent(number);
        log.info("entity : {}", entity);
        if( entity != null )
            return new MemberDTO( entity );
        return null;
    }
    public int insertContent(MemberDTO dto){
        int result = 0;
        try {
            result = repo.insertContent(dto.getUserId(),
                                      dto.getUserName(),
                                      dto.getAge(),
                                      dto.getPassword());
            log.info("service result : {}",result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public MemberDTO login(MemberDTO dto) {
        if (dto == null || dto.getUserId() == null || dto.getPassword() == null) {
            log.warn("Login attempt with null credentials");
            return null;
        }
        
        MemberEntity entity = repo.findByUserId(dto.getUserId());
        log.info("Login attempt for user: {}, found entity: {}", dto.getUserId(), entity);
        
        if (entity != null && entity.getPassword() != null && entity.getPassword().equals(dto.getPassword())) {
            return new MemberDTO(entity);
        }
        return null;
    }

    /**
     * 요구사항 4-7: 회원 정보 수정
     * - Postman에서 사용 예시:
     *   PUT http://localhost:8080/members/{userId}
     *   {
     *     "userName": "홍길동",
     *     "age": 25,
     *     "password": "1234"
     *   }
     */
    @Transactional
    public int updateData(String userId, MemberDTO dto) {
        try {
            repo.updateMember(userId, dto.getUserName(), dto.getAge(), dto.getPassword());
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



}




