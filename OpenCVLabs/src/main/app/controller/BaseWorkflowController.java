package main.app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BaseWorkflowController {
    @FXML
    protected Button getStarted;

    @FXML
    protected Pane workBlock;

    @FXML
    protected Pane startBlock;

    @FXML
    protected Pane faceBlock;

    @FXML
    protected ImageView cameraView;

    @FXML
    protected ImageView histogram;

//    @FXML
//    protected Slider sliderOne;
//
//    @FXML
//    protected Slider sliderTwo;

    @FXML
    protected CheckBox grayCheckBox;

    @FXML
    protected CheckBox logoCheckBox;

    @FXML
    protected CheckBox histogramCheckBox;

    @FXML
    protected CheckBox faceCheckBox;

    @FXML
    protected Label nPeople;

    @FXML
    protected Label dPeople;

    @FXML
    public Button takeScreenshot;
}
