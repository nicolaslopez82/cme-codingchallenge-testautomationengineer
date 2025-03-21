import cache.PropertiesCache;
import com.relevantcodes.extentreports.LogStatus;
import constants.CMEConstants;
import extentreport.ExtentTestManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pagesObjects.LoginPage;
import utils.CommonTestOperation;

import static org.testng.Assert.assertTrue;

public class LoginFailureVerificationTNGTest extends WebDriverManagerTest {

    @Test(groups = "regression")
    public void loginFailureVerification (){

        ExtentTestManager.getTest().log(LogStatus.INFO, """
                LoginFailureVerification test description:
                Verify that attempting to log in with the username "00000"
                and password "00000" results in a red error message containing the text "we are
                having trouble logging you in.""");
        ExtentTestManager.getTest().log(LogStatus.INFO, "loginFailureVerification regression test started");

        WebDriver driver = super.getDriver();
        CommonTestOperation.setupTest("loginFailureVerification", "Verify that attempting to log in with the username '00000'" +
                "and password '00000' results in a red error message containing the text 'we are " +
                "having trouble logging you in.'");
        try{
            LoginPage.launchCMELogin(driver, PropertiesCache.getInstance().getProperty(CMEConstants.CME_PORTAL_TEST_URL_LOGIN));
            CommonTestOperation.maximizeWindow(driver);
            assertTrue(super.findElementWaitForVisible(By.xpath("//*[@id='login-panel']")).isDisplayed());
            LoginPage.setTextInUsername(driver);
            LoginPage.setTextInPassword(driver);
            LoginPage.clickLogin(driver);
            Thread.sleep(4000);
            assertTrue(LoginPage.verifyErrorMessage(driver));
            CommonTestOperation.tearDownTest(true);
        } catch(Exception e){
            CommonTestOperation.logException(e);
        }
        ExtentTestManager.getTest().log(LogStatus.INFO, "loginFailureVerification regression test completed");
    }
}
