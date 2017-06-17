package io.jirsis.drzewo.album.controller;

import lombok.Data;

@Data
public class PaginationResponse {
	
	private int currentPage;
	private int totalPages;
	private int itemsPerPage;
	private int totalItems;

}
