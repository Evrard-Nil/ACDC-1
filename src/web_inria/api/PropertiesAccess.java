package web_inria.api;

import java.io.File;
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

	public static String LOCAL_REPOSITORY = new File("").getAbsolutePath() +"/";

	public PropertiesAccess() {

	}

	public static PropertiesAccess getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new PropertiesAccess();
		}

		return SINGLETON;
	}

	public String getLocalRepository() {
		return this.LOCAL_REPOSITORY;
	}
}
