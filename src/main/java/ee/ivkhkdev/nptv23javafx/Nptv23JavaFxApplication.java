package ee.ivkhkdev.nptv23javafx;

import ee.ivkhkdev.nptv23javafx.model.entity.AppUser;
import ee.ivkhkdev.nptv23javafx.tools.FormLoader;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Nptv23JavaFxApplication extends Application {
    private static ApplicationContext applicationContext;
    public static Stage primaryStage;
    //public static AppUser currentUser;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Nptv23JavaFxApplication.primaryStage = primaryStage;
        FormLoader formLoader = SpringApplication.run(Nptv23JavaFxApplication.class).getBean(FormLoader.class);
        formLoader.loadLoginForm();
    }
}
