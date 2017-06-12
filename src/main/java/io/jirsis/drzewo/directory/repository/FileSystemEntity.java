package io.jirsis.drzewo.directory.repository;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FileSystemEntity {
	
	private int images;
	private List<String> dirs;
	private String workingDirectory;
	private boolean root;
	
	public FileSystemEntity(){
		dirs= new ArrayList<>();
	}

	public boolean addDir(String dir){
		return dirs.add(dir);
	}

}
