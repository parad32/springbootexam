package com.example.db_test.service;

import com.example.db_test.domain.MemberEntity;
import com.example.db_test.dto.MemberDTO;
import com.example.db_test.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
    private final MemberRepo repo;
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
    public MemberDTO getData(long number){
        Optional<MemberEntity> opM = repo.findById(number);
        MemberEntity entity = opM.orElse(null);
        if( entity != null )
            return new MemberDTO( entity );
        return null;
    }
    public int deleteData(long num){
        int result = 0;
        MemberDTO dto = getData(num);
        if(dto != null){
            repo.deleteById(num);
            result = 1;
        }
        return result;
    }
    public int updateData(String userId, MemberDTO dto){
        MemberEntity entity = repo.findByUserId(userId);

        log.info("service update : {}", entity);
        if( entity != null ) {
            entity.setUserName(dto.getUserName());
            entity.setAge(dto.getAge());
            repo.save(entity);
            return 1;
        }
        return 0;
    }
}




