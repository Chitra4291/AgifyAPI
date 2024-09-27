package testcases;

import helpers.ApiHelper;
import helpers.ResponseHelper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ExcelUtil;
import listeners.CustomTestListener;
import java.io.IOException;
import java.util.List;

@Listeners(CustomTestListener.class)
public class AgifyTest {

    @DataProvider(name = "userData")
    public Object[][] userData() throws IOException {
        List<Object[]> data = ExcelUtil.readExcelData("src/test/resources/testdata.xlsx");
        return data.toArray(new Object[0][]);
    }

    @Test(dataProvider = "userData")
    public void testAgifyApi(int expectedCount, String name, int expectedAge) {
        // Make the API call
        Response response = ApiHelper.getAgeByName(name);

        // Extract data from the response
        int count = ResponseHelper.extractCount(response);
        int actualAge = ResponseHelper.extractAge(response);
        String responseName = ResponseHelper.extractName(response);
        

        // Format the response output
        String formattedResponse = String.format("{\"count\":%d,\"name\":\"%s\",\"age\":%d}", count, responseName, actualAge);
        
        // Print the formatted response
        System.out.println("RESPONSE" +formattedResponse);

        // Validate the response
        Assert.assertEquals(count, expectedCount, "Count mismatch");
        Assert.assertEquals(responseName, name, "Name mismatch");
        Assert.assertEquals(actualAge, expectedAge, "Age mismatch");
        
    }
}
