package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	private static final String FILENAME = "test.properties";
	private static final String PROPERTIES_DIRECTORY_NAME = "properties";
	
	private static Properties prop;
	
	static{
		prop = new Properties();
		try {
			prop.load(new FileInputStream(buildFilePath()));
		} catch (FileNotFoundException e){
			throw new RuntimeException("File '"+buildFilePath()+"' is required");
		} catch (IOException e) {
			throw new RuntimeException("Error in opening the properties file '"+buildFilePath()+"'");
		}
	}
	
	public static String get(String propertyName){
		String property = prop.getProperty(propertyName);
		if(property != null)
			return property;
		else
			throw new RuntimeException("No property '"+propertyName+"' is present in the properties file");
	}

	
	private static String buildFilePath(){
		return  System.getProperty("user.dir")+
				File.separator + 
				PROPERTIES_DIRECTORY_NAME + 
				File.separator + 
				FILENAME;
	}
	
}
