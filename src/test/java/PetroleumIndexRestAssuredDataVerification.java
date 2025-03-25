import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

import cache.PropertiesCache;
import com.relevantcodes.extentreports.LogStatus;
import constants.CMEConstants;
import extentreport.ExtentTestManager;
import io.restassured.http.ContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pagesObjects.PetroleumIndexObject;
import pagesObjects.PetroleumPage;
import utilities.Functions;
import utilities.TimeHelper;
import utils.CommonTestOperation;


public class PetroleumIndexRestAssuredDataVerification extends WebDriverManagerTest {

    private static PetroleumIndexObject petroleumIndexObject;

    static {
        petroleumIndexObject = new PetroleumIndexObject();
    }

    @Test
    public void testGetPetroleumPrice(){

        ExtentTestManager.getTest().log(LogStatus.INFO, """
                PetroleumIndexDataVerification test description:
                Navigate to the 'Petroleum Index' page by clicking 'Data' and then 'Petroleum Index' in the header navigation bar. 
                There, verify that the proper service data matches with the values displayed in the 2 tables (End of Day Index Value and Intraday Index Value 5 Minute Intervals) 
                - Important: You need to find what’s the service to be verified.""");
        ExtentTestManager.getTest().log(LogStatus.INFO, "petroleumIndexDataVerification smoke test started");

        CommonTestOperation.setupTest("petroleumIndexDataVerification", "Navigate to the 'Petroleum Index' page by " +
                "clicking 'Data' and then 'Petroleum Index' in the header navigation bar. There, verify " +
                "that the proper service data matches with the values displayed in the 2 tables (End of " +
                "Day Index Value and Intraday Index Value 5 Minute Intervals) - Important: You need to " +
                "find what’s the service to be verified.");

        getValuesToCompare();

        baseURI = "https://www.cmegroup.com/services/petroleum-price";

        given()

                .when()
                .then()
                .statusCode(200)
                .contentType(ContentType.TEXT)
                .body("closingIndex.date", equalTo(petroleumIndexObject.getClosingIndexDate()))
                .body("closingIndex.price", equalTo(petroleumIndexObject.getClosingIndexPrice()))
                .body("intradateIndex.date", equalTo(petroleumIndexObject.getIntradateIndexDate()))
                .body("intradateIndex.price", equalTo(petroleumIndexObject.getIntradateIndexPrice()));

        CommonTestOperation.tearDownTest(true);
    }

    private void getValuesToCompare(){
        WebDriver driver = super.getDriver();

        try {
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
            By xPathForEndOfDayIndexValueDate = By.xpath("//*[@id='main-content']/div/div[4]/div/div[1]/div[2]/div[2]/table/tbody/tr/td[1]");
            String endOfDayIndexValueDate = Functions.getElementText(driver, xPathForEndOfDayIndexValueDate);
            System.out.println("endOfDayIndexValueDate -> " + endOfDayIndexValueDate);
            String endOfDayIndexValueDateFormatted = TimeHelper.convertDateStringToString(endOfDayIndexValueDate);
            petroleumIndexObject.setClosingIndexDate(endOfDayIndexValueDateFormatted);

            By xPathForEndOfDayIndexPrice = By.xpath("//*[@id='main-content']/div/div[4]/div/div[1]/div[2]/div[2]/table/tbody/tr/td[2]");
            String endOfDayIndexPrice = Functions.getElementText(driver, xPathForEndOfDayIndexPrice);
            System.out.println("endOfDayIndexPrice -> " + endOfDayIndexPrice);
            petroleumIndexObject.setClosingIndexPrice(endOfDayIndexPrice);

            // Getting data for Intraday Index.
            By xPathForIntradayIndexValueDate = By.xpath("//*[@id='main-content']/div/div[4]/div/div[1]/div[2]/div[4]/table/tbody/tr/td[1]");
            String intradayIndexValueDate = Functions.getElementText(driver, xPathForIntradayIndexValueDate);
            System.out.println("intradayIndexValueDate -> " + intradayIndexValueDate);
            String intradayIndexValueDateFormatted = TimeHelper.convertDateStringToString(intradayIndexValueDate);
            petroleumIndexObject.setIntradateIndexDate(intradayIndexValueDateFormatted);

            By xPathForIntradayIndexPrice = By.xpath("//*[@id='main-content']/div/div[4]/div/div[1]/div[2]/div[4]/table/tbody/tr/td[2]");
            String intradayIndexPrice = Functions.getElementText(driver, xPathForIntradayIndexPrice);
            System.out.println("intradayIndexValue -> " + intradayIndexPrice);
            petroleumIndexObject.setIntradateIndexPrice(intradayIndexPrice);

        } catch(Exception e){
            CommonTestOperation.logException(e);
        }
    }
}