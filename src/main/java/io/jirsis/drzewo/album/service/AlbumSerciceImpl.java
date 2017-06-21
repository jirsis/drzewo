package io.jirsis.drzewo.album.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import io.jirsis.drzewo.album.controller.AlbumDetailResponse;
import io.jirsis.drzewo.album.controller.AlbumResponse;
import io.jirsis.drzewo.album.controller.AllAlbumResponse;
import io.jirsis.drzewo.album.controller.NewAlbumResponse;
import io.jirsis.drzewo.album.repository.AlbumEntity;
import io.jirsis.drzewo.album.repository.AlbumRepository;
import io.jirsis.drzewo.directory.repository.FileSystemEntity;
import io.jirsis.drzewo.directory.repository.FileSystemHelper;
import io.jirsis.drzewo.directory.repository.FileSystemRepository;
import io.jirsis.drzewo.helper.PaginationHelper;
import io.jirsis.drzewo.mapper.CustomMapper;
import io.jirsis.drzewo.thumbnail.repository.ThumbnailEntity;
import io.jirsis.drzewo.thumbnail.repository.ThumbnailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AlbumSerciceImpl implements AlbumService {
	private AlbumRepository albumRepository;
	private ThumbnailRepository thumbnailRepository;
	private FileSystemRepository fileSystemRepository;

	private CustomMapper<AlbumEntity, NewAlbumResponse> mapperEntityToNewAlbumResponse;

	private FileSystemHelper fileSystemHelper;
	private ImageHelper imageHelper;
	private PaginationHelper paginationHelper;

	private Converter<ThumbnailEntity, AlbumDetailResponse> thumbnailEntityToAlbumDetailResponseConverter;

	@Override
	public NewAlbumResponse createNewAlbum(String albumName, String relativePath) {
		AlbumEntity entity = saveNewAlbum(albumName, relativePath);
		CompletableFuture.runAsync(() -> {
			try{
				generateThumbnails(albumName, relativePath);
			}catch(Exception e){
				albumRepository.delete(entity);
				thumbnailRepository.delete(thumbnailRepository.findByAlbum(entity.getName()));
			}
		});
		return mapperEntityToNewAlbumResponse.from(entity);
	}

	private void generateThumbnails(String albumName, String relativePath) {
		ThumbnailEntity thumbnail;

		for (File image : imageHelper.listAllImagesInDir(relativePath)) {
			if (thumbnailRepository.findOne(exampleThumbnail(albumName, image)) == null) {
				log.info("Thumbnail of {} in album '{}'", image.getName(), albumName);
				thumbnail = new ThumbnailEntity();
				thumbnail.setRawData(imageHelper.createThumbnail(image.getAbsolutePath()).toByteArray());
				thumbnail.setAlbum(albumName);
				thumbnail.setImage(image.getName());
				fillInformation(imageHelper.getPhotoInfo(image.getAbsolutePath()), thumbnail);
				thumbnailRepository.save(thumbnail);
			}
		}
	}

	private void fillInformation(PhotoDetails photoInfo, ThumbnailEntity thumbnail) {
		thumbnail.setExifOrientation(photoInfo.getOrientation());
		thumbnail.setWidth(photoInfo.getWidth());
		thumbnail.setHeight(photoInfo.getHeight());
	}

	private Example<ThumbnailEntity> exampleThumbnail(String albumName, File image) {
		ThumbnailEntity example = new ThumbnailEntity();
		example.setAlbum(albumName);
		example.setImage(image.getName());
		return Example.of(example);
	}

	private AlbumEntity saveNewAlbum(String albumName, String relativePath) {
		AlbumEntity entity = new AlbumEntity();
		entity.setName(albumName);
		File path = fileSystemHelper.jailedPath(relativePath);
		FileSystemEntity fileSystem = fileSystemRepository.listDirectory(relativePath);
		entity.setPath(path.getAbsolutePath());
		entity.setTotalPhotos(fileSystem.getImages());
		entity.setCreationDate(new Date());
		entity = albumRepository.save(entity);
		return entity;
	}

	@Override
	public byte[] sendOneImage(String albumName, String image) {
		ThumbnailEntity imageInAlbum = thumbnailRepository.findByAlbumAndImage(albumName, image);
		byte[] rawImage = null;
		if (imageInAlbum != null) {
			AlbumEntity album = albumRepository.findOne(albumName);
			rawImage = imageHelper.getImage(album.getPath(), image, imageInAlbum.getExifOrientation());
		}
		return rawImage;
	}

	@Override
	public AllAlbumResponse getAllAlbums(int page) {
		Page<AlbumEntity> albumsEntity = albumRepository.findAllByOrderByCreationDateDesc(paginationHelper.getPageable(page));
		
		AllAlbumResponse allAlbum = new AllAlbumResponse();
		allAlbum.setPagination(paginationHelper.getPaginationResponse(albumsEntity));
		
		Map<String, List<AlbumDetailResponse>> mapa = 
			albumsEntity
			.map(a -> a.getName())
			.map(a -> thumbnailRepository.findByAlbum(a))
			.getContent()
			.stream()
			.flatMap(t -> t.stream())
			.map(t -> thumbnailEntityToAlbumDetailResponseConverter.convert(t))
			.collect(Collectors.groupingBy(AlbumDetailResponse::getAlbumName, Collectors.toList()));
		
		List<AlbumResponse> albumResponse = albumsEntity.map(album -> {
			AlbumResponse response = new AlbumResponse();
			response.setName(album.getName());
			response.setDetail(mapa.get(album.getName()));
			return response;
		}).getContent();
		allAlbum.setAlbums(albumResponse);
		return allAlbum;
	}

}
