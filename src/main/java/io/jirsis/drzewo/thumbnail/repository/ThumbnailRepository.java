package io.jirsis.drzewo.thumbnail.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbnailRepository extends MongoRepository<ThumbnailEntity, String>{

}
