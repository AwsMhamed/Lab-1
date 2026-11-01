package org.example;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        int n =Integer.parseInt(args[0]);
        int t =Integer.parseInt(args[1]);

        HelloApplication.n=n;
        HelloApplication.t=t;
        Application.launch(HelloApplication.class, args);
    }
}
