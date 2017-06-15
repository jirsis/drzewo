package io.jirsis.drzewo.album.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;

import io.jirsis.drzewo.directory.repository.FileSystemHelper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.util.exif.Orientation;

@Slf4j
@Component
public class ImageHelper {

	@Value("${io.jirsis.drzewo.thumbnail.width}")
	private int thumbnailWidth;

	@Value("${io.jirsis.drzewo.thumbnail.height}")
	private int thumbnailHeight;

	@Setter
	@Autowired
	private FileSystemHelper fileSystemHelper;

	public List<File> listAllImagesInDir(String relativePath) {
		File path = fileSystemHelper.jailedPath(relativePath);
		return Arrays.asList(path.listFiles(new ImageFilter()));
	}
	

	public ByteArrayOutputStream createThumbnail(String imagePath) {
		Orientation orientation = getOrientation(imagePath);
		ByteArrayOutputStream thumbnail = createThumbnail(imagePath, orientation);
		return thumbnail;
	}

	private ByteArrayOutputStream createThumbnail(String imagePath, Orientation orientation) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			Thumbnails
				.of(imagePath)
				.forceSize(thumbnailWidth, thumbnailHeight)
				.outputFormat("jpg")
				.toOutputStream(stream);
			stream.close();
		} catch (IOException e) {
			logErrorStackTrace(e);
		}
		return stream;
	}
	
	public Optional<ExifIFD0Directory> getExifInformation(String imagePath) {
		Optional<ExifIFD0Directory> exif;
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(new File(imagePath));
			ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			exif = Optional.ofNullable(directory);
		} catch (ImageProcessingException | IOException e) {
			exif = Optional.empty();
		}

		return exif;
	}
	
	public Optional<JpegDirectory> getJpegInformation(String imagePath) {
		Optional<JpegDirectory> exif;
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(new File(imagePath));
			JpegDirectory directory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
			exif = Optional.ofNullable(directory);
		} catch (ImageProcessingException | IOException e) {
			exif = Optional.empty();
		}

		return exif;
	}

	private double calculateAngle(Orientation orientation) {
		double angle = 0;
		switch(orientation){
		case LEFT_BOTTOM:
			angle=90;
			break;
		case TOP_LEFT:
		default:
			angle=0;
			break;
		}
		return angle;
	}

	private Orientation getOrientation(String imagePath) {
		Orientation orientation = Orientation.TOP_LEFT;
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(new File(imagePath));
			Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			orientation = Orientation.typeOf(directory.getInt(ExifDirectoryBase.TAG_ORIENTATION));
		} catch (ImageProcessingException | IOException | MetadataException | NullPointerException e) {
			//if happend any problem, we asume default orientation
			orientation = Orientation.TOP_LEFT;
		} 
		return orientation;
	}	

	private void logErrorStackTrace(Exception e) {
		log.error(e.getLocalizedMessage());
		for (StackTraceElement trace : e.getStackTrace()) {
			log.error(trace.toString());
		}
	}

	public byte[] getImage(String path, String image) {
		Path pathFilesystem = Paths.get(path, image);
		byte [] rawImage;
		try {
			rawImage = Files.readAllBytes(pathFilesystem);
		} catch (IOException e) {
			rawImage = null;
		}

		return rawImage;
	}
}
