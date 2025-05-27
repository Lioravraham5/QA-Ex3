package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
	public DriverFactory() {
		
	}
	
	@Before
	public static WebDriver getDriver() throws IOException {
	    Map<String,Object> prefs = new HashMap<>();
	    prefs.put("credentials_enable_service", false);
	    prefs.put("profile.password_manager_enabled", false);
        prefs.put("safebrowsing.enabled", false);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-features=SafeBrowsing,PasswordLeakToggleMove");
	    options.addArguments("--start-maximized");

	    return new ChromeDriver(options);
	}
}