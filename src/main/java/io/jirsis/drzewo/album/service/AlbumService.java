package io.jirsis.drzewo.album.service;

import io.jirsis.drzewo.album.controller.AllAlbumResponse;
import io.jirsis.drzewo.album.controller.NewAlbumResponse;

public interface AlbumService {
	
	NewAlbumResponse createNewAlbum(String albumName, String relativePath);
	
	byte[] sendOneImage(String album, String image);
	
	AllAlbumResponse getAllAlbums(int page);

}
