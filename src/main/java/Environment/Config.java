package Environment;

import domain.DriverFactory;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class Config {

//    protected  WebDriver driver;
    protected static Scenario scenarios;

    public static WebDriver getgetDriver() {

      return   DriverFactory.getDriver();
    }
}
