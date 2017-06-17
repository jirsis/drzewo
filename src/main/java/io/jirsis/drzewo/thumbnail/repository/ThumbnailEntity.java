package io.jirsis.drzewo.thumbnail.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="thumbnails")
public class ThumbnailEntity {
	
	@Id
	private String id;
	
	private String album;
	private String image;
	private byte[] rawData;
	
	private Integer exifOrientation;
	private Integer height;
	private Integer width;

}
