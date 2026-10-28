package org.example;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.io.InputStream;

public class UserApp {
    public static ArrayList<User> LoadUsers() {
        ArrayList<User> lst = new ArrayList<>();
        InputStream input  = UserApp.class.getResourceAsStream(("/Users.txt"));
        if(input==null){
            throw new RuntimeException("Users.txt not found");
        }
        Scanner reader;
        try {
            reader = new Scanner(input);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e);
        }

        while(reader.hasNextLine()){
            String data = reader.nextLine();
            String[] parts = data.trim().split("\\s+");
            try{
                User user = new User(parts[0], parts[1]);
                lst.add(user);
            } catch (IllegalArgumentException e){
                continue;
            }
        }
        return lst;
    }

}