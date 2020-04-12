import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import java.io.*;

public class JSONReader {

    private String filename;
    public JSONObject json;

    public JSONReader(String filename) {
		this.filename = filename;
		this.json = new JSONObject();
    }

    public JSONObject read() {
    	
    	JSONParser jsonparser = new JSONParser();

    	try (FileReader reader = new FileReader(this.filename)) {

    		Object obj = jsonparser.parse(reader);
			this.json = (JSONObject) obj;

    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (ParseException e) {
    		e.printStackTrace();
		}
		
		return this.getJSON();
    }

    public Boolean inFile(String key, String value) {
    	return this.json.containsKey(key) && this.json.containsValue(value);
    }

    public JSONObject getJSON(){
        return this.json;
    }

}