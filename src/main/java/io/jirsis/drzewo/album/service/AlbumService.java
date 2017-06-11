package io.jirsis.drzewo.album.service;

import java.util.Optional;

import io.jirsis.drzewo.album.controller.AlbumResponse;
import io.jirsis.drzewo.album.controller.NewAlbumResponse;

public interface AlbumService {
	
	NewAlbumResponse createNewAlbum(String albumName, String relativePath);
	
	Optional<AlbumResponse> getAlbumDetail(String albumName);

	byte[] sendOneImage(String album, String image);

}
