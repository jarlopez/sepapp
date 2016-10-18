package functional;

import config.TestConfig;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

class SepWebTest {
    Logger log = LoggerFactory.getLogger(SepWebTest.class);

    // TODO Override in system variables?
    static final String FF_46_PATH = "/home/johan/firefoxes/firefox-46/firefox";
    static final String BASE_URL = "http://localhost:8080";
    static FirefoxDriver driver;
    WebElement element;

    @Before
    public void setUp() throws Exception {
        File pathToBinary = new File(FF_46_PATH);
        FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        driver = new FirefoxDriver(ffBinary, firefoxProfile);
    }
    @After
    public void afterTest() {
        driver.quit();
    }

    void loginAsSudo() {
        driver.get(BASE_URL);
        driver.findElement(By.name("username")).sendKeys(TestConfig.SUDO_UN);
        driver.findElement(By.name("password")).sendKeys(TestConfig.SUDO_PW);
        driver.findElement(By.id("login-submit")).click();
        try{
            element = driver.findElement(By.id("welcome-msg"));
        }catch (Exception e){
            log.error("Exception caught when trying to access welcome message");
        }
    }
}
