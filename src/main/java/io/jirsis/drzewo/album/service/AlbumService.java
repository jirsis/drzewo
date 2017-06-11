package io.jirsis.drzewo.album.service;

import io.jirsis.drzewo.album.controller.NewAlbumResponse;

public interface AlbumService {
	
	NewAlbumResponse createNewAlbum(String albumName, String relativePath);

}
