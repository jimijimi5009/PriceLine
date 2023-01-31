/**
 * 
 */
package utility;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class JsonTool {

	public static JSONObject readJson(String filePath) throws IOException, ParseException {

		JSONParser jsonParser = new JSONParser();
		FileReader fileReader = new FileReader(filePath);
		Object object = jsonParser.parse(fileReader);
		JSONArray jsonArray = (JSONArray) object;

		String jsonText = jsonArray.toString().replaceAll("^[\\[]","").replaceAll("]$", "");

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(jsonText);

		return jsonObject;
	}

	public static String readJson(String jsonFilePath,String get){
		String name = null;
		try {

			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(jsonFilePath));
			JSONObject jsonObject =  (JSONObject) obj;
			name = (String) jsonObject.get(get);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return name;
	}

	public static void writeSingleJson(String jsonFilePath,String key, String value){

		JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);
        try {
            FileWriter file = new FileWriter(jsonFilePath);
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
