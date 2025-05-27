package pom_tests_cases;

import utils.DriverFactory;
import utils.Log;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import pages.SignUpPage;

public abstract class BaseTest {

    private static final Logger logger = Log.getLogger(SignUpPage.class);
    protected WebDriver driver;

    @Before
    public void setUp() throws IOException{
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        driver.get("https://jpetstore.aspectran.com/");
    }

	  @After
	  public void tearDown() {
          logger.debug("Quiting driver");
	      if (driver != null) driver.quit();
	  }
}

