package com.example.GrocceryShop;

import com.example.GrocceryShop.tool.FormLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class GrocceryShopApplication extends Application {

	public static ConfigurableApplicationContext applicationContext;
	public static Stage primaryStage;

	public static void main(String[] args) {
		applicationContext = SpringApplication.run(GrocceryShopApplication.class, args);
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		GrocceryShopApplication.primaryStage = stage;
		FormLoader formService = applicationContext.getBean(FormLoader.class);
		formService.loadLoginForm();
	}

	@Override
	public void stop() {
		applicationContext.close();
	}
}
