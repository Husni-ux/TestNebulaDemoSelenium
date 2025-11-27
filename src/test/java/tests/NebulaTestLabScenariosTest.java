package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.HomePage;
import pages.LoginPage;
import pages.sections.InteractiveMapSection;
import pages.sections.RealApiSection;
import pages.sections.VisibilitySection;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NebulaTestLabScenariosTest extends BaseTest {

    private static final String BASE_URL = "https://nebula-test-lab1.vercel.app/";

    private HomePage home;

    @BeforeEach
    void openAndLogin() {
        driver.get(BASE_URL);
        home = new LoginPage(driver)
                .waitForLoad()
                .loginAs("trainer", "selenium123");
    }

    @Test
    @Order(1)
    void realApiSearchAndPagination() {
        RealApiSection api = home.realApi().open().search("phone");

        System.out.println("Status text = " + driver.findElement(
                org.openqa.selenium.By.xpath("//section[@id='real-api-search']//div[contains(@class,'mb-3')]")
        ).getText());

        if (api.hasError()) {
            System.out.println("DummyJSON API returned error, skipping pagination checks.");
            return;
        }

        Assertions.assertTrue(api.rowsCount() > 0, "Expected at least one row after search");

        String firstIdPage1 = api.firstRowId();

        if (!api.isNextRendered()) {
            System.out.println("No pagination rendered (maybe only one page). Skipping next-page checks.");
            return;
        }
        if (!api.isNextEnabled()) {
            System.out.println("Next button disabled (only one page). Skipping next-page checks.");
            return;
        }

        api.goNextAndWaitDataChanges(firstIdPage1);
        String firstIdPage2 = api.firstRowId();

        Assertions.assertNotEquals(firstIdPage1, firstIdPage2,
                "First row in page 2 should be different than page 1");
    }

    @Test
    @Order(2)
    void interactiveMapSearchDrawAndClear() {
        InteractiveMapSection map = home.map()
                .open()
                .searchAndCenter("Riyadh")
                .startDrawing()
                .drawTriangle();

        Assertions.assertTrue(map.verticesCount() >= 3, "Expected at least 3 vertices");

        map.finishArea()
                .clearShapes();

        Assertions.assertEquals(0, map.verticesCount(), "Expected vertices list to be empty after clear");
    }

    @Test
    @Order(3)
    void visibilityAndStaleElementsDemo() {
        VisibilitySection vis = home.visibility().open();
        vis.runDemoAssertions();
    }
}