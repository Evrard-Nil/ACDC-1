package web_inria.ihm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import web_inria.api.Categories;
import web_inria.api.Post;
import web_inria.api.PropertiesAccess;
import web_inria.api.Tools;

public class FXMLController {
	public TextArea textArea = null;
	public ComboBox<String> layoutComboBox = null;
	public ComboBox<String> categoryComboBox = null;
	public TextField titleField = null;
	public DatePicker datePicker = null;
	public TextField authorField;

	private Boolean saved = false;
	private Stage stage = null;

	private String layout;
	private String title;
	private String date;
	private String author;
	private String categories;

	public void initComboBoxes() {
		List<String> categories = Categories.getCategories();
		categories.forEach(e -> {
			this.categoryComboBox.getItems().add(e);
		});
		this.categoryComboBox.setValue("post");
		File layoutFolder = new File(PropertiesAccess.getInstance().getLocalRepository() + File.separator + "_layouts");
		File[] layouts = layoutFolder.listFiles();
		for (File f : layouts) {
			if (f.isFile()) {
				this.layoutComboBox.getItems().add(f.getName().replaceAll(".html", ""));
			}
		}
		this.layoutComboBox.setValue("post");
	}

	public FXMLController() {

	}

	@FXML
	public void newFilePressed(Event e) {
		if (!saved) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("All changes will be lost !");
			alert.setContentText("Do you want to continue?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				this.textArea.clear();
				this.titleField.clear();
				this.datePicker.setValue(null);
				this.categoryComboBox.setValue("post");
				this.layoutComboBox.setValue("publication");
				this.authorField.clear();
			} else {
				return;
			}
		}
	}

	public void setStage(Stage s) {
		this.stage = s;
	}

	@FXML
	public void openFilePressed(Event e) throws IOException {
		List<String> fileContent = null;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open article");
		File article = fileChooser.showOpenDialog(stage);
		if (article != null) {
			fileContent = Files.readAllLines(article.toPath());
		} else
			return;
		fileContent.remove("---");
		fileContent.remove("---");

		boolean first = true;
		for (String s : fileContent) {
			if (s.startsWith("layout")) {
				this.layout = s.replaceAll("layout:", "").trim();
			}
			if (s.startsWith("title")) {
				this.title = s.substring(s.indexOf("\"")).replace("\"", "");
			}
			if (s.startsWith("date")) {
				this.date = s.replaceAll("date:", "").trim();
			}
			if (s.startsWith("categories")) {
				this.categories = s.replaceAll("categories:", "").trim();
			}
			if (s.startsWith("*") && s.endsWith("*") && first) {
				this.author = s.replaceAll("*", "");
				first = false;
			}
		}
		this.layoutComboBox.setValue(this.layout);
		this.titleField.setText(this.title);
		this.datePicker.setValue(LocalDate.parse(this.date));
		this.categoryComboBox.setValue(this.categories);
		this.authorField.setText(this.author);
		fileContent.removeIf(s -> s.startsWith("layout") || s.startsWith("title") || s.startsWith("date")
				|| s.startsWith("categories") || s.startsWith("*") && s.endsWith("*") && s.contains(categories));
		System.out.println(fileContent);
	}

	/*
	 * Post post = askPostInformationCmd();
	 * 
	 * Categories.addCategory(post.getCategory());
	 * 
	 * String markdown = Markdown.toMarkdown(post);
	 * Markdown.createMarkdownFile(markdown, post);
	 * 
	 * System.out.
	 * println("\nStarting server, please wait. Your browser will be launch automatically."
	 * ); Tools.executeCommand("bundle exec jekyll serve -o", localRepo, true);
	 * 
	 * if ( System.getProperty("os.name").toLowerCase().startsWith("windows"))
	 * Tools.executeCmd("taskkill /F /IM Ruby*", localRepo);
	 * System.out.println("Commiting and pushing git. Wait a moment ...");
	 * Tools.gitCommitAndPush(gitRepo, localRepo);
	 * 
	 * System.out.println("Jobs finished. You can close the program.");
	 * 
	 */

	/**
	 * Method that ask the data for the post
	 * 
	 * @return Post - Object that contains the post data
	 */
	public static Post askPostInformationCmd() {
		String layout = "post";
		System.out.println("Layout: " + layout);

		System.out.print("Title: ");
		String title = Tools.getStringUserInput();

		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		System.out.println("Date: " + date);

		System.out.print("Category: ");
		String category = Tools.getStringUserInput();

		System.out.print("Link to the image(s) (Split by a space if multiple or leave it blank): ");
		String imagesInputString = Tools.getStringUserInput();
		String[] images = imagesInputString.split(" ");
		List<String> imageList = Arrays.asList(images);

		System.out.print("External links (Split by a space if multiple or leave it blank): ");
		String linksInputString = Tools.getStringUserInput();
		String[] links = linksInputString.split(" ");
		List<String> linkList = Arrays.asList(links);

		System.out.print("Author name: ");
		String author = Tools.getStringUserInput();

		System.out.print("Content: ");
		String content = Tools.getStringUserInput();

		return new Post(layout, title, date, category, author, content, linkList, imageList);
	}
}