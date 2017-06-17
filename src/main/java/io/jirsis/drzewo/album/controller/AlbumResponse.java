package io.jirsis.drzewo.album.controller;

import java.util.List;

import lombok.Data;

@Data
public class AlbumResponse {
	private String name;
	private List<AlbumDetailResponse> detail;
	
}
