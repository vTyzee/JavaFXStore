// EditCustomerFormController.java
package org.example.grocerystore.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.grocerystore.model.entity.Customer;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.springframework.stereotype.Component;

@Component
public class EditCustomerFormController {

    public TextField firstnameField, lastnameField, usernameField, balanceField;

    private final CustomerService customerService;
    private final FormService formService;
    private Customer customer;

    public EditCustomerFormController(CustomerService cs, FormService fs) {
        this.customerService = cs;
        this.formService = fs;
    }

    public void setCustomer(Customer c) {
        this.customer = c;
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showAlert("Нет прав на редактирование!");
            formService.loadCustomerListForm();
            return;
        }
        firstnameField.setText(c.getFirstname());
        lastnameField.setText(c.getLastname());
        usernameField.setText(c.getUsername());
        balanceField.setText(String.valueOf(c.getBalance()));
    }

    public void saveCustomer() {
        customer.setFirstname(firstnameField.getText());
        customer.setLastname(lastnameField.getText());
        customer.setUsername(usernameField.getText());
        try {
            customer.setBalance(Double.parseDouble(balanceField.getText()));
        } catch (NumberFormatException ex) {
            showAlert("Баланс — число!");
            return;
        }
        try {
            customerService.update(customer);
            showInfo("Обновлено!");
            formService.loadCustomerListForm();
        } catch (Exception ex) {
            showAlert("Ошибка: " + ex.getMessage());
        }
    }

    public void cancelEdit() {
        formService.loadCustomerListForm();
    }

    private void showAlert(String m) {
        new Alert(Alert.AlertType.ERROR, m).showAndWait();
    }

    private void showInfo(String m) {
        new Alert(Alert.AlertType.INFORMATION, m).showAndWait();
    }
}
