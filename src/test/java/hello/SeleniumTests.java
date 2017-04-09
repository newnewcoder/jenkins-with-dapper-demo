package hello;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "server.port=9000", webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SeleniumTests {

    private WebDriver browser;

    private WebDriverWait wait;

    @Before
    public void setup() {
        browser = new ChromeDriver();
        wait = new WebDriverWait(browser, 2);
    }

    @After
    public void close() {
        if (browser != null) {
            browser.quit();
        }
    }

    @Test
    public void loginWithValidUserThenAuthenticated() throws Exception {
        login("user", "password");
    }

    @Test//always fail
    public void loginWithInvalidUserThenUnauthenticated() throws Exception {
        login("invalid", "invalidpassword");
    }

    private void login(String user, String password) throws InterruptedException {
        String host = "localhost:9000";
        browser.get(host + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        browser.findElement(By.name("username")).sendKeys(user);
        browser.findElement(By.name("password")).sendKeys(password);
        browser.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > h1")));
        browser.get(host + "/hello");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > h1")));
        assertEquals("Hello user!", browser.findElement(By.cssSelector("body > h1")).getText());
    }
}