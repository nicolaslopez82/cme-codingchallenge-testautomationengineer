package utilities;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static utilities.TimeHelper.generateMappedTimeZones;


public class Functions {

	public static boolean supplyText(WebDriver driver, By vxpath, String vtext) {
		try {

			// Retry supplying text up to 3 times
			for (int i = 0; i < 3; i++) {
				// Clear the existing text and input the new text
				WebElement element = driver.findElement(vxpath);
				element.clear();
				element.sendKeys(vtext);

				// Pause briefly to allow the browser to process the input
				Thread.sleep(100);

				// Verify if the text was input correctly
				if (vtext.equals(element.getAttribute("value"))) {
					System.out.println("Verified Text Supplied as '" + vtext + "'");
					return true;
				}
			}

			// Final verification in case the loop does not succeed
			WebElement element = driver.findElement(vxpath);
			if (vtext.equals(element.getAttribute("value"))) {
				System.out.println("Verified Text Supplied as '" + vtext + "'");
				return true;
			}

			return false;
		} catch (Exception e) {
			System.out.println("Verified Text Supply Failed for '" + vtext + "'");
			return false;
		}
	}


	public static boolean waitForElementToBeClickable(WebDriver driver, By locator, int timeout) {
		try {
			// Wait for the page to load
			if (!Functions.waitForPageLoad(driver, timeout)) {
				System.out.println("Page load wait failed");
				return false; // Exit if page load fails
			}
			// Wait until the element is clickable
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception ex) {
			System.out.println("Element not found within " + timeout + " seconds");
			return false;
		}
	}
	
	public static boolean waitForElementToBeClickable(WebDriver driver, By xpath, int timeout, String elementDescription) {
		try {
			// Wait for the page to load completely (considering JS and jQuery)
			if (!Functions.waitForPageLoad(driver, timeout)) {
				System.err.println("Page load timed out for JS and jQuery.");
				return false; // Return false as page load failed
			}

			// Wait for the element to be clickable within the specified timeout
			new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.elementToBeClickable(xpath));

			return true; // Element is clickable
		} catch (Exception ex) {
			System.err.println("Element '" + elementDescription + "' not clickable after " + timeout + " seconds.");
			return false; // Element was not found/clickable within timeout
		}
	}

	public static boolean waitForPageLoad(WebDriver webDriver, int overallTimeout) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(overallTimeout));

		// Wait for JavaScript to load
		ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) webDriver)
				.executeScript("return document.readyState").equals("complete");

		// Wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = driver -> (Boolean) ((JavascriptExecutor) webDriver)
				.executeScript("return jQuery.active == 0");

		// Check both conditions
		return wait.until(jsLoad) && wait.until(jQueryLoad);
	}

	public static boolean jsClickAndWait(WebDriver driver, By vxpath, int waitAfterClick, String name) {
		try {
			// Click the element using JavaScript
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", driver.findElement(vxpath));

			// Optional wait after click, if specified
			Thread.sleep(Math.max(waitAfterClick, 0) * 1000);

			System.out.println("Verified '" + name + "' Clicked");

			// Wait for the page to load
			if(Functions.waitForPageLoad(driver, 30)) {
				return true;
			}

			// Additional wait and retry if initial wait fails
			System.out.println("Waiting for PageLoad from JSLOAD & JQUERY Load");
			Thread.sleep(5000);
			return Functions.waitForPageLoad(driver, 30);
		} catch(Exception e) {
			System.out.println("Verified '" + name + "' Click Didn't Work: " + e.getMessage());
			return false;
		}
	}

	public static boolean clickAndWait(WebDriver driver, By vxpath, String name) {
		try {
			// Click the element
			driver.findElement(vxpath).click();
			System.out.println("Verified '" + name + "' Clicked");

			// Wait for the page to load
			try {
				return Functions.waitForPageLoad(driver, 30);
			} catch (Exception e) {
				System.out.println("Waiting for PageLoad from JSLOAD & JQUERY Load");
				Thread.sleep(3000);
				return Functions.waitForPageLoad(driver, 50);
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			return false;
		}
	}
	
	public static boolean jsScrollDownToElement(WebDriver driver, By vxpath) {
		try {
			// Locate the element using the specified XPath
			WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.presenceOfElementLocated(vxpath));

			// Scroll the element into view using JavaScript
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

			// Wait for the element to be clickable
			new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(vxpath));

			// Verify the element is clickable using the custom function
			return Functions.waitForElementToBeClickable(driver, vxpath, 30, "Element");
		} catch (Exception e) {
			// Log any exceptions that occur
			System.out.println("Exception: " + e);
			return false;
		}
	}

	public static String getElementText(WebDriver driver, By xpath) throws Exception {
		// Get the trimmed text of the web element
		return driver.findElement(xpath).getText().trim();
	}

	public static String getNonAlphanumaticCharactersCleanedSentence (String vtext1) {
		return vtext1.replaceAll("[^A-Za-z0-9 ]", "");

	}

	public static Date getDateTimeByZoneId(String timeZoneName) {
		// Get the map of timezones
		Map<String, String> mapZone = generateMappedTimeZones();

		// Retrieve the full timezone ID from the map
		String fullTimeZoneId = mapZone.getOrDefault(timeZoneName, timeZoneName);

		// Get the current ZonedDateTime for the given time zone
		ZonedDateTime now = ZonedDateTime.now(ZoneId.of(fullTimeZoneId));

		// Convert ZonedDateTime to Timestamp
		Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
		System.out.println("Timestamp: " + timestamp);

		// Convert Timestamp to Date
		Date date = new Date(timestamp.getTime());
		System.out.println("Date: " + date);

		return date;
	}

	public static boolean isElementDisplayed(WebDriver driver, By locator, String elementDescription, int timeout) {
		try {
			// Check if the element is displayed
			boolean isDisplayed = driver.findElement(locator).isDisplayed();
			System.out.println("Verified element '" + elementDescription + "' is " + (isDisplayed ? "Displayed" : "NOT Displayed"));
			return isDisplayed;
		} catch (Exception e) {
			// Log the exception and return false
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}
}
