package web_inria.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Class Markdown that contains the methods relatives to the markdown
 * 
 * @author Jordan GENEVE
 * @version 1.0
 * @since 1.0
 */

public class PropertiesAccess {

	private static PropertiesAccess SINGLETON = null;

	public static String LOCAL_REPOSITORY = "localRepository";

	ArrayList<String> result = new ArrayList<>();
	InputStream inputStream;
	FileOutputStream outputStream;

	public PropertiesAccess() {
		result = getPropValues();
	}

	public static PropertiesAccess getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new PropertiesAccess();
		}

		return SINGLETON;
	}

	public String getLocalRepository() {
		return result.get(0);
	}

	public void changeLocalRepository(String value) {
		this.changePropertyValue(PropertiesAccess.LOCAL_REPOSITORY, value);
	}

	public ArrayList<String> getPropValues() {
		String propFileName = "config.properties";
		Properties prop = new Properties();

		try {

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			result.add(prop.getProperty(PropertiesAccess.LOCAL_REPOSITORY));

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				System.out.println("Exception: " + e);
			}
		}
		return result;
	}

	private void changePropertyValue(String key, String value) {
		Properties props = new Properties();

		String propsFileName = "./resources/config.properties";
		try {
			// first load old one:
			FileInputStream configStream = new FileInputStream(propsFileName);
			props.load(configStream);
			configStream.close();

			// modifies existing or adds new property
			props.setProperty(key, value);

			// save modified property file
			FileOutputStream output = new FileOutputStream(propsFileName);
			props.store(output, null);
			output.close();

			result = getPropValues();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
