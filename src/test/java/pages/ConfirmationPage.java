package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.Log;

public class ConfirmationPage extends BasePage {
	
	private static final Logger logger = Log.getLogger(ConfirmationPage.class);
	
	@FindBy(xpath="//*[@id=\"MessageBar\"]/p") private WebElement messageBarConfirmation;
	
	// Payment Details:
	@FindBy(xpath = "//td[contains(text(), 'Card Type')]/following-sibling::td") private WebElement cardTypeValue;	
	@FindBy(xpath = "//td[contains(text(), 'Card Number')]/following-sibling::td") private WebElement cardNumberValue;
	@FindBy(xpath = "//td[contains(text(), 'Expiry Date')]/following-sibling::td") private WebElement expiryDateValue;
	
	// Billing Address:
    @FindBy(xpath = "//td[contains(text(), 'First name')]/following-sibling::td") private WebElement firstNameValue;
    @FindBy(xpath = "//td[contains(text(), 'Last name')]/following-sibling::td") private WebElement lastNameValue;
    @FindBy(xpath = "//td[contains(text(), 'Address 1')]/following-sibling::td") private WebElement address1Value;
    @FindBy(xpath = "//td[contains(text(), 'Address 2')]/following-sibling::td") private WebElement address2Value;
    @FindBy(xpath = "//td[contains(text(), 'City')]/following-sibling::td") private WebElement cityValue;
    @FindBy(xpath = "//td[contains(text(), 'State')]/following-sibling::td") private WebElement stateValue;
    @FindBy(xpath = "//td[contains(text(), 'Zip')]/following-sibling::td") private WebElement zipValue;
    @FindBy(xpath = "//td[contains(text(), 'Country')]/following-sibling::td") private WebElement countryValue;
	
    // Order
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/table[5]/tbody/tr[2]/td[1]/a") private WebElement itemIDValue;
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/table[5]/tbody/tr[2]/td[2]") private WebElement itemDescriptionValue;
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/table[5]/tbody/tr[2]/td[3]") private WebElement itemQuantityValue;
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/table[5]/tbody/tr[2]/td[4]") private WebElement itemPriceValue;
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/table[5]/tbody/tr[2]/td[5]") private WebElement itemTotalCostValue;
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/table[5]/tbody/tr[3]/th[3]") private WebElement orderTotalCostValue;

	
	public ConfirmationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public String getConfirmationText() {
        return wait
          .until(ExpectedConditions.visibilityOf(messageBarConfirmation))
          .getText()
          .trim();
    }
	
	public String getCardType() {
        return wait.until(ExpectedConditions.visibilityOf(cardTypeValue)).getText().trim();
    }
	
	public String getCardNumber() {
        return wait.until(ExpectedConditions.visibilityOf(cardNumberValue)).getText().trim();
    }
	
	public String getExpiryDate() {
        return wait.until(ExpectedConditions.visibilityOf(expiryDateValue)).getText().trim();
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
    
    public String getItemID() {
        return wait.until(ExpectedConditions.visibilityOf(itemIDValue)).getText().trim();
    }
    
    public String getItemDescription() {
        return wait.until(ExpectedConditions.visibilityOf(itemDescriptionValue)).getText().trim();
    }
    
    public String getItemQuantity() {
        return wait.until(ExpectedConditions.visibilityOf(itemQuantityValue)).getText().trim();
    }
    
    public String getItemPrice() {
        return wait.until(ExpectedConditions.visibilityOf(itemPriceValue)).getText().trim();
    } 
    
    public String getItemTotalCost() {
        return wait.until(ExpectedConditions.visibilityOf(itemTotalCostValue)).getText().trim();
    }
    
    public String getOrderTotalCost() {
        return wait.until(ExpectedConditions.visibilityOf(orderTotalCostValue)).getText().trim();
    }

   
	

	
}
