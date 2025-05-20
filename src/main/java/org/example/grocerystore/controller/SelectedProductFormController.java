// SelectedProductFormController.java
package org.example.grocerystore.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.example.grocerystore.model.entity.Product;
import org.example.grocerystore.service.FormService;
import org.springframework.stereotype.Component;

@Component
public class SelectedProductFormController {

    public Label nameLabel, suppliersLabel, priceLabel, quantityLabel, stockLabel;
    public Button buyProductButton;

    private Product selectedProduct;
    private final FormService formService;

    public SelectedProductFormController(FormService fs) {
        this.formService = fs;
    }

    public void setProduct(Product p) {
        this.selectedProduct = p;
        nameLabel.setText(p.getName());
        suppliersLabel.setText(
                p.getSuppliers().stream().map(s->s.getName()).reduce((a,b)->a+", "+b).orElse(""));
        priceLabel.setText(String.valueOf(p.getPrice()));
        quantityLabel.setText(String.valueOf(p.getQuantity()));
        stockLabel.setText(String.valueOf(p.getStock()));
    }
}
