package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Actions actions;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        this.actions = new Actions(driver);
    }

    protected WebElement $(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected List<WebElement> $$(By by) {
        return driver.findElements(by);
    }

    protected WebElement visible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement clickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    protected void click(By by) {
        clickable(by).click();
    }

    protected void type(By by, String text) {
        WebElement el = visible(by);
        el.clear();
        el.sendKeys(text);
    }

    protected String text(By by) {
        return visible(by).getText();
    }

    protected void scrollIntoView(By by) {
        WebElement el = $(by);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    protected void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    protected boolean exists(By by) {
        return !driver.findElements(by).isEmpty();
    }

    protected void waitUntilTextContains(By by, String partial) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(by, partial));
    }

    protected void waitUntilTextContains(WebElement el, String partial) {
        wait.until(d -> el.getText() != null && el.getText().contains(partial));
    }
}