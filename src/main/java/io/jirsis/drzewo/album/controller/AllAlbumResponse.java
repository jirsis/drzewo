package io.jirsis.drzewo.album.controller;

import java.util.ArrayList;
import java.util.List;

import io.jirsis.drzewo.helper.PaginationResponse;
import lombok.Data;

@Data
public class AllAlbumResponse {
	
	private List<AlbumResponse> albums;
	
	private PaginationResponse pagination;
	
	public AllAlbumResponse(){
		albums = new ArrayList<>();
	}
	
	public boolean add(AlbumResponse album){
		return albums.add(album);
	}
	
}
