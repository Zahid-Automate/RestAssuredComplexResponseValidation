package org.restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.*;

public class RestAssuredConfiguration {

    @BeforeSuite(alwaysRun=true)
    public void configure(){
        //RestAssured.baseURI = "http://localhost";
        RestAssured.baseURI = "https://www.googleapis.com";
        //RestAssured.port = 8080;
        //RestAssured.basePath="/SprintRestServices";
        RestAssured.basePath="/books";
    }

    public RequestSpecification getRequestSpecification() {
        return RestAssured.given().contentType(ContentType.JSON);
    }

    public Response getResponse(RequestSpecification requestSpecification, String endpoint, int status){
        Response response = requestSpecification.get(endpoint);
        Assert.assertEquals(response.getStatusCode(),status);
        response.then().log().all();
        return response;
    }
}