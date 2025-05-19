package pom_tests_cases;

import pages.SignUpPage;
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

public class SignUpTest {

	private WebDriver driver;
	private SignUpPage signUpPage;
	private JSONArray testCases;
	
	private static final Logger logger = Log.getLogger(SignUpTest.class);

	@Before
	public void setUp() throws Exception {
		driver = DriverFactory.setUp();
		driver.manage().window().maximize();
		signUpPage = new SignUpPage(driver);

		// Load test cases from JSON
		try {
			JSONParser jsonParser = new JSONParser();
			FileReader reader;
			reader = new FileReader("signUp.json");
			// Read JSON file
			testCases = (JSONArray) jsonParser.parse(reader);
			
			logger.info("Loaded test cases from signUp.json, count: " + testCases.size());

		} catch (FileNotFoundException e) {
			logger.error("Failed to load test cases", e);
			throw e;
		}
	}

	@Test
	public void runSignUpScenarios() {
		for (int i = 0; i < testCases.size(); i++) {
			JSONObject testCase = (JSONObject) testCases.get(i);
			String name = (String) testCase.get("name");
			JSONObject data = (JSONObject) testCase.get("data");

			System.out.println("🔍 Running test case #" + (i + 1) + ": " + name);
			driver.get("https://jpetstore.aspectran.com/");
			signUpPage.signUp(data);
		}
	}

	@After
	public void tearDown() {
		if (driver != null)
			driver.quit();
	}
}
