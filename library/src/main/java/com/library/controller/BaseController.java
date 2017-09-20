package com.library.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class BaseController {

    private static final Logger logger = LogManager.getLogger(BaseController.class);

    protected static final String MAIN_WINDOW = "windows/main.fxml";

    void createNextStage(String fxmlPath, String title, Control relatedObject) {
        try {
            Stage stage = (Stage) relatedObject.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource(fxmlPath));
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
