package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.model.Product;
import com.example.GrocceryShop.tool.FormLoader;
import com.example.GrocceryShop.service.AppService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class NewProductFormController {

    private final AppService<Product> productService;
    private final FormLoader formLoader;

    @FXML private TextField tfName;
    @FXML private TextField tfCategory;
    @FXML private TextField tfPrice;
    @FXML private TextField tfQuantity;
    @FXML private ImageView productImageView;

    private File selectedImageFile;

    public NewProductFormController(AppService<Product> productService, FormLoader formLoader) {
        this.productService = productService;
        this.formLoader = formLoader;
    }

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            Image image = new Image(file.toURI().toString());
            productImageView.setImage(image);
        }
    }

    @FXML
    private void saveProduct() {
        try {
            String name = tfName.getText().trim();
            String category = tfCategory.getText().trim();
            double price = Double.parseDouble(tfPrice.getText().trim());
            int quantity = Integer.parseInt(tfQuantity.getText().trim());

            if (name.isEmpty() || category.isEmpty()) {
                throw new IllegalArgumentException("Название и категория не могут быть пустыми.");
            }

            Product product = new Product(name, category, price, quantity);
            if (selectedImageFile != null) {
                product.setImagePath(selectedImageFile.getAbsolutePath());
            } else {
                product.setImagePath("default"); // используем "default" как флаг
            }

            productService.create(product);

            showSuccessAlert("Товар добавлен", "Товар \"" + name + "\" успешно добавлен.");
            formLoader.loadMainForm();

        } catch (NumberFormatException e) {
            showErrorAlert("Ошибка", "Цена и количество должны быть числами.");
        } catch (Exception e) {
            showErrorAlert("Ошибка", "Ошибка при добавлении товара: " + e.getMessage());
        }
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
