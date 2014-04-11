package com.ipet.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipet.server.domain.entity.App;

public interface AppDao extends JpaRepository<App, String> {

	public App findByAppKey(String appKey);

}
