package CommonPages;

import Environment.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import utility.ReadPropFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class PageElements extends Config {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static void checkIfPageIsReady() {
        JavascriptExecutor js = (JavascriptExecutor) getgetDriver();
        String pageState = null;
        do {
            pageState = js.executeScript("return document.readyState").toString();
        } while (!pageState.equalsIgnoreCase("complete"));
    }

    public static void scrollDown() {

        JavascriptExecutor jse = (JavascriptExecutor) getgetDriver();
        jse.executeScript("window.scrollBy(0,250)");
    }

    public static void scrollDownToElement(WebElement element) {

        JavascriptExecutor jse = (JavascriptExecutor) getgetDriver();
        jse.executeScript("arguments[0].scrollIntoView(true)", element);
    }

    public static void clickElementUsingAction(WebElement element, WebElement clickElem) {
        Actions action = new Actions(getgetDriver());
        action.moveToElement(element).build().perform();
        SeleniumElements.waitAndClick(clickElem);

    }

    public static String prettyPrintXml(String xmlStringToBeFormatted) {
        String formattedXmlString = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource(new StringReader(xmlStringToBeFormatted));
            Document document = documentBuilder.parse(inputSource);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            StreamResult streamResult = new StreamResult(new StringWriter());
            DOMSource dOMSource = new DOMSource(document);
            transformer.transform(dOMSource, streamResult);
            formattedXmlString = streamResult.getWriter().toString().trim();
        } catch (Exception ex) {
        }
        return formattedXmlString;
    }

    public static String formatXml(String xml) {

        try {
            final InputSource src = new InputSource(new StringReader(xml));
            final Node document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(src).getDocumentElement();
            final Boolean keepDeclaration = Boolean.valueOf(xml.startsWith("<?xml"));

            //May need this: System.setProperty(DOMImplementationRegistry.PROPERTY,"com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");


            final DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
            final LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print", Boolean.TRUE); // Set this to true if the output needs to be beautified.
            writer.getDomConfig().setParameter("xml-declaration", keepDeclaration); // Set this to true if the declaration is needed to be outputted.

            return writer.writeToString(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void selectFromDropDown(WebElement element, String testdata) {
        try {
            // highlightElementBackground(element, "Pass");
            SeleniumElements.waitUntilClickable(element);
            Select option = new Select(element);
            option.selectByVisibleText(testdata);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void selectFromDropDownByVal(WebElement element, String testdata) {
        try {
            //highlightElementBackground(element, "Pass");
            SeleniumElements.waitUntilClickable(element);
            Select option = new Select(element);
            option.selectByValue(testdata);
        } catch (NoSuchElementException e) {
            e.getMessage();
        }
    }

    public static String extractNumberFromString(String number) {
        String num = number.replaceAll("[^0-9]+", " ");
        return num.replaceAll(" ", "");
    }

    public static void switchingTabs(Integer tab) throws InterruptedException {
        ArrayList<String> tabs = new ArrayList<String>(getgetDriver() .getWindowHandles());
        getgetDriver().switchTo().window(tabs.get(tab));
        SeleniumElements.waitFor(2000);
    }

    public static void switchToFrame(String frame) throws InterruptedException {
        getgetDriver().switchTo().frame(frame);
        SeleniumElements.waitFor(2000);
    }

    public static String verifyPageTitle() {
        System.out.println(getgetDriver().getTitle());
        return getgetDriver() .getTitle();

    }

    public static void refreshingPage() throws InterruptedException {
        getgetDriver() .navigate().refresh();
        SeleniumElements.waitFor(2000);
    }

    public static void waitUntilPageLoad(By by) {
        try {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOfElementLocated(by)));
        } catch (Exception e) {

            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            //     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOfElementLocated(by)));
        }
    }

    public static void waitUntilPageLoadAndClick(By by, WebElement element) {
        try {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOfElementLocated(by)));
            element.click();
        } catch (Exception e) {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            //           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOfElementLocated(by)));
            element.click();
        }
    }

    public static void waitUntilPageLoadAndSendKey(By by, WebElement element, String data) {
        try {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            //         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOfElementLocated(by)));
            element.sendKeys(data);
        } catch (Exception e) {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            //          WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOfElementLocated(by)));
            element.sendKeys(data);
        }
    }

    public static void waitUntilPageLoadAndClickElements(By waitElement, List<WebElement> relements, String text) {


        for (int i = 0; i < relements.size(); i++) {

            String x = relements.get(i).getText();
            if (x.toLowerCase().contains(text.toLowerCase()) || x.contains(text)) {
                waitUntilPageLoad(waitElement);
                relements.get(i).click();
            }
        }

    }


//AR: Wait For List of Elements
    public static void waitForListOfElements(List<WebElement> elements) {

        try {
             long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
             final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
             wait.until(ExpectedConditions.visibilityOfAllElements(elements));

        } catch (NumberFormatException e) {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.visibilityOfAllElements(elements));
        }
    }

    public static void waitAndClickUsingDoW(WebElement element, String locator) {

        do {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(locator)));
        } while (!element.isDisplayed());
        element.click();
    }

    public static void scrollToElementUsingDoW(WebElement element, String locator) {

        do {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))));
        } while (!element.isEnabled());
        JsElements.scrollToElement(element);
    }

    public static void waitAndSendKeysUsingDoW(WebElement element, String locator, String text) {

        do {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.elementToBeClickable(By.xpath(locator))));
        } while (!element.isEnabled());
        element.sendKeys(Keys.ENTER, text);
    }

