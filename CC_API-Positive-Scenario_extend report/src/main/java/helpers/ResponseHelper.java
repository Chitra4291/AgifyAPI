package helpers;

import io.restassured.response.Response;

public class ResponseHelper {

    public static int extractAge(Response response) {
        return response.jsonPath().getInt("age");
    }

    public static String extractName(Response response) {
        return response.jsonPath().getString("name");
    }
    
    public static int extractCount(Response response) {
        return response.jsonPath().getInt("count");
    }
}
