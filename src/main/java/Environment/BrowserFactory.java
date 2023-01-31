package Environment;

import com.microsoft.edge.seleniumtools.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.JsonTool;
import utility.OsUtill;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;

public class BrowserFactory extends Config {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    WebDriver driver;

    public  WebDriver getBrowser() {

        ///// Reading the Browser list that will use on the entire test
        ////  It could one Browser or for parallel execution could be multiple
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
            else {
                log.error("No environment Found....");
            }

            getLocalDriver(browser,osName);
            getgetDriver() .manage().window().maximize();
            getgetDriver() .findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
            return getgetDriver() ;
        } catch (IOException | ParseException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private WebDriver getLocalDriver(String browser,String osName){

        try {

            switch (browser.toLowerCase()) {
                case "chrome":
                    if (osName.contains("Win")) {
                        setChromeDriverForWin();
                    } else if (osName.contains("Mac")) {
                        setChromeDriverForMac();
                    } else if (osName.contains("Linux")) {
                        setChromeDriverForLinux(); }
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
        return  getgetDriver() ;
    }


    private  WebDriver setChromeDriverForWin() {

        try {
            //String downloadFilepath = System.getProperty("user.dir") + "\\src\\resources\\browser_downloads";
//            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
//            chromePrefs.put("profile.default_content_settings.popups", 0);
//            chromePrefs.put("download.default_directory", downloadFilepath);

//            ChromeOptions options = new ChromeOptions();
//            //options.setExperimentalOption("prefs", chromePrefs);
//            options.addArguments("--test-type");
//            options.addArguments("--disable-extensions");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            options.addArguments("start-maximized");
            options.addArguments("--enable-precise-memory-info");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-default-apps");
            options.addArguments("test-type=browser");
           // options.addArguments("--incognito");
            options.addArguments("window-size=1920x1480");
            options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
            System.out.println("Starting Chrome browser");
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private  WebDriver setChromeDriverForMac() {
        try {
            ChromeOptions options = new ChromeOptions();
            String downloadFilepath = System.getProperty("user.dir") + "\\src\\resources\\browser_downloads";
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            chromePrefs.put("profile.default_content_settings.popups", 0);
            chromePrefs.put("download.default_directory", downloadFilepath);
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments("--test-type");
            options.addArguments("--disable-extensions");
            options.addArguments("headless");
            options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private  WebDriver setChromeDriverForLinux() {

        try {

            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            options.addArguments("start-maximized");
            options.addArguments("--enable-precise-memory-info");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-default-apps");
            options.addArguments("test-type=browser");
            options.addArguments("headless");

            // options.addArguments("--incognito");
            options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
            System.out.println("Starting Chrome browser");
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private  WebDriver setFirefoxDriver() {

        try {
//            ProfilesIni profile = new ProfilesIni();
//            FirefoxProfile ffProfile = profile.getProfile("default");
//            FirefoxOptions opts = new FirefoxOptions();
//            opts.addArguments("-private");
//            //ffProfile.setPreference("browser.privatebrowsing.autostart", true);
//            opts.setCapability(FirefoxDriver.PROFILE, ffProfile);
            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver(opts);
            driver = new FirefoxDriver();
            System.out.println("Starting FireFox browser");
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private WebDriver setIEDriver() {

        try {
            InternetExplorerOptions options = new InternetExplorerOptions();
            options.ignoreZoomSettings().introduceFlakinessByIgnoringSecurityDomains().setCapability("requireWindowFocus",
                    true);
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver(options);
            System.out.println("Starting IE browser");
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private WebDriver setEdgeDriver() {


        try {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("-inprivate");

            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver(edgeOptions);
            System.out.println("Starting Edge browser");
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

//    private  WebDriver setUpSasuaseLab(String browserName) {
//        Map<String ,String> credential =  new  EnvironmentFactory().getCloudAccessCredentials();
//        try {
//            String userName = credential.get("sauceUserName");
//            String accessKey = credential.get("sauceAccessKey");
//            String sauceURL = Const.getSauceURL();
//
//            /// Below String contains the name of the Scenario That is Running ...
//            String RunningScenarioName = scenarios.getName();
//
//            MutableCapabilities sauceOpts = new MutableCapabilities();
//            sauceOpts.setCapability("username", userName);
//            sauceOpts.setCapability("access_key", accessKey);
//            sauceOpts.setCapability("projectName", "Knicks Web-Tests"); //TODO: This should access any Project name
//            sauceOpts.setCapability("build", "v1.0"); //ToDo: This should access any Project name
//            //capabilities.setCapability("name", RunningFeature + " - " +RunningScenarioName ); //TODO: How can we get feature name
//            sauceOpts.setCapability("name", RunningScenarioName);
//
//            MutableCapabilities capabilities;
//            switch(browserName)
//            {
//                case BrowserType.FIREFOX:
//                    capabilities = new FirefoxOptions();
//                    break;
//                case BrowserType.CHROME:
//                    capabilities = new ChromeOptions();
//                    break;
//                case BrowserType.EDGE:
//                    capabilities = new ChromeOptions();
//                    break;
//                default:
//                    capabilities = new ChromeOptions();
//            }
//            capabilities.setCapability("browserName", browserName);
//            capabilities.setCapability("browserVersion", "latest");
//            capabilities.setCapability("platformName", "Windows 10");
//            capabilities.setCapability("sauce:options", sauceOpts);
//            driver = new RemoteWebDriver(new URL(sauceURL), capabilities);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }

//        return driver;
//    }

}
