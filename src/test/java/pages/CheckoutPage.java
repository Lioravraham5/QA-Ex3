package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.Log;

public class CheckoutPage extends BasePage {
    private static final Logger logger = Log.getLogger(CheckoutPage.class);
    
    // Message Bar confirmation 
    @FindBy(xpath="//div[@id='MessageBar']/p")   private WebElement messageBarConfirmation;
    
    // Buttons
    @FindBy(xpath = "//*[@id='CenterForm']/form/div/button[1]") private WebElement confirmButton;
    @FindBy(xpath = "//*[@id='CenterForm']/form/div/button[2]") private WebElement backButton;
    
    // Order Date
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[1]/tbody/tr/td[2]") private WebElement orderDateValue;
  
    // Fields displayed for verification 
    @FindBy(xpath = "//td[contains(text(), 'First name')]/following-sibling::td") private WebElement firstNameValue;
    @FindBy(xpath = "//td[contains(text(), 'Last name')]/following-sibling::td") private WebElement lastNameValue;
    @FindBy(xpath = "//td[contains(text(), 'Address 1')]/following-sibling::td") private WebElement address1Value;
    @FindBy(xpath = "//td[contains(text(), 'Address 2')]/following-sibling::td") private WebElement address2Value;
    @FindBy(xpath = "//td[contains(text(), 'City')]/following-sibling::td") private WebElement cityValue;
    @FindBy(xpath = "//td[contains(text(), 'State')]/following-sibling::td") private WebElement stateValue;
    @FindBy(xpath = "//td[contains(text(), 'Zip')]/following-sibling::td") private WebElement zipValue;
    @FindBy(xpath = "//td[contains(text(), 'Country')]/following-sibling::td") private WebElement countryValue;
    
    
    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public String getConfirmationText() {
        return wait
          .until(ExpectedConditions.visibilityOf(messageBarConfirmation))
          .getText()
          .trim();
    }
    
    public String getFirstName() {
        return wait.until(ExpectedConditions.visibilityOf(firstNameValue)).getText().trim();
    }
    
    public String getLastName() {
        return wait.until(ExpectedConditions.visibilityOf(lastNameValue)).getText().trim();
    }
    
    public String getAddress1() {
        return wait.until(ExpectedConditions.visibilityOf(address1Value)).getText().trim();
    }

    public String getAddress2() {
        return wait.until(ExpectedConditions.visibilityOf(address2Value)).getText().trim();
    }

    public String getCity() {
        return wait.until(ExpectedConditions.visibilityOf(cityValue)).getText().trim();
    }

    public String getState() {
        return wait.until(ExpectedConditions.visibilityOf(stateValue)).getText().trim();
    }

    public String getZip() {
        return wait.until(ExpectedConditions.visibilityOf(zipValue)).getText().trim();
    }

    public String getCountry() {
        return wait.until(ExpectedConditions.visibilityOf(countryValue)).getText().trim();
    }

    public String getOrderDate() {
        return wait.until(ExpectedConditions.visibilityOf(orderDateValue)).getText().trim();
    }

    public void clickConfirm() {
    	logger.debug("Clicking confirm button");
        confirmButton.click();
    }

    public void clickBack() {
    	logger.debug("Clicking back button");
        backButton.click();
    }
}
