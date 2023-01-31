package CommonPages;

import Environment.Config;
import io.cucumber.datatable.DataTable;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.ReadPropFile;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SeleniumElements extends Config {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


	public static void waitUntilElementIsClickable(WebElement element) {
		long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
//		new WebDriverWait(driver, Duration.ofSeconds(waitTime)).until(ExpectedConditions.elementToBeClickable(element));
		new WebDriverWait(getgetDriver(), (waitTime)).until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void selectFromDropDownByVal(WebElement element, String testdata) {
		try {
			// highlightElementBackground(element, "Pass");
			waitUntilClickable(element);
			Select option = new Select(element);
			option.selectByValue(testdata);
		} catch (NoSuchElementException e) {
			e.getMessage();
		}
	}

	public static void selectFromDropDownByText(WebElement element, String testdata) {
		try {
			// highlightElementBackground(element, "Pass");
			waitUntilClickable(element);
			Select option = new Select(element);
			option.selectByVisibleText(testdata);
		} catch (NoSuchElementException e) {
			e.getMessage();
		}
	}

	public static void selectFromDropDownForMSR(WebElement element, String testdata) {
		try {
			Select option = new Select(element);
			option.selectByVisibleText(testdata);
		} catch (NoSuchElementException e) {
			e.getMessage();
		}
	}

	public static void refreshPage() {
		try {
			getgetDriver().navigate().refresh();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void waitUntilClickable(WebElement element) {
		try {
			waitUntilElementIsClickable(element);
		} catch (Exception e) {
			waitUntilElementIsClickable(element);
		}
	}
	public static String waitNGetText(WebElement element) {
		//highlightElementBackground(element, "pass");
		waitUntilClickable(element);
		String getElement = element.getText();
		return getElement;
	}
	public static String getPageSource() {
		try {
			String data =getgetDriver().getPageSource();
			return data;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static void waitAndClick(WebElement element) {
		waitUntilClickable(element);
		element.click();
	}
	public static void sendKeys(WebElement element, Keys keys) {
		try {
			waitFor(2000);
			element.sendKeys(keys);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void getWindowHandleAndSwitchToAnotherWindow(int window) {
		try {
		    ArrayList<String> newTb = new ArrayList<>(getgetDriver().getWindowHandles());
			getgetDriver().switchTo().window(newTb.get(window));
			System.out.println(getgetDriver().getTitle());
			}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	public static void waitUntilTextIsVisible(WebElement element, String testData) {
		long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
//		new WebDriverWait(driver, Duration.ofSeconds(waitTime)).until(ExpectedConditions.textToBePresentInElement(element, testData));
		new WebDriverWait(getgetDriver(), (waitTime)).until(ExpectedConditions.textToBePresentInElement(element, testData));
	}

	public static void waitUntilElementIsVisible(WebElement element) {
		long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
//		new WebDriverWait(driver, Duration.ofSeconds(waitTime)).until(ExpectedConditions.visibilityOf(element));
		new WebDriverWait(getgetDriver(), (waitTime)).until(ExpectedConditions.visibilityOf(element));
	}
	public static void waitUntilElementIsVisible(String element) {
		try {
			long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
//			new WebDriverWait(driver, Duration.ofSeconds(waitTime)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
			new WebDriverWait(getgetDriver(), (waitTime)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void waitUntilElementIsVisibleLocated(By by) {
		try {
			long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
//			new WebDriverWait(driver, Duration.ofSeconds(waitTime)).until(ExpectedConditions.visibilityOfElementLocated(by));
			new WebDriverWait(getgetDriver(), (waitTime)).until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static boolean isElementPresent(WebElement element) {
		return (element != null) ? true : false;
	}

	public static boolean isElePresent(By testData) {
		boolean flag = false;
		try {
			flag = getgetDriver().findElements(testData).size() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	public static boolean isCssVisible(By testData) {
		boolean flag = false;
		try {
			String elementStyle = getgetDriver().findElement(testData).getAttribute("style");
			flag = !(elementStyle.contentEquals("display: none;") || elementStyle.contentEquals("visibility: hidden"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	public static WebElement eligibilityOrAvality(By para1, By para2) {
		WebElement element = null;
		WebElement element1;
		WebElement element2;

		do {
			try {
				element1 = getgetDriver().findElement(para1);
			} catch (Exception e) {
				element1 = null;
			}
			try {
				element2 = getgetDriver().findElement(para2);
			} catch (Exception e) {
				element2 = null;
			}
			if (element1 != null) {
				element = element1;
			} else if (element2 != null) {
				element = element2;
			}
		} while (element == null);
		return element;
	}


	public static List<WebElement> getDropdownListItems(By testData) {
		return new Select(getgetDriver().findElement(testData)).getOptions();
	}

	public static void navigateBackToPreviousPage() {
		getgetDriver().navigate().back();
	}

	public static WebElement getRootElement(WebElement element) {
		return (WebElement) ((JavascriptExecutor) getgetDriver()).executeScript("return arguments[0].shadowRoot", element);
	}

	public static String initCap(String testData) {
		return testData.substring(0, testData.length() - (testData.length() - 1)).toUpperCase()
				+ testData.substring(1).toLowerCase();
	}


	public static void setTextFieldValueIfNull(WebElement element, String testData)
			throws HeadlessException, UnsupportedFlavorException, IOException, InterruptedException {
		try {
			StringSelection stringSelection = new StringSelection("");
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			Thread.sleep(1000); // intended
			element.click();
			Thread.sleep(1000); // intended
			element.sendKeys(Keys.chord(Keys.CONTROL, "A"));
			element.sendKeys(Keys.chord(Keys.CONTROL, "C"));
			Thread.sleep(1000); // intended
			String clipData = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
					.getData(DataFlavor.stringFlavor);
			clipData = clipData.trim();
			if (clipData == null || clipData.equals("")) {
				Thread.sleep(1000); // intended
				element.sendKeys(testData);
			}
		} catch (HeadlessException | InterruptedException | UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
	}



	public static void waitFor(int waitMilis) {
		try {
			Thread.sleep(waitMilis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void explicitlyWaitForElementisClickable(WebElement ele) {
		long explicitWaitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
		try {
//			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));
			WebDriverWait wait = new WebDriverWait(getgetDriver(), (explicitWaitTime));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
		} catch (Exception e) {
			System.out.println("Failed to Get the elelement After waiting " + explicitWaitTime + "Second...");
			e.printStackTrace();
		}
	}
	public static void waitAndSendKeys(WebElement element, String testData) {
		waitUntilClickable(element);
		element.sendKeys(testData);
	}

	public static void WaitForElementText(WebElement ele, String text) {
		long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
		WebDriverWait wait = new WebDriverWait(getgetDriver(), (waitTime));
		wait.until(ExpectedConditions.textToBePresentInElement(ele, text));

	}
	public static void explicitlyWaitForElement(WebElement ele) {
		long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
		try {
//			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
			WebDriverWait wait = new WebDriverWait(getgetDriver(), (waitTime));
			wait.until(ExpectedConditions.visibilityOf(ele));
		} catch (Exception e) {
			System.out.println("Failed to Get the elelement After waiting " + waitTime + "Second...");
			e.printStackTrace();
		}
	}

	public static boolean explicitlyWaitForElementText(WebElement ele, String text) {
		long waitTime = Long.parseLong(ReadPropFile.config.get("WaitTime"));
		try {
			WebDriverWait wait = new WebDriverWait(getgetDriver(), waitTime);
			return wait.until(ExpectedConditions.textToBePresentInElement(ele, text));
		} catch (Exception e) {
			System.out.println("Failed to Get the Expected Text After waiting " + waitTime + "Second...");
			e.printStackTrace();
			return false;
		}
	}

	public static void highlightElementBackground(WebElement element, String flag) {
		JavascriptExecutor js = (JavascriptExecutor) getgetDriver();

		if (flag.equalsIgnoreCase("pass")) {
			// js.executeScript("arguments[0].style.border='1px groove green'", element);
			js.executeScript("arguments[0].style.backgroundColor = '" + "yellow" + "'", element);
		} else {
			// js.executeScript("arguments[0].style.border='4px solid red'", element);
			js.executeScript("arguments[0].style.backgroundColor = '" + "red" + "'", element);
		}

		waitFor(2000);
	}



	public static void swithcFrameBasedOnId(String frameIDname) {
		log.debug("getting inside frame..........................>>>");
		getgetDriver().switchTo().frame(frameIDname);
	}

	public static void exitFromFrame() {
		log.debug("exiting from frame..........................>>>");
		getgetDriver().switchTo().defaultContent();

	}

	public static void ClickButtoninsideFrame(WebElement element, int frameInx) {
		// this.logger.debug("Navigating to My Providers");
		getgetDriver().switchTo().frame(frameInx);
		waitFor(1000);
		element.click();
		// driver.switchTo().defaultContent();

	}

	public static void ClickButtoninsideFrameWithExit(WebElement element, int frameInx) {
		// this.logger.debug("Navigating to My Providers");
		getgetDriver().switchTo().frame(frameInx);
		waitFor(1000);
		element.click();
		getgetDriver().switchTo().defaultContent();

	}

	public static void varifyText(String text, WebElement element) {

		if (element.getText().contains(text)) {
			element.isDisplayed();
		} else {
			throw new RuntimeException("Text Don't Match");
		}

	}

	public static String getText(WebElement element) {
		//highlightElementBackground(element, "pass");
		String getElement = null;
		try {
			 getElement = element.getText();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getElement;
	}
	public static void clickWithStrElement(String xpath) {
		try {
			getgetDriver().findElement(By.xpath(xpath)).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void scrollToElement(WebElement webElement) throws Exception {
		((JavascriptExecutor)getgetDriver()).executeScript("arguments[0].scrollIntoViewIfNeeded()", webElement);
		Thread.sleep(500);
	}
	public static void clickFromElements(List<WebElement> relements,String text) {

		for (int i = 0; i < relements.size(); i++) {

		String x=	relements.get(i).getText();
			if ( x.toLowerCase().contains(text.toLowerCase()) || x.contains(text)) {
				relements.get(i).click();
			}
		}

	}
	public static void clickFromElementsWithEquleText(List<WebElement> relements,String text) {

		for (int i = 0; i < relements.size(); i++) {

		String x=	relements.get(i).getText();
			if (x.toLowerCase().contentEquals(text.toLowerCase())) {
				relements.get(i).click();
			}
		}

	}

	public static List<String> getAllText(List<WebElement> relements) {
		List<String> all_elements_text=new ArrayList<>();

		for (int i = 0; i < relements.size(); i++) {
			all_elements_text.add(relements.get(i).getText());
		}

		return all_elements_text;

	}
	public static List<String> getAllAttributeText(List<WebElement> relements,String attbName) {

		List<String> all_elements_text=new ArrayList<>();

		for (int i = 0; i < relements.size(); i++) {
			all_elements_text.add(relements.get(i).getAttribute(attbName));
		}

		return all_elements_text;
	}
	public static List<String> getSubStringFromText(List<String> strData,String openingStr,String closingStr) {

		List<String> all_elements_text=new ArrayList<>();
		for (int i = 0; i < strData.size(); i++) {
			String data = StringUtils.substringBetween(strData.get(i),openingStr,closingStr);
			all_elements_text.add(data);
		}

		return all_elements_text;

	}

	public static String getSubStringFromText(String strData,String openingStr,String closingStr) {
		String data= null;
		try {
			data = StringUtils.substringBetween(strData,openingStr,closingStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static void clickButton(WebElement element) {
		log.debug("Navigating to My Providers");
		//highlightElementBackground(element, "pass");
		element.click();
	}

	public static void sendKeys(WebElement element, String data) {
		 log.debug("Navigating to My Providers");
		 //highlightElementBackground(element, "pass");
		element.sendKeys(data);
	}

	public static void waitandclear(WebElement element) {

		waitUntilElementIsVisible(element);
		element.clear();
	}
	public static String getWebSiteUrl() {

		String url = getgetDriver().getCurrentUrl();
		return url;

	}
	public static String getWebSiteUrl(WebDriver driver) {

		String url = driver.getCurrentUrl();
		return url;

	}
	public static void clickFromListOfElement(List<WebElement> elements){

		JavascriptExecutor js = (JavascriptExecutor) getgetDriver();
		elements.forEach(e -> {
			js.executeScript("arguments[0].click();", e);
		});
	}
	public static WebElement getElementBasedOnText(WebElement element,String text){
		return element.findElement(By.xpath("//span[contains(string(), "+ text +")]"));
	}


	public static String waitAndgetValue(WebElement element) {
		String value = null;
		try {
			waitUntilElementIsVisible(element);
			value = element.getAttribute("value");
			return  value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	public static String waitAndgetValue(WebElement element,String attribute) {
		String value = null;
		try {
			waitUntilElementIsVisible(element);
			value = element.getAttribute(attribute);
			return  value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	public static void clickWithActionCls( WebElement element ) {

		Actions builder = new Actions(getgetDriver());
		builder.moveToElement( element ).click( element );
		builder.perform();
	}
	public static void clickWithActionCls( String  element) {

		WebElement el = getgetDriver().findElement(By.xpath(element));
		Actions builder = new Actions(getgetDriver());
		builder.moveToElement( el ).click( el );
		builder.perform();
	}
	public static void getDataFromDataTable( DataTable dataTable,String data) {

		Map<String,String> dataM = dataTable.asMap(String.class, String.class);
		dataM.get(data);
	}
	public static void waitUntilElementIsVisibleless(WebElement element) {
//	new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(element));
	new WebDriverWait(getgetDriver(), (10)).until(ExpectedConditions.visibilityOf(element));
}


	public static ArrayList<Integer> getIntegerArray(ArrayList<String> stringArray) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(String stringValue : stringArray) {
			try {
				//Convert String to Integer, and store it into integer array list.
				result.add(Integer.parseInt(stringValue));
			} catch(NumberFormatException nfe) {
				System.out.println("Could not parse " + nfe);

			}
		}
		return result;
	}

	public static void switchToWindow(WebDriver driver) {
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}
	public static void switchFrameBasedOnId(String frameIDname) {
		SeleniumElements.waitFor(4000);
		log.debug("getting inside frame..........................>>>");
		getgetDriver().switchTo().frame(frameIDname);
		SeleniumElements.waitFor(2000);
	}

	public static void getWindowHandleAndSwitchToAnotherWindowWithWaitTime(int window) {
		try {SeleniumElements.waitFor(4000);
			ArrayList<String> newTb = new ArrayList<>(getgetDriver().getWindowHandles());
			getgetDriver().switchTo().window(newTb.get(window));
			System.out.println(getgetDriver().getTitle());
			SeleniumElements.waitFor(5000);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void refreshPageWithWait() {
		try {
			getgetDriver().navigate().refresh();
			SeleniumElements.waitFor(2000);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void moveToElement(String  element) {

		WebElement el = getgetDriver().findElement(By.xpath(element));
		Actions actions = new Actions(getgetDriver());
		actions.moveToElement(el).build().perform();

	}




}
