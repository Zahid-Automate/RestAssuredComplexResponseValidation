package com.restAssured.test;

import bin.EmployeeBin;
import common.Endpoint;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.restassured.RestAssuredConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.equalTo;

public class Employee {
    @Test
    public void validateEmployee(){
        given().get("http://localhost:8080/SprintRestServices/employee/getEmployee").then().statusCode(200).log().all();
    }

    @Test(groups="demo")
    public void validateEmployee2(){
        given().get(Endpoint.GET_EMPLOYEE).then().statusCode(200).log().all();
    }

    //http://localhost:8080/SprintRestServices/employee/getEmployeeQuery?employeeId=100
    @Test(groups="demo")
    public void validateQueryParameter(){
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.queryParam("employeeId",200).log().all();
        given().spec(requestSpecification).get(Endpoint.GET_EMPLOYEE_QUERY_PARAM).then().statusCode(200).log().all();

        //Getting Response
        Response response = given().spec(requestSpecification).get(Endpoint.GET_EMPLOYEE_QUERY_PARAM);
        //Inline Validation
        //Hard Assertion
        response.then().body("firstName",equalTo("Fluent")).body("address",equalTo("New York"));
        //Soft Assertion
        response.then().body("firstName",equalTo("Fluent"),"address",equalTo("New York"));
        //Path Validation
        //1.Hard Assertion
        Assert.assertEquals(response.path("firstName"),"Fluent");
        Assert.assertEquals(response.path("lastName"),"Wait");
        //2. Soft Assertion
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.path("firstName"),"Fluent","First Name is Incorrect");
        softAssert.assertEquals(response.path("address"),"New York");
        softAssert.assertAll();

        // Java Object
        EmployeeBin employeeBin = response.as(EmployeeBin.class);
        //1. Hard Assertion
        Assert.assertEquals(employeeBin.getFirstName(),"Fluent");
        //2. Soft Assertion
        SoftAssert newSoftAssert = new SoftAssert();
        newSoftAssert.assertEquals(employeeBin.getFirstName(),"Fluent","First Name is Incorrect");
        newSoftAssert.assertEquals(employeeBin.getAddress(),"New York");
        newSoftAssert.assertAll();
    }


}
