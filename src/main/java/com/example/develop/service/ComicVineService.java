package com.example.develop.service;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.example.develop.ComicApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import okhttp3.*;


public class ComicVineService {
	private Properties p = new Properties ();
	private String apiKey;
	private String baseURI;
	private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";
	private final OkHttpClient httpClient = new OkHttpClient.Builder()
			.connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
			.build();
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static ComicVineService comicVineService = null;
	private ComicVineService() {
		try{
			InputStream input = ComicApplication.class.getResourceAsStream("prop/API.prop");
			p.load(input);
			apiKey= (String) p.get("Api_Key");
			baseURI =  (String) p.get("Base_Uri");
		}catch (IOException e){
			System.out.println(e);
		}

	}
	public static ComicVineService getComicVineService(){
		if(comicVineService == null)
			comicVineService = new ComicVineService();
		return  comicVineService;
	}

	private CompletableFuture<JsonNode> sendRequest(HttpUrl.Builder urlBuilder) {
		String url = urlBuilder.build().toString();
		return CompletableFuture.supplyAsync(() -> {
			Request request = new Request.Builder()
					.url(url)
					.header("User-Agent", userAgent)
					.build();
			try (Response response = httpClient.newCall(request).execute()) {
				if (!response.isSuccessful()) {
					throw new IOException("Unexpected code " + response);
				}
				JsonNode json = objectMapper.readTree(response.body().string());
				if (json.get("status_code").asInt() != 1) {
					throw new IOException("API error: " + json.get("error").asText());
				}
				return json.get("results");
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		});
	}

	public CompletableFuture<JsonNode> GetComicById(String idComic) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(baseURI + "/issue/4000-"+idComic).newBuilder();
		urlBuilder.addQueryParameter("api_key", apiKey);
		urlBuilder.addQueryParameter("format", "json");
		urlBuilder.addQueryParameter("field_list", "name,image,description,person_credits,character_credits,volume,issue_number,concept_credits");
		return sendRequest(urlBuilder);
	}

	public CompletableFuture<JsonNode> GetComicByConcept(String concept, int limit) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(baseURI + "/search/").newBuilder();
		urlBuilder.addQueryParameter("api_key", apiKey);
		urlBuilder.addQueryParameter("format", "json");
		urlBuilder.addQueryParameter("field_list", "id,name,image,volume,issue_number");
		urlBuilder.addQueryParameter("query", concept);
		urlBuilder.addQueryParameter("resources", "issue");
		urlBuilder.addQueryParameter("limit", Integer.toString(limit));
		return sendRequest(urlBuilder);
	}

	public CompletableFuture<JsonNode> GetComicByAuthor(String author, int limit) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(baseURI + "/search/").newBuilder();
		urlBuilder.addQueryParameter("api_key", apiKey);
		urlBuilder.addQueryParameter("format", "json");
		urlBuilder.addQueryParameter("field_list", "id,name,image,volume,issue_number");
		urlBuilder.addQueryParameter("query", author);
		urlBuilder.addQueryParameter("resources", "issue");
		urlBuilder.addQueryParameter("limit", Integer.toString(limit));
		return sendRequest(urlBuilder);
	}

	public CompletableFuture<JsonNode> searchLatestComics(int limit, int offset) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(baseURI + "/issues").newBuilder();
		urlBuilder.addQueryParameter("api_key", apiKey);
		urlBuilder.addQueryParameter("format", "json");
		urlBuilder.addQueryParameter("field_list", "id,name,image,volume,issue_number,publisher,store_date");
		urlBuilder.addQueryParameter("sort", "store_date:desc");
		urlBuilder.addQueryParameter("limit", Integer.toString(limit));
		urlBuilder.addQueryParameter("offset", Integer.toString(offset));
		return sendRequest(urlBuilder);
	}


	public CompletableFuture<JsonNode> searchAuthor(String idAuthor) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(baseURI + "/person/4040-" + idAuthor).newBuilder();
		urlBuilder.addQueryParameter("api_key", apiKey);
		urlBuilder.addQueryParameter("format", "json");
		urlBuilder.addQueryParameter("field_list", "id,name,image,deck,description,issues,created_characters,birth,country");
		return sendRequest(urlBuilder);
	}

	public CompletableFuture<JsonNode> searchCharacter(String idCharacter) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(baseURI + "/character/4005-" + idCharacter).newBuilder();
		urlBuilder.addQueryParameter("api_key", apiKey);
		urlBuilder.addQueryParameter("format", "json");
		urlBuilder.addQueryParameter("field_list", "id,name,image,deck,description,issue_credits,character_friends,character_enemies,date_added,count_of_issue_appearances");
		return sendRequest(urlBuilder);
	}

	public CompletableFuture<JsonNode> search(String domain, String keyword, int limit, int offset) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(baseURI + "/" + domain + "/").newBuilder();
		urlBuilder.addQueryParameter("api_key", apiKey);
		urlBuilder.addQueryParameter("format", "json");
		urlBuilder.addQueryParameter("filter", "name:" + keyword);
		urlBuilder.addQueryParameter("limit", Integer.toString(limit));
		urlBuilder.addQueryParameter("offset", Integer.toString(offset));
		return sendRequest(urlBuilder);

	}


}