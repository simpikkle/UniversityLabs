package main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public abstract class BaseController {
    @FXML
    protected TextField expressionField;

    @FXML
    protected Button calcButton;

    @FXML
    protected TextField answerField;

    @FXML
    protected Button executeTwo;

    @FXML
    protected TextArea resultTwo;

    @FXML
    protected Button executeThree;

    @FXML
    protected TextArea resultThree;

    @FXML
    protected Button executeFourth;

    @FXML
    protected TextArea resultFourth;

    @FXML
    protected TextField nThreads;

    @FXML
    protected TextField nRows;

    @FXML
    protected abstract void onCalcAction();

    @FXML
    protected abstract void onExecuteSecond();

    @FXML
    protected abstract void onExecuteThird();

    @FXML
    protected abstract void onExecuteFourth();
}
