package pagesObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.Functions;

public class PetroleumPage {

    public static boolean launchCME(WebDriver driver, String url) {
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

    public static boolean clickOnData(WebDriver driver) throws Exception {
        // Define the xpath for the Data link.
        By dataLinkPath = By.xpath("//*[@id='collapsibleNavbarMenu']/ul/li[2]/a");

        // Perform a JavaScript click and wait action on the data link.
        return Functions.clickAndWait(driver, dataLinkPath, "Data Link");
    }

    public static boolean verifyDataPage(WebDriver driver) throws Exception {
        // Define the xpath for the Data page.
        By dataLinkPath = By.id("market-data-home");
        // Use the isElementDisplayed method to verify if the Market Data Home is displayed.
        return Functions.isElementDisplayed(driver, dataLinkPath, "Market Data Home", 30);
    }

    public static boolean clickOnPetroleumIndex(WebDriver driver) {
        // Define the xpath for the Petroleum Index.
        By petroleumIndexLinkPath = By.xpath("//*[@id='collapsibleNavbarMenu']/ul/li[2]/div/div/div[2]/div/div/div/div[1]/div[2]/div[3]/div/div/div/ul/li[5]/a");

        // Perform a JavaScript click and wait action on the Petroleum Index link.
        return Functions.clickAndWait(driver, petroleumIndexLinkPath, "Petroleum Index Link");
    }

    public static boolean verifyPetroleumIndexPage(WebDriver driver) throws Exception {
        // Define the xpath for the Petroleum Index page.
        By dataLinkPath = By.id("cme-group-petroleum-index");

        // Use the isElementDisplayed method to verify if the CME Group Petroleum Index" is displayed.
        return Functions.isElementDisplayed(driver, dataLinkPath, "CME Group Petroleum Index", 30);
    }

    public static boolean launchPetroleumPriceService(WebDriver driver, String url) {
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

}
