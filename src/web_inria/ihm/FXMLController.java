package web_inria.ihm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import web_inria.api.Categories;
import web_inria.api.Markdown;
import web_inria.api.Post;
import web_inria.api.PropertiesAccess;
import web_inria.api.Tools;

/**
 * Controller for FXML file
 * 
 * @author edaillet
 *
 */
public class FXMLController {
	public TextArea textArea;
	public ComboBox<String> layoutComboBox;
	public ComboBox<String> categoryComboBox;
	public TextField titleField;
	public DatePicker datePicker;
	public WebView htmlView;
	public ComboBox<String> headerBox;

	private Boolean saved = false;
	private Stage stage;
	private Parser parser;
	private HtmlRenderer renderer;

	private String[] headers = { "Title", "Subtitle", "Header 3", "Header 4" };
	private String layout;
	private String title;
	private String date;
	private String categories;

	public Post post;

	public FXMLController() {
		this.parser = Parser.builder().build();
		this.renderer = HtmlRenderer.builder().build();
	}

	/**
	 * Dynamically load layouts and categories according to files in blog.
	 */
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
		for (String s : headers) {
			this.headerBox.getItems().add(s);
		}
		this.headerBox.setValue("Header type");

	}

	/**
	 * Adds listener to textArea, refreshing html each time it changes. Set the
	 * style sheet for webview.
	 * 
	 */
	public void initWebViewController() {
		textArea.textProperty().addListener((obs, old, newValue) -> {
			Node document = parser.parse(newValue);
			htmlView.getEngine().loadContent((renderer.render(document)));

		});
		htmlView.getEngine().setUserStyleSheetLocation("file:" + PropertiesAccess.getInstance().getLocalRepository()
				+ File.separator + "css" + File.separator + "style.css");
	}
	/**
	 * Blank out all fields
	 * @param e
	 */
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

			} else {
				return;
			}
		}
	}

	public void setStage(Stage s) {
		this.stage = s;
	}
	/**
	 * Opens a file manager and fill different fields with article data
	 * @param e
	 * @throws IOException
	 */
	@FXML
	public void openFilePressed(Event e) throws IOException {
		
		try {
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

			for (String s : fileContent) {
				if (s.startsWith("layout")) {
					this.layout = s.replaceAll("layout:", "").trim();
				}
				if (s.startsWith("title")) {
					this.title = s.substring(s.indexOf("\"")).replace("\"", "");
				}
				if (s.startsWith("date")) {
					this.date = s.replaceAll("date:", "").trim().substring(0, 10);
				}
				if (s.startsWith("categories")) {
					this.categories = s.replaceAll("categories:", "").trim();
				}
			}
			this.textArea.clear();
			try {
				this.layoutComboBox.setValue(this.layout);
				this.titleField.setText(this.title);
		
				this.datePicker.setValue(LocalDate.parse(this.date));
				this.categoryComboBox.setValue(this.categories);
				fileContent.removeIf(s -> (s.startsWith("layout") || s.startsWith("title") || s.startsWith("date")
						|| s.startsWith("categories")));
			} catch (Exception e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error while reading file");
				alert.setHeaderText("This file might not be a jekyll article");
				alert.setContentText("Consider checking file header.");

				alert.showAndWait();
			}

			for (String s : fileContent) {
				this.textArea.setText(this.textArea.getText().concat(s + "\n"));
			}
		} catch (Exception e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error while opening file");
			alert.setHeaderText("This file might not be a jekyll article");
			alert.setContentText("Consider checking file.");

			alert.showAndWait();

		}
	}

	/**
	 * Adds "**" around the selected text
	 * @param e
	 */
	public void boldButtonPressed(Event e) {
		String old = textArea.getSelectedText();
		String newtext = textArea.getText().replace(old, "**" + old + "**");
		textArea.setText(newtext);
	}

	/**
	 * Set selected text to italic
	 * @param e
	 */
	public void italicButtonPressed(Event e) {
		String old = textArea.getSelectedText();
		String newtext = textArea.getText().replace(old, "*" + old + "*");
		textArea.setText(newtext);

	}

	/**
	 * Set selected text to header according to value chosen
	 * @param e
	 */
	public void headerButtonPressed(Event e) {
		String header = "";
		switch (this.headerBox.getValue()) {
		case "Title":
			header = "# ";
			break;
		case "Subtitle":
			header = "## ";
			break;

		case "Header 3":
			header = "### ";
			break;

		case "Header 4":
			header = "#### ";
			break;

		default:
			break;
		}
		String old = textArea.getSelectedText();
		String newtext = textArea.getText().replace(old, header + old.replace("#", "").trim());
		textArea.setText(newtext);

	}

	/**
	 * Opens window allowing user to add a link in text
	 * @param e
	 */
	public void linkButtonPressed(Event e) {
		int caretPos = textArea.getCaretPosition();
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Link Dialog");
		dialog.setHeaderText("Add a link to the post");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Add", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField desc = new TextField();
		desc.setPromptText("Description");
		TextField link = new TextField();
		link.setPromptText("Link");

		grid.add(new Label("Description:"), 0, 0);
		grid.add(desc, 1, 0);
		grid.add(new Label("Link:"), 0, 1);
		grid.add(link, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		javafx.scene.Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		desc.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> desc.requestFocus());

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(desc.getText(), link.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(linkDesc -> {
			textArea.insertText(caretPos, "[" + linkDesc.getKey() + "](" + linkDesc.getValue() + ")");
		});
	}

	/**
	 * Opens file chooser in order to add an image to text
	 * @param e
	 * @throws IOException
	 */
	public void onAddPicturePressed(Event e) throws IOException {
		int caretPos = textArea.getCaretPosition();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open image");
		File image = fileChooser.showOpenDialog(stage);
		String newImage = PropertiesAccess.getInstance().getLocalRepository() + File.separator + "images-blog"
				+ File.separator + image.getName();
		Files.copy(image.toPath(), new File(newImage).toPath());
		textArea.insertText(caretPos, "<img src=\"file://" + newImage + "\" alt=\"drawing\" width=\"200px\"/>");
	}

	/**
	 * Save article and launch jekyll
	 * @param e
	 */
	public void previewButtonPressed(Event e) {
		onSaveButtonPressed(null);
		Tools.executeCommand("bundle exec jekyll serve -o", PropertiesAccess.getInstance().getLocalRepository(), true);
	}

	/**
	 *Save article
	 * @param e
	 */
	public void onSaveButtonPressed(Event e) {
		post = new Post(layoutComboBox.getValue(), titleField.getText(), datePicker.getValue().toString(),
				categoryComboBox.getValue(), textArea.getText(), null, null);
		Categories.addCategory(post.getCategory());
		String md = Markdown.toMarkdown(post);
		Markdown.createMarkdownFile(md, post);
	}

	/**
	 * Commits and push
	 * @param e
	 */
	public void onPushButtonPressed(Event e) {
		Tools.gitCommitAndPush(PropertiesAccess.getInstance().getLocalRepository());
	}

	/**
	 * Quits app. 
	 */
	public void onQuitButtonPressed(Event e) {
		System.exit(0);
	}

	/**
	 * Kills jekyll if needed
	 * @param e
	 */
	public void onKillJekyllButtonPressed(Event e) {
		if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
			Tools.executeCommand("TASKKILL -F -IM ruby.exe", PropertiesAccess.getInstance().getLocalRepository(), false);
		} else {
			Tools.process.destroyForcibly();
		}
	}
	

	/**
	 * Delete markdown article
	 * @param e
	 */
	public void onDeleteButtonPressed(Event e) {
		if (this.post != null) {
			Markdown.deleteMarkdownFile(this.post);
		}
		this.newFilePressed(e);

	}

}