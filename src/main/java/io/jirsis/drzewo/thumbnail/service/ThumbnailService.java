package io.jirsis.drzewo.thumbnail.service;

import io.jirsis.drzewo.thumbnail.controller.ThumbnailResumeResponse;

public interface ThumbnailService {
	
	ThumbnailResumeResponse getResumeThumbnails(String albumName);

}
