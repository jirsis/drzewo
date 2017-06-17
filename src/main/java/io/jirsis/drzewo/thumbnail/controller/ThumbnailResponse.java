package io.jirsis.drzewo.thumbnail.controller;

import lombok.Data;

@Data
public class ThumbnailResponse {
	
	private OriginalPhotoResponse originalPhoto;
	private byte [] rawData;

}
