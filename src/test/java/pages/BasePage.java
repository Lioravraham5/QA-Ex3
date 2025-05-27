package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    protected void sendKeysIfPresent(By locator, Object value) {
        if (value != null) {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(value.toString());
        }
    }
    
    protected boolean isElementVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    protected ExpectedCondition<Select> dropdownToBePopulated(final WebElement elm) {
        return driver -> {
            Select sel = new Select(elm);
            // you can tweak the threshold if you know exactly how many options you expect
            return (sel.getOptions().size() > 0) ? sel : null;
        };
    }
}