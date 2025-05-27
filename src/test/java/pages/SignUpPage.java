package pages;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Log;
import org.apache.logging.log4j.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class SignUpPage extends BasePage {

    private static final Logger logger = Log.getLogger(SignUpPage.class);

    // — User Information —
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[1]/tbody/tr[1]/td[2]/input")
    private WebElement userIdField;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[1]/tbody/tr[2]/td[2]/input")
    private WebElement newPasswordField;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[1]/tbody/tr[3]/td[2]/input")
    private WebElement confirmPasswordField;

    // — Account Information —
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[1]/td[2]/input")
    private WebElement firstNameField;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[2]/td[2]/input")
    private WebElement lastNameField;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[3]/td[2]/input")
    private WebElement emailField;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[4]/td[2]/input")
    private WebElement phoneField;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[5]/td[2]/input")
    private WebElement address1Field;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[6]/td[2]/input")
    private WebElement address2Field;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[7]/td[2]/input")
    private WebElement cityField;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[8]/td[2]/input")
    private WebElement stateField;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[9]/td[2]/input")
    private WebElement zipField;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[2]/tbody/tr[10]/td[2]/input")
    private WebElement countryField;

    // — Profile Information —
    @FindBy(name = "languagePreference")
    private WebElement languageDropdown;
    
    @FindBy(name = "favouriteCategoryId")
    private WebElement categoryDropdown;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[3]/tbody/tr[3]/td[2]/input")
    private WebElement listCheckbox;
    
    @FindBy(xpath = "//*[@id=\"CenterForm\"]/form/table[3]/tbody/tr[4]/td[2]/input")
    private WebElement bannerCheckbox;

    // — Actions —
    @FindBy(xpath = "//button[normalize-space()='Save Account Information']")
    private WebElement submitButton;

    // — Navigation —
    @FindBy(xpath = "//*[@id=\"Menu\"]/div[1]/a[3]")
    private WebElement signUpLink;
    
    // — Success/Error Messages —
    @FindBy(css = "#MessageBar p")
    private WebElement successfulSignUpBanner;
    
    @FindBy(css = "span.error-msg, .error-message, .alert-danger")
    private List<WebElement> errorSpans;
    
    public SignUpPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public SignUpPage open() {
        logger.debug("Clicking Sign Up link");
        signUpLink.click();
        return this;
    }

    public SignUpPage enterUserInfo(String userId, String newPassword, String confirmPassword) {
        logger.debug("Entering user information");
        userIdField.clear();
        userIdField.sendKeys(safe(userId));
        
        newPasswordField.clear();
        newPasswordField.sendKeys(safe(newPassword));
        
        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(safe(confirmPassword));
        
        return this;
    }

    public SignUpPage enterAccountInfo(String firstName, String lastName, String email, String phone,
                                      String address1, String address2, String city, String state, 
                                      String zip, String country) {
        logger.debug("Entering account information");
        firstNameField.clear();
        firstNameField.sendKeys(safe(firstName));
        
        lastNameField.clear();
        lastNameField.sendKeys(safe(lastName));
        
        emailField.clear();
        emailField.sendKeys(safe(email));
        
        phoneField.clear();
        phoneField.sendKeys(safe(phone));
        
        address1Field.clear();
        address1Field.sendKeys(safe(address1));
        
        address2Field.clear();
        address2Field.sendKeys(safe(address2));
        
        cityField.clear();
        cityField.sendKeys(safe(city));
        
        stateField.clear();
        stateField.sendKeys(safe(state));
        
        zipField.clear();
        zipField.sendKeys(safe(zip));
        
        countryField.clear();
        countryField.sendKeys(safe(country));
        
        return this;
    }

    public SignUpPage selectLanguagePreference(String language) {
        if (language != null && !language.isEmpty()) {
            logger.debug("Selecting language preference: {}", language);
            wait.until(dropdownToBePopulated(languageDropdown))
                .selectByVisibleText(language);
        }
        return this;
    }

    public SignUpPage selectFavouriteCategory(String category) {
        if (category != null && !category.isEmpty()) {
            logger.debug("Selecting favourite category: {}", category);
            wait.until(dropdownToBePopulated(categoryDropdown))
                .selectByVisibleText(category);
        }
        return this;
    }

    public SignUpPage enableMyList(boolean enable) {
        if (enable && !listCheckbox.isSelected()) {
            logger.debug("Enabling MyList option");
            listCheckbox.click();
        } else if (!enable && listCheckbox.isSelected()) {
            logger.debug("Disabling MyList option");
            listCheckbox.click();
        }
        return this;
    }

    public SignUpPage enableMyBanner(boolean enable) {
        if (enable && !bannerCheckbox.isSelected()) {
            logger.debug("Enabling MyBanner option");
            bannerCheckbox.click();
        } else if (!enable && bannerCheckbox.isSelected()) {
            logger.debug("Disabling MyBanner option");
            bannerCheckbox.click();
        }
        return this;
    }

    public void clickSubmit() {
        logger.debug("Clicking submit button");
        submitButton.click(); 
    }
    
    public boolean successfulSignup() {
        try {
            String messageText = wait.until(ExpectedConditions
                .visibilityOf(successfulSignUpBanner)).getText().trim();
            return messageText.contains("Your account has been created.");
        } catch (Exception e) {
            logger.debug("Success message not found");
            return false;
        }
    }


    public Optional<String> findErrorMessage() {
        return errorSpans.stream()
                         .map(WebElement::getText)
                         .map(String::trim)
                         .filter(s -> !s.isEmpty())
                         .findFirst();
    }
    
    public SignUpPage fillForm(JSONObject data) {
        logger.debug("Filling sign up form from JSON data");
        
        // User Info
        enterUserInfo(
            getValue(data, "User ID"),
            getValue(data, "New password"),
            getValue(data, "Confirm password")
        );

        // Account Info
        enterAccountInfo(
            getValue(data, "First name"),
            getValue(data, "Last name"),
            getValue(data, "Email"),
            getValue(data, "Phone"),
            getValue(data, "Address 1"),
            getValue(data, "Address 2"),
            getValue(data, "City"),
            getValue(data, "State"),
            getValue(data, "Zip"),
            getValue(data, "Country")
        );

        // Profile Info
        selectLanguagePreference(getValue(data, "Language Preference"));
        selectFavouriteCategory(getValue(data, "Favourite Category"));
        enableMyList(Boolean.TRUE.equals(data.get("Enable MyList")));
        enableMyBanner(Boolean.TRUE.equals(data.get("Enable MyBanner")));
        
        return this;
    }

    public void signUp(JSONObject testCase) {
        open()
          .fillForm(testCase)
          .clickSubmit();
    }

    private String safe(String value) {
        return value != null ? value : "";
    }

    private String getValue(JSONObject obj, String key) {
        Object value = obj.get(key);
        return value != null ? value.toString() : "";
    }
}