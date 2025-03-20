import cache.PropertiesCache;
import constants.CMEConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.grid.Main;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

public class ChromeWithWebDriverManagerTest {
    protected int defaultWaitTimeSeconds = Integer.parseInt(PropertiesCache.getInstance().getProperty(CMEConstants.TEST_DEFAULT_WAIT_SECONDS));
    WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    static void setupSuite() {
        // Resolve driver for Selenium Grid
        WebDriverManager.chromedriver().setup();

        // Start Selenium Grid in standalone mode
        Main.main(new String[] { "standalone", "--port", "4445" });
        //Main.main(new String[] { "standalone", "--port", "5555" });
    }

    @BeforeTest
    public void setup() {
        driver = WebDriverManager.chromedriver()
                .remoteAddress("http://localhost:4445/wd/hub").create();
                //.remoteAddress("https://hub-coding-challenge:5555").create();
    }

    @AfterTest
    void tearDown() {
        driver.quit();
    }

    public WebElement findElementWaitForVisible(By by) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(defaultWaitTimeSeconds)).until(
                ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebDriver getDriver() { return driver; }
}
