package petroleum.price.restassured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;


public class TestGetPetroleumPrice {

    @Test
    public void testGetPetroleumPrice(){
        baseURI = "https://www.cmegroup.com/services/petroleum-price";

        given()
                .when()
                .then()
                .statusCode(200)
                //.body("closingIndex.lastUpdateTime", equalTo("-"));
                .body("closingIndex.lastUpdateTime", equalTo("2025-03-20 15:30:00"));
    }
}