import cache.PropertiesCache;
import constants.CMEConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pagesObjects.PetroleumPage;
import utils.CommonTestOperation;
import static org.testng.Assert.assertTrue;

public class PetroleumIndexDataVerificationTNGTest extends ChromeWithWebDriverManagerTest{

    @Test(groups = "smoke")
    public void petroleumIndexDataVerification (){
        WebDriver driver = super.getDriver();
        CommonTestOperation.setupTest("petroleumIndexDataVerification", "Navigate to the 'Petroleum Index' page by " +
                "clicking 'Data' and then 'Petroleum Index' in the header navigation bar. There, verify " +
                "that the proper service data matches with the values displayed in the 2 tables (End of " +
                "Day Index Value and Intraday Index Value 5 Minute Intervals) - Important: You need to " +
                "find what’s the service to be verified.");
        try{
            PetroleumPage.launchCME(driver, PropertiesCache.getInstance().getProperty(CMEConstants.CME_PORTAL_TEST_URL));
            CommonTestOperation.maximizeWindow(driver);
            assertTrue(super.findElementWaitForVisible(By.xpath("//*[@id='collapsibleNavbarMenu']")).isDisplayed());
            assertTrue(PetroleumPage.clickOnData(driver));
            assertTrue(PetroleumPage.verifyDataPage(driver));
            assertTrue(PetroleumPage.clickOnPetroleumIndex(driver));
            assertTrue(PetroleumPage.verifyPetroleumIndexPage(driver));

            CommonTestOperation.tearDownTest(true);
        } catch(Exception e){
            CommonTestOperation.logException(e);
        }
    }

}
