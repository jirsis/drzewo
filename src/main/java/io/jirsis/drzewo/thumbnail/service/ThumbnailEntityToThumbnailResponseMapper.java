package io.jirsis.drzewo.thumbnail.service;

import io.jirsis.drzewo.album.controller.AlbumLinks;
import io.jirsis.drzewo.mapper.CustomMapper;
import io.jirsis.drzewo.mapper.Mapper;
import io.jirsis.drzewo.thumbnail.controller.ThumbnailResponse;
import io.jirsis.drzewo.thumbnail.repository.ThumbnailEntity;

@Mapper
public class ThumbnailEntityToThumbnailResponseMapper extends CustomMapper<ThumbnailEntity, ThumbnailResponse>{

	@Override
	public ThumbnailResponse from(ThumbnailEntity source) {
		ThumbnailResponse response = new ThumbnailResponse();
		response.setRawData(source.getRawData());
		response.setOriginalPhoto(String.format("%s/%s/%s", AlbumLinks.DEFAULT, source.getAlbum(), source.getImage()));
		return response;
	}

}
