package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.interfaces.OrderService;
import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class ModalProductDetailsFormController {

    @FXML private Label lblProductName;
    @FXML private Label lblCategory;
    @FXML private Label lblPrice;
    @FXML private Label lblAvailableQuantity;
    @FXML private TextField tfQuantityToBuy;
    @FXML private Button btnBuy;
    @FXML private Button btnCancel;

    private Product currentProduct;
    private Customer currentCustomer;
    private OrderService orderService;
    private Runnable onPurchaseSuccess;

    @FXML
    private void initialize() {
        btnBuy.setOnAction(e -> handleBuy());
        btnCancel.setOnAction(e -> closeWindow());
    }

    public void setData(Product product,
                        Customer customer,
                        OrderService orderService,
                        Runnable onPurchaseSuccess) {
        this.currentProduct = product;
        this.currentCustomer = customer;
        this.orderService = orderService;
        this.onPurchaseSuccess = onPurchaseSuccess;
        fillProductDetails();
    }

    private void fillProductDetails() {
        if (currentProduct == null) return;
        lblProductName.setText(currentProduct.getName());
        lblCategory.setText("Категория: " + currentProduct.getCategory());
        lblPrice.setText("Цена: " + currentProduct.getPrice());
        lblAvailableQuantity.setText("В наличии: " + currentProduct.getQuantity());
    }

    private void handleBuy() {
        int quantity;
        try {
            quantity = Integer.parseInt(tfQuantityToBuy.getText());
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Неверный формат количества");
            return;
        }
        if (quantity <= 0) {
            showAlert("Ошибка", "Количество должно быть больше нуля");
            return;
        }
        if (quantity > currentProduct.getQuantity()) {
            showAlert("Ошибка", "Недостаточно товара в наличии");
            return;
        }
        boolean success = orderService.createOrder(currentCustomer, currentProduct, quantity);
        if (success) {
            if (onPurchaseSuccess != null) {
                onPurchaseSuccess.run();
            }
            closeWindow();
        } else {
            showAlert("Ошибка", "Не удалось оформить заказ. Возможно, недостаточно средств или товара.");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(btnCancel.getScene().getWindow());
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
