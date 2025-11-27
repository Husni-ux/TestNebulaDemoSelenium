package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.sections.InteractiveMapSection;
import pages.sections.RealApiSection;
import pages.sections.VisibilitySection;

public class HomePage extends BasePage {

    private final By siteTitle = By.id("site-title");

    public HomePage(WebDriver driver) { super(driver); }

    public HomePage waitForLoad() {
        visible(siteTitle);
        return this;
    }

    public RealApiSection realApi() {
        return new RealApiSection(driver);
    }

    public InteractiveMapSection map() {
        return new InteractiveMapSection(driver);
    }

    public VisibilitySection visibility() {
        return new VisibilitySection(driver);
    }
}
