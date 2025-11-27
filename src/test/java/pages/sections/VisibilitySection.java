package pages.sections;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import pages.BasePage;

public class VisibilitySection extends BasePage {

    private final By section = By.id("visibility-demo");

    private final By displayBox = By.id("display-box");
    private final By toggleDisplayBtn = By.id("btn-toggle-display-box");

    private final By visibilityBox = By.id("visibility-box");
    private final By toggleVisibilityBtn = By.id("btn-toggle-visibility-box");

    private final By offscreenBox = By.id("offscreen-box");
    private final By toggleOffscreenBtn = By.id("btn-toggle-offscreen-box");

    private final By domBox = By.id("dom-box");
    private final By removeDomBtn = By.id("btn-remove-dom-box");
    private final By resetDomBtn = By.id("btn-reset-dom-box");

    public VisibilitySection(WebDriver driver) { super(driver); }

    public VisibilitySection open() {
        scrollIntoView(section);
        visible(section);
        return this;
    }

    public void runDemoAssertions() {
        WebElement dBox = visible(displayBox);
        Assertions.assertTrue(dBox.isDisplayed());

        click(toggleDisplayBtn);
        Assertions.assertFalse(dBox.isDisplayed());

        click(toggleDisplayBtn);
        Assertions.assertTrue(dBox.isDisplayed());

        WebElement vBox = visible(visibilityBox);
        Assertions.assertTrue(vBox.isDisplayed());

        click(toggleVisibilityBtn);
        Assertions.assertFalse(vBox.isDisplayed());

        click(toggleVisibilityBtn);
        Assertions.assertTrue(vBox.isDisplayed());

        WebElement offBox = visible(offscreenBox);
        Assertions.assertTrue(offBox.isDisplayed());

        click(toggleOffscreenBtn);
        System.out.println("isDisplayed() after moving off-screen = " + offBox.isDisplayed());

        WebElement dom = visible(domBox);
        Assertions.assertTrue(dom.getText().contains("currently in the DOM"));

        click(removeDomBtn);
        Assertions.assertThrows(StaleElementReferenceException.class, dom::getText);

        click(resetDomBtn);
        WebElement domAgain = visible(domBox);
        Assertions.assertTrue(domAgain.isDisplayed());
    }
}