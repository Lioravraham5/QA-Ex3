package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

//	private WebDriver driver;
//	private Map<String, Object> vars;
//	JavascriptExecutor js;
	
	public DriverFactory() {
		
	}
	
	@Before
	public static WebDriver setUp() throws IOException {
//		js = (JavascriptExecutor) driver;
//		vars = new HashMap<String, Object>();
		return new ChromeDriver();
	}
}
