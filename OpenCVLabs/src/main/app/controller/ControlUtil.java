package main.app.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.io.IOException;

public class ControlUtil {

    public void createNextStage(String fxmlPath, String title, Control relatedObject) throws IOException {
        Stage stage = (Stage) relatedObject.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlPath));
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
