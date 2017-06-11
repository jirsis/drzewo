package io.jirsis.drzewo.album.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jirsis.drzewo.album.service.AlbumService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AlbumController {
	
	private AlbumService albumService;

//	@RequestMapping(path = AlbumLinks.ALBUM_NAME, method = GET)
//	public ResponseEntity<AlbumsResponse> getAllPhotosInAlbum(@PathVariable String name) {
//		AlbumsResponse response = new AlbumsResponse();
//		response.setAlbum(name);
//		response.setThumbnail(Boolean.FALSE);
//		return ResponseEntity.ok(response);
//	}

//	@RequestMapping(path = AlbumLinks.ALBUM_NAME_IMAGE, method = GET)
//	public ResponseEntity<AlbumsResponse> getSingleImage(@PathVariable("name") String album, @PathVariable String image) {
//		AlbumsResponse response = new AlbumsResponse();
//		response.setAlbum(album);
//		response.setImage(image);
//		response.setThumbnail(Boolean.FALSE);
//		return ResponseEntity.ok(response);
//	}

	@RequestMapping(path = AlbumLinks.ALBUM_NAME, method = POST)
	public ResponseEntity<NewAlbumResponse> generateAlbum(@PathVariable("name") String album, @RequestBody NewAlbumRequest request) throws URISyntaxException {
		NewAlbumResponse response = albumService.createNewAlbum(album, request.getPath());
		
		return ResponseEntity.created(new URI(String.format("%s/%s", AlbumLinks.DEFAULT, album))).body(response);
	}

}
