package fr.tse.prinfo3.control;


import java.util.HashMap;
import java.util.Map;
import fr.tse.prinfo3.model.ResultIssue;
import fr.tse.prinfo3.model.SearchResultDto;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class ComicVineService {
	public ComicVineService() {
		RestAssured.baseURI = "https://comicvine.gamespot.com/api";
		RestAssured.port = 443;
	}
	//?api_key=f9073eee3658e2a4f39a9f531ad521b935ce87bc&sort=cover_date:desc
	public SearchResultDto search(String keyword, int limit,int offset) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("api_key", "f9073eee3658e2a4f39a9f531ad521b935ce87bc");
		params.put("resources","issue");
		params.put("format", "json");
		params.put("query", keyword);
		params.put("limit", Integer.toString(limit));
		params.put("offset", Integer.toString(offset));
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";
		SearchResultDto result = given().params(params).header("User-Agent", userAgent).expect().statusCode(200)
				.body("status_code", equalTo(1)).when().get("/search").as(SearchResultDto.class);
		return result;
	}
	
	public SearchResultDto searchLastesComics(int limit,int offset) {
		RestAssured.baseURI += "/issues";
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("api_key", "f9073eee3658e2a4f39a9f531ad521b935ce87bc");
		
		params.put("format", "json");
		params.put("field_list", "id,name,image,description,volume");
		params.put("sort", "cover_date:desc");
		
		

		params.put("limit", Integer.toString(limit));
		params.put("offset", Integer.toString(offset));
		
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";
		
		
		
		return given().params(params).header("User-Agent", userAgent).expect().statusCode(200)
				.body("status_code", equalTo(1)).when().get().as(SearchResultDto.class);

	}
	
	public ResultIssue searchComics(String idComic) {
		RestAssured.baseURI += "/issue/"+idComic;
		Map<String, String> params = new HashMap<String, String>();
		
		params.put("api_key", "f9073eee3658e2a4f39a9f531ad521b935ce87bc");
		
		params.put("format", "json");
		params.put("field_list", "name,image,description,person_credits");
	
		
		String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";
		
		
		
		return given().params(params).header("User-Agent", userAgent).expect().statusCode(200)
				.body("status_code", equalTo(1)).when().get().as(ResultIssue.class);

	}
	
	
}