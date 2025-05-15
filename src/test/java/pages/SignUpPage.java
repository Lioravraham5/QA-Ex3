package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class SignUpPage extends BasePage{

	public SignUpPage(WebDriver driver) {
		super(driver);
		
	}
	
	public void goTo() {
        driver.findElement(By.xpath("//*[@id=\"Menu\"]/div[1]/a[3]")).click();
    }

    public void fillUserInformation(String username, String password) {
        driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[1]/tbody/tr[1]/td[2]/input")).sendKeys(username);
        driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[1]/tbody/tr[2]/td[2]/input")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[1]/tbody/tr[3]/td[2]/input")).sendKeys(password);
    }

    public void fillAccountInformation(String value) {
        for (int i = 1; i <= 10; i++) {
            driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[" + i + "]/td[2]/input")).sendKeys(value);
        }
    }

    public void fillProfileInformation() {
        new Select(driver.findElement(By.name("languagePreference"))).selectByVisibleText("French");
        new Select(driver.findElement(By.name("favouriteCategoryId"))).selectByVisibleText("Cats");

        driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[3]/tbody/tr[3]/td[2]/input")).click(); // listOption
        driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/table[3]/tbody/tr[4]/td[2]/input")).click(); // bannerOption
    }

    public void submit() {
        driver.findElement(By.xpath("//*[@id=\"CenterForm\"]/form/div/button")).click();
    }

    public void signUp(String username, String password) {
        goTo();
        fillUserInformation(username, password);
        fillAccountInformation(username);
        fillProfileInformation();
        submit();
    }

}
