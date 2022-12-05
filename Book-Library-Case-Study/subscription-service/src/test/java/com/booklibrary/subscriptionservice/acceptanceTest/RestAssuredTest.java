package com.booklibrary.subscriptionservice.acceptanceTest;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.when;

public class RestAssuredTest {

    @Test
    public void getAllBooksTest() {
        when()
                .get("http://localhost:8081/subscriptions")
                .then()
                .statusCode(200);
    }
}
