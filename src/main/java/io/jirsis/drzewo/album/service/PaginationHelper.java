package io.jirsis.drzewo.album.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PaginationHelper {
	
	@Value("${io.jirsis.drzewo.pageSize}")
	private int size;

	public Pageable getPage(int page) {
		return new PageRequest(page, size);
	}

}
