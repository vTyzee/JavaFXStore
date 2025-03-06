package com.shop.emilxima;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EmilximaApplication extends Application {

	public static ConfigurableApplicationContext springContext;
	public static Stage primaryStage;

	public static void main(String[] args) {
		// 1) Запускаем Spring Boot
		springContext = SpringApplication.run(EmilximaApplication.class, args);
		// 2) Запускаем JavaFX
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		// Сохраняем stage, чтобы FormService мог менять сцены
		primaryStage = stage;

		// Получаем FormService из Spring
		var formService = springContext.getBean(
				com.shop.emilxima.model.service.FormService.class
		);
		// Открываем главную форму (например, mainForm.fxml)
		formService.loadMainForm();

		primaryStage.show();
	}
}
