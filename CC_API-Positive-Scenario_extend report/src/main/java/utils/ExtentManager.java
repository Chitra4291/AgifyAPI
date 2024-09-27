package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static ExtentReports createInstance() {
        String reportPath = "target/ExtentReports.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("API Test Report");
        sparkReporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);


        extent.setSystemInfo("Executor", "Chitra"); 
     
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        return extent;
    }
}
