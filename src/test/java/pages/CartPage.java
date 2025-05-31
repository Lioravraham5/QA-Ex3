package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Log;

public class CartPage extends BasePage {
    private static final Logger logger = Log.getLogger(CartPage.class);

    @FindBy(linkText = "Proceed to Checkout")   private WebElement ProceedToCheckoutButton;    

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isProductInCart(String productId) {
        logger.info("Checking if Product with id {} in cart", productId);
        String xpath = String.format("//td/a[contains(@href,'%s')]", productId);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return true;
        } catch (TimeoutException | NoSuchElementException e) {
            logger.debug("Product with id {} not found in cart", productId);
            return false;
        }
    }
    
    public void clickProceedToCheckout() {
        logger.debug("Clicking proceed to checkout button");
        wait.until(ExpectedConditions.elementToBeClickable(ProceedToCheckoutButton)).click(); 
    }
}
