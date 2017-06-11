package io.jirsis.drzewo.album.service;

import java.util.Optional;

import io.jirsis.drzewo.album.controller.AlbumResponse;
import io.jirsis.drzewo.album.repository.AlbumEntity;
import io.jirsis.drzewo.mapper.CustomMapper;
import io.jirsis.drzewo.mapper.Mapper;

@Mapper
public class AlbumEntityToAlbumResponseMapper extends CustomMapper<AlbumEntity, AlbumResponse>{
	
	@Override
	public AlbumResponse from(AlbumEntity source){
		AlbumResponse response = new AlbumResponse();
		response.setAlbum(source.getName());
		response.setTotalImages(source.getTotalPhotos());
		return response;
	}
	
	@Override
	public Optional<AlbumResponse> from(Optional<AlbumEntity> source){
		if(source.isPresent()){
			AlbumResponse response = from(source.get());
			return Optional.of(response);
		}else{
			return Optional.empty();
		}
	}
	

}
