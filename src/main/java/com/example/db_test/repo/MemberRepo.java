package com.example.db_test.repo;

import com.example.db_test.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<MemberEntity , Long> {
    MemberEntity findByUserId(String userId);

}