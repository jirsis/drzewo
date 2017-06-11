package io.jirsis.drzewo.album.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="albums")
public class AlbumEntity {
	
	@Id
	private String name;
	
	private String path;
	
	private int totalPhotos;

}
