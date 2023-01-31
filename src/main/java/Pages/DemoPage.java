package Pages;
import CommonPages.JsElements;
import CommonPages.SeleniumElements;
import io.cucumber.java.PendingException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import utility.Const;
import utility.ExcelReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DemoPage {

    private WebDriver driver;
    private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    public DemoPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//ul//li//span[@class='k-link']")
    public List<WebElement> test;


    public void selectServiceStateFromMainTabsStrip(){
        throw new PendingException();
    }




}
