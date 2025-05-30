package pom_tests_cases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.Test;

import pages.CartPage;
import pages.CatalogPage;
import pages.ProductPage;
import pages.SignInPage;
import utils.Log;

public class AddToCartTest extends BaseTest {

    private static final Logger logger = Log.getLogger(AddToCartTest.class);
    private JSONArray testCases;
    private SignInPage signInPage;
    private CatalogPage catalogPage;
    private ProductPage productPage;
    private CartPage cartPage;

    @Before
    public void loadTestDataAndSignIn() throws Exception {
        // 1) instantiate pages
        signInPage = new SignInPage(driver);
        catalogPage = new CatalogPage(driver);
        productPage = new ProductPage(driver);
        cartPage    = new CartPage(driver);

        // 2) sign in once
        signInPage.loginAs("j2ee", "j2ee");

        // 3) load category→productId pairs
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("addToCart.json")) {
            testCases = (JSONArray) parser.parse(reader);
            logger.info("Loaded {} entries from productCategories.json", testCases.size());
        } catch (FileNotFoundException e) {
            logger.error("Could not find productCategories.json", e);
            throw e;
        }
    }
    
    @Test
    public void runAddToCartScenarios() {
        logger.info("Starting Add-To-Cart scenarios ({} records)", testCases.size());

        for (int i = 0; i < testCases.size(); i++) {
            JSONObject entry   = (JSONObject) testCases.get(i);
            String category  = entry.get("category").toString();
            String productId = entry.get("productId").toString();

            logger.info("Scenario #{}: category='{}', productId='{}'", i+1, category, productId);

            try {
                // navigate to category and product
                driver.get("https://jpetstore.aspectran.com/");
                catalogPage.openCatalog(category);
                catalogPage.openProduct(productId);

                // grab cart count before adding
                int before = productPage.getCartCount();
                logger.debug("Cart count before add: {}", before);

                // add product to cart
                productPage.addProductToCart(productId);

                // grab cart count after
                int after = productPage.getCartCount();
                logger.debug("Cart count after add: {}", after);

                // verify increment
                assertEquals(
                  String.format("Cart count should go up by 1 for product %s", productId),
                  before + 1, after
                );

                // verify item is in cart
                assertTrue(
                  String.format("Product %s should appear in the cart", productId),
                  cartPage.isProductInCart(productId)
                );

                logger.info("Scenario #{} passed", i+1);
            } catch (Exception | AssertionError e) {
                logger.error("Scenario #{} failed: category='{}', productId='{}'", i+1, category, productId, e);
            }
        }

        logger.info("All Add-To-Cart scenarios completed");
    }
}

