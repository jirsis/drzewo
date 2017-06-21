package io.jirsis.drzewo.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PaginationHelper {
	
	@Value("${io.jirsis.drzewo.pageSize}")
	private int size;

	public Pageable getPageable(int page) {
		return new PageRequest(page-1, size);
	}
	
	public PaginationResponse getPaginationResponse(Page<?> page){
		PaginationResponse pagination = new PaginationResponse();
		pagination.setCurrentPage(page.getNumber()+1);
		pagination.setTotalPages(page.getTotalPages());
		return pagination;
	}

}
