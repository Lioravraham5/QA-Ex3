package pages;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Log;
import org.apache.logging.log4j.*;

public class SignInPage extends BasePage {

    private static final Logger logger = Log.getLogger(SignInPage.class);

    // Locators
    private final By usernameField = By.xpath("//*[@id=\"Signon\"]/form/div/label[1]/input");
    private final By passwordField = By.xpath("//*[@id=\"Signon\"]/form/div/label[2]/input");
    private final By loginButton = By.xpath("//*[@id=\"Signon\"]/form/div/div/button");
    private final By signInLink = By.xpath("//*[@id=\"Menu\"]/div[1]/a[2]");
    private final By signOutLink = By.xpath("//*[@id=\"Menu\"]/div[1]/a[4]"); // Sign Out link when logged in
    
    public SignInPage(WebDriver driver) {
        super(driver);
    }

    public void goTo() {
        try {
            logger.debug("Checking if user is already logged in");
            String menuText = driver.findElement(signInLink).getText();
            logger.debug("Current menu text: " + menuText);
            
            // If menu shows "My Orders" instead of "Sign In", user is logged in
            if (menuText.contains("My Orders") || menuText.contains("My Account")) {
                logger.debug("User is logged in, performing logout");
                try {
                    driver.findElement(signOutLink).click();
                    logger.debug("Successfully clicked Sign Out link");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    logger.debug("Could not find Sign Out link: " + e.getMessage() + ", refreshing page");
                    driver.get("https://jpetstore.aspectran.com/");
                    Thread.sleep(1000);
                }
            } else {
                logger.debug("User is not logged in, proceeding to sign in");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.debug("Issue checking login status: " + e.getMessage());
        }
        
        try {
            logger.debug("Clicking Sign In link");
            driver.findElement(signInLink).click();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            logger.debug("Issue clicking Sign In: " + e.getMessage() + ", refreshing page");
            try {
                driver.get("https://jpetstore.aspectran.com/");
                Thread.sleep(1000);
                driver.findElement(signInLink).click();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void fillForm(JSONObject data) {
        logger.debug("Filling sign in form");
        
        // Clear and fill username field
        if (data.containsKey("Username") && data.get("Username") != null) {
            logger.debug("Entering username");
            driver.findElement(usernameField).clear();
            driver.findElement(usernameField).sendKeys(safe(data, "Username"));
        }

        // Clear and fill password field
        if (data.containsKey("Password") && data.get("Password") != null) {
            logger.debug("Entering password");
            driver.findElement(passwordField).clear();
            driver.findElement(passwordField).sendKeys(safe(data, "Password"));
        }
    }

    public void submit() {
        logger.debug("Clicking login button");
        driver.findElement(loginButton).click();
    }

    public void signIn(JSONObject testCase) {
        goTo();
        fillForm(testCase);
        submit();
        
        // Wait a moment for the page to respond
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String safe(JSONObject obj, String key) {
        Object value = obj.get(key);
        return value != null ? value.toString() : "";
    }
}