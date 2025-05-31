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
    @FindBy(linkText = "My Orders")  private WebElement myOrdersLink;
    @FindBy(linkText = "My Account")  private WebElement myAccountLink;
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
            wait.until(ExpectedConditions.elementToBeClickable(signOutLink)).click();
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
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
    
    public String checkFailedToLogIn() {
        logger.debug("Checking if passing invalid credentials did not sign in the user");
    	wait.until(
  			  ExpectedConditions.visibilityOf(invalidSignIn));                
        return invalidSignIn.getText().trim();
    }
    
    public boolean isUserLoggedIn(int i, String name) {
        try {
        	logger.debug("Check 'Sign In' button is not visible");
        	wait.until(ExpectedConditions.invisibilityOf(signInLink));
        	logger.debug("Check 'Sign Out' button is visible");
        	wait.until(ExpectedConditions.visibilityOf(signOutLink));
        	logger.debug("Check 'My Account' button is visible");
            wait.until(ExpectedConditions.visibilityOf(myAccountLink));
        	logger.debug("Check 'My Orders' button is visible");
            wait.until(ExpectedConditions.visibilityOf(myOrdersLink));
            return true;
        } catch (TimeoutException|NoSuchElementException e) {
            logger.error("Test case #{} ({}) failed", i + 1, name, e);
            return false;
        }
    }
    
    public void clickSignOut() {
    	logger.debug("Clicking Sign Out button");
        wait.until(
        		ExpectedConditions.refreshed(
        		ExpectedConditions.elementToBeClickable(signOutLink))).click();
    }

    public void loginAs(String username, String password) {
        open()
          .enterCredentials(username, password)
          .submit();
    }
}

