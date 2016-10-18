package functional;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EPRTests extends SepWebTest {
    Logger log = LoggerFactory.getLogger(EPRTests.class);
    private static String eprId;

    @Test
    public void testEprCreation() throws Exception {
        String todayDateStr = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        loginAsSudo();
        driver.findElement(By.cssSelector("#epr-dropdown .dropdown-toggle")).click();
        driver.findElement(By.cssSelector("#epr-dropdown .dropdown-menu a")).click();

        // At creation form
        assert(driver.getTitle().equals("SEP | EPR Form"));
        WebElement eprForm = driver.findElementById("epr-form");

        eprForm.findElement(By.cssSelector("input[name='name']")).sendKeys("[TEST] New Event Name");
        eprForm.findElement(By.cssSelector("input[name='description']")).sendKeys("[TEST] Some description");
        eprForm.findElement(By.cssSelector("input[name='eventType']")).sendKeys("[TEST] Event type");
        eprForm.findElement(By.cssSelector("input[name='numAttendees']")).sendKeys("20");
        eprForm.findElement(By.cssSelector("input[name='preferences']")).click(); // Clicks first one
        eprForm.findElement(By.cssSelector("input[name='budget']")).sendKeys("777");
        eprForm.findElement(By.cssSelector("input[name='fromDate']")).sendKeys(todayDateStr);
        eprForm.findElement(By.cssSelector("input[name='toDate']")).sendKeys(todayDateStr);

        eprForm.findElement(By.cssSelector("button[type='submit']")).click();

        assert(driver.findElementByCssSelector(".alert.alert-info") != null);
        eprId = driver.findElementById("eprId").getAttribute("value");
        log.info("New EPR with ID: " + eprId);
        System.out.println("New EPR with ID: " + eprId);
    }

    @Test
    public void testEprCsApproval() throws Exception {
        testEprCreation();

        driver.findElement(By.cssSelector("#epr-dropdown .dropdown-toggle")).click();
        driver.findElements(By.cssSelector("#epr-dropdown .dropdown-menu a")).get(1).click();

        // At creation form
        assert(driver.getTitle().equals("SEP | View All"));

        WebElement row = driver.findElement(By.cssSelector("#epr-" + eprId));
        assert(row != null);

        row.findElement(By.cssSelector("a.epr-approve")).click();
        assert(driver.findElementByCssSelector(".alert.alert-info") != null);
    }

}


