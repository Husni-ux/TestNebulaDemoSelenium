package pages.sections;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

public class RealApiSection extends BasePage {

    private final By section = By.id("real-api-search");
    private final By queryInput = By.id("real-api-query");
    private final By searchBtn = By.id("btn-real-api-search");
    private final By statusBar = By.xpath("//section[@id='real-api-search']//div[contains(@class,'mb-3')]");
    private final By tableRows = By.cssSelector("#real-api-table tbody tr");
    private final By nextBtn = By.id("real-api-next");
    private final By prevBtn = By.id("real-api-prev");

    public RealApiSection(WebDriver driver) { super(driver); }

    public RealApiSection open() {
        scrollIntoView(section);
        visible(section);
        return this;
    }

    public RealApiSection search(String q) {
        type(queryInput, q);
        click(searchBtn);


        waitUntilTextContains(searchBtn, "Search");

        WebElement bar = visible(statusBar);
        wait.until(d -> {
            String t = bar.getText();
            return t.contains("Found") || t.contains("No results") || t.contains("Error");
        });

        return this;
    }

    public boolean hasError() {
        return text(statusBar).contains("Error");
    }

    public int rowsCount() {
        return $$(tableRows).size();
    }

    public String firstRowId() {
        List<WebElement> rows = $$(tableRows);
        Assertions.assertFalse(rows.isEmpty(), "Expected at least one row in table");
        return rows.get(0).findElements(By.tagName("td")).get(0).getText();
    }

    public boolean isNextRendered() {
        return exists(nextBtn);
    }

    public boolean isNextEnabled() {
        List<WebElement> btns = $$(nextBtn);
        return !btns.isEmpty() && btns.get(0).isEnabled();
    }

    public RealApiSection goNextAndWaitDataChanges(String firstIdPage1) {
        click(nextBtn);

        wait.until(d -> {
            List<WebElement> rows = d.findElements(tableRows);
            if (rows.isEmpty()) return false;
            String firstIdNow = rows.get(0).findElements(By.tagName("td")).get(0).getText();
            return !firstIdNow.equals(firstIdPage1);
        });

        return this;
    }
}