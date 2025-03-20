package run;

import cache.PropertiesCache;
import constants.CMEConstants;
import ChromeWithWebDriverManagerTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pagesObjects.LoginPage;
import utils.CommonTestOperation;


public class LoginFailureVerificationTNGTest extends ChromeWithWebDriverManagerTest {

    private static final Logger logger = LogManager.getLogger(LoginFailureVerificationTNGTest.class);

    @Test
    public void loginFailureVerification (){
        WebDriver driver = super.getDriver();
        CommonTestOperation.setupTest("loginFailureVerification", "Verify that attempting to log in with the username '00000'" +
                "and password '00000' results in a red error message containing the text 'we are " +
                "having trouble logging you in.'")  ;
        try{
            LoginPage.launchCMELogin(driver, PropertiesCache.getInstance().getProperty(CMEConstants.CME_PORTAL_TEST_URL));
            LoginPage.setTextInUsername(driver);
            LoginPage.setTextInPassword(driver);
            LoginPage.clickLogin(driver);
            Thread.sleep(4000);
            Assert.assertTrue(LoginPage.verifyErrorMessage(driver));
            CommonTestOperation.tearDownTest(true);
        } catch(Exception e){
            CommonTestOperation.logException(e);
        }
    }
}
