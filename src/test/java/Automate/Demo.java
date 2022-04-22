package Automate;

import Automate.GoogleLogin;
import com.beust.ah.A;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.support.ui.Select;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class Demo {


    public static void main(String[] args) throws InterruptedException {

        WebDriverManager.firefoxdriver().setup();
        FirefoxDriver driver = new FirefoxDriver();

        driver.get("https://uat-dot-remit-web.dallas-hamrostack.com");
        driver.manage().window().maximize();
        driver.findElement(By.id("rcc-confirm-button")).click();

        WebElement signINButton = driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div/div[1]/div[1]/div/div[1]/div[2]/button/span[1]"));
        signINButton.click();

        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(15));


        GoogleLogin googleLogin = new GoogleLogin(driver , wait);
        googleLogin.automate();
        registerYourself(driver);


    }

    private static void registerYourself(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(15));



        var register = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div/div/div/section/section/main/div/div[1]/div[1]/div[2]/div/div[2]/button")));
        if(register.isDisplayed()){
            register.click();

            var phone = wait.until(ExpectedConditions.elementToBeClickable(By.id("phone")));
            phone.findElement(By.id("phone")).sendKeys("9823636607");

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");


            var locationButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("stateCode")));
            Actions actions = new Actions(driver);
            actions.moveToElement(locationButton);
            actions.perform();
            Select location = new Select(locationButton);
            location.selectByVisibleText("California");

//        var randomString = RandomString.make(4);
//        var emailText = "automationselenuim99" + randomString + "@gmail.com";
//        var email =  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"email\"]")));
//        email.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(emailText);


            var clickContinue = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div/div/div/section/section/main/div/div[4]/div/div[2]/div/div/form/button")));
            clickContinue.click();

        }else{

            phoneVerification(driver);
        }
    }


    private static void phoneVerification(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(15));

        var phoneVer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div/div/div/section/section/main/div/div[1]/div[1]/div[2]/div/div[2]/button")));
        phoneVer.click();

        var sendOtp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("commonButtonRed margin50")));
        sendOtp.click();
        System.out.println("wkefbefb");


    }



}
