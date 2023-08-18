package com.openclassrooms.chatop.repository;

import com.openclassrooms.chatop.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo save(UserInfo userInfo);

    UserInfo findByName(String username);

    boolean existsByName(String name);

    boolean existsByEmail(String email);

}
