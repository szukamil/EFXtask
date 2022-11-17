package com.example.efxtask.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

class PriceControllerTest {

    @Test
    void testPutWithProperBody() throws JSONException {
        JSONObject bodyObject = new JSONObject().put("feed", "106, EUR/USD, 1.1000,1.2,01-06-2020 12:01:01:001…" +
                "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002");
        RestAssured.given()
                .body(bodyObject.toString()).contentType(ContentType.JSON)
                .when()
                .post("http://localhost:8080/api/process")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void testPutWithWrongPath() throws JSONException {
        JSONObject bodyObject = new JSONObject().put("feed", "106, EUR/USD, 1.1000,1.2,01-06-2020 12:01:01:001");
        RestAssured.given()
                .body(bodyObject.toString()).contentType(ContentType.JSON)
                .when()
                .post("http://localhost:8080/api/processs")
                .then()
                .assertThat()
                .body("message", equalTo("path does not exists"));
    }

    @Test
    void testPutWithGet() throws JSONException {
        JSONObject bodyObject = new JSONObject().put("feed", "106, EUR/USD, 1.1000,1.2,01-06-2020 12:01:01:001…" +
                "107, EUR/JPY, 119.60,119.90,01-06-2020 12:01:02:002");
        RestAssured.given()
                .body(bodyObject.toString()).contentType(ContentType.JSON)
                .when()
                .get("http://localhost:8080/api/process")
                .then()
                .assertThat()
                .body("message", equalTo("method GET is not supported for this operation."));
    }

    @Test
    void testPutWithWrongBody() throws JSONException {
        JSONObject bodyObject = new JSONObject().put("feed", "g");
        RestAssured.given()
                .body(bodyObject.toString()).contentType(ContentType.JSON)
                .when()
                .post("http://localhost:8080/api/process")
                .then()
                .assertThat()
                .body("message", equalTo("Wrong format For input string: \"g\""));
    }

    @Test
    void testGetAllProper() {
        RestAssured.given()
                .when()
                .get("http://localhost:8080/api/feed?size=10&page=0")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    void testGetAllWrongPath() {
        RestAssured.given()
                .when()
                .get("http://localhost:8080/api/fe2ed?size=10&page=0")
                .then()
                .assertThat()
                .body("message", equalTo("path does not exists"));
    }

    @Test
    void testGetAllWithPost() {
        RestAssured.given()
                .when()
                .post("http://localhost:8080/api/feed?size=10&page=0")
                .then()
                .assertThat()
                .body("message", equalTo("method POST is not supported for this operation."));
    }
}