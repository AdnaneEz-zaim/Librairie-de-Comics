package fr.tse.prinfo3;


import org.junit.Assert;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;





class ComicVineServiceTest {
	
	/*@Before
	public void init() {
		
		RestAssured.port = 443;   
	}*/
	

	@Test
	public void test_searchLastesComics() {
		RestAssured.port = 443;   
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";
		
		
		Response response = RestAssured.given().header("User-Agent", userAgent).get("https://comicvine.gamespot.com/api/issues/?api_key=f9073eee3658e2a4f39a9f531ad521b935ce87bc&format=json&filter=cover_date:1900-01-01|2023-01-22&fieldlist=id,name,image,description,volume&sort=cover_date:desc");
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

	}
	
	public void test_searchComics() {
		RestAssured.port = 443;   
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";
		
		
		Response response = RestAssured.given().header("User-Agent", userAgent).get("https://comicvine.gamespot.com/api/issue/4000-10813/?api_key=f9073eee3658e2a4f39a9f531ad521b935ce87bc&format=json&field_list=id,name,image,description,person_credits,character_credits");
		
		int statusCode = response.getStatusCode();
		//String id = response.body("results.id[0]", equals("10813"))
		System.out.println(response.statusCode());
		System.out.println(response.asString());
		System.out.println(response.getBody().asString());
		System.out.println(response.statusLine());
		Assert.assertEquals(statusCode, 200);

	}

}
