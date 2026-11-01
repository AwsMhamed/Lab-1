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

    private int n;
    private int t;
    User temp;
    boolean blocked;

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
        n=HelloApplication.n;
        t=HelloApplication.t;
    }

    @FXML
    private void onLoginClick() {

        String email = emailField.getText();
        String pass = passwordField.getText();

        User temp = null;

        for (User s : Users) {
            if (s.getEmail().equals(email)) {
                temp = s;
                break;
            }
        }

        if (temp == null) {
            errorLabel.setText("Error: Invalid email or password");
            return;
        }

        synchronized (temp) {
            blocked = System.currentTimeMillis() < temp.getBlockedtime();
        }
        if(blocked){
            errorLabel.setText("You are blocked for "+t+" Seconds after "+n+ " wrong attempts");
            return;
        }

        if (!temp.getPass().equals(pass)) {
            errorLabel.setText("Error: Invalid email or password");
            User user = temp;

            Thread t1 = new Thread(() -> {

                synchronized (user) {

                    user.setTry_Num(user.getTry_Num() + 1);

                    if (user.getTry_Num() >= n) {
                        user.setTry_Num(0);
                        user.setBlockedtime(
                                System.currentTimeMillis() + t * 1000L
                        );
                        Platform.runLater(()->{
                            errorLabel.setText("You are blocked for "+t+" Seconds after "+n+ " wrong attempts");
                        });
                    }
                }

            });

            t1.start();
            return;
        }


        User user = temp;

        Thread t2 = new Thread(() -> {

            synchronized (user) {
                blocked = System.currentTimeMillis() < user.getBlockedtime();
            }

            Platform.runLater(() -> {

                if (blocked) {
                    errorLabel.setText("You are blocked for "+t+" Seconds after "+n+ " wrong attempts");
                } else {
                    openWelcomeScreen();
                }

            });

        });
        t2.start();
    }

    @FXML
    private void openWelcomeScreen() {

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
            e.printStackTrace();
        }
    }
}
