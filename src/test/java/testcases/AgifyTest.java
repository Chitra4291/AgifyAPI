package testcases;

import helpers.ApiHelper;
import helpers.ResponseHelper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import utils.ExcelUtil;
import listeners.CustomTestListener;

import java.io.IOException;
import java.util.List;
import utils.RetryAnalyzer;

@Listeners(CustomTestListener.class)
public class AgifyTest {

    @DataProvider(name = "userData")
    public Object[][] userData() throws IOException {
        List<Object[]> data = ExcelUtil.readExcelData("src/test/resources/test_data.xlsx");
        return data.toArray(new Object[0][]);
    }

    @Test(dataProvider = "userData", retryAnalyzer = RetryAnalyzer.class)
    public void testAgifyApi(int expectedCount, String name, int expectedAge) {
        Response response = ApiHelper.getAgeByName(name);

        // Extract data from the response
        int count = ResponseHelper.extractCount(response);
        int actualAge = ResponseHelper.extractAge(response);
        String responseName = ResponseHelper.extractName(response);

        // Log the request and response details
        logRequestResponse(response);

        // Validate the response with custom failure messages
        Assert.assertEquals(count, expectedCount, String.format("Count mismatch expected [%d] but found [%d]", expectedCount, count));
        Assert.assertEquals(responseName, name, String.format("Name mismatch expected [%s] but found [%s]", name, responseName));
        Assert.assertEquals(actualAge, expectedAge, String.format("Age mismatch expected [%d] but found [%d]", expectedAge, actualAge));
    }

    private void logRequestResponse(Response response) {
        if (response != null) {
            String responsePayload = response.asString();
            int statusCode = response.getStatusCode();
            String responseHeaders = response.getHeaders().toString();

            ExtentTest extentTest = CustomTestListener.getCurrentTest();
            extentTest.log(Status.INFO, "Response Payload: " + responsePayload);
            extentTest.log(Status.INFO, "Status Code: " + statusCode);
            extentTest.log(Status.INFO, "Response Headers: " + responseHeaders);
        }
    }

}
