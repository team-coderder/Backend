package com.coderder.colorMeeting.repository;

import com.coderder.colorMeeting.model.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    UserTest findByUsername(String username);
}
