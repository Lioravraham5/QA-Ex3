package pom_tests_cases;

import org.junit.Before;
import org.junit.Test;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.logging.log4j.Logger;
import utils.Log;
import pages.CatalogPage;
import pages.CheckoutPage;
import pages.PaymentPage;
import pages.ProductPage;
import pages.SignInPage;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Optional;

import static org.junit.Assert.*;

public class PaymentTest extends BaseTest {
    private static final Logger logger = Log.getLogger(PaymentTest.class);

    private JSONArray testCases;
    private PaymentPage paymentPage;
    private SignInPage signInPage;
    private CatalogPage catalogPage;
    private ProductPage productPage;
    private CheckoutPage checkoutPage;

    
    @Before
    public void loadTestCases() throws Exception {
        // 1) Sign in
        signInPage = new SignInPage(driver);
        signInPage.loginAs("j2ee", "j2ee");

        // 2) Add a product to cart (Fish -> FI-SW-01)
        catalogPage = new CatalogPage(driver);
        productPage = new ProductPage(driver);
        checkoutPage = new CheckoutPage(driver); 
        paymentPage = new PaymentPage(driver);

        catalogPage.openCatalog("Fish");
        catalogPage.openProduct("FI-SW-01");
        

        // Load JSON scenarios
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("checkout.json")) {
            testCases = (JSONArray) parser.parse(reader);
            logger.debug("Loaded {} payment/billing scenarios", testCases.size());
        } catch (FileNotFoundException e) {
            logger.error("checkout.json not found", e);
            throw e;
        }
    }
    
    @Test 
    public void checkEmptyCartScenario() {
    	logger.debug("Check if with an empty cart user can continue to Payment page");
    	try {
        	assertTrue("Proceed to checkout button should not be presented",
        			productPage.checkIfInvisibileProceedToCheckout());
        	logger.info("Proceed to checkout button is not visible to user with empty cart");
    	}
		catch (AssertionError | Exception e) {
			logger.error("Failed Test: {}", e.getMessage());
		}
    }
    
    @Test 
    public void checkNotSignedInScenario() {
    	logger.debug("Check if non signed in user is redirected to sign in after clicking Proceed to Checkout button");
    	
    	try {
        	signInPage.clickSignOut();
    		driver.get("https://jpetstore.aspectran.com/");
    		Thread.sleep(2000);
            catalogPage.openCatalog("Fish");
            catalogPage.openProduct("FI-SW-01");
            productPage.addProductToCart("FI-SW-01");
            productPage.clickProceedToCheckout();

			String urlAfterClick = driver.getCurrentUrl();
			Thread.sleep(8000);
			logger.debug(urlAfterClick);
        	assertTrue("Should navigate to sign in page",
        			urlAfterClick.contains("account/signonForm"));
        	logger.info("Non signed in user is navigated to sign in page after clicking Proceed to checkout button");
    	}
		catch (AssertionError | Exception e) {
			logger.error("Failed Test: {}", e.getMessage());
		}
    }
    
    

    @Test
    public void runPaymentScenarios() {
        logger.debug("Starting payment/billing scenarios ({})", testCases.size());
        productPage.addProductToCart("FI-SW-01");
        productPage.clickProceedToCheckout();
        boolean hasFailures = false;

        for (int i = 0; i < testCases.size(); i++) {
            JSONObject scenario = (JSONObject) testCases.get(i);
            String name = (String) scenario.get("name");
            JSONObject data = (JSONObject) scenario.get("data");
            JSONObject expected = (JSONObject) scenario.get("expected");
            boolean proceed = (Boolean) expected.get("proceed");

            logger.debug("Scenario #{}: {}", i+1, name);
            driver.get("https://jpetstore.aspectran.com/order/newOrderForm");
            

            try {
            	
                // 1) Card Type (dropdown)                
                paymentPage.selectCardType(data.get("cardType").toString());                

                // 2) Card Number
                paymentPage.enterCardNumber(data.get("cardNumber").toString());

                // 3) Expiry Date
                paymentPage.enterExpiryDate(data.get("expiryDate").toString());

                // 4) Billing Name
                paymentPage.enterBillingName(
                    data.get("firstName").toString(),
                    data.get("lastName").toString()
                );

                // 5) Billing Address
                paymentPage.enterBillingAddress(
                    data.get("address1").toString(),
                    data.get("address2").toString(),
                    data.get("city").toString(),
                    data.get("state").toString(),
                    data.get("zip").toString(),
                    data.get("country").toString()
                );

                // 6) Ship to different address
                boolean shipDiff = (Boolean) data.get("shipToDifferent");
                paymentPage.shipToDifferentAddress(shipDiff);

                // 7) Submit
                if(shipDiff) {
        			String currentUrlAfter = driver.getCurrentUrl();			
        			assertEquals("Expected to navigate to the Shipping Address update page after selecting 'Ship to different address'.",
        					"https://jpetstore.aspectran.com/order/newOrder", currentUrlAfter);
        			logger.info("'Ship to different address' selection successfully navigated to Shipping Address update page.");
                }
                paymentPage.clickContinue();

                // 8) Verify outcome
                if (proceed) {                	
                	assertEquals(
                			  "Please confirm the information below and then press continue...",
                			  checkoutPage.getConfirmationText()
                			);  
                } else {
                    // collect visible error texts
                    Optional<String> errorMessage = paymentPage.findErrorMessage();
                    assertFalse(
                        String.format("Expected error in scenario '%s'", name),
                        errorMessage.isEmpty()
                        );
                }

                logger.info("Scenario #{} passed", i+1);
            } catch (AssertionError | Exception ex) {
                hasFailures = true;
                logger.error("Scenario #{} failed", i+1, name);
            }
        }

        if (hasFailures) {
            fail("One or more payment/billing scenarios failed; see logs for details.");
        }

        logger.info("All payment/billing scenarios completed");
    }
}

