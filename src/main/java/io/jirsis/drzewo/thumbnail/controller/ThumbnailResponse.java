package io.jirsis.drzewo.thumbnail.controller;

import lombok.Data;

@Data
public class ThumbnailResponse {
	
	private String originalPhoto;
	private byte [] rawData;

}
