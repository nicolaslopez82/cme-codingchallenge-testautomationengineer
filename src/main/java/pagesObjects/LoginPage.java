package pagesObjects;
import cache.PropertiesCache;
import constants.CMEConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.Functions;

public class LoginPage {

    public static boolean launchCMELogin(WebDriver driver, String url) {
        try {
            // Navigate to the specified URL
            driver.get(url);

            // Short sleep to allow the page to fully load
            Thread.sleep(2500);

            // Check if the current URL matches the expected URL with a trailing slash
            return driver.getCurrentUrl().equals(url + "/");

        } catch (Exception e) {
            // Log any exception encountered
            System.out.println("Exception=" + e);
            return false;
        }
    }

    public static boolean setTextInUsername(WebDriver driver) throws Exception {
        // Locate the input field by its ID
        By usernameField = By.id("user");

        // Retrieve the username from PropertiesCache
        String username = PropertiesCache.getInstance().getProperty(CMEConstants.CME_PORTAL_TEST_DATA_VALID_USER);

        // Use the Functions utility to set the text in the input field
        return Functions.supplyText(driver, usernameField, username);
    }

    public static boolean setTextInPassword(WebDriver driver) throws Exception {
        // Locate password input field by its id
        By passwordField = By.id("pwd");

        // Retrieve the password from the properties cache
        String password = PropertiesCache.getInstance().getProperty(CMEConstants.CME_PORTAL_TEST_DATA_VALID_PASS);

        // Use the supplyText method to input the password into the password field and return the result
        return Functions.supplyText(driver, passwordField, password);
    }

    public static boolean clickLogin(WebDriver driver) throws Exception {
        // Define the ID for the login button
        By loginButtonXPath = By.id("loginBtn");

        // Perform a JavaScript click and wait action on the login button
        return Functions.clickAndWait(driver, loginButtonXPath, "Login Button");
    }

    public static boolean verifyErrorMessage(WebDriver driver) throws Exception {
        WebElement e = driver.findElement(By.xpath("//*[@id='authProcessor']/div/div/div[1]/div/div/div[2]"));
        System.out.println("Error Message: " + e.getText());
        String text = e.getText();

        return text.equals("Sorry, we are having trouble logging you in. Please see the User Guide to resolve.");
    }
}
