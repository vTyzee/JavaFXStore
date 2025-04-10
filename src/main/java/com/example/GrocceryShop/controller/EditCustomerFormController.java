package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.service.AppService;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class EditCustomerFormController {

    private final AppService<Customer> customerService;
    private final FormLoader formLoader;
    private Customer customer; // Выбранный покупатель

    @FXML private TextField tfName;
    @FXML private TextField tfEmail;
    @FXML private TextField tfPhoneNumber;
    @FXML private TextField tfAddress;

    public EditCustomerFormController(AppService<Customer> customerService, FormLoader formLoader) {
        this.customerService = customerService;
        this.formLoader = formLoader;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        tfName.setText(customer.getName());
        tfEmail.setText(customer.getEmail());
        tfPhoneNumber.setText(customer.getPhoneNumber());
        tfAddress.setText(customer.getAddress());
    }

    @FXML
    private void saveCustomer() {
        try {
            customer.setName(tfName.getText().trim());
            customer.setEmail(tfEmail.getText().trim());
            customer.setPhoneNumber(tfPhoneNumber.getText().trim());
            customer.setAddress(tfAddress.getText().trim());

            customerService.create(customer);

            showAlert("Успешно", "Данные покупателя обновлены!", Alert.AlertType.INFORMATION);

            formLoader.loadCustomerListForm();
        } catch (Exception e) {
            showAlert("Ошибка", "Ошибка при редактировании покупателя: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cancel() {
        formLoader.loadCustomerListForm();
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
