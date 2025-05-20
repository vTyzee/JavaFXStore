// src/main/java/org/example/grocerystore/Main.java
package org.example.grocerystore;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.grocerystore.service.FormService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {
    public static ConfigurableApplicationContext applicationContext;
    public static Stage primaryStage;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Main.class, args);
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        FormService formService = applicationContext.getBean(FormService.class);
        formService.loadLoginForm();
    }
}
