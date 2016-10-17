package functional;

import config.TestConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LoginTests {
    Logger log = LoggerFactory.getLogger(LoginTests.class);

    // TODO Override in system variables?
    private static final String FF_46_PATH = "/home/johan/firefoxes/firefox-46/firefox";
    private static final String BASE_URL = "http://localhost:8080";
    private static FirefoxDriver driver;
    WebElement element;

    @Before
    public void setUp() throws Exception {
        File pathToBinary = new File(FF_46_PATH);
        FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        driver = new FirefoxDriver(ffBinary, firefoxProfile);
    }

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


