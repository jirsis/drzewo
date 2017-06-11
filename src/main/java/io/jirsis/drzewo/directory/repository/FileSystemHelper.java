package io.jirsis.drzewo.directory.repository;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileSystemHelper {
	
	@Value("${io.jirsis.drzewo.gallery-path}")
	private String defaultPath;
	
	public File jailedPath(String relativePath) {
		String newPath = null;
		try {
			String path = new File(String.format("%s%s%s", defaultPath, File.separator, relativePath)).getCanonicalPath();
			if (path.startsWith(defaultPath)) {
				newPath = path;
			} else {
				newPath = defaultPath;
			}
		} catch (java.io.IOException e) {
			newPath = defaultPath;
		}
		return createJailedDirectory(newPath);
	}
	
	private File createJailedDirectory(String path) {
		File jailedDirectory;
		if(new File(path).exists()){
			jailedDirectory = new File(path);
		}else{
			jailedDirectory = new File(defaultPath);
		}
		return jailedDirectory;
	}

}
