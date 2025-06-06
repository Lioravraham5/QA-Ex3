package pages;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


import utils.Log;

public class PaymentPage extends BasePage {
    private static final Logger logger = Log.getLogger(PaymentPage.class);

    // — Payment Details —
    @FindBy(name="cardType")                  		private WebElement cardTypeSelect;
    @FindBy(name="creditCard")                		private WebElement cardNumberInput;
    @FindBy(name="expiryDate")                		private WebElement expiryDateInput;

    // — Billing Address —
    @FindBy(name="billToFirstName")           private WebElement billFirstNameInput;
    @FindBy(name="billToLastName")            private WebElement billLastNameInput;
    @FindBy(name="billAddress1")            private WebElement billAddress1Input;
    @FindBy(name="billAddress2")            private WebElement billAddress2Input;
    @FindBy(name="billCity")                private WebElement billCityInput;
    @FindBy(name="billState")               private WebElement billStateInput;
    @FindBy(name="billZip")                 private WebElement billZipInput;
    @FindBy(name="billCountry")             private WebElement billCountryInput;

    // — Ship to different address? —
    @FindBy(name="shippingAddressRequired")    private WebElement shippingAddressRequired;

    // — Actions —
    @FindBy(xpath="//button[@type='submit' and normalize-space()='Continue']")   private WebElement continueButton;
    @FindBy(xpath="//button[@type='button' and normalize-space()='Cancel']")   private WebElement cancelButton;
    
    // - Error Message -
    @FindBy(css="span.error-msg") private List<WebElement> errorSpans;



    public PaymentPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public PaymentPage selectCardType(String type) {
    	logger.debug("Selecting card type: {}", type);
    	wait.until(dropdownToBePopulated(cardTypeSelect))
        .selectByVisibleText(type);
        return this;
    }
    
    public PaymentPage enterCardNumber(String number) {
    	logger.debug("Filling card number");
        cardNumberInput.clear();
        cardNumberInput.sendKeys(number);
        return this;
    }

    public PaymentPage enterExpiryDate(String mmYYYY) {
    	logger.debug("Filling expiry date");
        expiryDateInput.clear();
        expiryDateInput.sendKeys(mmYYYY);
        return this;
    }

    public PaymentPage enterBillingName(String first, String last) {
    	logger.debug("Filling billing first and last name");
        billFirstNameInput.clear();
        billFirstNameInput.sendKeys(first);
        billLastNameInput.clear();
        billLastNameInput.sendKeys(last);
        return this;
    }

    public PaymentPage enterBillingAddress(
        String addr1, String addr2, String city, String state, String zip, String country
    ) {
    	logger.debug("Filling billing addresses");
        billAddress1Input.clear();
        billAddress1Input.sendKeys(addr1);
        billAddress2Input.clear();
        billAddress2Input.sendKeys(addr2);
        billCityInput.clear();
        billCityInput.sendKeys(city);
        billStateInput.clear();
        billStateInput.sendKeys(state);
        billZipInput.clear();
        billZipInput.sendKeys(zip);
        billCountryInput.clear();
        billCountryInput.sendKeys(country);
        return this;
    }

    public PaymentPage shipToDifferentAddress(boolean yes) {
        if (shippingAddressRequired.isSelected() != yes) {
        	logger.debug("Selecting Ship to different address");
            wait.until(ExpectedConditions.elementToBeClickable(shippingAddressRequired)).click();
        }
        return this;
    }
    
    public void clickContinue() {
    	logger.debug("Clicking Continue button");
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    public void clickCancel() {
    	logger.debug("Clicking Cancel button");
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }
    

    public Optional<String> findErrorMessage() {
        return errorSpans.stream()
                         .map(WebElement::getText)
                         .map(String::trim)
                         .filter(s -> !s.isEmpty())
                         .findFirst();
    }
    
}
