package io.jirsis.drzewo.album.service;

import java.io.File;

import org.springframework.stereotype.Service;

import io.jirsis.drzewo.album.controller.NewAlbumResponse;
import io.jirsis.drzewo.album.repository.AlbumEntity;
import io.jirsis.drzewo.album.repository.AlbumRepository;
import io.jirsis.drzewo.directory.repository.FileSystemEntity;
import io.jirsis.drzewo.directory.repository.FileSystemHelper;
import io.jirsis.drzewo.directory.repository.FileSystemRepository;
import io.jirsis.drzewo.mapper.CustomMapper;
import io.jirsis.drzewo.thumbnail.repository.ThumbnailEntity;
import io.jirsis.drzewo.thumbnail.repository.ThumbnailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AlbumSerciceImpl implements AlbumService{
	
	private AlbumRepository albumRepository;
	
	private ThumbnailRepository thumbnailRepository;
	
	private FileSystemRepository fileSystemRepository;
	
	private CustomMapper<AlbumEntity, NewAlbumResponse> mapperEntityToResponse;
	
	private FileSystemHelper helper;
	
	private ImageHelper imageHelper;

	@Override
	public NewAlbumResponse createNewAlbum(String albumName, String relativePath){
		AlbumEntity entity = saveNewAlbum(albumName, relativePath);
		generateThumbnails(albumName, relativePath);
		return mapperEntityToResponse.from(entity);
	}

	private void generateThumbnails(String albumName, String relativePath) {
		ThumbnailEntity thumbnail;
		for(File image : imageHelper.listAllImagesInDir(relativePath)){
			log.info("Thumbnail of {} in album '{}'", image.getName(), albumName);
			thumbnail = new ThumbnailEntity();
			thumbnail.setRawData(imageHelper.createThumbnail(image.getAbsolutePath()).toByteArray());
			thumbnail.setAlbum(albumName);
			thumbnail.setImage(image.getName());
			thumbnailRepository.save(thumbnail);
		}
		
	}

	private AlbumEntity saveNewAlbum(String albumName, String relativePath) {
		AlbumEntity entity = new AlbumEntity();
		entity.setName(albumName);
		File path = helper.jailedPath(relativePath);
		FileSystemEntity fileSystem = fileSystemRepository.listDirectory(relativePath);
		entity.setPath(path.getAbsolutePath());
		entity.setTotalPhotos(fileSystem.getImages());
		entity = albumRepository.save(entity);
		return entity;
	}

}
