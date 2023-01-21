package com.example.develop.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.example.develop.ComicApplication;
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
	private String apiKey;
	private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";
	private final OkHttpClient httpClient = new OkHttpClient.Builder()
			.connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
			.build();
	private final ObjectMapper objectMapper = new ObjectMapper();

	public ComicVineService() throws IOException {
		InputStream input = ComicApplication.class.getResourceAsStream("prop/API.prop");
		p.load(input);

		RestAssured.baseURI = (String) p.get("Base_Uri");
		RestAssured.port = Integer.parseInt((String) p.get("Port"));
		apiKey= (String) p.get("Api_Key");
	}
	public CompletableFuture<JsonNode> GetComicById(String idComic) {
		RestAssured.baseURI += "/issue/4000-"+idComic;
		Map<String, String> params = new HashMap<String, String>();

		params.put("api_key", apiKey);
		params.put("format", "json");
		params.put("field_list", "name,image,description,person_credits,character_credits,volume,issue_number,concept_credits");

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
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime now = LocalDateTime.now();
		String actualDate = dtf.format(now);


		Map<String, String> params = new HashMap<String, String>();
		params.put("api_key", apiKey);
		params.put("format", "json");
		params.put("field_list", "id,name,image,volume,issue_number");
		params.put("filter", "cover_date:1900-01-01|"+actualDate);
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
		params.put("api_key", apiKey);
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

		params.put("api_key", apiKey);
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


	public CompletableFuture<JsonNode> search(String domain, String keyword, int limit,int offset) {
		RestAssured.baseURI += "/"+domain+"/";
		Map<String, String> params = new HashMap<String, String>();
		params.put("api_key", apiKey);
		params.put("format", "json");
		params.put("filter", "name:"+keyword);
		params.put("limit", Integer.toString(limit));
		params.put("offset", Integer.toString(offset));

		System.out.print(domain);


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

	public static void main(String[] args) throws IOException {
		ComicVineService comicVineService = new ComicVineService();
		System.out.println(comicVineService.search("issues", "United", 60, 0).toString());
	}

}