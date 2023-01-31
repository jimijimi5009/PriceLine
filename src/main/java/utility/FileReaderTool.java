/*
 * 
 */
package utility;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileReaderTool {

	public static String readFile(Path filePath) {
		String text = "";

		if (!fileExists(filePath))
			return text;
		try {
			File f = new File(filePath.toString());
			Scanner scanner = new Scanner(f);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				text = text + "\n" + line;
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return text;
	}

	public static boolean fileExists(Path filePath) {
		return Files.exists(filePath);
	}

	public static String readTestFile(String testCaseName) {
		String fileText = "";
		try {
			String jsonFilePath = System.getProperty("user.dir") + "\\src\\test\\java\\com\\ccx\\TestData\\"
					+ testCaseName + ".json";
			JSONObject jsonObject = JsonTool.readJson(jsonFilePath);
			String sourcePath = (String) jsonObject.get("SourcePath");
			String fileName = (String) jsonObject.get("FileName");
			Path sourceFile = Paths.get(sourcePath + "\\" + fileName);
			fileText = FileReaderTool.readFile(sourceFile);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return fileText;
	}

	public static String readTestFile(String appName,String testCaseName) {
		String fileText = "";
		try {
			String jsonFilePath = System.getProperty("user.dir") + "\\src\\test\\java\\com\\ccx\\TestData\\" + appName + "\\" +testCaseName + ".json";
			JSONObject jsonObject = JsonTool.readJson(jsonFilePath);
			String sourcePath = (String) jsonObject.get("SourcePath");
			String fileName = (String) jsonObject.get("FileName");
			Path sourceFile = Paths.get(sourcePath + "\\" + fileName);
			fileText = FileReaderTool.readFile(sourceFile);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return fileText;
	}
	public static List<Path> getFilesInFolder(Path folderPath, long timeTestStarted) {
		// Get all files in directory, sorted by last Modified descending
		File directory = folderPath.toFile();
		File[] files = directory.listFiles();
		Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

		// Only add files matching time criteria
		List<Path> filesWithinTimeframe = new ArrayList<Path>();
		for (File file : files) {
			// If file has been created since we started the test, add it to list
			Long lastModified = file.lastModified();
			if (lastModified >= timeTestStarted) {
				filesWithinTimeframe.add(file.toPath());
			} else {
				break;
			}
		}

		return filesWithinTimeframe;
	}



}
