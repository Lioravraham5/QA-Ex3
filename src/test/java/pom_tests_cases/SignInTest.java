package pom_tests_cases;

import pages.SignInPage;
import utils.DriverFactory;
import utils.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import org.apache.logging.log4j.*;

public class SignInTest {

    private WebDriver driver;
    private SignInPage signInPage;
    private JSONArray testCases;
    
    private static final Logger logger = Log.getLogger(SignInTest.class);

    @Before
    public void setUp() throws Exception {
        driver = DriverFactory.setUp();
        driver.manage().window().maximize();
        signInPage = new SignInPage(driver);

        // Load test cases from JSON
        try {
            JSONParser jsonParser = new JSONParser();
            FileReader reader;
            reader = new FileReader("signIn.json");
            // Read JSON file
            testCases = (JSONArray) jsonParser.parse(reader);
            
            logger.info("Loaded test cases from signIn.json, count: " + testCases.size());

        } catch (FileNotFoundException e) {
            logger.error("Failed to load test cases", e);
            throw e;
        }
    }

    @Test
    public void runSignInScenarios() {
        logger.info("Starting SignIn test execution");
        
        for (int i = 0; i < testCases.size(); i++) {
            JSONObject testCase = (JSONObject) testCases.get(i);
            String name = (String) testCase.get("name");
            JSONObject data = (JSONObject) testCase.get("data");

            logger.info("Running test case #" + (i + 1) + ": " + name);
            
            try {
                // Always start fresh from home page before each test
                logger.debug("Navigating to JPetStore home page");
                driver.get("https://jpetstore.aspectran.com/");
                Thread.sleep(1000);
                
                logger.debug("Starting sign in process");
                signInPage.signIn(data);
                
                logger.debug("Waiting for sign in result");
                Thread.sleep(2000);
                
                logger.info("Test case #" + (i + 1) + " completed successfully");
                
            } catch (Exception e) {
                logger.error("Test case #" + (i + 1) + " failed: " + name, e);
            }
        }
        
        logger.info("SignIn test execution completed");
    }

    @After
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }
}