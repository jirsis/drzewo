package io.jirsis.drzewo.album.service;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;

import io.jirsis.drzewo.album.controller.AlbumResponse;
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
public class AlbumSerciceImpl implements AlbumService {

	private AlbumRepository albumRepository;

	private ThumbnailRepository thumbnailRepository;

	private FileSystemRepository fileSystemRepository;

	private CustomMapper<AlbumEntity, NewAlbumResponse> mapperEntityToNewAlbumResponse;
	private CustomMapper<AlbumEntity, AlbumResponse> mapperEntityToAlbumResponse;

	private FileSystemHelper helper;

	private ImageHelper imageHelper;

	@Override
	public Optional<AlbumResponse> getAlbumDetail(String albumName) {
		Optional<AlbumEntity> album = Optional.ofNullable(albumRepository.findOne(albumName));
		return mapperEntityToAlbumResponse.from(album);
	}

	@Override
	public NewAlbumResponse createNewAlbum(String albumName, String relativePath) {
		AlbumEntity entity = saveNewAlbum(albumName, relativePath);
		CompletableFuture.runAsync(() -> {
			generateThumbnails(albumName, relativePath);
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
				fillExifInformation(image.getAbsolutePath(), thumbnail);
				fillJpegInformation(image.getAbsolutePath(), thumbnail);
				thumbnailRepository.save(thumbnail);
			}
		}
	}

	private void fillJpegInformation(String absolutePath, ThumbnailEntity thumbnail) {
		JpegDirectory jpegInformation = imageHelper.getJpegInformation(absolutePath).get();
		try {
			thumbnail.setWidth(jpegInformation.getDouble(JpegDirectory.TAG_IMAGE_WIDTH));
			thumbnail.setHeight(jpegInformation.getDouble(JpegDirectory.TAG_IMAGE_HEIGHT));
		} catch (MetadataException e) {
			e.printStackTrace();
		}

	}

	private void fillExifInformation(String absolutePath, ThumbnailEntity thumbnail) {
		ExifIFD0Directory exifInformation = imageHelper.getExifInformation(absolutePath).get();
		try {
			thumbnail.setExifOrientation(exifInformation.getInt(ExifDirectoryBase.TAG_ORIENTATION));
		} catch (MetadataException e) {
			e.printStackTrace();
		}
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
		File path = helper.jailedPath(relativePath);
		FileSystemEntity fileSystem = fileSystemRepository.listDirectory(relativePath);
		entity.setPath(path.getAbsolutePath());
		entity.setTotalPhotos(fileSystem.getImages());
		entity = albumRepository.save(entity);
		return entity;
	}

	@Override
	public byte[] sendOneImage(String albumName, String image) {
		ThumbnailEntity imageInAlbum = thumbnailRepository.findByAlbumAndImage(albumName, image);

		byte[] rawImage = null;
		if (imageInAlbum != null) {
			AlbumEntity album = albumRepository.findOne(albumName);
			rawImage = imageHelper.getImage(album.getPath(), image);
		}
		return rawImage;
	}

}
