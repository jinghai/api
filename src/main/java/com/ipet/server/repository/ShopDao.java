package com.ipet.server.repository;

import com.ipet.server.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopDao extends JpaRepository<Shop, String> {

}
