package pages.sections;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

public class InteractiveMapSection extends BasePage {

    private final By section = By.id("interactive-map");
    private final By mapDiv = By.id("leaflet-map");

    private final By searchInput = By.id("map-search-input");
    private final By searchBtn = By.xpath("//section[@id='interactive-map']//button[contains(.,'Search & Center')]");
    private final By errorSpan = By.xpath("//section[@id='interactive-map']//span[contains(@class,'text-rose-600')]");

    private final By startDrawBtn = By.xpath("//section[@id='interactive-map']//button[contains(.,'Start drawing area')]");
    private final By finishBtn = By.xpath("//section[@id='interactive-map']//button[contains(.,'Finish area')]");
    private final By clearBtn = By.xpath("//section[@id='interactive-map']//button[contains(.,'Clear marker & polygon')]");
    private final By verticesItems = By.cssSelector("#interactive-map ol li");

    public InteractiveMapSection(WebDriver driver) { super(driver); }

    public InteractiveMapSection open() {
        scrollIntoView(section);
        visible(section);
        return this;
    }

    public InteractiveMapSection searchAndCenter(String q) {
        type(searchInput, q);
        click(searchBtn);

        // يرجع الزر لنصه الطبيعي (خلص)
        WebElement btn = $(searchBtn);
        waitUntilTextContains(btn, "Search & Center");

        // fail لو ظهر error
        List<WebElement> errors = $$(errorSpan);
        if (!errors.isEmpty()) {
            Assertions.fail("Map search returned error: " + errors.get(0).getText());
        }
        return this;
    }

    public InteractiveMapSection startDrawing() {
        click(startDrawBtn);
        return this;
    }

    public InteractiveMapSection drawTriangle() {
        WebElement map = visible(mapDiv);
        int w = map.getSize().getWidth();
        int h = map.getSize().getHeight();

        actions
                .moveToElement(map, w / 4, h / 4).click()
                .moveToElement(map, w / 2, h / 3).click()
                .moveToElement(map, (int) (w * 0.7), (int) (h * 0.6)).click()
                .perform();

        wait.until(d -> d.findElements(verticesItems).size() >= 3);
        return this;
    }

    public int verticesCount() {
        return $$(verticesItems).size();
    }

    public InteractiveMapSection finishArea() {
        click(finishBtn);
        return this;
    }

    public InteractiveMapSection clearShapes() {
        click(clearBtn);
        wait.until(d -> d.findElements(verticesItems).size() == 0);
        return this;
    }
}