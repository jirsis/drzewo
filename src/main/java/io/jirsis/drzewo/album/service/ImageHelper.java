package io.jirsis.drzewo.album.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
				.size(thumbnailWidth, thumbnailHeight)
				.rotate(calculateAngle(orientation))
				.outputFormat("jpg")
				.toOutputStream(stream);
			stream.close();
		} catch (IOException e) {
			logErrorStackTrace(e);
		}
		return stream;
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
		} catch (ImageProcessingException | IOException | MetadataException e) {
			logErrorStackTrace(e);
		}
		return orientation;
	}

	private void logErrorStackTrace(Exception e) {
		log.error(e.getLocalizedMessage());
		for (StackTraceElement trace : e.getStackTrace()) {
			log.error(trace.toString());
		}
	}
}
