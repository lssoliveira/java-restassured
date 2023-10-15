package dummyJson.domain;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public class ApiBase {

    @BeforeAll
    public static void testSetup() {
        baseURI = "https://dummyjson.com";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
        RestAssured.requestSpecification = new RequestSpecBuilder().
            setContentType(ContentType.JSON).build();
    }
}
