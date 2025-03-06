package com.shop.emilxima.controller;

import com.shop.emilxima.service.CustomerService;
import com.shop.emilxima.service.FormService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class CustomerFormController {

    private final CustomerService customerService;
    private final FormService formService;

    @FXML private TextField tfFirstName;
    @FXML private TextField tfLastName;
    @FXML private TextField tfBalance;

    public CustomerFormController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    @FXML
    private void saveCustomer() {
        // Пример простого чтения полей
        String fn = tfFirstName.getText();
        String ln = tfLastName.getText();
        double bal = Double.parseDouble(tfBalance.getText());
        // Вызываем логику из старого сервиса
        customerService.addCustomer(fn, ln, bal);

        // Возврат на главную форму:
        formService.loadMainForm();
    }

    @FXML
    private void cancel() {
        formService.loadMainForm();
    }
}
