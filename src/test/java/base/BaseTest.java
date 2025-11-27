package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void baseSetUp() {
        driver = DriverFactory.newChrome();
    }

    @AfterEach
    void baseTearDown() {
        if (driver != null) driver.quit();
    }
}