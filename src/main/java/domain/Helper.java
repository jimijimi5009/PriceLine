package domain;

import Environment.BrowserFactory;
import Environment.Config;
import Pages.DemoPage;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class Helper extends Config {

    //    WebDriver driver; //Local for Helper

    DemoPage demoPage;





    WebDriver driver= getgetDriver();

    /////////////////////////// Starting Points
    public void startApplication(String url) {

        if (this.isBrowserClosed(driver)) {
            driver = new BrowserFactory().getBrowser(); /// Either Local or Remote Returning
        }
        driver.get(url);

    }

    public boolean isBrowserClosed(WebDriver driver)
    {
        boolean isClosed = false;
        try {
            driver.getTitle();
        } catch(Exception ubex) {
            isClosed = true;
        }

        return isClosed;
    }

/////////////////////////// AlayaCare Pages Init/////////////////



    public DemoPage demoPage() {
        if (demoPage == null) {
            demoPage = new DemoPage(driver);
        }
        return demoPage;
    }


    /////////////////////////// Ending Points

    public void closeReourceForCompleted(Scenario scenario) {

        try {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            if (scenario.isFailed()) {
                scenario.log("failure This Page...");
                TakesScreenshot ts = (TakesScreenshot)  DriverFactory.getDriver();
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", screenshotName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            DriverFactory.getDriver().quit();
        } catch (NullPointerException e) {
            System.out.println("No Driver to close");
        }

    }

}
