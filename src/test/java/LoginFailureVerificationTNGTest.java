import cache.PropertiesCache;
import constants.CMEConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pagesObjects.LoginPage;
import utils.CommonTestOperation;


public class LoginFailureVerificationTNGTest extends ChromeWithWebDriverManagerTest {

    @Test(groups = "regression")
    public void loginFailureVerification (){
        WebDriver driver = super.getDriver();
        CommonTestOperation.setupTest("loginFailureVerification", "Verify that attempting to log in with the username '00000'" +
                "and password '00000' results in a red error message containing the text 'we are " +
                "having trouble logging you in.'");
        try{
            LoginPage.launchCMELogin(driver, PropertiesCache.getInstance().getProperty(CMEConstants.CME_PORTAL_TEST_URL_LOGIN));
            CommonTestOperation.maximizeWindow(driver);
            Assert.assertTrue(super.findElementWaitForVisible(By.xpath("//*[@id='login-panel']")).isDisplayed());
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
