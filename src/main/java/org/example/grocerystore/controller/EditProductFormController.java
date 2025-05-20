// EditProductFormController.java
package org.example.grocerystore.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.grocerystore.model.entity.Product;
import org.example.grocerystore.service.FormService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class EditProductFormController implements Initializable {

    @FXML
    private TextField tfId;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPrice;
    @FXML
    private TextField tfQuantity;
    @FXML
    private TextField tfStock;

    private final FormService formService;
    private Product product;

    public EditProductFormController(FormService formService) {
        this.formService = formService;
    }

    /** Этот метод вызывается из FormService перед отображением формы */
    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            tfId.setText(product.getId().toString());
            tfName.setText(product.getName());
            tfPrice.setText(String.valueOf(product.getPrice()));
            tfQuantity.setText(String.valueOf(product.getQuantity()));
            tfStock.setText(String.valueOf(product.getStock()));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ID редактировать нельзя
        tfId.setEditable(false);
    }

    /** Сохранить изменения и вернуться к списку продуктов */
    @FXML
    private void onSave() {
        try {
            product.setName(tfName.getText().trim());
            product.setPrice(Double.parseDouble(tfPrice.getText().trim()));
            product.setQuantity(Integer.parseInt(tfQuantity.getText().trim()));
            product.setStock(Integer.parseInt(tfStock.getText().trim()));
            // Сохраняем через сервис, если он у вас есть:
            // productService.update(product);
            formService.loadProductListForm();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Поля цены и количества должны быть числами").showAndWait();
        }
    }

    /** Отмена редактирования */
    @FXML
    private void onCancel() {
        formService.loadProductListForm();
    }
}
