package com.library.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class CrudController extends BaseController {

    private static State state;

    @FXML
    protected TableView tableView;

    @FXML
    protected Button addNewButton;

    @FXML
    protected Button editButton;

    @FXML
    protected Button deleteButton;

    public static State getState() {
        return state;
    }

    public static void setState(State state) {
        state = state;
    }
}
