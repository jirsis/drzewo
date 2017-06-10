package io.jirsis.drzewo.album.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;

import org.junit.Test;

import io.jirsis.drzewo.album.controller.AlbumController;

public class AlbumRestAssuredTest {
	
	@Test public void 
	get_custom_album(){
		given()
			.standaloneSetup(new AlbumController())
		.when()
			.get("/albums/custom-name")
		.then()
			.statusCode(200)
			.and()
			.body("album", equalTo("custom-name"))
			.and()
			.body("image", isEmptyOrNullString())
			.and()
			.body("thumbnail", equalTo(Boolean.FALSE));
	}
	
	@Test public void 
	get_custom_image(){
		given()
			.standaloneSetup(new AlbumController())
		.when()
			.get("/albums/custom-album-name/custom-image")
		.then()
			.statusCode(200)
			.and()
			.body("album", equalTo("custom-album-name"))
			.and()
			.body("image", equalTo("custom-image"))
			.and()
			.body("thumbnail", equalTo(Boolean.FALSE));
	}
	
	@Test public void
	post_custom_album(){
		given()
			.standaloneSetup(new AlbumController())
		.when()
			.post("/albums/new-album-name")
		.then()
			.statusCode(201)
			.and()
			.body("album", equalTo("new-album-name"))
			.and()
			.body("image", isEmptyOrNullString())
			.and()
			.body("thumbnail", equalTo(Boolean.FALSE))
			.and()
			.header("location", equalTo("/albums/new-album-name"));
	}
}
