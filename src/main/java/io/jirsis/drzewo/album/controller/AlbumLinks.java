package io.jirsis.drzewo.album.controller;

public interface AlbumLinks {
	public static final String DEFAULT="/albums";
	
	public static final String ALBUM_NAME=DEFAULT+"/{name}";
	
	public static final String ALBUM_NAME_IMAGE=ALBUM_NAME + "/{image}";
	
}
