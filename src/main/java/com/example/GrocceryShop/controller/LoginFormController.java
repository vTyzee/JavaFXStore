package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.interfaces.AppUserService;
import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginFormController {

    private final FormLoader formLoader;
    private final AppUserService appUserService;

    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;

    public LoginFormController(FormLoader formLoader, AppUserService appUserService) {
        this.formLoader = formLoader;
        this.appUserService = appUserService;
    }
    @FXML
    private void showRegistrationForm() {
        formLoader.loadRegistrationForm();
    }


    @FXML
    private void login() {
        String username = tfUsername.getText().trim();
        String password = pfPassword.getText().trim();

        Optional<AppUser> optionalUser = appUserService.findByUsername(username);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                appUserService.setCurrentUser(user);

                boolean isAdmin = user.getRoles().contains(AppUserService.ROLES.ADMINISTRATOR.name());
                if (isAdmin) {
                    formLoader.loadMainForm();
                } else {
                    formLoader.loadUserMainForm();
                }
            } else {
                showErrorAlert("Неверное имя пользователя или пароль!");
            }
        } else {
            showErrorAlert("Неверное имя пользователя или пароль!");
        }
    }


    private void showErrorAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
