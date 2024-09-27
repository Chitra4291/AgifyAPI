package helpers;

import config.ConfigProperties;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiHelper {

    // Declare a static final variable for BASE_URL
    private static final String BASE_URL = ConfigProperties.getProperty("api.base.url");

    // Method to make the API request
    public static Response getAgeByName(String name) {
        return RestAssured.given()
                .baseUri(BASE_URL) // Use the loaded base URL
                .param("name", name)
                .get();
    }
}
