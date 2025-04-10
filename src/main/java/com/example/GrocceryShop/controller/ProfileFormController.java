package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.service.AppUserServiceImpl;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class ProfileFormController {

    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;
    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;

    private final AppUserServiceImpl userService;
    private final FormLoader formLoader;

    private AppUser currentUser;

    public ProfileFormController(AppUserServiceImpl userService, FormLoader formLoader) {
        this.userService = userService;
        this.formLoader = formLoader;
    }


    public void setUser(AppUser user) {
        this.currentUser = user;
        tfUsername.setText(user.getUsername());
        pfPassword.setText(user.getPassword());
        tfFirstName.setText(user.getFirstname());
        tfLastName.setText(user.getLastname());
    }

    @FXML
    private void saveProfile() {
        try {
            currentUser.setUsername(tfUsername.getText().trim());
            currentUser.setPassword(pfPassword.getText().trim());
            currentUser.setFirstname(tfFirstName.getText().trim());
            currentUser.setLastname(tfLastName.getText().trim());

            userService.saveOrUpdate(currentUser);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Профиль");
            alert.setHeaderText(null);
            alert.setContentText("Данные профиля успешно обновлены!");
            alert.showAndWait();

            Stage stage = (Stage) tfUsername.getScene().getWindow();
            stage.close();


        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Не удалось обновить профиль");
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
