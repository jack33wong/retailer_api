package com.generic.retailer.controller;

import static org.hamcrest.MatcherAssert.assertThat;

import com.generic.retailer.RetailerAPIApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static io.restassured.RestAssured.given;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = RetailerAPIApplication.class)
class ProductIntegrationTest {

  @LocalServerPort
  int port;

  @BeforeEach
  public void setUp() {
    RestAssured.port = port;
  }

  @Test
  void listAllProduct() throws Exception {

    Response response = given()
        .when()
        .accept(ContentType.JSON)
        .get("/retailer/v0/products");

    response.then()
        .statusCode(200)
        .contentType(ContentType.JSON);

    assertThat(response.getBody().jsonPath().getJsonObject("trolley"), Matchers.iterableWithSize  (3));
  }

}