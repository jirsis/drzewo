package io.jirsis.drzewo.album.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jirsis.drzewo.thumbnail.controller.OriginalPhotoResponse;
import lombok.Data;

@Data
public class AlbumDetailResponse {

	@JsonIgnore
	private String albumName;
	private OriginalPhotoResponse originalPhoto;
	private byte [] rawData;
}
