package com.shop.emilxima.model.controller;

import com.shop.emilxima.model.service.FormService;
import com.shop.emilxima.model.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class ProductFormController {

    private final ProductService productService;
    private final FormService formService;

    @FXML private TextField tfName;
    @FXML private TextField tfPrice;
    @FXML private TextField tfQuantity;

    public ProductFormController(ProductService productService,
                                 FormService formService) {
        this.productService = productService;
        this.formService = formService;
    }

    @FXML
    private void saveProduct() {
        String name = tfName.getText();
        double price = Double.parseDouble(tfPrice.getText());
        int quantity = Integer.parseInt(tfQuantity.getText());
        productService.addProduct(name, price, quantity);

        formService.loadMainForm();
    }

    @FXML
    private void cancel() {
        formService.loadMainForm();
    }
}
