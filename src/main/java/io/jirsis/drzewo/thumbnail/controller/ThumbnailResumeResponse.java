package io.jirsis.drzewo.thumbnail.controller;

import java.util.List;

import lombok.Data;

@Data
public class ThumbnailResumeResponse {
	
	private String albumName;
	private List<ThumbnailResponse> thumbnails;
	private int totalImages;

}
