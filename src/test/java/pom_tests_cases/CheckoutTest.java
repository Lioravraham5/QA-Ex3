package pom_tests_cases;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pages.CatalogPage;
import pages.CheckoutPage;
import pages.ConfirmationPage;
import pages.PaymentPage;
import pages.ProductPage;
import pages.SignInPage;
import utils.Log;

public class CheckoutTest extends BaseTest {

	private static final Logger logger = Log.getLogger(CheckoutTest.class);

	private SignInPage signInPage;
	private CatalogPage catalogPage;
	private ProductPage productPage;
	private PaymentPage paymentPage;
	private CheckoutPage checkoutPage;
	private ConfirmationPage confirmationPage;

	private final String testFirstName = "Alice";
	private final String testLastName = "Wonder";
	private final String testAddress1 = "123 Main";
	private final String testAddress2 = "123 second";
	private final String testCity = "New York";
	private final String testState = "NY";
	private final String testZip = "10001";
	private final String testCountry = "USA";
	private final String testCardType = "Visa";
	private final String testCardNumber = "4111111111111111";
	private final String testExpiry = "12/25";

	@Before
	public void initializeCheckoutScenario() {
		logger.info("Starting Checkout Test setup...");

		// 1) Sign in
		signInPage = new SignInPage(driver);
		signInPage.loginAs("j2ee", "j2ee");

		// 2) Add product to cart
		catalogPage = new CatalogPage(driver);
		productPage = new ProductPage(driver);
		catalogPage.openCatalog("Fish");
		catalogPage.openProduct("FI-SW-01");
		productPage.addProductToCart("FI-SW-01");

		// 3) Go to payment
		driver.get("https://jpetstore.aspectran.com/order/newOrderForm");
		paymentPage = new PaymentPage(driver);

		// 4) Fill payment form

		WebElement dropdown = driver.findElement(By.name("cardType"));
		new Select(dropdown).selectByVisibleText(testCardType);

		paymentPage.enterCardNumber(testCardNumber).enterExpiryDate(testExpiry)
				.enterBillingName(testFirstName, testLastName)
				.enterBillingAddress(testAddress1, testAddress2, testCity, testState, testZip, testCountry)
				.shipToDifferentAddress(false).clickContinue();

		logger.info("Checkout Test setup completed successfully.");

	}

	@Test
	public void testCheckoutValuesAndConfirmation() {
		logger.info("Starting checkout values and confirmation test...");

		checkoutPage = new CheckoutPage(driver);

		// Verify that checkout page fields match what was entered in payment
		logger.debug("Verifying values in Checkout Page...");
		try {
			logger.debug("Checking: FirstName = '{}'", checkoutPage.getFirstName());
			assertEquals(testFirstName, checkoutPage.getFirstName());

			logger.debug("Checking: LastName: = '{}'", checkoutPage.getLastName());
			assertEquals(testLastName, checkoutPage.getLastName());

			logger.debug("Checking: Addres1: = '{}'", checkoutPage.getAddress1());
			assertEquals(testAddress1, checkoutPage.getAddress1());

			logger.debug("Checking: Addres2: = '{}'", checkoutPage.getAddress2());
			assertEquals(testAddress2, checkoutPage.getAddress2());

			logger.debug("Checking: LastName: = '{}'", checkoutPage.getLastName());
			assertEquals(testCity, checkoutPage.getCity());

			logger.debug("Checking: State: = '{}'", checkoutPage.getState());
			assertEquals(testState, checkoutPage.getState());

			logger.debug("Checking: Zip: = '{}'", checkoutPage.getZip());
			assertEquals(testZip, checkoutPage.getZip());

			logger.debug("Checking: Country: = '{}'", checkoutPage.getCountry());
			assertEquals(testCountry, checkoutPage.getCountry());

			logger.info("All checkout fields match payment inputs");

		} catch (AssertionError e) {
			logger.error("Field mismatch: {}", e.getMessage());
			throw e;
		}

		// Click Confirm button
		checkoutPage.clickConfirm();

		// Check confirmation message
		confirmationPage = new ConfirmationPage(driver);
		String confirmationText = confirmationPage.getConfirmationText();
		logger.debug("Confirmation message received: {}", confirmationText);
		assertEquals("Thank you, your order has been submitted.", confirmationText);

		logger.info("Confirmation page message: {}", confirmationText);
	}

	@Test
	public void testBackButtonReturnsToPaymentPage() {
		logger.info("Starting test: Checkout Back Button navigation...");

		checkoutPage = new CheckoutPage(driver);

		// Click the Back button on the checkout page
		checkoutPage.clickBack();

		// Log current URL after clicking back
		String currentUrlAfter = driver.getCurrentUrl();
		logger.debug("Current URL after clicking Back: {}", currentUrlAfter);

		// Verify that clicking the Back button returns to the payment page URL
		assertEquals("Expected to return to the payment page URL after clicking back.", "https://jpetstore.aspectran.com/order/newOrderForm", currentUrlAfter);

		logger.info("Back button successfully returned to payment page.");
	}

}
