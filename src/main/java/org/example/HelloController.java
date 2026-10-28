package org.example;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private  ArrayList<User> Users;
    @FXML
    private TextField emailField;
    @FXML
    private TextField passwordField;

    @FXML
    public void initialize() {
        Users = UserApp.LoadUsers();
    }
    @FXML
    private void onLoginClick() {
        String email = emailField.getText();
        String pass = passwordField.getText();
        int sign=0;
        for(User s : Users){
            if (s.getEmail().equals(email) && s.getPass().equals(pass)){
                sign++;
            }

        }
        if(sign==0){
            errorLabel.setText("Error: Invalid email or password");
        }
        else {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/org.example/welcome.fxml")
                );

                Parent root = loader.load();

                Stage stage = (Stage) emailField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setOnCloseRequest(event -> Platform.exit());
            } catch (IOException e) {
                e.getMessage();
            }

        }
    }


}
