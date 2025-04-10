package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.service.AppService;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class NewCustomerFormController {

    private final AppService<Customer> customerService;
    private final FormLoader formLoader;

    @FXML private TextField tfName;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPhoneNumber;
    @FXML private TextField tfAddress;

    public NewCustomerFormController(AppService<Customer> customerService, FormLoader formLoader) {
        this.customerService = customerService;
        this.formLoader = formLoader;
    }

    @FXML
    private void saveCustomer() {
        try {
            String name = tfName.getText().trim();
            String email = tfEmail.getText().trim();
            String phoneNumber = tfPhoneNumber.getText().trim();
            String address = tfAddress.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                showAlert("Ошибка", "Имя, email и номер телефона не могут быть пустыми!");
                return;
            }

            Customer customer = new Customer(name, email, phoneNumber, address);
            customerService.create(customer);

            showAlert("Успешно", "Покупатель \"" + name + "\" добавлен!");
            formLoader.loadMainForm();

        } catch (Exception e) {
            showAlert("Ошибка", "Ошибка при добавлении покупателя: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
