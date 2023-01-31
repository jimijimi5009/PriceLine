package CommonPages;

import Environment.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.List;

public class JsElements  extends Config {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void jsClick(WebElement element) {

        try {
            JavascriptExecutor js = (JavascriptExecutor) getgetDriver();
            js.executeScript("arguments[0].click()", element);
        }catch (ElementClickInterceptedException e){
            log.error(e.getMessage());
        }


    }
    public static void jsMoveToView(WebElement element) {

        try {
            JavascriptExecutor js = (JavascriptExecutor) getgetDriver();
            js.executeScript("arguments[0].scrollIntoView()", element);
        }catch (ElementClickInterceptedException e){
            log.error(e.getMessage());
        }


    }
    public static void scrollToElement(WebElement element) {
       // SeleniumElements.waitUntilElementIsVisible(element);
        ((JavascriptExecutor) getgetDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public static void handleAlert() {
        try {
            Thread.sleep(2000); // intended
            if (isAlertPresent()) {
                getgetDriver().switchTo().alert().accept();
            }
        } catch (Exception e) {
            // do nothing
        }
    }

    public static boolean isAlertPresent() {
        boolean flag = false;
        try {
           // new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.alertIsPresent());
        //    new WebDriverWait(driver, (5)).until(ExpectedConditions.alertIsPresent());
            getgetDriver().switchTo().alert();
            flag = true;
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
        return flag;
    }
    public static void scrollDown() {

        JavascriptExecutor jse = (JavascriptExecutor)getgetDriver();
        jse.executeScript("window.scrollBy(0,250)");
    }
    public static void scrollDownToElement(WebElement element) {

        JavascriptExecutor jse = (JavascriptExecutor)getgetDriver();
        jse.executeScript("arguments[0].scrollIntoView(true)", element);
    }

    public static void sendText(String text, WebElement element){
        JavascriptExecutor jse = (JavascriptExecutor)getgetDriver();
        jse.executeScript("arguments[0].value='"+text+"'", element);
    }

    public static void jsClickFromElements(List<WebElement> relements, String text) {

        for (int i = 0; i < relements.size(); i++) {

            String x=	relements.get(i).getText();
            if ( x.toLowerCase().contains(text.toLowerCase()) || x.contains(text)) {

                jsClick(relements.get(i));
            }
        }

    }
}
