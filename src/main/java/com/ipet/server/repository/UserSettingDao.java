package com.ipet.server.repository;

import com.ipet.server.domain.entity.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSettingDao extends JpaRepository<UserSetting, String> {

}
