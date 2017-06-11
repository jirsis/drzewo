package io.jirsis.drzewo.directory.service;

import org.springframework.stereotype.Service;

import io.jirsis.drzewo.directory.controller.DirectoryResponse;
import io.jirsis.drzewo.directory.repository.FileSystemEntity;
import io.jirsis.drzewo.directory.repository.FileSystemRepository;
import io.jirsis.drzewo.mapper.CustomMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DirectoryServiceImpl implements DirectoryService{
	
	private FileSystemRepository repository;
	
	private CustomMapper<FileSystemEntity, DirectoryResponse> mapper;

	@Override
	public DirectoryResponse getInfoAboutDir(String relativePath) {
		FileSystemEntity directory = repository.listDirectory(relativePath);
		return mapper.from(directory);
	}

}
