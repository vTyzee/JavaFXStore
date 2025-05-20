// NewCustomerFormController.java
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
public class NewCustomerFormController {

    public TextField tfFirstname, tfLastname, tfUsername, tfBalance;
    public PasswordField pfPassword;

    private final CustomerService customerService;
    private final FormService formService;

    public NewCustomerFormController(CustomerService cs, FormService fs) {
        this.customerService = cs;
        this.formService = fs;
    }

    @FXML
    private void createCustomer() {
        try {
            if (tfFirstname.getText().trim().isEmpty() ||
                    tfLastname.getText().trim().isEmpty() ||
                    tfUsername.getText().trim().isEmpty() ||
                    pfPassword.getText().trim().isEmpty() ||
                    tfBalance.getText().trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Заполните все поля!").showAndWait();
                return;
            }
            Customer c = new Customer();
            c.setFirstname(tfFirstname.getText().trim());
            c.setLastname(tfLastname.getText().trim());
            c.setUsername(tfUsername.getText().trim());
            c.setPassword(pfPassword.getText().trim());
            c.setBalance(Double.parseDouble(tfBalance.getText().trim()));
            c.getRoles().add(CustomerService.ROLES.CUSTOMER.toString());
            customerService.add(c);
            formService.loadLoginForm();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Ошибка: "+ex.getMessage()).showAndWait();
        }
    }

    @FXML
    private void cancel() {
        formService.loadMainForm();
    }
}
