package stepdefs;





import io.cucumber.java.After;
import io.cucumber.java.Before;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Hooks {

  public static  WebDriver driver;

    @Before
    public void setUp() {

        Map<String, Object> preferences = new HashMap<>();

        // Password save popup disable
        preferences.put("credentials_enable_service", false);
        preferences.put("profile.password_manager_enabled", false);

        // Compromised-password/data-breach popup disable
        preferences.put(
                "profile.password_manager_leak_detection",
                false
        );

        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("prefs", preferences);
        options.addArguments("--disable-extensions");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("https://www.saucedemo.com/");

        driver.manage().deleteAllCookies();
        driver.navigate().refresh();




    }







    @After
    public void tearDown() {



         //  driver.quit();


    }
}