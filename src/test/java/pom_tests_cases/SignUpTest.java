package pom_tests_cases;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.*;
import org.apache.logging.log4j.*;
import utils.Log;
import pages.SignUpPage;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class SignUpTest extends BaseTest {

    private static final Logger logger = Log.getLogger(SignUpTest.class);
    private JSONArray testCases;
    private SignUpPage signUpPage;    


    @Before
    public void loadTestCases() throws Exception {
        JSONParser parser = new JSONParser();
        try {
        	FileReader reader = new FileReader("signUp.json");
            testCases = (JSONArray) parser.parse(reader);
            logger.info("Loaded {} test cases", testCases.size());
        }
        catch (FileNotFoundException e) {
			logger.error("Failed to load test cases", e);
			throw e;
		}
        signUpPage = new SignUpPage(driver);
    }

    @Test
    public void runSignUpScenarios() {
    	logger.info("Starting SignUp scenarios");

        for (int i = 0; i < testCases.size(); i++) {
            JSONObject tc   = (JSONObject) testCases.get(i);
            String     name = (String) tc.get("name");
            JSONObject data = (JSONObject) tc.get("data");
            
			logger.info("Running test case #" + (i + 1) + ": " + name);
			
			try {
				logger.debug("Navigating to JPetStore home page");
				driver.get("https://jpetstore.aspectran.com/");
				
				logger.debug("Starting sign up process");
				signUpPage.signUp(data);
				
				logger.debug("Waiting for sign up result");
				Thread.sleep(2000);
								
				logger.info("Test case #" + (i + 1) + " completed successfully");
				
			} catch (Exception e) {
				logger.error("Test case #" + (i + 1) + " failed: " + name, e);
			}
        }
    }
}


