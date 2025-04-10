package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.interfaces.ProductService;
import com.example.GrocceryShop.model.Product;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductListFormController {

    private final ProductService productService;
    private final FormLoader formLoader;

    @FXML private TableView<Product> tvProducts;
    @FXML private TableColumn<Product, Long> tcId;
    @FXML private TableColumn<Product, String> tcName;
    @FXML private TableColumn<Product, String> tcCategory;
    @FXML private TableColumn<Product, Double> tcPrice;
    @FXML private TableColumn<Product, Integer> tcQuantity;

    public ProductListFormController(ProductService productService, FormLoader formLoader) {
        this.productService = productService;
        this.formLoader = formLoader;
    }

    @FXML
    private void initialize() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        loadProducts();
    }

    @FXML
    private void loadProducts() {
        List<Product> products = productService.getAll();
        ObservableList<Product> observableList = FXCollections.observableArrayList(products);
        tvProducts.setItems(observableList);
    }
    @FXML
    private void editSelectedProduct() {
        Product selectedProduct = tvProducts.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            formLoader.loadEditProductForm(selectedProduct);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Выберите товар для редактирования!");
            alert.showAndWait();
        }
    }


    @FXML
    private void goBackToMainForm() {
        formLoader.loadMainForm();
    }
}