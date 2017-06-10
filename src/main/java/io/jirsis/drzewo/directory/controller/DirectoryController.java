package io.jirsis.drzewo.directory.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jirsis.drzewo.directory.service.DirectoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@AllArgsConstructor
public class DirectoryController {
	
	private DirectoryService service; 
	
	@RequestMapping(path=DirectoryLinks.DEFAULT, method=GET)
	public ResponseEntity<DirectoryResponse> listPath(@RequestParam(defaultValue=".", required=false) String path){
		DirectoryResponse response = service.getInfoAboutDir(path);
		return ResponseEntity.ok(response);
	}

}
