package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Log;

public class ProductPage extends BasePage {
	private static final Logger logger = Log.getLogger(ProductPage.class);
	
    @FindBy(linkText="Proceed to Checkout") private WebElement proceedToCheckoutButton;
	@FindBy(css = "div.MenuContent a[href='/cart/viewCart']") private WebElement cartLink;

	public ProductPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	public int getCartCount() {
        logger.debug("Checking cart count");
		String text = wait.until(ExpectedConditions.visibilityOf(cartLink)).getText().trim();
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			throw new IllegalStateException("Cart count was not a number: '" + text + "'", e);
		}
	}

	public void addProductToCart(String productId) {
		logger.debug("Adding product to cart");
		By locator = By.xpath(String.format(
				"//td[normalize-space()='%s']/following-sibling::td//a[normalize-space()='Add to Cart']", productId));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
	public void clickProceedToCheckout() {
		logger.debug("Proceed to Checkout");
		wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton)).click();
		
	}
	
	public boolean checkIfInvisibileProceedToCheckout() {
		logger.debug("Verify Proceed to Checkout button is not visible");
		return wait.until(ExpectedConditions.invisibilityOf(proceedToCheckoutButton));		
	}

}
