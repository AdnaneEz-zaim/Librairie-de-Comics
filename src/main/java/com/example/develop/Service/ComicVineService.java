package com.example.develop.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;



public class ComicVineService {
	private Properties p = new Properties ();
	private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";
	private final OkHttpClient httpClient = new OkHttpClient.Builder()
			.connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
			.build();
	private final ObjectMapper objectMapper = new ObjectMapper();

	public ComicVineService() throws IOException {
		RestAssured.baseURI = "https://comicvine.gamespot.com/api";
		RestAssured.port = 443;
		FileInputStream fis=new FileInputStream("src\\main\\java\\com\\example\\develop\\API.prop");
		p.load(fis);
	}
	public CompletableFuture<JsonNode> GetComicById(String idComic) {
		RestAssured.baseURI += "/issue/4000-"+idComic;
		Map<String, String> params = new HashMap<String, String>();

		params.put("api_key", (String) p.get("Api_Key"));
		params.put("format", "json");
		params.put("field_list", "name,image,description,person_credits,character_credits,volume,issue_number");

		// Make request asynchronously
		return CompletableFuture.supplyAsync(() -> {
			JsonNode response = given().params(params).header("User-Agent", userAgent).expect().statusCode(200)
					.body("status_code", equalTo(1)).when().get().as(JsonNode.class);
			try {
				return objectMapper.readTree(response.get("results").toString());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		});
	}
	public CompletableFuture<JsonNode> searchLatestComics(int limit,int offset) {
		RestAssured.baseURI += "/issues";
		Map<String, String> params = new HashMap<String, String>();
		params.put("api_key", (String) p.get("Api_Key"));
		params.put("format", "json");
		params.put("field_list", "id,name,image,volume,issue_number");
		params.put("sort", "cover_date:desc");
		params.put("limit", Integer.toString(limit));
		params.put("offset", Integer.toString(offset));

		// Make request asynchronously
		return CompletableFuture.supplyAsync(() -> {
			JsonNode response =  given().params(params).header("User-Agent", userAgent).expect().statusCode(200)
					.body("status_code", equalTo(1)).when().get().as(JsonNode.class);
			try {
				return objectMapper.readTree(response.get("results").toString());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		});
	}
	public CompletableFuture<JsonNode> searchAuthor(String idAuthor) {
		RestAssured.baseURI += "/person/4040-"+idAuthor;
		Map<String, String> params = new HashMap<String, String>();
		params.put("api_key", (String) p.get("Api_Key"));
		params.put("format", "json");
		params.put("field_list", "id,name,image,deck,description,issues,created_characters,birth,country");

		// Make request asynchronously
		return CompletableFuture.supplyAsync(() -> {
			JsonNode result =  given().params(params).header("User-Agent", userAgent).expect().statusCode(200)
					.body("status_code", equalTo(1)).when().get().as(JsonNode.class);
			try {
				return objectMapper.readTree(result.get("results").toString());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		});
	}

	public CompletableFuture<JsonNode> searchCharacter(String idCharacter) {
		RestAssured.baseURI += "/character/4005-"+idCharacter;
		Map<String, String> params = new HashMap<String, String>();

		params.put("api_key", (String) p.get("Api_Key"));
		params.put("format", "json");
		params.put("field_list", "id,name,image,deck,description,issue_credits,character_friends,character_enemies,date_added,count_of_issue_appearances");

		// Make request asynchronously
		return CompletableFuture.supplyAsync(() -> {
			JsonNode result =  given().params(params).header("User-Agent", userAgent).expect().statusCode(200)
					.body("status_code", equalTo(1)).when().get().as(JsonNode.class);
			try {
				return objectMapper.readTree(result.get("results").toString());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		});
	}


	public JsonNode search(String keyword, int limit, int offset) throws JsonProcessingException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("api_key", (String) p.get("Api_Key"));
		params.put("resources","issue");
		params.put("format", "json");
		params.put("query", keyword);
		params.put("limit", Integer.toString(limit));
		params.put("offset", Integer.toString(offset));
		JsonNode result = given().params(params).header("User-Agent", userAgent).expect().statusCode(200)
				.body("status_code", equalTo(1)).when().get("/search").as(JsonNode.class);
		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.readTree(result.get("results").toString());
	}

	public static void main(String[] args) throws IOException {
		ComicVineService comicVineService = new ComicVineService();
		System.out.println(comicVineService.GetComicById("14582"));
	}

}