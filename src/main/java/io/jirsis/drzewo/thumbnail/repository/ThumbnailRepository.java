package io.jirsis.drzewo.thumbnail.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbnailRepository extends MongoRepository<ThumbnailEntity, String>{

	ThumbnailEntity findByAlbumAndImage(String album, String image);
	List<ThumbnailEntity> findByAlbum(String album);
}
