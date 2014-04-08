package com.ipet.server.repository;

import com.ipet.server.domain.entity.LastLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LastLocationDao extends JpaRepository<LastLocation, String> {

}
