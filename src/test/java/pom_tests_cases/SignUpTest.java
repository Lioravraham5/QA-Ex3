package pom_tests_cases;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.*;
import org.apache.logging.log4j.*;
import utils.Log;
import pages.SignUpPage;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;

public class SignUpTest extends BaseTest {

    private static final Logger logger = Log.getLogger(SignUpTest.class);
    private JSONArray testCases;
    private SignUpPage signUpPage;    

    @Before
    public void loadTestCases() throws Exception {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("signUp.json")) {
            testCases = (JSONArray) parser.parse(reader);
            logger.info("Loaded {} sign up scenarios", testCases.size());
        } catch (FileNotFoundException e) {
            logger.error("signUp.json not found", e);
            throw e;
        }
        signUpPage = new SignUpPage(driver);
    }

    @Test
    public void runSignUpScenarios() {
        logger.info("Starting sign up scenarios ({})", testCases.size());
        boolean hasFailures = false;

        for (int i = 0; i < testCases.size(); i++) {
            JSONObject scenario = (JSONObject) testCases.get(i);
            String name = (String) scenario.get("name");
            JSONObject data = (JSONObject) scenario.get("data");
            boolean expectSuccess = Boolean.parseBoolean(scenario.get("expectSuccess").toString());
            
            logger.info("Scenario #{}: {}", i + 1, name);
            
            try {
                driver.get("https://jpetstore.aspectran.com/");
                signUpPage.signUp(data);
//                Thread.sleep(2000);
                
                if (expectSuccess) {
                    assertTrue(
                        String.format("Sign up should succeed for scenario '%s'", name),
                        signUpPage.successfulSignup()
                    );
                    
                    Optional<String> errorMessage = signUpPage.findErrorMessage();
                    assertTrue(
                        String.format("No error message should appear for successful scenario '%s'", name),
                        errorMessage.isEmpty()
                    );
                    
                } else {
                    assertFalse(
                        String.format("Sign up should fail for scenario '%s'", name),
                        signUpPage.successfulSignup()
                    );
                    
                    Optional<String> errorMessage = signUpPage.findErrorMessage();
                    if (errorMessage.isPresent()) {
                        logger.debug("Error message: {}", errorMessage.get());
                    }
                }
                
                logger.info("Scenario #{} passed", i + 1);
                
            } catch (AssertionError | Exception ex) {
                hasFailures = true;
                logger.error("Scenario #{} failed: {}", i + 1, name);
            }
        }
        
        if (hasFailures) {
            fail("One or more sign up scenarios failed; see logs for details.");
        }
        
        logger.info("All sign up scenarios completed successfully");
    }
}