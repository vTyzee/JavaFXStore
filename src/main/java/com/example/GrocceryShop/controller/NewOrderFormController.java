package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.interfaces.OrderService;
import com.example.GrocceryShop.interfaces.ProductService;
import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.model.Order;
import com.example.GrocceryShop.model.Product;
import com.example.GrocceryShop.service.AppService; // универсальный сервис для работы с сущностями (например, покупателями)
import com.example.GrocceryShop.tool.FormLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewOrderFormController {

    private final OrderService orderService;
    private final AppService<Customer> customerService;
    private final ProductService productService;
    private final FormLoader formLoader;

    @FXML
    private ComboBox<Customer> cbCustomers;

    @FXML
    private ComboBox<Product> cbProducts;

    @FXML
    private TextField tfQuantity;

    public NewOrderFormController(OrderService orderService,
                                  AppService<Customer> customerService,
                                  ProductService productService,
                                  FormLoader formLoader) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
        this.formLoader = formLoader;
    }

    @FXML
    private void initialize() {
        loadCustomers();
        loadProducts();
    }

    private void loadCustomers() {
        List<Customer> customers = customerService.getAll();
        ObservableList<Customer> customerList = FXCollections.observableArrayList(customers);
        cbCustomers.setItems(customerList);
    }

    private void loadProducts() {
        List<Product> products = productService.getAll();
        ObservableList<Product> productList = FXCollections.observableArrayList(products);
        cbProducts.setItems(productList);
    }

    @FXML
    private void createOrder() {
        Customer selectedCustomer = cbCustomers.getSelectionModel().getSelectedItem();
        Product selectedProduct = cbProducts.getSelectionModel().getSelectedItem();
        String quantityText = tfQuantity.getText().trim();

        if (selectedCustomer == null) {
            showAlert("Ошибка", "Выберите покупателя для заказа!");
            return;
        }
        if (selectedProduct == null) {
            showAlert("Ошибка", "Выберите продукт для заказа!");
            return;
        }
        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                showAlert("Ошибка", "Количество должно быть больше нуля!");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Введите корректное число для количества!");
            return;
        }

        // Попытка создать заказ через OrderService
        boolean success = orderService.createOrder(selectedCustomer, selectedProduct, quantity);
        if (success) {
            showAlert("Успех", "Заказ успешно создан!", Alert.AlertType.INFORMATION);
            // Переключаемся на главную форму (если необходимо)
            formLoader.loadMainForm();
        } else {
            showAlert("Ошибка", "Не удалось создать заказ. Проверьте баланс покупателя и наличие товара.");
        }
    }

    private void showAlert(String title, String message) {
        showAlert(title, message, Alert.AlertType.ERROR);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goBackToMainForm() {
        formLoader.loadMainForm();
    }
}
