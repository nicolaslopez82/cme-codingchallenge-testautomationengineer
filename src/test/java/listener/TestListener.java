package listener;

import extentreport.ExtentManager;
import extentreport.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Created by nicolaslopez82.
 */

public class TestListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult){
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    /**
     * Start operation for extentreports.
     * @param iTestResult
     */
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("onTestStart method" + getTestMethodName(iTestResult) + "start");
        ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(), "");
    }

    /**
     *
     * @param iTestResult
     */
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("onTestSuccess method " + getTestMethodName(iTestResult) + "succeed");
        ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed.");
    }

    /**
     *
     * @param iTestResult
     */
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("onTestFailure method " + getTestMethodName(iTestResult) + "failure");
        //Get driver from BaseTest and assign to local webdriver variable.
        Object testClass = iTestResult.getInstance();
        ExtentTestManager.getTest().log(LogStatus.FAIL, "Test fail.");
    }

    /**
     *
     * @param iTestResult
     */
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("onTestSkipped method " + getTestMethodName(iTestResult) + "skipped");
        ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped.");
    }

    /**
     *
     * @param iTestResult
     */
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }

    /**
     * Before starting all tests, onStart method runs.
     * @param iTestContext
     */
    public void onStart(ITestContext iTestContext) {
        System.out.println("onStart method " + iTestContext.getName());
    }

    /**
     * After ending all tests, onFinish method runs.
     * @param iTestContext
     */
    public void onFinish(ITestContext iTestContext) {
        System.out.println("onFinish method " + iTestContext.getName());
        //Do tier down operations for extentreports reporting.
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
    }
}
