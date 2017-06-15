package io.jirsis.drzewo.thumbnail.controller;

import lombok.Data;

@Data
public class OriginalPhotoResponse {
	
	private String href;
	private double rotateDegrees;
	private double width;
	private double height;

}
