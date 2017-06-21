package io.jirsis.drzewo.album.service;

import org.springframework.core.convert.converter.Converter;

import io.jirsis.drzewo.album.controller.AlbumDetailResponse;
import io.jirsis.drzewo.album.controller.AlbumLinks;
import io.jirsis.drzewo.mapper.Mapper;
import io.jirsis.drzewo.thumbnail.controller.OriginalPhotoResponse;
import io.jirsis.drzewo.thumbnail.repository.ThumbnailEntity;

@Mapper
public class ThumbnailEntityToAlbumDetailResponseConverter implements Converter<ThumbnailEntity, AlbumDetailResponse> {

	@Override
	public AlbumDetailResponse convert(ThumbnailEntity source) {
		AlbumDetailResponse response = new AlbumDetailResponse();
		response.setAlbumName(source.getAlbum());
		response.setThumbnailRaw(source.getRawData());
		response.setOriginalPhoto(convertOriginalPhotoResponse(source));
		return response;
	}

	private OriginalPhotoResponse convertOriginalPhotoResponse(ThumbnailEntity source) {
		OriginalPhotoResponse response = new OriginalPhotoResponse();
		response.setHeight(source.getHeight());
		response.setWidth(source.getWidth());
		response.setHref(String.format("%s/%s", AlbumLinks.ALBUM_NAME.replace("{name}", source.getAlbum()), source.getImage()));
		return response;
	}

}
