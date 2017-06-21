package io.jirsis.drzewo.thumbnail.service;

import io.jirsis.drzewo.album.controller.AlbumLinks;
import io.jirsis.drzewo.mapper.CustomMapper;
import io.jirsis.drzewo.mapper.Mapper;
import io.jirsis.drzewo.thumbnail.controller.OriginalPhotoResponse;
import io.jirsis.drzewo.thumbnail.repository.ThumbnailEntity;

@Mapper
public class ThumbnailEntityToOriginalPhotoResponse extends CustomMapper<ThumbnailEntity, OriginalPhotoResponse>{
	
	@Override
	public OriginalPhotoResponse from(ThumbnailEntity source) {
		OriginalPhotoResponse response = new OriginalPhotoResponse();
		response.setHeight(source.getHeight());
		response.setWidth(source.getWidth());
		response.setHref(String.format("%s/%s/%s", AlbumLinks.DEFAULT, source.getAlbum(), source.getImage()));
		return response;
	}
}
