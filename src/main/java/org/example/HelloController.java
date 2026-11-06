package org.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class HelloController {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button loginB;

    @FXML
    private void onLoginClick() {
        String email = emailField.getText();
        String pass = passwordField.getText();

        emailField.setDisable(true);
        passwordField.setDisable(true);
        loginB.setDisable(true);

        LoginTask task =new LoginTask(email,pass,HelloApplication.tracker,errorLabel,this);
        Thread t = new Thread(task);
        t.start();


    }

    public void enableLogin() {
        emailField.setDisable(false);
        passwordField.setDisable(false);
        loginB.setDisable(false);
    }

}
