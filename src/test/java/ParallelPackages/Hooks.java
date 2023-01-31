package ParallelPackages;

import domain.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utility.FileRead;
import utility.ReadPropFile;

import java.io.IOException;


public class Hooks {

	private WebDriver driver;

	@Before()
	public void launchBrowser() {
		try {
			ReadPropFile.config = FileRead.readProperties();
			DriverFactory driverFactory = new DriverFactory();
			driver = driverFactory.init();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@After(order = 0)
	public void quitBrowser() {
		driver.quit();
	}

	@After(order = 1)
	public void tearDown(Scenario scenario) {

		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_");
			byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sourcePath, "image/png", screenshotName);

		}

	}

}
