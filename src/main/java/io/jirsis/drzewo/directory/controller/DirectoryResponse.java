package io.jirsis.drzewo.directory.controller;

import java.util.List;

import lombok.Data;

@Data
public class DirectoryResponse {
	
	private int totalImages;
	private List<String> directories;
	private String pwd;
	private boolean root;

}
