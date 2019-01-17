package web_inria.api;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.scene.control.TextInputDialog;

/**
 * Class Categories that contains the methods relatives to the categories
 * @author Jordan GENEVE
 * @version 1.0
 * @since 1.0
 */
public class Categories {

	public static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	public static File file = new File(PropertiesAccess.getInstance().getLocalRepository() 
			+ File.separator 
			+ "category" 
			+ File.separator 
			+ "categories.txt");

	/**
	 * Method that add a category to the category.txt file
	 * @param category : String - Name of the category
	 */
	public static void addCategory(String category) {
		try {	
			List<String> categoriesList = getCategories();
			if (!categoriesList.contains(category.toLowerCase())) {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath(), true));
				writer.append(category.toLowerCase());
				writer.newLine();
				writer.close();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method that get the categories write in the category.txt file
	 * @return List&lt;String&gt; - List of the categories
	 */
	public static List<String> getCategories() {
		try {
			if (!file.exists()) {
				throw new UnexpectedException("No file Categories.txt found at"+file.getAbsolutePath());
			}
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			List<String> categoryList = new ArrayList<>();
			while ((line = reader.readLine()) != null) {
				categoryList.add(line);
			}
			reader.close();
			return categoryList;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Method that remove an existing category to the category.txt file
	 * @param category : String - Name of the category
	 */
	public static void removeCategory(String category) {
		try {
			List<String> out = Files.lines(file.toPath())
					.filter(line -> !line.contains(category))
					.collect(Collectors.toList());
			Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
