package jpetstore;

import org.junit.Test;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.Before;
//import org.apache.poi.sl.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Row;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.geom.Arc2D.Double;
import java.io.File;

import java.io.FileInputStream;

import java.io.IOException;

//import org.apache.poi.hssf.usermodel.HSSFWorkbook;

//import org.apache.poi.ss.usermodel.Row;

//import org.apache.poi.ss.usermodel.Workbook;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.naming.spi.DirStateFactory.Result;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ExampleTest {

	private WebDriver driver;
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@After
	public void tearDown() {
		// driver.quit();
	}

	@Before
	public void setUp() throws IOException {
		// System.setProperty("webdriver.chrome.driver","C:\\Users\\acer\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();

	}

	@Test
	public void simple() throws InterruptedException {

		driver.get("https://jpetstore.aspectran.com/");
		driver.manage().window().setSize(new Dimension(1004, 724));

		signUp(driver);

//		// click on "sign in" button
//		driver.findElement(By.xpath("//*[@id=\"Menu\"]/div[1]/a[2]")).click();
//
//		// find username textField and insert input to it
//		WebElement userNameTextField = driver.findElement(By.xpath("//*[@id=\"Signon\"]/form/div/label[1]/input"));
//		userNameTextField.clear();
//		userNameTextField.sendKeys("standard_user");
//
//		// find password textField and insert input to it
//		WebElement passwordTextField = driver.findElement(By.xpath("//*[@id=\"Signon\"]/form/div/label[2]/input"));
//		passwordTextField.clear();
//		passwordTextField.sendKeys("pknknkjnkjkjnk");
//
//		// click on "Login" button
//		driver.findElement(By.xpath("//*[@id=\"Signon\"]/form/div/div/button")).click();

	}

	private void signUp(WebDriver driver) {

	
		// click on "sign up" button	
		driver.findElement(By.xpath("//*[@id=\"Menu\"]/div[1]/a[3]")).click();

		// User Information:
		// user ID textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[1]/tbody/tr[1]/td[2]/input"))
				.sendKeys("standard_user");

		// New password textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[1]/tbody/tr[2]/td[2]/input"))
				.sendKeys("standard_user");

		// confirm password textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[1]/tbody/tr[3]/td[2]/input"))
				.sendKeys("standard_user");

		// Account Information:
		// First name: textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[1]/td[2]/input"))
				.sendKeys("standard_user");

		// Last name: textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[2]/td[2]/input"))
				.sendKeys("standard_user");

		// Email: textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[3]/td[2]/input"))
				.sendKeys("standard_user");

		// Phone: textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[4]/td[2]/input"))
				.sendKeys("standard_user");

		// Address 1: textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[5]/td[2]/input"))
				.sendKeys("standard_user");

		// Address 2: textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[6]/td[2]/input"))
				.sendKeys("standard_user");

		// City: textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[7]/td[2]/input"))
				.sendKeys("standard_user");

		// State: textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[8]/td[2]/input"))
				.sendKeys("standard_user");

		// Zip: textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[9]/td[2]/input"))
				.sendKeys("standard_user");

		// Country: textField
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[10]/td[2]/input"))
				.sendKeys("standard_user");
		
		// Profile Information:
		// Dropdown languagePreference:
		Select drpLanguagePreference = new Select(driver.findElement(By.name("languagePreference")));
		drpLanguagePreference.selectByVisibleText("French");
		
		// Dropdown Favorite Category:
		Select drpFavouriteCategory = new Select(driver.findElement(By.name("favouriteCategoryId")));
		drpFavouriteCategory.selectByVisibleText("Cats");
		
		// CheckBox listOption
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[3]/tbody/tr[3]/td[2]/input")).click();
		
		// CheckBox bannerOption
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[3]/tbody/tr[4]/td[2]/input")).click();
		
		// click on Save account information button
		driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/div/button")).click();
		
	}

	public static void main(String args[]) {
		JUnitCore junit = new JUnitCore();
		junit.addListener(new TextListener(System.out));
		org.junit.runner.Result result = junit.run(ExampleTest.class); // Replace "SampleTest" with the name of your
																		// class
		if (result.getFailureCount() > 0) {
			System.out.println("Test failed.");
			System.exit(1);
		} else {
			System.out.println("Test finished successfully.");
			System.exit(0);
		}
	}
}
