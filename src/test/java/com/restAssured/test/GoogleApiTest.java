package com.restAssured.test;

import bin.Google;
import bin.Item;
import bin.SaleInfo;
import bin.VolumeInfo;
import common.Endpoint;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.restassured.RestAssuredConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoogleApiTest {
    @Test(groups = {"demo", "google"})
    public void googleApiTest() {
        RequestSpecification requestSpecification = new RestAssuredConfiguration().getRequestSpecification();
        requestSpecification.queryParam("q", "turing");
        Response response =
                new RestAssuredConfiguration().getResponse(requestSpecification, Endpoint.GOOGLE_API, HttpStatus.SC_OK);
        Google google = response.as(Google.class, ObjectMapperType.GSON);
        SoftAssert softAssert = new SoftAssert();
        Assert.assertTrue(response.getTimeIn(TimeUnit.SECONDS) <= 10, "Response Time Is Not Within Limit");
        List<Item> items = google.getItems();
        for (Item item : items) {
            System.out.println(item.toString());
            softAssert.assertTrue(!item.getId().equalsIgnoreCase(""), "ID IS BLANK");
            VolumeInfo volumeInfo = item.getVolumeInfo();
            softAssert.assertTrue(!volumeInfo.getTitle().equalsIgnoreCase(""), "Title is Blank");
            SaleInfo saleInfo = item.getSaleInfo();
            softAssert.assertTrue(saleInfo.getCountry().equalsIgnoreCase("CA"),"Country is other than CA");
            System.out.println(saleInfo.toString());
            if(volumeInfo.getRatingsCount()!=null){
                if(volumeInfo.getRatingsCount()>1){
                    System.out.println("Publisher rating is greater than 1 :"+volumeInfo.getPublisher()+" RATING IS "+volumeInfo.getRatingsCount());
                }
                else System.out.println("Publisher rating is 1");
            }
            else System.out.println("Publisher rating is null or not present in response");

        }
        softAssert.assertAll();
    }
}

