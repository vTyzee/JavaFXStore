package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.interfaces.AppUserService;
import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class EditUserFormController {

    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;

    private final AppUserService appUserService;
    private final FormLoader formLoader;

    private AppUser editingUser;

    public EditUserFormController(AppUserService appUserService, FormLoader formLoader) {
        this.appUserService = appUserService;
        this.formLoader = formLoader;
    }

    public void setUser(AppUser user) {
        this.editingUser = user;
        tfUsername.setText(user.getUsername());
        pfPassword.setText(user.getPassword());
        tfFirstName.setText(user.getFirstname());
        tfLastName.setText(user.getLastname());
    }

    @FXML
    private void saveUser() {
        try {
            editingUser.setUsername(tfUsername.getText().trim());
            editingUser.setPassword(pfPassword.getText().trim());
            editingUser.setFirstname(tfFirstName.getText().trim());
            editingUser.setLastname(tfLastName.getText().trim());

            appUserService.updateUser(editingUser);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Редактирование пользователя");
            alert.setHeaderText(null);
            alert.setContentText("Пользователь успешно обновлён!");
            alert.showAndWait();

            // Закрываем окно редактирования
            Stage stage = (Stage) tfUsername.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось обновить пользователя");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) tfUsername.getScene().getWindow();
        stage.close();
    }
}
