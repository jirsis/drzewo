package io.jirsis.drzewo.album.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlbumRepository extends MongoRepository<AlbumEntity, String>{

}
