package functional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class LoginTests {
    // TODO Override in system variables?
    private static final String FF_46_PATH = "/home/johan/firefoxes/firefox-46/firefox";
    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        File pathToBinary = new File(FF_46_PATH);
        FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
        FirefoxProfile firefoxProfile = new FirefoxProfile();
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        this.driver = new FirefoxDriver(ffBinary, firefoxProfile);
    }

    @Test
    public void testSimple() throws Exception {
        this.driver.get("http://www.google.com");
        assert("Google".equals(this.driver.getTitle()));
    }

    @After
    public void afterTest() {
        this.driver.quit();
    }

}


