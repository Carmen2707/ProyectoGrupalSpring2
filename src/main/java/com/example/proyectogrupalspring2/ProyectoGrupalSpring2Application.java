package com.example.proyectogrupalspring2;

import com.example.proyectogrupalspring2.util.Utility;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class ProyectoGrupalSpring2Application {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoGrupalSpring2Application.class, args);
     //   Utility.launchWeb();
    }

}
