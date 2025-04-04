package ee.ivkhkdev.nptv23javafx.tools;

import ee.ivkhkdev.nptv23javafx.Nptv23JavaFxApplication;
import ee.ivkhkdev.nptv23javafx.controller.*;
import ee.ivkhkdev.nptv23javafx.model.entity.Author;
import ee.ivkhkdev.nptv23javafx.model.entity.Book;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FormLoader {
    private final SpringFXMLLoader springFXMLLoader;

    public FormLoader(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    private Stage getPrimaryStage(){
        return Nptv23JavaFxApplication.primaryStage;
    }

    private void handle(WindowEvent event) {
        // Можно показать диалог подтверждения перед закрытием
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы уверены, что хотите закрыть приложение?");
        alert.setContentText("Нажмите ОК для закрытия или Отмена для продолжения.");

        // Если пользователь нажимает "ОК", закрываем окно
        if (alert.showAndWait().get() == ButtonType.OK) {
            Platform.exit(); // Закрываем приложение
        } else {
            event.consume(); // Отменяем событие закрытия (не закрываем окно)
        }
    }

    public void loadLoginForm(){
        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/user/loginForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setResizable(false);//Делаем окно с неизменяемым размером
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Nptv23JavaFX вход пользователя");
        getPrimaryStage().centerOnScreen();
        getPrimaryStage().show();
    }
    public void loadMainForm() {
        loadMainForm("");
    }
    public void loadMainForm(String message) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/main/mainForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
            MainFormController mainFormController = fxmlLoader.getController();
            if(message!=null && !message.isEmpty()){
                mainFormController.setInfoMessage(message);
            }
            mainFormController.initTableView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        getPrimaryStage().setOnCloseRequest(this::handle);
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Nptv23JavaFX Библиотека");
    }
    public void loadNewBookForm(){
        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/book/newBookForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание новой книги");
    }
    public void loadEditBookForm(Book selectedBook) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/book/editBookForm.fxml");
        try {
            Parent editBookFormRoot = fxmlLoader.load();
            EditBookFormController editBookFormController = fxmlLoader.getController();
            editBookFormController.setEditBook(selectedBook);
            Scene scene = new Scene(editBookFormRoot);
            getPrimaryStage().setTitle("Nptv23JavaFX Библиотека - Редактирование книги");
            getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Parent loadMenuForm(){

        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/menu/menuForm.fxml");
        try {
            Parent root = fxmlLoader.load();
            MenuFormController menuFormController = fxmlLoader.getController();
            menuFormController.setSession();
            return root;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    public void loadAuthorForm(){
        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/author/AuthorForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового автора");
    }

    public void loadRegistrationForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/user/registrationForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        getPrimaryStage().setScene(scene);
        getPrimaryStage().setTitle("Создание нового пользователя");
    }

    public void loadSelectedBookFormModality(Book book, boolean readingBook) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/book/selectedBookFormModality.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
            SelectedBookFromModalityController selectedBookFromController = fxmlLoader.getController();
            selectedBookFromController.setBook(book);
            selectedBookFromController.setVizibleButtons(readingBook);
            // Создаем модальное окно
            Stage stage = new Stage();
            stage.setTitle("Информация о книге");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadListAuthorForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/author/listAuthorsForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle("Список авторов");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSelectedAuthorFormModality(Author author) {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/author/selectedAuthorForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
            SelectedAuthorFormController selectedAuthorFormController = fxmlLoader.getController();
            selectedAuthorFormController.setAuthor(author);
            selectedAuthorFormController.initAuthorForm();
            // Создаем модальное окно
            Stage stage = new Stage();
            stage.setTitle("Информация об авторе");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadTakedBookForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/book/listTakedBookForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle("Список выданных книг");
        } catch (IOException e) {
                throw new RuntimeException(e);
        }
    }

    public void loadProfileForm() {
        FXMLLoader fxmlLoader = springFXMLLoader.load("/view/user/profileForm.fxml");
        Parent root;
        try {
            root = fxmlLoader.load();
            Scene scene = new Scene(root);
            getPrimaryStage().setScene(scene);
            getPrimaryStage().setTitle("Профиль пользователя");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