//AR: Clicking From List of Elements
    public static void arGHFieldValues(List<WebElement> elements, String val) {
        SeleniumElements.waitFor(1000);
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getText().equals(val)) {
                elements.get(i).click();
            }
        }
    }

//AR: Selecting Target Element From List:
    public static void clickElementFromList(List<WebElement> elements, String val) {

            waitForListOfElements(elements);
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).getText().equals(val)) {
                    elements.get(i).click();
                }
            }
    }

//AR: Clicking From List of Elements using Action Class
    public static void arGHFieldValuesWithAction(List<WebElement> actElements, String val) {

        for (int i = 0; i < actElements.size(); i++) {
            if (actElements.get(i).getText().equals(val)) {
                Actions act = new Actions(getgetDriver() );
                act.moveToElement(actElements.get(i)).click().build().perform();
                SeleniumElements.waitFor(1000);
            }
        }
    }

//AR: Selecting Target Element From List using Action Class:
    public static void clickElementFromListUsingAction(List<WebElement> actElements, String val) {

            waitForListOfElements(actElements);
            for (int i = 0; i < actElements.size(); i++) {
                if (actElements.get(i).getText().equals(val)) {
                    Actions act = new Actions(getgetDriver() );
                    act.moveToElement(actElements.get(i)).click().build().perform();
                    SeleniumElements.waitFor(1000);
                }
            }
    }

    //Tamara - this method loops and first does wait and then checks condition if the element is displayed and clicks using WebElement and locator
    public static void doWhileLoopUntilConditionIsNotTrueWaitAndClick(WebElement element, String locator) {

        do {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(locator)));
        } while (!element.isDisplayed());
        element.click();
    }

    //Tamara - this method loops and first does wait and then checks condition if the element is displayed using WebElement and the locator
    public static void doWhileLoopUntilConditionIsNotTrueWait(WebElement element, String locator) {

        do {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))));
        } while (!element.isDisplayed());
    }

    //Tamara - This method loops until element is enabled  and scrolls to element TY
    public static void doWhileLoopUntilConditionIsNotTrueWaitAndScrollToElement(WebElement element, String locator) {
        do {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))));
        } while (!element.isEnabled());
        JsElements.scrollToElement(element);

    }

    //Tamara - This method loops until element is enabled and senskeys TY
    public static void doWhileLoopUntilConditionIsNotTrueWaitAndSendKeys(WebElement element, String locator, String text) {

        do {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.elementToBeClickable(By.xpath(locator))));
        } while (!element.isEnabled());
        element.sendKeys(Keys.ENTER, text);
    }

    //Tamara - This method loops until element is displayed and click with JS TY
    public static void doWhileLoopUntilConditionIsNotTrueWaitAndClickWithJs(WebElement element, String locator) {

        do {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOf(element)));
        } while (!element.isDisplayed());

        try {
            JavascriptExecutor js = (JavascriptExecutor) getgetDriver() ;
            js.executeScript("arguments[0].click()", element);
        } catch (ElementClickInterceptedException e) {
            log.error(e.getMessage());
        }
    }

    //Tamara - This method loops until element is enabled and clicksTY
      public static void doWhileLoopUntilConditionIsNotTrueWaitAndClick2(WebElement element, String locator) {

        do {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath(locator))));
        } while (!element.isEnabled());
        element.click();
    }

    //Tamara - This method loops until WebElement is displayed TY
    public static void doWhileLoopUntilConditionIsNotTrueWaitForWebElement(WebElement element) {
        do {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOf(element)));
        } while (!element.isDisplayed());


}
//Tamara - This method loops until element is enabled TY
    public static void doWhileLoopUntilConditionIsNotTrueWaitElementIsEnabled(WebElement element) {

        do {
            long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
            final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.visibilityOf(element)));
        } while (!element.isEnabled());
    }


    //Tamara - This method waits wor iframe to be available and then switches TY
    public static void waitForFrameToBeAvailableAndSwitch(String frame) {
        long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
        final WebDriverWait wait = new WebDriverWait(getgetDriver() , waitTime);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

}