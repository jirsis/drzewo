package io.jirsis.drzewo.thumbnail.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jirsis.drzewo.thumbnail.service.ThumbnailService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ThumbnailController {
	
	private ThumbnailService service;

	@RequestMapping(path = ThumbnailLinks.ALL_THUMBNAILS_BY_ALBUM, method = GET)
	public ResponseEntity<ThumbnailResumeResponse> getThumbnail(@PathVariable String album) {
		ThumbnailResumeResponse response = service.getResumeThumbnails(album);
		if(response.getTotalImages() == 0){
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}else if(response.getThumbnails().size() == response.getTotalImages()){
			return ResponseEntity.ok(response); 
		}else{
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
		}
	}

}
