package com.library.controller;

import com.library.exception.AuthenticationException;
import com.library.exception.DatabaseException;
import com.library.security.Authentication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    protected Label exceptionLabel;

    @FXML
    protected TextField usernameInput;

    @FXML
    protected TextField passwordInput;

    @FXML
    protected Button loginButton;

    public void runAuthentication() {
        try {
            new Authentication().runAuthentication(usernameInput.getText(), passwordInput.getText());
        } catch (DatabaseException | AuthenticationException e) {
            exceptionLabel.setText(e.getMessage());
            exceptionLabel.setVisible(true);
        }
    }

    public TextField getUsernameInput() {
        return usernameInput;
    }

    public TextField getPasswordInput() {
        return passwordInput;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Label getExceptionLabel() {
        return exceptionLabel;
    }
}
