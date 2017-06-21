package io.jirsis.drzewo.album.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlbumRepository extends MongoRepository<AlbumEntity, String>{
	
	Page<AlbumEntity> findAllByOrderByCreationDateDesc(Pageable pageRequest);
	
}
