import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;


public class CommonTestOperation {
    private static final Logger logger = LogManager.getLogger(CommonTestOperation.class);

    public static void setupTest(String testName, String description) {
        //ExtentTestManager.startTest(testName, description);
        System.out.println("The thread ID for " + testName + " Chrome is "+ Thread.currentThread().getId());
        System.out.println("Starting test: " + testName);
        logger.info("Starting test: {}", testName);
    }

    public static void tearDownTest(boolean success) {
        if (success) {
            logger.info("Testcase {} completed successfully", getCurrentMethodName());
            System.out.println("Testcase completed successfully" + getCurrentMethodName());
        } else {
            logger.error("Testcase {} failed", getCurrentMethodName());
            System.out.println("Testcase failed" + getCurrentMethodName());
        }
    }

    private static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public  static void logException(Exception e){
        logger.error("Exception occurred on Testcase" + Thread.currentThread().getStackTrace()[1].getMethodName() + " failed", e);
        System.out.println("Exception occurred on Testcase " + Thread.currentThread().getStackTrace()[1].getMethodName() + " failed");
        Assert.fail();
    }
}
