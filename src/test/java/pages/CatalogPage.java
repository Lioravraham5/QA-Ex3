package pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utils.Log;

public class CatalogPage extends BasePage {
    private static final Logger logger = Log.getLogger(SignInPage.class);
    
    @FindBy(id = "SidebarContent") private WebElement sidebarContent;
    @FindBy(id = "Catalog") private WebElement catalogContent;
    @FindBy(linkText = "Add to Cart")   private WebElement AddToCartLink;
    
    public CatalogPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void openCatalog(String categoryName) {
        logger.info("Navigating to Catagory page: {}", categoryName);
        WebElement link = sidebarContent.findElement(By.linkText(categoryName));
        wait.until(ExpectedConditions.elementToBeClickable(link))
            .click();
    }
    
    public void openProduct(String profuctId) {
    	logger.info("Navigating to Product page with id: {}", profuctId);
        WebElement link = catalogContent.findElement(By.linkText(profuctId));
        wait.until(ExpectedConditions.elementToBeClickable(link))
            .click();
    }
    
}
