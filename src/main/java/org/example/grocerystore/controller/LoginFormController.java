// LoginFormController.java
package org.example.grocerystore.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.springframework.stereotype.Component;

@Component
public class LoginFormController {
    private final FormService formService;
    private final CustomerService customerService;

    @FXML private Label lbInfo;
    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;

    public LoginFormController(FormService fs, CustomerService cs) {
        this.formService = fs;
        this.customerService = cs;
    }

    @FXML private void login() {
        if (customerService.authenticate(tfUsername.getText(), pfPassword.getText())) {
            formService.loadMainForm();
        } else {
            lbInfo.setText("Неверный логин или пароль");
        }
    }

    @FXML private void showRegistrationForm() {
        formService.loadRegistrationForm();
    }
}
