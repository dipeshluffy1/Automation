package Automate;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.bytebuddy.utility.RandomString;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Demo {


    public static void main(String[] args) throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(20));

        driver.get("https://uat-dot-remit-web.dallas-hamrostack.com");
        driver.manage().window().maximize();
        driver.findElement(By.id("rcc-confirm-button")).click();

        WebElement signINButton = driver.findElement(By.xpath("/html/body/div/div/div/div[1]/div/div[1]/div[1]/div/div[1]/div[2]/button/span[1]"));
        signINButton.click();

        var LoginWithGoogle = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div/div/button[1]/div")));
        LoginWithGoogle.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div[2]/div/div/div/button[1]/div")).click();



        GoogleLogin googleLogin = new GoogleLogin(driver , wait);
        googleLogin.automate();
        registerYourself(driver);


    }

    private static void registerYourself(WebDriver driver) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(15));



        var register = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div/div/div/section/section/main/div/div[1]/div[1]/div[2]/div/div[2]/button")));
        register.click();
        if(driver.getCurrentUrl().contains("send")){

            var phone = wait.until(ExpectedConditions.elementToBeClickable(By.id("phone")));
            phone.findElement(By.id("phone")).sendKeys("9823636607");

            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,500)");


            var locationButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("stateCode")));
            Actions actions = new Actions(driver);
            actions.moveToElement(locationButton);
            actions.perform();
            Select location = new Select(locationButton);
            location.selectByValue("CA");


//            var randomString = RandomString.make(4);
//            var emailText = "automationselenuim99" + randomString + "@gmail.com";
//            var email =  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"email\"]")));
//


            var clickContinue = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div/div/div/section/section/main/div/div[4]/div/div[2]/div/div/form/button")));
            clickContinue.click();

        }else if(driver.getCurrentUrl().contains("verify")){
            phoneVerification(driver);
        }
        else {
            accountSetup(driver);
        }

    }


    private static void phoneVerification(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(15));
        var sendOtp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#app > div > div > div > div > div:nth-child(2) > div > div:nth-child(4) > form > button")));
        sendOtp.click();

        var enterOtp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("otpInput")));
        enterOtp.sendKeys("000000" + Keys.ENTER);

        var verifyCode = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#app > div > div > div > div > div:nth-child(2) > div > div:nth-child(4) > div.phoneWrapper > button")));
        verifyCode.click();


    }

    private static void accountSetup(WebDriver driver){

        WebDriverWait wait = new WebDriverWait(driver ,Duration.ofSeconds(15));

        driver.navigate().to("https://uat-dot-remit-web.dallas-hamrostack.com/dashboard");

        var sendMoney = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#app > div > div > div > section > aside > div > div > div > div > a > div > button")));
        sendMoney.click();

        var selectGender = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first_name")));
        selectGender.click();



    }



}
