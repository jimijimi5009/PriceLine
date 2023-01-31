package domain;

import com.microsoft.edge.seleniumtools.EdgeDriver;
import com.microsoft.edge.seleniumtools.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ThreadGuard;
import utility.JsonTool;
import utility.OsUtill;

import java.util.HashMap;

public class DriverFactory {

	public WebDriver driver;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

	public WebDriver init( ) {


		try {
			String jsonFilePath = System.getProperty("user.dir") +"/src/test/resources/BrowsersList.json";
			JSONObject jsonObject = JsonTool.readJson(jsonFilePath);
			String browser = (String) jsonObject.get("Browser");
			String osName = OsUtill.getOperatingSystemSystemUtils();
			if (osName.contains("Win")) {
				OsUtill.killAllProcesses(browser);}
			else if (osName.contains("Lin")) {
				OsUtill.killAllProcesses(browser);
			}

			getLocalDriver(browser);
			getDriver().manage().deleteAllCookies();
			getDriver().manage().window().maximize();

		}catch (Exception e) {
			e.printStackTrace();
		}

		return getDriver();
	}

	private WebDriver getLocalDriver(String browser){

		try {

			switch (browser.toLowerCase()) {
				case "chrome":
						setChromeDriverForWin();
					break;
				case "firefox":
					setFirefoxDriver();
					break;
				case "edge": //microsoftedge
					setEdgeDriver();
					break;
				default:
					System.out.println(browser + " is not a supported browser");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return  driver;
	}


	private  void setChromeDriverForWin() {

		try {

			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			options.addArguments("start-maximized");
			options.addArguments("--enable-precise-memory-info");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-default-apps");
			options.addArguments("test-type=browser");
//			options.addArguments("--headless");
//			options.addArguments("--disable-gpu");
			// options.addArguments("--incognito");
			options.addArguments("window-size=1920x1480");
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			WebDriverManager.chromedriver().setup();
			tlDriver.set(ThreadGuard.protect(new ChromeDriver(options)));
			System.out.println("Starting Chrome browser");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private  void setFirefoxDriver() {

		try {

			WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver());
			System.out.println("Starting FireFox browser");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void setEdgeDriver() {


		try {
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("-inprivate");

			WebDriverManager.edgedriver().setup();
			tlDriver.set(new EdgeDriver(edgeOptions));
			System.out.println("Starting Edge browser");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}
}
