package com.example.demo.backend.repository;

import com.example.demo.backend.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>, JpaSpecificationExecutor<UserInfo> {
    UserInfo findByUsername(String username);
}