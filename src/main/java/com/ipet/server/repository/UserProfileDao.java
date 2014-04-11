package com.ipet.server.repository;

import com.ipet.server.domain.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileDao extends JpaRepository<UserProfile, String> {

}
