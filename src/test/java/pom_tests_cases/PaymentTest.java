package pom_tests_cases;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Log;
import pages.CartPage;
import pages.CatalogPage;
import pages.CheckoutPage;
import pages.PaymentPage;
import pages.ProductPage;
import pages.SignInPage;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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
        catalogPage.openCatalog("Fish");
        catalogPage.openProduct("FI-SW-01");
        productPage.addProductToCart("FI-SW-01");
        

        // Load JSON scenarios
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("checkout.json")) {
            testCases = (JSONArray) parser.parse(reader);
            logger.info("Loaded {} payment/billing scenarios", testCases.size());
        } catch (FileNotFoundException e) {
            logger.error("paymentBillingScenarios.json not found", e);
            throw e;
        }
    }

    @Test
    public void runPaymentScenarios() {
        logger.info("Starting payment/billing scenarios ({})", testCases.size());
        boolean hasFailures = false;

        for (int i = 0; i < testCases.size(); i++) {
            JSONObject scenario = (JSONObject) testCases.get(i);
            String name = (String) scenario.get("name");
            JSONObject data = (JSONObject) scenario.get("data");
            JSONObject expected = (JSONObject) scenario.get("expected");
            boolean proceed = (Boolean) expected.get("proceed");
            JSONArray errors = (JSONArray) expected.get("errors");

            logger.info("Scenario #{}: {}", i+1, name);
            // 3) Navigate to payment page
            driver.get("https://jpetstore.aspectran.com/order/newOrderForm");
            paymentPage = new PaymentPage(driver);

            try {
            	
                // 1) Card Type (dropdown)
                if (data.containsKey("cardType")) {
                    String type = data.get("cardType").toString();
                    logger.debug("Selecting card type: {}", type);
                    WebElement dropdown = driver.findElement(By.name("cardType"));
                    new Select(dropdown).selectByVisibleText(type);
                }

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
                paymentPage.clickContinue();

                // 8) Verify outcome
                if (proceed) {                	
                	assertEquals(
                			  "Please confirm the information below and then press continue...",
                			  checkoutPage.getConfirmationText()
                			);  
                } else {
                    // collect visible error texts
                    List<String> actualErrors = driver.findElements(By.cssSelector(".error"))
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList());

                    for (Object e : errors) {
                        String errMsg = e.toString();
                        assertTrue(
                            String.format("Expected error '%s' in scenario '%s'", errMsg, name),
                            actualErrors.contains(errMsg)
                        );
                    }
                }

                logger.info("Scenario #{} passed", i+1);
            } catch (AssertionError | Exception ex) {
                hasFailures = true;
                logger.error("Scenario #{} ({}) failed", i+1, name, ex);
            }
        }

        if (hasFailures) {
            fail("One or more payment/billing scenarios failed; see logs for details.");
        }

        logger.info("All payment/billing scenarios completed");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

