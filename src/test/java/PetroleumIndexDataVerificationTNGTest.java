import cache.PropertiesCache;
import com.relevantcodes.extentreports.LogStatus;
import constants.CMEConstants;
import extentreport.ExtentTestManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pagesObjects.PetroleumPage;
import utilities.*;
import utils.CommonTestOperation;

import static org.testng.Assert.assertTrue;

public class PetroleumIndexDataVerificationTNGTest extends WebDriverManagerTest {

    @Test(groups = "smoke")
    public void petroleumIndexDataVerification (){

        ExtentTestManager.getTest().log(LogStatus.INFO, """
                PetroleumIndexDataVerification test description:
                Navigate to the 'Petroleum Index' page by clicking 'Data' and then 'Petroleum Index' in the header navigation bar. 
                There, verify that the proper service data matches with the values displayed in the 2 tables (End of Day Index Value and Intraday Index Value 5 Minute Intervals) 
                - Important: You need to find what’s the service to be verified.""");
        ExtentTestManager.getTest().log(LogStatus.INFO, "petroleumIndexDataVerification smoke test started");

        WebDriver driver = super.getDriver();
        CommonTestOperation.setupTest("petroleumIndexDataVerification", "Navigate to the 'Petroleum Index' page by " +
                "clicking 'Data' and then 'Petroleum Index' in the header navigation bar. There, verify " +
                "that the proper service data matches with the values displayed in the 2 tables (End of " +
                "Day Index Value and Intraday Index Value 5 Minute Intervals) - Important: You need to " +
                "find what’s the service to be verified.");
        try{
            PetroleumPage.launchPetroleumPriceService(driver, "https://www.cmegroup.com/services/petroleum-price");
            By bodyXpath = By.xpath("/html/body/pre");
            String body = Functions.getElementText(driver, bodyXpath);
            System.out.println("body -> " + body);

            PetroleumPage.launchCME(driver, PropertiesCache.getInstance().getProperty(CMEConstants.CME_PORTAL_TEST_URL));
            CommonTestOperation.maximizeWindow(driver);
            assertTrue(super.findElementWaitForVisible(By.xpath("//*[@id='collapsibleNavbarMenu']")).isDisplayed());
            assertTrue(PetroleumPage.clickOnData(driver));
            assertTrue(PetroleumPage.verifyDataPage(driver));
            assertTrue(PetroleumPage.clickOnPetroleumIndex(driver));
            assertTrue(PetroleumPage.verifyPetroleumIndexPage(driver));

            Thread.sleep(2000);

            // Getting data for End of Day Index.
            By xPathForEndOfDayIndexValueTable = By.xpath("//*[@id='end-of-day-index-value']");
            String endOfDayIndexValueTable = Functions.getElementText(driver, xPathForEndOfDayIndexValueTable);
            System.out.println("endOfDayIndexValueTable -> " + endOfDayIndexValueTable);

            By xPathForEndOfDayIndexValueDate = By.xpath("//*[@id='main-content']/div/div[4]/div/div[1]/div[2]/div[2]/table/tbody/tr/td[1]");
            String endOfDayIndexValueDate = Functions.getElementText(driver, xPathForEndOfDayIndexValueDate);
            System.out.println("endOfDayIndexValueDate -> " + endOfDayIndexValueDate);

            String endOfDayIndexValueDateFormatted = TimeHelper.convertDateStringToString(endOfDayIndexValueDate);

            assertTrue(body.contains(endOfDayIndexValueDateFormatted));

            By xPathForEndOfDayIndexValue = By.xpath("//*[@id='main-content']/div/div[4]/div/div[1]/div[2]/div[2]/table/tbody/tr/td[2]");
            String endOfDayIndexValue = Functions.getElementText(driver, xPathForEndOfDayIndexValue);
            System.out.println("endOfDayIndexValue -> " + endOfDayIndexValue);

            assertTrue(body.contains(endOfDayIndexValue));

            // Getting data for Intraday Index.
            By xPathForIntradayIndexValueTable = By.xpath("//*[@id='intraday-index-value-5-minute-intervals']");
            String intradayIndexValueTable = Functions.getElementText(driver, xPathForIntradayIndexValueTable);
            System.out.println("intradayIndexValueTable -> " + intradayIndexValueTable);

            By xPathForIntradayIndexValueDate = By.xpath("//*[@id='main-content']/div/div[4]/div/div[1]/div[2]/div[4]/table/tbody/tr/td[1]");
            String intradayIndexValueDate = Functions.getElementText(driver, xPathForIntradayIndexValueDate);
            System.out.println("intradayIndexValueDate -> " + intradayIndexValueDate);

            String intradayIndexValueDateFormatted = TimeHelper.convertDateStringToString(intradayIndexValueDate);

            assertTrue(body.contains(intradayIndexValueDateFormatted));

            By xPathForIntradayIndexValue = By.xpath("//*[@id='main-content']/div/div[4]/div/div[1]/div[2]/div[4]/table/tbody/tr/td[2]");
            String intradayIndexValue = Functions.getElementText(driver, xPathForIntradayIndexValue);
            System.out.println("intradayIndexValue -> " + intradayIndexValue);

            assertTrue(body.contains(intradayIndexValue));

            CommonTestOperation.tearDownTest(true);
        } catch(Exception e){
            CommonTestOperation.logException(e);
        }
        ExtentTestManager.getTest().log(LogStatus.INFO, "petroleumIndexDataVerification smoke test completed");
    }

}
