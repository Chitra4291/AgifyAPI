package utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import listeners.CustomTestListener;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryCount = 2;

    @Override
    public boolean retry(ITestResult result) {
        System.out.println("Retrying " + result.getName() + " - Attempt: " + (retryCount + 1));
        
        if (retryCount < maxRetryCount) {
            retryCount++;
            CustomTestListener.getCurrentTest().log(Status.WARNING, 
                "Retrying test " + result.getName() + " (" + retryCount + "/" + maxRetryCount + ")");
            return true; // Retry the test
        }
        
        return false; 
    }
}
