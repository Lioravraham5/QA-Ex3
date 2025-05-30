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

	private final String testItemID = "EST-1";
	private final String testItemDescription = "Large Angelfish";
	private final String testItemQuantity = "1";
	private final String testItemPrice = "$16.50";
	private final String testItemTotalCost = "$16.50";
	private final String testOrderTotalCost = "$16.50";

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

		verifyBillingAddressFields(checkoutPage);

		// Click Confirm button
		checkoutPage.clickConfirm();

		confirmationPage = new ConfirmationPage(driver);

		checkConfirmationMessage(confirmationPage);

		verifyPaymentAndBillingDetails(confirmationPage);

		verifyOrderDetails(confirmationPage);

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
		assertEquals("Expected to return to the payment page URL after clicking back.",
				"https://jpetstore.aspectran.com/order/newOrderForm", currentUrlAfter);

		logger.info("Back button successfully returned to payment page.");
	}

	private void verifyOrderDetails(ConfirmationPage confirmationPage2) {
		// Verify that order details in confirmation page
		logger.debug("Verifying order details values in Confirmation Page...");
		try {

			logger.debug("Checking: ItemID = '{}'", confirmationPage.getItemID());
			assertEquals(testItemID, confirmationPage.getItemID());

			logger.debug("Checking: ItemDescription = '{}'", confirmationPage.getItemDescription());
			assertEquals(testItemDescription, confirmationPage.getItemDescription());

			logger.debug("Checking: ItemQuantity = '{}'", confirmationPage.getItemQuantity());
			assertEquals(testItemQuantity, confirmationPage.getItemQuantity());

			logger.debug("Checking: ItemPrice = '{}'", confirmationPage.getItemPrice());
			assertEquals(testItemPrice, confirmationPage.getItemPrice());

			logger.debug("Checking: ItemTotalCost: = '{}'", confirmationPage.getItemTotalCost());
			assertEquals(testItemTotalCost, confirmationPage.getItemTotalCost());

			logger.debug("Checking: OrderTotalCost: = '{}'", confirmationPage.getOrderTotalCost());
			assertEquals(testOrderTotalCost, confirmationPage.getOrderTotalCost());

			logger.info("All order details match in confirmation page");

		} catch (AssertionError e) {
			logger.error("Field mismatch: {}", e.getMessage());
			throw e;
		}

	}

	private void verifyPaymentAndBillingDetails(ConfirmationPage confirmationPage2) {
		// Verify that confirmation page fields match what was entered in payment
		logger.debug("Verifying values in Confirmation Page...");
		try {

			logger.debug("Checking: CardType = '{}'", confirmationPage.getCardType());
			assertEquals(testCardType, confirmationPage.getCardType());

			logger.debug("Checking: CardNumber = '{}'", confirmationPage.getCardNumber());
			assertEquals(testCardNumber, confirmationPage.getCardNumber());

			logger.debug("Checking: ExpiryDate = '{}'", confirmationPage.getExpiryDate());
			assertEquals(testExpiry, confirmationPage.getExpiryDate());

			logger.debug("Checking: FirstName = '{}'", confirmationPage.getFirstName());
			assertEquals(testFirstName, confirmationPage.getFirstName());

			logger.debug("Checking: LastName: = '{}'", confirmationPage.getLastName());
			assertEquals(testLastName, confirmationPage.getLastName());

			logger.debug("Checking: Addres1: = '{}'", confirmationPage.getAddress1());
			assertEquals(testAddress1, confirmationPage.getAddress1());

			logger.debug("Checking: Addres2: = '{}'", confirmationPage.getAddress2());
			assertEquals(testAddress2, confirmationPage.getAddress2());

			logger.debug("Checking: LastName: = '{}'", confirmationPage.getLastName());
			assertEquals(testCity, confirmationPage.getCity());

			logger.debug("Checking: State: = '{}'", confirmationPage.getState());
			assertEquals(testState, confirmationPage.getState());

			logger.debug("Checking: Zip: = '{}'", confirmationPage.getZip());
			assertEquals(testZip, confirmationPage.getZip());

			logger.debug("Checking: Country: = '{}'", confirmationPage.getCountry());
			assertEquals(testCountry, confirmationPage.getCountry());

			logger.info("All checkout fields match payment inputs in confirmation page");

		} catch (AssertionError e) {
			logger.error("Field mismatch: {}", e.getMessage());
			throw e;
		}

	}

	private void checkConfirmationMessage(ConfirmationPage confirmationPage2) {
		// Check confirmation message
		String confirmationText = confirmationPage.getConfirmationText();
		logger.debug("Confirmation message received: {}", confirmationText);
		assertEquals("Thank you, your order has been submitted.", confirmationText);

		logger.info("Confirmation page message: {}", confirmationText);

	}

	private void verifyBillingAddressFields(CheckoutPage checkoutPage2) {
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

			logger.info("All checkout fields match payment inputs in checkout page");

		} catch (AssertionError e) {
			logger.error("Field mismatch: {}", e.getMessage());
			throw e;
		}
	}

}
