package pom_tests_cases;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import pages.SignUpPage;
import utils.DriverFactory;

public class SignUpTest {

	private WebDriver driver;
    private SignUpPage signUpPage;
    
    @Before
    public void setUp() {
//        driver = DriverFactory.setUp();
        driver.manage().window().maximize();
        driver.get("https://jpetstore.aspectran.com/");
        signUpPage = new SignUpPage(driver);
    }

    @Test
    public void testSignUpWithValidData() {
        signUpPage.signUp("standard_user", "standard_user");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
