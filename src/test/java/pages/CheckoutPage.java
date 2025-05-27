package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.Log;

public class CheckoutPage extends BasePage {
    private static final Logger logger = Log.getLogger(SignInPage.class);
    @FindBy(xpath="//div[@id='MessageBar']/p")   private WebElement messageBarConfirmation;

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
    
}
