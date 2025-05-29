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
	
	

	
}
