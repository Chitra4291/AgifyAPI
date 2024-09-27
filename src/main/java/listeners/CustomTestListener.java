package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import utils.ExtentManager;
import utils.RetryAnalyzer;

public class CustomTestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getCurrentTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        extentTest.assignAuthor("Chitra");
        extentTest.assignCategory("Regression Tests");
        extentTest.assignDevice(System.getProperty("os.name"));
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed");
        logRequestResponse(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        RetryAnalyzer retryAnalyzer = (RetryAnalyzer) result.getMethod().getRetryAnalyzer(result);
        
        if (retryAnalyzer != null) {
            boolean retry = retryAnalyzer.retry(result);
            if (retry) {
                test.get().log(Status.WARNING, "Test failed, retrying...");
                return; 
            }
        }

        
        test.get().log(Status.FAIL, "Test failed after retries: " + result.getThrowable());
        logRequestResponse(result);
    }



    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private void logRequestResponse(ITestResult result) {
        // Assuming response is available to log, adapt this according to your logic
        String requestPayload = "Request Payload Placeholder";
        String responsePayload = "Response Payload Placeholder";
        int statusCode = 200; // Placeholder for status code
        String responseHeaders = "Response Headers Placeholder";

        ExtentTest extentTest = test.get();
        extentTest.log(Status.INFO, "Request Payload: " + requestPayload);
        extentTest.log(Status.INFO, "Response Payload: " + responsePayload);
        extentTest.log(Status.INFO, "Status Code: " + statusCode);
        extentTest.log(Status.INFO, "Response Headers: " + responseHeaders);
    }
}
