package functional;

import config.TestConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginTests  extends SepWebTest {
    Logger log = LoggerFactory.getLogger(LoginTests.class);

    @Test
    public void testCorrectLogin() throws Exception {
        driver.get(BASE_URL);
        driver.findElement(By.name("username")).sendKeys(TestConfig.SUDO_UN);
        driver.findElement(By.name("password")).sendKeys(TestConfig.SUDO_PW);
        driver.findElement(By.id("login-submit")).click();
        try{
            element = driver.findElement(By.id("welcome-msg"));
        }catch (Exception e){
            log.error("Exception caught when trying to access welcome message");
        }
        Thread.sleep(3000);
        Assert.assertNotNull(element);
    }
    @Test
    public void testIncorrectLogin() throws Exception {
        driver.get(BASE_URL);
        driver.findElement(By.name("username")).sendKeys("invalid");
        driver.findElement(By.name("password")).sendKeys("invalid");
        driver.findElement(By.id("login-submit")).click();
        try{
            element = driver.findElement(By.id("welcome-msg"));
        }catch (Exception e){
            log.error("Exception caught when trying to access welcome message");
        }
        WebElement errorMsg = driver.findElementByCssSelector(".alert.alert-danger");
        Assert.assertNull(element);
        Assert.assertNotNull(errorMsg);
        Thread.sleep(3000);
    }

    @After
    public void afterTest() {
        driver.quit();
    }

}


