package io.jirsis.drzewo.directory.repository;

import java.io.File;

import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class FileSystemRepository {

	private FileSystemHelper helper;

	public FileSystemEntity listDirectory(String relativePath) {
		File directory = helper.jailedPath(relativePath);

		FileSystemEntity entity = new FileSystemEntity();
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				addDirectory(file, entity);
			} else if (file.isFile()) {
				addFile(file, entity);
			}
		}
		boolean root = helper.isRoot(relativePath);
		entity.setRoot(root);
		if(root){
			entity.setWorkingDirectory("."+File.separator);
		}else{
			entity.setWorkingDirectory(relativePath+File.separator);
		}
		return entity;

	}
	
	private void addFile(File file, FileSystemEntity entity) {
		if (!file.isHidden()) {
			entity.setImages(entity.getImages() + 1);
		}
	}

	private void addDirectory(File file, FileSystemEntity entity) {
		entity.addDir(file.getName());
	}

}
