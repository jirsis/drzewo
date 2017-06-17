package io.jirsis.drzewo.album.controller;

import lombok.Data;

@Data
public class AlbumResponse {
	private String album;
	private String href; 
	private int totalImages;
}
