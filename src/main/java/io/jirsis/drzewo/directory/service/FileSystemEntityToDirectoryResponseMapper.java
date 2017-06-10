package io.jirsis.drzewo.directory.service;

import io.jirsis.drzewo.directory.controller.DirectoryResponse;
import io.jirsis.drzewo.directory.repository.FileSystemEntity;
import io.jirsis.drzewo.mapper.AbstractMapper;
import io.jirsis.drzewo.mapper.Mapper;

@Mapper
public class FileSystemEntityToDirectoryResponseMapper extends AbstractMapper<FileSystemEntity, DirectoryResponse>{

	@Override
	public DirectoryResponse from(FileSystemEntity source) {
		DirectoryResponse response = new DirectoryResponse();
		response.setDirectories(source.getDirs());
		response.setTotalImages(source.getImages());
		return response;
	}

}
