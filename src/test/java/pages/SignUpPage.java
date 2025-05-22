package pages;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.Log;
import org.apache.logging.log4j.*;

public class SignUpPage extends BasePage {

    private static final Logger logger = Log.getLogger(SignUpPage.class);

    // Base paths
    private final String userTableBase = "//*[@id=\"CenterForm\"]/form/table[1]/tbody/tr[%d]/td[2]/input";
    private final String accountTableBase = "//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[%d]/td[2]/input";
    private final String profileTableBase = "//*[@id=\"CenterForm\"]/form/table[3]/tbody/tr[%d]/td[2]/input";

    // Locators – User Info
    private final By userIdField = By.xpath(String.format(userTableBase, 1));
    private final By newPasswordField = By.xpath(String.format(userTableBase, 2));
    private final By confirmPasswordField = By.xpath(String.format(userTableBase, 3));

    // Locators – Account Info
    private final By firstNameField = By.xpath(String.format(accountTableBase, 1));
    private final By lastNameField = By.xpath(String.format(accountTableBase, 2));
    private final By emailField = By.xpath(String.format(accountTableBase, 3));
    private final By phoneField = By.xpath(String.format(accountTableBase, 4));
    private final By address1Field = By.xpath(String.format(accountTableBase, 5));
    private final By address2Field = By.xpath(String.format(accountTableBase, 6));
    private final By cityField = By.xpath(String.format(accountTableBase, 7));
    private final By stateField = By.xpath(String.format(accountTableBase, 8));
    private final By zipField = By.xpath(String.format(accountTableBase, 9));
    private final By countryField = By.xpath(String.format(accountTableBase, 10));

    // Locators – Profile Info
    private final By languageDropdown = By.name("languagePreference");
    private final By categoryDropdown = By.name("favouriteCategoryId");
    private final By listCheckbox = By.xpath(String.format(profileTableBase, 3));
    private final By bannerCheckbox = By.xpath(String.format(profileTableBase, 4));

    // Locator – Submit button
    private final By submitButton = By.xpath("//*[@id=\"CenterForm\"]/form/div/button");

    // Locator – Navigation
    private final By signUpLink = By.xpath("//*[@id=\"Menu\"]/div[1]/a[3]");
    
    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public void goTo() {
        logger.debug("Clicking Sign Up link");
        driver.findElement(signUpLink).click();
    }

    public void fillForm(JSONObject data) {
        logger.debug("Filling sign up form");
        
        // User Info
        logger.debug("Entering user information");
        driver.findElement(userIdField).sendKeys(safe(data, "User ID"));
        driver.findElement(newPasswordField).sendKeys(safe(data, "New password"));
        driver.findElement(confirmPasswordField).sendKeys(safe(data, "Confirm password"));

        // Account Info
        logger.debug("Entering account information");
        driver.findElement(firstNameField).sendKeys(safe(data, "First name"));
        driver.findElement(lastNameField).sendKeys(safe(data, "Last name"));
        driver.findElement(emailField).sendKeys(safe(data, "Email"));
        driver.findElement(phoneField).sendKeys(safe(data, "Phone"));
        driver.findElement(address1Field).sendKeys(safe(data, "Address 1"));
        driver.findElement(address2Field).sendKeys(safe(data, "Address 2"));
        driver.findElement(cityField).sendKeys(safe(data, "City"));
        driver.findElement(stateField).sendKeys(safe(data, "State"));
        driver.findElement(zipField).sendKeys(safe(data, "Zip"));
        driver.findElement(countryField).sendKeys(safe(data, "Country"));

        // Profile Info
        logger.debug("Selecting profile preferences");
        if (data.containsKey("Language Preference") && data.get("Language Preference") != null) {
            logger.debug("Selecting language preference");
            new Select(driver.findElement(languageDropdown)).selectByVisibleText(data.get("Language Preference").toString());
        }

        if (data.containsKey("Favourite Category") && data.get("Favourite Category") != null) {
            logger.debug("Selecting favourite category");
            new Select(driver.findElement(categoryDropdown)).selectByVisibleText(data.get("Favourite Category").toString());
        }

        if (Boolean.TRUE.equals(data.get("Enable MyList"))) {
            logger.debug("Enabling MyList option");
            driver.findElement(listCheckbox).click();
        }

        if (Boolean.TRUE.equals(data.get("Enable MyBanner"))) {
            logger.debug("Enabling MyBanner option");
            driver.findElement(bannerCheckbox).click();
        }
    }

    public void submit() {
        logger.debug("Clicking submit button");
        driver.findElement(submitButton).click();
    }

    public void signUp(JSONObject testCase) {
        goTo();
        fillForm(testCase);
        submit();
    }

    private String safe(JSONObject obj, String key) {
        Object value = obj.get(key);
        return value != null ? value.toString() : "";
    }
}