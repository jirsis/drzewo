package io.jirsis.drzewo.album.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbumController {

	@RequestMapping(path = AlbumLinks.ALBUM_NAME, method = GET)
	public ResponseEntity<AlbumsResponse> getAllPhotosInAlbum(@PathVariable String name) {
		AlbumsResponse response = new AlbumsResponse();
		response.setAlbum(name);
		response.setThumbnail(Boolean.FALSE);
		return ResponseEntity.ok(response);
	}

	@RequestMapping(path = AlbumLinks.ALBUM_NAME_IMAGE, method = GET)
	public ResponseEntity<AlbumsResponse> getSingleImage(@PathVariable("name") String album, @PathVariable String image) {
		AlbumsResponse response = new AlbumsResponse();
		response.setAlbum(album);
		response.setImage(image);
		response.setThumbnail(Boolean.FALSE);
		return ResponseEntity.ok(response);
	}

	@RequestMapping(path = AlbumLinks.ALBUM_NAME, method = POST)
	public ResponseEntity<AlbumsResponse> generateAlbum(@PathVariable("name") String album) throws URISyntaxException {
		AlbumsResponse response = new AlbumsResponse();
		response.setAlbum(album);
		response.setThumbnail(Boolean.FALSE);
		return ResponseEntity.created(new URI(String.format("%s/%s", AlbumLinks.DEFAULT, album))).body(response);
	}

}
