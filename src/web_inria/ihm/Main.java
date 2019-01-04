package web_inria.ihm;


import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import web_inria.api.Categories;
import web_inria.api.Markdown;
import web_inria.api.Post;
import web_inria.api.PropertiesAccess;
import web_inria.api.Tools;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Class of the program
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
        loader.setLocation(new URL("file:resources/FXML/menu.fxml"));
        Pane vbox = loader.<Pane>load();
        FXMLController controller =loader.getController();
        controller.setStage(primaryStage);
        
        controller.initComboBoxes();
        
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


	
	public static void main(String[] args) {
		String localRepo = PropertiesAccess.getInstance().getLocalRepository();
		String gitRepo = PropertiesAccess.getInstance().getGitRepo();
		launch();
	}

}
