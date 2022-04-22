package Automate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GoogleLogin {

/**
 * @author Sumesh Gajmer
 * @since 2022-4-11
 **/

    /**
     * To make this automation work, you need to disable your gmail account two-factor auth and
     * enable less secure apps gmail login
     **/

    private static final String NEXT_BUTTON_XPATH = "/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div/div[2]/div/div[2]/div/div[1]/div/div/button";
    private static final String PASSWORD_CSS_SELECTOR = "input[type=password]";
    private static final String EMAIL_INPUT_ID = "identifierId";
    private static final String PHONE_INPUT_ID = "phoneNumberId";
    private static final String PHONE_VERIFICATION_BUTTON_XPATH = "/html/body/div[1]/div[1]/div[2]/div/div[2]/div/div/div[2]/div/div[1]/div/form/span/section/div/div/div/ul/li[4]";
    private static final String LOGIN_BUTTON_XPATH = "/html/body/div[2]/div/div[2]/div/div[2]/div/div/div/button[1]";
    private static final String EMAIL = "EMAIL";
    private static final String PASSWORD = "PASSWORD";
    private static final String PHONE = "PHONE";
    private static final String GOOGLE_LOGIN_URL = "https://accounts.google.com";
    private static final String PROPERTIES_FILE_NAME = "application.properties";
    private static final Logger LOGGER = Logger.getLogger(GoogleLogin.class.getName());
    private final WebDriver webDriver;
    private final WebDriverWait wait;

    GoogleLogin(WebDriver webDriver, WebDriverWait webDriverWait){
        this.webDriver = webDriver;
        this.wait = webDriverWait;
    }

    public void automate() {
        try {
            webDriver.findElement(By.xpath(LOGIN_BUTTON_XPATH)).click();
            setEmailAndContinue();
            setPasswordAndContinue();
            checkPhoneVerificationAndContinue();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    private void checkPhoneVerificationAndContinue() {
        var currentWebUrl = webDriver.getCurrentUrl();
        if (currentWebUrl.contains(GOOGLE_LOGIN_URL)) {
            var phoneVerificationButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PHONE_VERIFICATION_BUTTON_XPATH)));
            phoneVerificationButton.click();
            var phoneInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(PHONE_INPUT_ID)));
            phoneInput.sendKeys(getPropertiesValueOf(PHONE));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(NEXT_BUTTON_XPATH)));
            webDriver.findElement(By.xpath(NEXT_BUTTON_XPATH)).click();
        }
    }

    private void setEmailAndContinue() {
        var emailTextField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(EMAIL_INPUT_ID)));
        emailTextField.sendKeys(getPropertiesValueOf(EMAIL));
        webDriver.findElement(By.xpath(NEXT_BUTTON_XPATH)).click();
    }

    private void setPasswordAndContinue() {
        var passwordTextField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(PASSWORD_CSS_SELECTOR)));
        passwordTextField.sendKeys(getPropertiesValueOf(PASSWORD));
        webDriver.findElement(By.xpath(NEXT_BUTTON_XPATH)).click();
    }

    private String getPropertiesValueOf(String property) {
        try {
            Properties p = new Properties();
            p.load(GoogleLogin.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME));
            var propertyText = p.getProperty(property);
            if (propertyText == null || propertyText.isEmpty())
                throw new IllegalArgumentException("Please provide valid property name, it cannot be null or empty");
            return propertyText;
        } catch (IOException e) {
            throw new IllegalArgumentException("Please provide valid property name.");
        }
    }
}
