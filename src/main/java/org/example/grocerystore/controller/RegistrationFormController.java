// RegistrationFormController.java
package org.example.grocerystore.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.grocerystore.model.entity.Customer;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.springframework.stereotype.Component;

@Component
public class RegistrationFormController {
    public TextField tfFirstname, tfLastname, tfUsername;
    public PasswordField pfPassword;

    private final CustomerService customerService;
    private final FormService formService;

    public RegistrationFormController(CustomerService cs, FormService fs) {
        this.customerService = cs;
        this.formService = fs;
    }

    @FXML
    private void registration() {
        try {
            if (tfUsername.getText().trim().isEmpty() ||
                    pfPassword.getText().trim().isEmpty() ||
                    tfFirstname.getText().trim().isEmpty() ||
                    tfLastname.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Заполните все поля.").showAndWait();
                return;
            }
            if (customerService.isUsernameTaken(tfUsername.getText().trim())) {
                new Alert(Alert.AlertType.ERROR, "Логин занят.").showAndWait();
                return;
            }
            Customer c = new Customer();
            c.setFirstname(tfFirstname.getText().trim());
            c.setLastname(tfLastname.getText().trim());
            c.setUsername(tfUsername.getText().trim());
            c.setPassword(pfPassword.getText().trim());
            c.setBalance(0.0);
            c.getRoles().add(CustomerService.ROLES.CUSTOMER.toString());
            customerService.add(c);
            formService.loadLoginForm();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Ошибка регистрации: "+e.getMessage()).showAndWait();
        }
    }
}
