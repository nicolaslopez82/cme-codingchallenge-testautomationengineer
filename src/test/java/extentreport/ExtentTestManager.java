package extentreport;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nicolaslopez82.
 */

public class ExtentTestManager {

    static Map extentTestMap = new HashMap();
    static ExtentReports extent = ExtentManager.getReporter();

    /**
     *
     * @return current report thread.
     */
    public static synchronized ExtentTest getTest(){
        return (ExtentTest)extentTestMap.get((int) (long)(
            Thread.currentThread().getId()
        ));
    }

    /**
     *
     * @param testName
     * @param desc
     * @return test
     */
    public static synchronized ExtentTest startTest(String testName, String desc){

        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }

    public static synchronized void endTest(){
        extent.endTest((ExtentTest)extentTestMap.get((int) (long) (
                Thread.currentThread().getId())
        ));
    }
}
