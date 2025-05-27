package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Log;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignInPage extends BasePage {
    private static final Logger logger = Log.getLogger(SignInPage.class);

    @FindBy(linkText = "Sign In")   private WebElement signInLink;
    @FindBy(linkText = "Sign Out")  private WebElement signOutLink;
    @FindBy(linkText = "My Orders")  private WebElement successfullyLoggedInIndicator;
    @FindBy(xpath = "//label[contains(text(),'Username')]/input") private WebElement usernameInput;
    @FindBy(xpath = "//label[contains(text(),'Password')]/input") private WebElement passwordInput;
    @FindBy(css = "div.button-bar > button[type='submit']")     private WebElement loginButton;
    @FindBy(css = "div.panel.failed")     private WebElement invalidSignIn;
    

    public SignInPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public SignInPage open() {
        logger.debug("Opening home page");
        driver.get("https://jpetstore.aspectran.com/");

        // if already signed in, click “Sign Out” first
        if (isElementVisible(signOutLink)) {
            logger.debug("User appears logged in; clicking Sign Out");
            signOutLink.click();
            wait.until(ExpectedConditions.invisibilityOf(signOutLink));
        }

        logger.debug("Clicking Sign In");
        wait.until(ExpectedConditions.elementToBeClickable(signInLink)).click();
        wait.until(ExpectedConditions.visibilityOf(usernameInput));
        return this;
    }

    public SignInPage enterCredentials(String username, String password) {
        logger.debug("Entering credentials");
        usernameInput.clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    public void submit() {
        logger.debug("Submitting login");
        loginButton.click();
    }
    
    public String checkFailedToLogIn() {
        logger.debug("Checking if passing invalid credentials did not sign in the user");
    	wait.until(
  			  ExpectedConditions.visibilityOf(invalidSignIn));                
        return invalidSignIn.getText().trim();
    }
    
    public boolean isUserLoggedIn(int i, String name) {
        try {
            wait.until(ExpectedConditions.visibilityOf(successfullyLoggedInIndicator));
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            logger.error("Test case #{} ({}) failed", i + 1, name, e);
            return false;
        }
    }

    public void loginAs(String username, String password) {
        open()
          .enterCredentials(username, password)
          .submit();
    }
}

