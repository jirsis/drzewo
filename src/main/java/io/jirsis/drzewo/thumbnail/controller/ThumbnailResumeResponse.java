package io.jirsis.drzewo.thumbnail.controller;

import java.util.List;

import lombok.Data;

@Data
public class ThumbnailResumeResponse {
	
	private String albumName;
	private List<String> thumbnails;
	private int totalImages;

}
