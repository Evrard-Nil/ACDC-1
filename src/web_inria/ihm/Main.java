package web_inria.ihm;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Class of the program
 * 
 * @author Jordan GENEVE
 * @version 1.0
 * @since 1.0
 * 
 */

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Jekyll article editor");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/FXML/menu.fxml"));
		Pane vbox = loader.<Pane>load();
		FXMLController controller = loader.getController();
		controller.setStage(primaryStage);

		controller.initComboBoxes();
		controller.initWebViewController();

		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("file:fxml/png/jekyll_Icon.png"));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}
