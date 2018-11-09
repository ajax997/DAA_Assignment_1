package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception{

        primaryStage.resizableProperty().setValue(Boolean.FALSE);
        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        primaryStage.setTitle("Testing Scheduler");
        primaryStage.setOpacity(1);
        primaryStage.setScene(new Scene(root, 971, 714));
        primaryStage.show();
        showInfoPopup();
    }
    public void showInfoPopup() throws Exception
    {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Readme");
        dialog.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("dialog.fxml"));
        dialog.setScene(new Scene(root,600,400));
        dialog.setOpacity(0.86);
        dialog.show();
    }

}
