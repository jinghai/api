package com.ipet.server.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ipet.server.domain.entity.Favor;

public interface FavorDao extends JpaRepository<Favor, String> {

	public Favor findByPhotoIdAndUserId(String photoId, String userId);

	public List<Favor> findByPhotoId(String photoId);

	public List<Favor> findByPhotoIdInAndUserId(Collection<String> photoIds, String userId);

}
