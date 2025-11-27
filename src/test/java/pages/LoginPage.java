package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By loginForm = By.id("login-form");
    private final By username = By.id("username-input");
    private final By password = By.id("password-input");
    private final By loginBtn = By.id("btn-login");

    public LoginPage(WebDriver driver) { super(driver); }

    public LoginPage waitForLoad() {
        visible(loginForm);
        return this;
    }

    public HomePage loginAs(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginBtn);
        return new HomePage(driver).waitForLoad();
    }
}