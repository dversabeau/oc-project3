package com.openclassrooms.chatop.repository;

import com.openclassrooms.chatop.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    UserInfo getUserById(Long id);

    UserInfo save(UserInfo userInfo);

    Optional<UserInfo> findByName(String username);
}
