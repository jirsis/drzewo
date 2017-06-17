package io.jirsis.drzewo.thumbnail.service;

import io.jirsis.drzewo.mapper.CustomMapper;
import io.jirsis.drzewo.mapper.Mapper;
import io.jirsis.drzewo.thumbnail.controller.OriginalPhotoResponse;
import io.jirsis.drzewo.thumbnail.controller.ThumbnailResponse;
import io.jirsis.drzewo.thumbnail.repository.ThumbnailEntity;
import lombok.AllArgsConstructor;

@Mapper
@AllArgsConstructor
public class ThumbnailEntityToThumbnailResponseMapper extends CustomMapper<ThumbnailEntity, ThumbnailResponse>{
	
	private CustomMapper<ThumbnailEntity, OriginalPhotoResponse> mapper;
	
	@Override
	public ThumbnailResponse from(ThumbnailEntity source) {
		ThumbnailResponse response = new ThumbnailResponse();
		response.setRawData(source.getRawData());
		response.setOriginalPhoto(mapper.from(source));
		return response;
	}

}
