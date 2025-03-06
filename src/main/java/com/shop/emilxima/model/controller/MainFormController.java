package com.shop.emilxima.model.controller;

import com.shop.emilxima.service.FormService;
import com.shop.emilxima.service.CustomerService;
import com.shop.emilxima.service.ProductService;
import javafx.fxml.FXML;
import org.springframework.stereotype.Component;

@Component
public class MainFormController {

    private final FormService formService;
    private final CustomerService customerService;
    private final ProductService productService;

    public MainFormController(FormService formService,
                              CustomerService customerService,
                              ProductService productService) {
        this.formService = formService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @FXML
    private void openCustomers() {
        // Переходим на форму редактирования/создания покупателя
        formService.loadCustomerForm();
    }

    @FXML
    private void openProducts() {
        // formService.loadProductForm();
    }

    @FXML
    private void exitApp(){
        System.exit(0);
    }
}
