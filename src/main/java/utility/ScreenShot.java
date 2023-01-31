package utility;

import Environment.Config;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShot extends Config {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final String OUTPUT_DIR = "test-output";
	private static final String IMAGE_DIR = "images";

//	public static void take(WebDriver driver) {
//
//		logger.debug("Taking screenshot...");
//		File screenshot = (File) ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE);
//		String reportDir = ".";
//		reportDir = reportDir + File.separatorChar + "test-output";
//		File imagesDir = new File(reportDir + File.separatorChar + "images");
//		if (!imagesDir.isDirectory() && !imagesDir.mkdirs()) {
//			logger.error("Failed to create directory " + imagesDir);
//		}
//
//		DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy h-m-s");
//		Date date = new Date();
//		String imageName = dateFormat.format(date);
//		String imageNameStr;
//		if (!imageName.endsWith(".")) {
//			imageNameStr = imageName + ".png";
//		} else {
//			imageNameStr = imageName + "png";
//		}
//
//		File image = new File(imagesDir.toString() + File.separatorChar + imageNameStr);
//		if (image.exists()) {
//			logger.debug("Old screenshot with same name exists, deleting: " + imageNameStr);
//			image.delete();
//		}
//
//		screenshot.renameTo(image);
//		String screenShotLocation = "Screenshot saved to: " + image.getAbsolutePath();
//		logger.debug(screenShotLocation);
//		logger.debug("RP_MESSAGE#FILE#{}#{}", image.getAbsolutePath(), imageName);
//		//Reporter.log(screenShotLocation);
//	}


	public static void takeScreenshot(){
		try {
			File scr = ((TakesScreenshot)getgetDriver() ).getScreenshotAs(OutputType.FILE);
			String filename =  new SimpleDateFormat("yyyyMMddhhmmss'.txt'").format(new Date());
			File dest = new File("filePath/" + filename);
			FileUtils.copyFile(scr, dest);
		} catch (WebDriverException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}