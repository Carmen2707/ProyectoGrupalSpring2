package com.example.proyectogrupalspring2.util;

import java.io.IOException;

public class Utility {
    public static void launchWeb(){
        String system = System.getProperty("system.name").toLowerCase();
        try{
            if (system.contains("win")){
                Runtime.getRuntime().exec("cmd /c start chrome http://localhost:8080/login");
            }else{
                Runtime.getRuntime().exec("cmd -a start Safari http://localhost:8080/login");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
