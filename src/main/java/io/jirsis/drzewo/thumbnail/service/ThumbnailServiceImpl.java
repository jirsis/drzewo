package io.jirsis.drzewo.thumbnail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import io.jirsis.drzewo.album.repository.AlbumEntity;
import io.jirsis.drzewo.album.repository.AlbumRepository;
import io.jirsis.drzewo.mapper.CustomMapper;
import io.jirsis.drzewo.thumbnail.controller.ThumbnailResponse;
import io.jirsis.drzewo.thumbnail.controller.ThumbnailResumeResponse;
import io.jirsis.drzewo.thumbnail.repository.ThumbnailEntity;
import io.jirsis.drzewo.thumbnail.repository.ThumbnailRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ThumbnailServiceImpl implements ThumbnailService{
	
	private ThumbnailRepository thumbnailRepository;
	private AlbumRepository albumRepository;
	
	private CustomMapper<ThumbnailEntity, ThumbnailResponse> mapper;
	
	@Override
	public ThumbnailResumeResponse getResumeThumbnails(String albumName) {
		ThumbnailResumeResponse response = null;
		response = new ThumbnailResumeResponse();
		Optional<List<ThumbnailEntity>> thumbnails = Optional.ofNullable(thumbnailRepository.findAll(example(albumName)));
		Optional<AlbumEntity> album = Optional.ofNullable(albumRepository.findOne(albumName));
		if(album.isPresent()) {response.setTotalImages(album.get().getTotalPhotos());}
		response.setAlbumName(albumName);
		response.setThumbnails(
					thumbnails.orElse(new ArrayList<>())
					.stream()
					.map(t -> mapper.from(t))
					.collect(Collectors.toList()));
		return response;
	}

	private Example<ThumbnailEntity> example(String albumName) {
		ThumbnailEntity entity = new ThumbnailEntity();
		entity.setAlbum(albumName);
		return Example.of(entity);
	}

}
