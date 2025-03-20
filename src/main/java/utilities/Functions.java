package utilities;
import static com.celltrak.automation.utilities.GlobalVariables.timeout;
import static com.celltrak.automation.utilities.TimeHelper.generateMappedTimeZones;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Functions {
//	public static String csvFilename="";

//Updated 07/2024
public static boolean supplyText(WebDriver driver, By vxpath, String vtext) {
	try {
		// Wait for the element to be clickable and return false if it isn't within 30 seconds
		if (!Functions.waitForElementToBeClickable(driver, vxpath, 30)) {
			return false;
		}

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

	//Updated 07/2024
	public static boolean supplyKeys(WebDriver driver, By vxpath, Keys enter) {
		try {
			// Wait for the element to be clickable, with a timeout of 30 seconds
			if (!Functions.waitForElementToBeClickable(driver, vxpath, 30)) {
				throw new Exception("Element not found for XPath: " + vxpath);
			}

			// Send keys to the located element
			driver.findElement(vxpath).sendKeys(enter);
			return true;
		} catch (Exception e) {
			// Print the exception and return false
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}

	//Updated 07/2024
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

	//Updated 07/2024
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

	//Updated 07/2024
	public static boolean waitForPageLoad(WebDriver webDriver, int overallTimeout) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(overallTimeout));

		// Wait for site logo to be clickable
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//body/div/div//a[contains(@id,'site_logo')])[1]")));

		// Wait for JavaScript to load
		ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) webDriver)
				.executeScript("return document.readyState").equals("complete");

		// Wait for jQuery to load
		ExpectedCondition<Boolean> jQueryLoad = driver -> (Boolean) ((JavascriptExecutor) webDriver)
				.executeScript("return jQuery.active == 0");

		// Check both conditions
		return wait.until(jsLoad) && wait.until(jQueryLoad);
	}

	//Updated 07/2024
	public static boolean verifyTextElement(WebDriver driver, By vxpath, String vtext) {
		try {
			// Wait for the element to be clickable
			if (!Functions.waitForElementToBeClickable(driver, vxpath, 60, vtext)) {
				return false;
			}

			// Get the text from the element and compare it with the provided text
			String actualText = driver.findElement(vxpath).getText().trim();

			if (actualText.equalsIgnoreCase(vtext.trim())) {
				System.out.printf("Verified: actualText: '%s' & expectedText: '%s' are equal%n", actualText, vtext);
				return true;
			} else {
				System.out.printf("Verified: actualText: '%s' & expectedText: '%s' are NOT equal%n", actualText, vtext);
				return false;
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			return false;
		}
	}

	public static boolean verifyContainsTextElement(WebDriver driver, By vxpath, String vtext){
		try{
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 60, vtext)){
				return false;
			}
			String AText = driver.findElement(vxpath).getText().trim();
			if(AText.trim().contains(vtext.trim())){
				System.out.println("Verified AText:'"+ AText + "' & VText:'" + vtext + "' are equal");
				return true;
			} else {
				System.out.println("Verified AText:'"+ AText + "' & VText:'" + vtext + "' are NOT equal");
				return false;
			}
		}catch(Exception e){
			System.out.println("Exception= " + e);
			return false;
		}
	}
	public static boolean verifyTextElementAfterCleaningNonAlphaNumericChars(WebDriver driver, By vxpath, String vtext){
		try{
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 30, "")){
				return false;
			}
			String AText = driver.findElement(vxpath).getText();
			AText = Functions.getNonAlphanumaticCharactersCleannedSentence(AText);
			if(AText.trim().equals(vtext.trim())){
				System.out.println("Verified AText:'"+ AText + "' & VText:'" + vtext + "' are equal");
				return true;
			} else {
				System.out.println("Verified AText:'"+ AText + "' & VText:'" + vtext + "' are NOT equal");
				return false;
			}
		}catch(Exception e){
			System.out.println("Exception e="+e);
			return false;
		}
	}

	//Updated 07/2024
	public static boolean isElementDisplayed(WebDriver driver, By locator, String elementName) {
		try {
			// Wait until the element is clickable for a maximum of 30 seconds
			if (!Functions.waitForElementToBeClickable(driver, locator, 30, elementName)) {
				// Return false if the element is not clickable within the specified timeout
				return false;
			}

			// Check if the element is displayed
			boolean isDisplayed = driver.findElement(locator).isDisplayed();

			// Log the result
			System.out.println("Verified element '" + elementName + "' is " + (isDisplayed ? "Displayed" : "NOT Displayed"));

			return isDisplayed;
		} catch (Exception e) {
			// Catch any exception and log it
			System.out.println("Exception: " + e);
			return false;
		}
	}

	//Updated 07/2024
	public static boolean isElementDisplayed(WebDriver driver, By locator, String elementDescription, int timeout) {
		try {
			// Wait for the element to be clickable within the given timeout
			if (!Functions.waitForElementToBeClickable(driver, locator, timeout, elementDescription)) {
				return false;
			}

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

	//Updated 08/2024
	public static boolean verifyTextAttributeForDatePickerPlugin(WebDriver driver, By xpath, String attributeName, String expectedValue) {
		try {
			// Wait for the element to be clickable
			if(!Functions.waitForElementToBeClickable(driver, xpath, 30, expectedValue)) {
				return false;
			}

			// Click the element
			driver.findElement(xpath).click();

			// Wait for the page interaction to complete
			Thread.sleep(2000);

			// Get the attribute value
			String actualValue = driver.findElement(xpath).getAttribute(attributeName);

			// Check if the actual attribute value matches the expected value
			boolean isMatch = actualValue.equalsIgnoreCase(expectedValue);
			System.out.println("Attribute '" + attributeName + "' = '" + actualValue +"' " + (isMatch ? "matches" : "does not match") + " with expected value = '" + expectedValue + "'");

			return isMatch;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}

	//Updated 07/2024
	public static boolean verifyTextAttribute(WebDriver driver, By vxpath, String attributeName, String expectedValue) {
		try {
			// Wait for the element to be clickable
			if (!Functions.waitForElementToBeClickable(driver, vxpath, 30)) {
				System.out.println("Was it clickable?");
				return false;
			}

			// Get the attribute value and the text of the element
			String actualAttributeValue = driver.findElement(vxpath).getAttribute(attributeName);

			//Not sure why this is here
			//String elementText = driver.findElement(vxpath).getText();
			//System.out.println("Element Text: " + elementText);

			// Compare the actual attribute value with the expected value
			if (actualAttributeValue.equalsIgnoreCase(expectedValue)) {
				System.out.println("Attribute '" + attributeName + "' with value '" + actualAttributeValue + "' matches the expected value '" + expectedValue + "'.");
				return true;
			} else {
				System.out.println("Attribute '" + attributeName + "' with value '" + actualAttributeValue + "' does not match the expected value '" + expectedValue + "'.");
				return false;
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			return false;
		}
	}
	public static String getTextAttribute(WebDriver driver, By vxpath) throws Exception{
		if(!Functions.waitForElementToBeClickable(driver, vxpath, 30, "Element")){
			throw new Exception("Element not found for xpath "+vxpath);
		}
		Functions.jsScrollDownToElement(driver, vxpath);
		return driver.findElement(vxpath).getAttribute("value").trim();
	}

	//Updated 07/2024
	public static boolean jsClickAndWait(WebDriver driver, By vxpath, int waitAfterClick, String name) {
		try {
			// Wait for the element to be clickable
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 30, name)) {
				return false;
			}

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

	//Updated 08/2024  int x is not used and needs to be removed, highlights many unused or incorrect methods
	public static boolean clickAndWait(WebDriver driver, By vxpath, int x,String name) {
		try {
			// Wait for the element to be clickable
			if (!Functions.waitForElementToBeClickable(driver, vxpath, 30, name)) {
				return false;
			}

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

	public static boolean clickAndWaitLonger(WebDriver driver, By vxpath, String name){
		try{
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 100, name)){
				return false;
			}
			// Functions.jsScrollDownToElement(driver, vxpath);
			Thread.sleep(2000);
			driver.findElement(vxpath).click();
			Thread.sleep(3000);
			System.out.println("Verified '" + name + "' Clicked");

			//		Thread.sleep(Integer.valueOf((String.valueOf(waitafterclick) + "000")));
			try{
				return Functions.waitForPageLoad(driver, 100);
			}catch(Exception e){
				System.out.println("Waiting for PageLoad from JSLOAD & JQUERY Load");
				Thread.sleep(100);
				return Functions.waitForPageLoad(driver, 100);
			}
		}catch(Exception e){
			System.out.println("Exception e="+e);
			return false;
		}
	}

	//Updated 07/2024
	public static boolean jsCheckCheckBox(WebDriver driver, By locator, int waitAfterClick, String name) {
		try {
			// Wait for the element to be clickable
			if (!Functions.waitForElementToBeClickable(driver, locator, 30, name)) {
				return false;
			}

			// Click the checkbox using JavaScript Executor
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].click()", driver.findElement(locator));

			// Log success
			System.out.println("Checkbox '" + name + "' clicked successfully");

			// Optional: Wait after the click for any subsequent operations
			if (waitAfterClick > 0) {
				Thread.sleep(waitAfterClick * 1000);
			}

			return true;
		} catch (Exception e) {
			// Log failure
			System.out.println("Failed to click checkbox '" + name + "'");
			return false;
		}
	}

	//Updated 07/2024
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

	//Updted 07/2024
	public static boolean jsScrollDown(WebDriver driver, By locator, int scrollValue) {
		try {
			// Execute JavaScript to scroll down by the specified value
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, arguments[0]);", scrollValue);

			// Wait for a brief moment to allow the scroll to finish
			Thread.sleep(100);

			// Check if the element specified by the locator is clickable within 30 seconds
			return Functions.waitForElementToBeClickable(driver, locator, 30, "Element");
		} catch (InterruptedException e) {
			// Handle the InterruptedException separately
			Thread.currentThread().interrupt();
			System.out.println("InterruptedException: " + e.getMessage());
			return false;
		} catch (Exception e) {
			// Print exception and return false
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}

	//Updated 07/2024
	public static boolean hoverElement(WebDriver driver, By vxpath1, By vxpath2, String vtext) {
		try {
			// Wait for the first element to be clickable
			if (!Functions.waitForElementToBeClickable(driver, vxpath1, 30, vtext)) {
				return false;
			}

			WebElement element1 = driver.findElement(vxpath1);
			Actions action = new Actions(driver);

			// Try to perform hover and check second element's clickability within 3 attempts
			for (int attempt = 0; attempt < 3; attempt++) {
				action.moveToElement(element1).perform();
				// Implicitly wait for the hover effect to take place
				if (Functions.waitForElementToBeClickable(driver, vxpath2, 1)) {
					System.out.println("Verified Hover on Element '" + vtext + "' Performed");
					return true;
				}
				Thread.sleep(1000); // Adding pause between attempts
			}

			System.out.println("Hover Didn't work on Element '" + vtext + "'");
			return false;

		} catch (InterruptedException e) {
			// Re-interrupt the thread if an interruption occurs
			Thread.currentThread().interrupt();
			System.out.println("Hover didn't work on Element '" + vtext + "' due to interruption");
			return false;
		} catch (Exception e) {
			System.out.println("Hover didn't work on Element '" + vtext + "' due to exception: " + e.getMessage());
			return false;
		}
	}

	public static boolean hoverElementToGetToolTip(WebDriver driver, By vxpath, String vtext) throws Exception {
		try{
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
				return false;
			}
			Actions action = new Actions(driver);
			WebElement we = driver.findElement(vxpath);
			timeout = 3;
			do {
				action.moveToElement(we).build().perform();
				timeout--;
			} while (timeout > 0 && !Functions.waitForElementToBeClickable(driver, By.xpath(".//body/div[@class='tooltip_popup']"), 1));

			if (Functions.waitForElementToBeClickable(driver, By.xpath(".//body/div[@class='tooltip_popup']"), 2)){
				System.out.println("Verified Hover on Element '" + vtext +"' Performed");
				return true ;
			} else {
				return false ;
			}
		}catch(Exception e){
			System.out.println("Hover Didn't work on Element '" + vtext);
			return false;
		}
	}
	public static boolean jsVerifyTextElement(WebDriver driver, By vxpath, String vtext) throws Exception {
		try{
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
					return false;
			}
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			WebElement element = driver.findElement(vxpath);
			String AText = jse.executeScript("return arguments[0].text", element).toString().trim();
			if(AText.equalsIgnoreCase(vtext)){
				System.out.println("Verified AText:'"+ AText + "' & VText:'" + vtext + "' are equal");
				return true;
			} else {
				System.out.println("Verified AText:'"+ AText + "' & VText:'" + vtext + "' are NOT equal");
				return false;
			}
		}catch(Exception e){
			System.out.println("Exception e="+e.getMessage());
			return false;
		}
	}

	//Updatd 07/2024
	public static String getElementText(WebDriver driver, By xpath) throws Exception {
		// Wait for the element to be clickable, with a timeout of 30 seconds
		if (!Functions.waitForElementToBeClickable(driver, xpath, 30)) {
			throw new Exception("Element not found for xpath " + xpath);
		}

		// Get the trimmed text of the web element
		return driver.findElement(xpath).getText().trim();
	}

	//Updated 07/2024
	public static boolean isCheckboxChecked(WebDriver driver, By vxpath, String checkboxName) throws Exception {
		// Wait for the checkbox element to be clickable within a 30-second timeout
		if (!Functions.waitForElementToBeClickable(driver, vxpath, 30)) {
			return false;
		}

		// Check if the checkbox is selected and return the result
		boolean isChecked = driver.findElement(vxpath).isSelected();
		System.out.println("Checkbox " + checkboxName + " is " + (isChecked ? "Checked" : "NOT Checked"));
		return isChecked;
	}
	public static boolean isCheckboxOnlyCheckedForXLabel(WebDriver driver, By vxpath, String desiredLabelName) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		List<WebElement> listItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(vxpath));
		WebElement targetCheckbox = null;
		for (WebElement listItem : listItems) {
			WebElement label = listItem.findElement(By.tagName("label"));
			WebElement checkbox = listItem.findElement(By.cssSelector("input[type='checkbox']"));
			if (label.getText().equals(desiredLabelName)) {
				targetCheckbox = checkbox;
			} else if (checkbox.isSelected()) {
				checkbox.click();       //this click will uncheck all other checkbox
			}
		}
		boolean isSuccess = targetCheckbox != null && targetCheckbox.isSelected();
		if (isSuccess) {
			System.out.println("Checkbox with label '" + desiredLabelName + "' is successfully checked.");
		} else {
			System.out.println("Checkbox with label '" + desiredLabelName + "' could not be properly checked.");
		}
		return isSuccess;
	}
	public static boolean validateImageAndLabel(WebDriver driver, By vxpath, String expectedImage, String expectedText) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement container = wait.until(ExpectedConditions.presenceOfElementLocated(vxpath));
		List<WebElement> spans = container.findElements(By.tagName("span"));
		WebElement iconSpan = container.findElement(By.className("icon"));
		String backgroundImage = iconSpan.getCssValue("background-image");
		boolean isImageValid = backgroundImage.contains(expectedImage);
		boolean isTextValid = false;
		for (WebElement span : spans) {
			String spanText = span.getText();
			if (spanText.equals(expectedText) || Pattern.matches(expectedText, spanText)) {
				isTextValid = true;
				break;
			}
		}
		if (isImageValid && isTextValid) {
			System.out.println("Icon image and text/date validation successful.");
			return true;
		} else {
			if (!isImageValid) {
				System.out.println("Icon image is not as expected. Actual background-image: " + backgroundImage);
			}
			if (!isTextValid) {
				System.out.println("Text/Date validation failed. Expected text/date: " + expectedText);
			}
			return false;
		}
	}

	public static boolean verifymultiTextElements(WebDriver driver, By vxpath, String vtext) throws Exception{
		boolean flag = false;
		if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
				return false;
			}
		List<WebElement> elements = driver.findElements(vxpath);
		String AElements[] = vtext.trim().split(",");
		System.out.println("AElements.length="+AElements.length);
		System.out.println("elements.size()="+elements.size());
		if(elements.size()>0){
			if (AElements.length != elements.size()) {
			    System.out.println("fail, wrong number of elements found");
			    return false;
			}
			for (int i = 0; i < AElements.length; i++) {
			    String element = elements.get(i).getText().trim();
			    if (element.equals(AElements[i].trim())) {
			        System.out.println("passed on: " + element);
			        flag=true;
			    } else {
			        System.out.println("failed on: " + element);
			        return false;
			    }
			}
		}else {
			System.out.println("No Element present");
		}
		return flag;
	}

	public static boolean ischeckboxNotchecked(WebDriver driver, By vxpath) throws Exception{
		if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
				return false;
			}
		return !driver.findElement(vxpath).isSelected();
	}

	//Updated 07/2024
	public static boolean setZoomToDefault(WebDriver driver, By vxpath) {
		try {
			// Wait for the element to be clickable within 30 seconds
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(vxpath));

			// Send the control + 0 keys to reset the zoom
			element.sendKeys(Keys.chord(Keys.CONTROL, "0"));
			return true;
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
			return false;
		}
	}

	//Update 07/2024
	public static boolean verifySelectedDropdown(WebDriver driver, By locator, String expectedText) {
		try {
			// Wait for the element to be clickable for up to 30 seconds
			if (!Functions.waitForElementToBeClickable(driver, locator, 30)) {
				return false;
			}

			// Get the selected option text
			Select dropdown = new Select(driver.findElement(locator));
			String selectedText = dropdown.getFirstSelectedOption().getText().trim();

			// Compare the selected text with the expected text
			boolean isEqual = selectedText.equalsIgnoreCase(expectedText.trim());
			System.out.println("Verified selectedText: '" + selectedText + "' & expectedText: '" + expectedText
					+ "' are " + (isEqual ? "equal" : "NOT equal"));
			System.out.println("isEqual --> " + isEqual);
			return isEqual;
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
			return false;
		}
	}
	public static boolean verifyAllValuesOfDropdownInOrder(WebDriver driver, By vxpath, String [] vtext) {
		try{
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
				return false;
			}
			int count = 0;
			WebElement dropdown = driver.findElement(vxpath);
			Select select = new Select(dropdown);
			boolean match = false;
			List<WebElement> options = select.getOptions();
			for(WebElement we:options) {
				for (int i=count; i < vtext.length;){
					if (we.getText().trim().equals(vtext[i].trim())){
						System.out.println("Verified "+we.getText()+ " is matching with "+vtext[i]);
						count++;
						match = true;
						break;
					} else {
						System.out.println("Verified "+we.getText()+ " is not matching with "+vtext[i]);
						return false;
					}
				}
			}
			if (count == vtext.length) {
		        System.out.println("Total Count "+ count +" matched");
		    } else {
		        System.out.println("Total Count Not matched");
		        return false;
		    }
			return match;
		}catch(Exception e){
			System.out.println("Exception=" + e);
			return false;
		}
	}
	public static boolean verifyFewValuesOfDropdown(WebDriver driver, By vxpath, String [] vtext) {
		try{
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
				return false;
			}
//			WebElement dropdown = driver.findElement(vxpath);
//			Select select = new Select(dropdown);
			boolean match = false;
//			List<WebElement> options = select.getOptions();
			List<WebElement> options = driver.findElements(vxpath);
			for (int i=0; i < vtext.length; i++){
				for(WebElement we:options) {
					if (we.getText().trim().equals(vtext[i].trim())){
						System.out.println("Verified "+we.getText()+ " is matching with "+vtext[i]);
						match = true;
						break;
					} else {
						match = false;
					}
				}
				if (match == false){
					System.out.println("Verification failed for "+vtext[i]);
					return false;
				}
			}
			return match;

		}catch(Exception e){
			System.out.println("Exception=" + e);
			return false;
		}
	}

	public static boolean selectVisibleCheckboxInDropdown(WebDriver driver, int fieldsetno, String vtext){
		if(!Functions.clickAndWait(driver, By.xpath(".//*[@id='export_status']/div/div[1]/div[2]"), 2, "All Export Statuses")) {
			System.out.println("All Export Statuses haven't been selected");
			return false;
		}else{
			System.out.println("All Export Statuses have been selected");
			return true;
		}
	}

	//Updated 07/2024
	public static boolean selectVisibleTextInDropdown(WebDriver driver, By locator, String text) throws Exception {
		try {
			// Wait for the element to be clickable
			if (!Functions.waitForElementToBeClickable(driver, locator, 30)) {
				return false;
			}

			// Select the dropdown option by visible text
			Select dropdown = new Select(driver.findElement(locator));
			dropdown.selectByVisibleText(text);

			// Retry checking the selected option up to 5 times
			for (int i = 0; i < 5; i++) {
				if (Functions.verifySelectedDropdown(driver, locator, text)) {
					return true; // Return true if the correct option is selected
				}
				Thread.sleep(500);
			}

			// Verify again after retry attempts
			return Functions.verifySelectedDropdown(driver, locator, text);
		} catch (Exception e) {
			System.out.println("Exception=" + e);
			return false;
		}
	}
	public static boolean selectIndexinDropdown(WebDriver driver, By vxpath, int index) throws Exception {
		try{
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
				return false;
			}
			Select dropdown = new Select(driver.findElement(vxpath));
			dropdown.selectByIndex(index);
			Thread.sleep(500);
			return true;
		}catch(Exception e){
			System.out.println("Exception=" + e);
			return false;
		}
	}
	public static boolean selectDropdownByvalue(WebDriver driver, By vxpath, String value) throws Exception {
		try{
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
				return false;
			}
			Select dropdown = new Select(driver.findElement(vxpath));
			dropdown.selectByValue(value);
			Thread.sleep(500);
			return true;
		}catch(Exception e){
			System.out.println("Exception=" + e);
			return false;
		}
	}

	public static ArrayList<String> getListofTextElements(WebDriver driver, By vxpath){
		ArrayList<String> myarraylist = new ArrayList<String>();
		try{
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
				throw new Exception("Element not found for xpath "+vxpath);
			}
			for(WebElement element: driver.findElements(vxpath)){
				myarraylist.add(element.getText().toString());
			}
		}catch(Exception e){
			System.out.println("Exception=" + e);
		}
		return myarraylist;
	}
	public static boolean verifymultiTextElements3(WebDriver driver, By vxpath, String vtext) throws Exception{
		boolean flag = false;
		if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
			return false;
		}
		List<WebElement> elements = driver.findElements(vxpath);
		String AElements[] = vtext.trim().split("/");
		System.out.println("AElements.length="+AElements.length);
		System.out.println("elements.size()="+elements.size());
		if(elements.size()>0){
			if (AElements.length != elements.size()) {
			    System.out.println("fail, wrong number of elements found");
			    return flag=false;
			}
			for (int i = 0; i < AElements.length; i++) {
			    String element = elements.get(i).getText().trim();
			    if (element.equals(AElements[i].trim())) {
			        System.out.println("passed on: " + element);
			        flag=true;
			    } else {
			        System.out.println("failed on: " + element);
			        return flag=false;
			    }
			}
		}else {
			System.out.println("No Element present");
			return flag=false;

		}
		return flag;
	}

	//Updated 08/2024
	public static boolean verifyFirstOutputText(WebDriver driver, By elementXPath, String expectedText) {
		try {
			// Wait for the element to be clickable within 30 seconds
			if (!Functions.waitForElementToBeClickable(driver, elementXPath, 30)) {
				return false;
			}

			WebElement element = null;
			String actualText = null;
			int retryCount = 10;

			// Retry fetching the element to handle StaleElementReferenceException
			while (retryCount > 0) {
				try {
					element = driver.findElement(elementXPath);
					actualText = element.getText();
					break;
				} catch (StaleElementReferenceException e) {
					System.out.println("Element is not visible in DOM");
					Thread.sleep(500); // Wait for half a second before retrying
					retryCount--;
				}
			}

			// Remove the text content of any child elements from the actual text
			for (WebElement child : element.findElements(By.xpath(".//*"))) {
				actualText = actualText.replaceFirst(child.getText(), "").trim();
			}

			// Compare the trimmed actual text with the provided text
			boolean isEqual = actualText.trim().equals(expectedText.trim());
			System.out.println("Verified actualText:'" + actualText + "' & expectedText:'" + expectedText + "' are " + (isEqual ? "equal" : "NOT equal"));
			return isEqual;
		} catch (Exception e) {
			System.out.println("Exception =" + e);
			return false;
		}
	}
	public static boolean verifyFirstOutputTextStandardized(WebDriver driver, By elementXPath, String expectedText) {
		try {
			// Wait for the element to be clickable within 30 seconds
			if (!Functions.waitForElementToBeClickable(driver, elementXPath, 30)) {
				return false;
			}

			WebElement element = null;
			String actualText = null;
			int retryCount = 10;

			// Retry fetching the element to handle StaleElementReferenceException
			while (retryCount > 0) {
				try {
					element = driver.findElement(elementXPath);
					actualText = element.getText();
					break;
				} catch (StaleElementReferenceException e) {
					System.out.println("Element is not visible in DOM");
					Thread.sleep(500); // Wait for half a second before retrying
					retryCount--;
				}
			}

			// Remove the text content of any child elements from the actual text
			for (WebElement child : element.findElements(By.xpath(".//*"))) {
				actualText = actualText.replaceFirst(child.getText(), "").trim();
			}
			actualText = actualText.replaceAll("\n", "");
			// Compare the trimmed actual text with the provided text
			boolean isEqual = actualText.trim().equals(expectedText.trim());
			System.out.println("Verified actualText:'" + actualText + "' & expectedText:'" + expectedText + "' are " + (isEqual ? "equal" : "NOT equal"));
			return isEqual;
		} catch (Exception e) {
			System.out.println("Exception =" + e);
			return false;
		}
	}
	public static String getFirstOutputText(WebDriver driver, By vxpath){
		String text = "";
		try {
			if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
				throw new Exception("Element not found for xpath "+vxpath);
			}
			WebElement element = driver.findElement(vxpath);
			text = element.getText();
			for(WebElement child : element.findElements(By.xpath(".//*"))){
			    text = text.replaceFirst(child.getText(), "").trim();
			}
			System.out.println("AText="+text.trim());
		} catch (Exception e) {
			System.out.println("Exception =" + e);
		}
		return text;
	}
	public static String getDropdownSelectedValue(WebDriver driver, By vxpath) throws Exception {
		if(!Functions.waitForElementToBeClickable(driver, vxpath, 30)){
			throw new Exception("Element not found for xpath "+vxpath);
		}
		WebElement option = new Select(driver.findElement(vxpath)).getFirstSelectedOption();
		System.out.println(option.getText().trim());
		return option.getText().trim();
	}

	//Updated 07/2024
	// Supplies text to an input field and selects an option from the dropdown that matches the specified texts.
	public static boolean supplyTextAndSelectOutput(WebDriver driver, By vxpath, String vtext1, String vtext2) {
		// Log the input texts
		System.out.println("vtext1 passed to method is " + vtext1);
		System.out.println("vtext2 passed to method is " + vtext2);

		try {
			// Supply the initial text to the specified element
			Functions.supplyText(driver, vxpath, vtext1);

			// Attempt to make sure the dropdown becomes clickable by retrying the text input
			int retryCount = 0;
			while (!Functions.waitForElementToBeClickable(driver, By.xpath("//body/ul/li"), 5) && retryCount < 4) {
				Functions.supplyText(driver, vxpath, vtext1);
				retryCount++;
			}

			// Get the list of dropdown items
			List<WebElement> dropdownItems = driver.findElements(By.xpath("//body/ul/li"));

			// Iterate through the dropdown items to find the one with matching texts
			for (int i = 1; i <= dropdownItems.size(); i++) {
				WebElement item = driver.findElement(By.xpath("//body/ul/li[" + i + "]"));
				String itemText = item.getText();
				if (itemText.contains(vtext1) && itemText.contains(vtext2)) {
					driver.findElement(vxpath).sendKeys(Keys.ENTER); // Select the matched item
					Thread.sleep(250); // Wait briefly to ensure action is complete
					break;
				} else {
					driver.findElement(vxpath).sendKeys(Keys.ARROW_DOWN); // Move to the next dropdown item
					Thread.sleep(50); // Short pause before moving to the next item
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("Exception=" + e);
			return false;
		}
	}

	public static String getNonAlphanumaticCharactersCleannedSentence (String vtext1) {
		return vtext1.replaceAll("[^A-Za-z0-9 ]", "");

	}

	public static boolean selectMultipleOptions(WebDriver driver, By vxpath, String vtext1){
		System.out.println("selectMultipleOptions logging");
		WebElement selectElement = driver.findElement(By.name("export_status"));
		Select select = new Select(selectElement);

		WebElement allExportStatusesElement = driver.findElement(By.xpath(".//*[@id='export_status']/div/div[1]/div[2]"));
		//select.selectByVisibleText("All Export Statuses");
		System.out.println("allExportStatusesElement.isSelected() --> " + allExportStatusesElement.isSelected());

		new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='export_status']/div/div[1]/div[2]")));
		//select.selectByVisibleText("Exported");
		//exportedElement.click(); //TODO
		System.out.println("great quetion");
		List<WebElement> optionElements_ctchecked = selectElement.findElements(By.cssSelector("ctcheckbox ctchecked"));
		List<WebElement> optionElements_ctunchecked = selectElement.findElements(By.cssSelector("ctcheckbox ctunchecked"));

		for (WebElement we:optionElements_ctchecked) {
			System.out.println("optionElements -> " + we.getText());
		}

		for (WebElement we:optionElements_ctunchecked) {
			we.click();
			System.out.println("optionElements -> " + we.getText());
		}


		List<WebElement> optionElements = selectElement.findElements(By.tagName("option"));
		List<WebElement> optionList = select.getOptions();
		List<WebElement> selectedOptionList = select.getAllSelectedOptions();

		for (WebElement we:optionElements) {
			we.click();
			System.out.println("optionElements -> " + we.getText());
		}

		for (WebElement we:optionList) {
			System.out.println("optionList -> " + we.getText());
		}

		for (WebElement we:selectedOptionList) {
			System.out.println("selectedOptionList -> " + we.getText().toString());
			System.out.println("selectedOptionList isSelected -> " + we.isSelected());
		}

	/*	Select dropdown = new Select(driver.findElement(vxpath));
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='export_status']/div/div[2]/div[1]/span")));
		dropdown.selectByVisibleText("Exported");

		List<WebElement> selectedOptions = dropdown.getAllSelectedOptions();
		System.out.println(selectedOptions.size());
		for (WebElement element:selectedOptions
			 ) {
			System.out.println("Selected -> " + element.getText());
		}
		*/
		return false;

	}

	//Updated 08/2024
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

}
