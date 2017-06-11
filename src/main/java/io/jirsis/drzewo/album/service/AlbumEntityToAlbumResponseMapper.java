package io.jirsis.drzewo.album.service;

import io.jirsis.drzewo.album.controller.NewAlbumResponse;
import io.jirsis.drzewo.album.repository.AlbumEntity;
import io.jirsis.drzewo.mapper.CustomMapper;
import io.jirsis.drzewo.mapper.Mapper;

@Mapper
public class AlbumEntityToAlbumResponseMapper extends CustomMapper<AlbumEntity, NewAlbumResponse>{
	
	@Override
	public NewAlbumResponse from(AlbumEntity source){
		NewAlbumResponse response = new NewAlbumResponse();
		response.setAlbumName(source.getName());
		response.setTotalImages(source.getTotalPhotos());
		return response;
	}
	

}
