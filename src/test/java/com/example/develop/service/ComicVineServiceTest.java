package com.example.develop.service;

import com.example.develop.model.Comic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import javafx.application.Platform;
import javafx.scene.image.Image;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.*;

public class ComicVineServiceTest {
    ComicVineService comicVineService = null;

    private static CompletableFuture<JsonNode> future = null;
    @Before
    public void init() throws IOException {
        comicVineService = new ComicVineService();
        RestAssured.port = 443;
    }

    @Test
    public void getComicById() throws ExecutionException, InterruptedException {

        String nameofComic = "";
        future = comicVineService.GetComicById("10813");
        JsonNode prefComics = future.get();
        nameofComic = prefComics.get("name").textValue();

        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";

        RestAssured.given().header("User-Agent", userAgent).get("https://comicvine.gamespot.com/api/issue/4000-10813/?api_key=f9073eee3658e2a4f39a9f531ad521b935ce87bc&format=json&field_list=id,name,image,description,person_credits,character_credits")
                .then().assertThat()
                .body("results.name", Matchers.equalTo(nameofComic));
    }


    @Test
    public void search() throws IOException, ExecutionException, InterruptedException {

        comicVineService = new ComicVineService();
        String titre = "";
        future = comicVineService.search("issues", "United", 10, 0);
        JsonNode prefComics = future.get();
        titre = prefComics.get(0).get("name").textValue();
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:106.0) Gecko/20100101 Firefox/106.0";

        RestAssured.given().header("User-Agent", userAgent).get("https://comicvine.gamespot.com/api/issues/?api_key=f9073eee3658e2a4f39a9f531ad521b935ce87bc&format=json&filter=name:United&limit=1&offset=0")
                .then().assertThat()
                .body("results.name", Matchers.hasItem(titre));

    }
}