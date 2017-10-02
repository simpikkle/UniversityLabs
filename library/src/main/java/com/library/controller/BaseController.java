package com.library.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class BaseController {

    private static final Logger logger = LogManager.getLogger(BaseController.class);

    protected static final String MAIN_WINDOW = "windows/main.fxml";
    protected static final String CRUD_WINDOW = "windows/crud.fxml";
    protected static final String ADD_EDIT_WINDOW = "windows/add-edit.fxml";

    void createNextStage(String fxmlPath, String title, Node relatedObject) {
        createNextStage(fxmlPath, title, (Stage) relatedObject.getScene().getWindow());
    }

    void createNextStage(String fxmlPath, String title, Stage stage) {
        try {
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getClassLoader().getResource(fxmlPath));
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            if (fxmlPath.equals(CRUD_WINDOW) || fxmlPath.equals(ADD_EDIT_WINDOW)) {
                CrudController.setAction(null);
                stage.setOnCloseRequest(event -> createNextStage(MAIN_WINDOW, "Main", new Stage()));
            }
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
