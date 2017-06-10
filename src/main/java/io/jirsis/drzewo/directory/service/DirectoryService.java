package io.jirsis.drzewo.directory.service;

import io.jirsis.drzewo.directory.controller.DirectoryResponse;

public interface DirectoryService {
	
	DirectoryResponse getInfoAboutDir(String relativePath);

	
}
