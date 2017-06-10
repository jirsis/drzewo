package io.jirsis.drzewo.album.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
public class AlbumsResponse {
	private String album;
	
	@JsonInclude(Include.NON_NULL)
	private String image;
	private boolean thumbnail;
	

}
