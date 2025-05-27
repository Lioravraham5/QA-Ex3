package pom_tests_cases;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.*;
import org.apache.logging.log4j.Logger;
import utils.Log;
import pages.SignInPage;

import java.io.FileReader;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SignInTest extends BaseTest {

    private static final Logger logger = Log.getLogger(SignInTest.class);
    private JSONArray testCases;
    private SignInPage signInPage;

    @Before
    public void loadTestCases() throws Exception {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("signIn.json")) {
            testCases = (JSONArray) parser.parse(reader);
            logger.info("Loaded {} sign in scenarios", testCases.size());
        } catch (FileNotFoundException e) {
            logger.error("signIn.json not found", e);
            throw e;
        }
        signInPage = new SignInPage(driver);
    }

    @Test
    public void runSignInScenarios() {
        logger.info("Starting sign in scenarios ({})", testCases.size());
        boolean hasFailures = false;

        for (int i = 0; i < testCases.size(); i++) {
            JSONObject scenario = (JSONObject) testCases.get(i);
            String name = (String) scenario.get("name");
            JSONObject data = (JSONObject) scenario.get("data");
            boolean expectSuccess = Boolean.parseBoolean(scenario.get("expectSuccess").toString());
            String username = data.get("Username").toString();
            String password = data.get("Password").toString();

            logger.info("Scenario #{}: {}", i + 1, name);
            
            try {
                driver.get("https://jpetstore.aspectran.com/");
                signInPage.loginAs(username, password);

                if (expectSuccess) {
                    assertTrue(
                        String.format("Sign in should succeed for scenario '%s'", name),
                        signInPage.isUserLoggedIn(i, name)
                    );
                } else {
                    assertEquals(
                        "Invalid username or password. Signon failed.",
                        signInPage.checkFailedToLogIn()
                    );
                }

                logger.info("Scenario #{} passed", i + 1);
                
            } catch (AssertionError | Exception ex) {
                hasFailures = true;
                logger.error("Scenario #{} failed: {}", i + 1, name);
            }
        }

        if (hasFailures) {
            fail("One or more sign in scenarios failed; see logs for details.");
        }

        logger.info("All sign in scenarios completed successfully");
    }
}