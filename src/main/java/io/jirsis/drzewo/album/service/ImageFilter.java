package io.jirsis.drzewo.album.service;

import java.io.File;
import java.io.FilenameFilter;

public class ImageFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith(".jpg");
	}

}
