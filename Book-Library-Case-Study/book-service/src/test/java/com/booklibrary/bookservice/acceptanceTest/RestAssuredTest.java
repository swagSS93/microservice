package com.booklibrary.bookservice.acceptanceTest;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class RestAssuredTest {

    @Test
    public void getAllBooksTest() {
        when()
                .get("/books")
                .then()
                .statusCode(200);
    }

    @Test
    public void addBookTest() throws JSONException {

        JSONObject requestParams = new JSONObject();
        requestParams.put("bookId", "B4234");
        requestParams.put("bookName", "Hercule Poirot");
        requestParams.put("author", "Agatha Christie");
        requestParams.put("availableCopies", 5);
        requestParams.put("totalCopies", 5);



        given().
                contentType("application/json").
                body(requestParams.toString()).
                when().
                post("/books").
                then().
                statusCode(201);
    }

}
