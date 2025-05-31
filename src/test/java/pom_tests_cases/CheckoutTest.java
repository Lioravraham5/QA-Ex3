package pom_tests_cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

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
	private final String testCardType = "MasterCard";
	private final String testCardNumber = "4111111111111111";
	private final String testExpiry = "12/2025";

	private final String testItemID = "EST-1";
	private final String testItemDescription = "Large Angelfish";
	private final String testItemQuantity = "1";
	private final String testItemPrice = "$16.50";
	private final String testItemTotalCost = "$16.50";
	private final String testOrderTotalCost = "$16.50";

	@Before
	public void initializeCheckoutScenario() throws Exception{
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
		paymentPage.selectCardType(testCardType);
		paymentPage.enterCardNumber(testCardNumber).enterExpiryDate(testExpiry)
				.enterBillingName(testFirstName, testLastName)
				.enterBillingAddress(testAddress1, testAddress2, testCity, testState, testZip, testCountry)
				.shipToDifferentAddress(false).clickContinue();
		Thread.sleep(2000);

		checkoutPage = new CheckoutPage(driver);
		confirmationPage = new ConfirmationPage(driver);
		logger.info("Checkout Test setup completed successfully.");

	}

	@Test
	public void testCheckoutValuesAndConfirmation() {
		logger.info("Starting checkout values and confirmation test...");
		verifyBillingAddressFields();

		checkoutPage.clickConfirm();
		checkConfirmButton();
		checkConfirmationMessage();
		verifyPaymentAndBillingDetails();
		verifyOrderDetails();
	}
	
	@Test
	public void testBackButtonReturnsToPaymentPage() {
		try {
			logger.info("Starting test: Checkout Back Button navigation...");
	
			// Click the Back button on the checkout page and test url
			checkoutPage.clickBack();
			String currentUrlAfter = driver.getCurrentUrl();
			
			logger.debug("Current URL after clicking Back: {}", currentUrlAfter);
			assertEquals("Expected to return to the payment page URL after clicking back.",
					"https://jpetstore.aspectran.com/order/newOrderForm", currentUrlAfter);
			logger.info("Back button successfully returned to payment page.");
		}
			
		catch (AssertionError | Exception e) {
			logger.error("Field mismatch: {}", e.getMessage());
		}
	
	}
	
	private void checkConfirmButton() {
		try {
			String currentUrlAfter = driver.getCurrentUrl();
			Thread.sleep(8000);
			logger.info(currentUrlAfter);
			assertTrue("Expected to return to the confirmation page URL after clicking confirm.",
					currentUrlAfter.contains("/order/viewOrder"));
			logger.info("Confirm button successfully navigated to confirmation page.");
			}
		catch (AssertionError | Exception e) {
			logger.error("Failed Test: {}", e.getMessage());
		}
	}
	
	private void verifyOrderDetails() {
	    logger.debug("Verifying product-details values in Confirmation Page…");

	    SoftAssertions softly = new SoftAssertions();

	    logger.debug("Checking: ItemID          = '{}'", testItemID);
	    softly.assertThat(confirmationPage.getItemID())
	          .isEqualTo(testItemID);

	    logger.debug("Checking: ItemDescription = '{}'", testItemDescription);
	    softly.assertThat(confirmationPage.getItemDescription())
	          .isEqualTo(testItemDescription);

	    logger.debug("Checking: ItemQuantity    = '{}'", testItemQuantity);
	    softly.assertThat(confirmationPage.getItemQuantity())
	          .isEqualTo(testItemQuantity);

	    logger.debug("Checking: ItemPrice       = '{}'", testItemPrice);
	    softly.assertThat(confirmationPage.getItemPrice())
	          .isEqualTo(testItemPrice);

	    logger.debug("Checking: ItemTotalCost   = '{}'", testItemTotalCost);
	    softly.assertThat(confirmationPage.getItemTotalCost())
	          .isEqualTo(testItemTotalCost);

	    logger.debug("Checking: OrderTotalCost  = '{}'", testOrderTotalCost);
	    softly.assertThat(confirmationPage.getOrderTotalCost())
	          .isEqualTo(testOrderTotalCost);

	    try {
	        softly.assertAll();                     
	        logger.info("All order details match in confirmation page");
	    } catch (AssertionError ae) {
	        logger.error("Order-details verification in confirmation page failed:\n{}", ae.getMessage());
	    }
	}

	private void verifyPaymentAndBillingDetails() {
	    logger.debug("Verifying payment & billing values in Confirmation Page…");
	    SoftAssertions softly = new SoftAssertions();

	    logger.debug("Checking: CardType       = '{}'", testCardType);
	    softly.assertThat(confirmationPage.getCardType())
	          .isEqualTo(testCardType);

	    logger.debug("Checking: CardNumber     = '{}'", testCardNumber);
	    softly.assertThat(confirmationPage.getCardNumber())
	          .isEqualTo(testCardNumber);

	    logger.debug("Checking: ExpiryDate     = '{}'", testExpiry);
	    softly.assertThat(confirmationPage.getExpiryDate())
	          .isEqualTo(testExpiry);

	    logger.debug("Checking: FirstName      = '{}'", testFirstName);
	    softly.assertThat(confirmationPage.getFirstName())
	          .isEqualTo(testFirstName);

	    logger.debug("Checking: LastName       = '{}'", testLastName);
	    softly.assertThat(confirmationPage.getLastName())
	          .isEqualTo(testLastName);

	    logger.debug("Checking: Address1       = '{}'", testAddress1);
	    softly.assertThat(confirmationPage.getAddress1())
	          .isEqualTo(testAddress1);

	    logger.debug("Checking: Address2       = '{}'", testAddress2);
	    softly.assertThat(confirmationPage.getAddress2())
	          .isEqualTo(testAddress2);

	    logger.debug("Checking: City           = '{}'", testCity);
	    softly.assertThat(confirmationPage.getCity())
	          .isEqualTo(testCity);

	    logger.debug("Checking: State          = '{}'", testState);
	    softly.assertThat(confirmationPage.getState())
	          .isEqualTo(testState);

	    logger.debug("Checking: Zip            = '{}'", testZip);
	    softly.assertThat(confirmationPage.getZip())
	          .isEqualTo(testZip);

	    logger.debug("Checking: Country        = '{}'", testCountry);
	    softly.assertThat(confirmationPage.getCountry())
	          .isEqualTo(testCountry);

	    try {
	        softly.assertAll();                     
	        logger.info("All payment & billing fields match inputs in confirmation page");
	    } catch (AssertionError ae) {
	        logger.error("payment & billing details verification in confirmation page failed:\n{}", ae.getMessage());
	    }
	}

	private void checkConfirmationMessage() {
		// Check confirmation message
		String confirmationText = confirmationPage.getConfirmationText();
		logger.debug("Confirmation message received: {}", confirmationText);
		assertEquals("Thank you, your order has been submitted.", confirmationText);
	}

	private void verifyBillingAddressFields(){
		// Verify that checkout page fields match what was entered in payment
		logger.debug("Verifying values in Checkout Page...");
	    SoftAssertions softly = new SoftAssertions();

	    logger.debug("Checking: FirstName  = '{}'", testFirstName);
	    softly.assertThat(checkoutPage.getFirstName())
	          .isEqualTo(testFirstName);

	    logger.debug("Checking: LastName   = '{}'", testLastName);
	    softly.assertThat(checkoutPage.getLastName())
	          .isEqualTo(testLastName);

	    logger.debug("Checking: Address1   = '{}'", testAddress1);
	    softly.assertThat(checkoutPage.getAddress1())
	          .isEqualTo(testAddress1);

	    logger.debug("Checking: Address2   = '{}'", testAddress2);
	    softly.assertThat(checkoutPage.getAddress2())
	          .isEqualTo(testAddress2);

	    logger.debug("Checking: City       = '{}'", testCity);
	    softly.assertThat(checkoutPage.getCity())
	          .isEqualTo(testCity);

	    logger.debug("Checking: State      = '{}'", testState);
	    softly.assertThat(checkoutPage.getState())
	          .isEqualTo(testState);

	    logger.debug("Checking: Zip        = '{}'", testZip);
	    softly.assertThat(checkoutPage.getZip())
	          .isEqualTo(testZip);

	    logger.debug("Checking: Country    = '{}'", testCountry);
	    softly.assertThat(checkoutPage.getCountry())
	          .isEqualTo(testCountry);
	    
	    try {
	        softly.assertAll();                     
	        logger.info("All checkout fields match payment inputs in checkout page");
	    } catch (AssertionError ae) {
	        logger.error("Order-details verification in checkout failed:\n{}", ae.getMessage());
	    }
	}
}
