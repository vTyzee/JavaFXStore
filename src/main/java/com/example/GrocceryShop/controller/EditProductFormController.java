package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.interfaces.ProductService;
import com.example.GrocceryShop.model.Product;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class EditProductFormController {

    private final ProductService productService;
    private final FormLoader formLoader;
    private Product selectedProduct;
    private File selectedImageFile;

    @FXML private TextField tfName;
    @FXML private TextField tfCategory;
    @FXML private TextField tfPrice;
    @FXML private TextField tfQuantity;
    @FXML private ImageView productImageView;

    public EditProductFormController(ProductService productService, FormLoader formLoader) {
        this.productService = productService;
        this.formLoader = formLoader;
    }

    public void setProduct(Product product) {
        this.selectedProduct = product;

        tfName.setText(product.getName());
        tfCategory.setText(product.getCategory());
        tfPrice.setText(String.valueOf(product.getPrice()));
        tfQuantity.setText(String.valueOf(product.getQuantity()));

        try {
            Image image;
            if (product.getImagePath() != null && !product.getImagePath().equals("default") &&
                    new File(product.getImagePath()).exists()) {
                image = new Image(new File(product.getImagePath()).toURI().toString());
            } else {
                image = new Image(getClass().getResource("/img/no-img.jpg").toExternalForm());
            }
            productImageView.setImage(image);
        } catch (Exception e) {
            productImageView.setImage(new Image(getClass().getResource("/img/no-img.jpg").toExternalForm()));
        }
    }

    @FXML
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите новое изображение");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            selectedImageFile = file;
            productImageView.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void saveProduct() {
        try {
            selectedProduct.setName(tfName.getText().trim());
            selectedProduct.setCategory(tfCategory.getText().trim());
            selectedProduct.setPrice(Double.parseDouble(tfPrice.getText().trim()));
            selectedProduct.setQuantity(Integer.parseInt(tfQuantity.getText().trim()));

            if (selectedImageFile != null) {
                selectedProduct.setImagePath(selectedImageFile.getAbsolutePath());
            }

            productService.updateProduct(selectedProduct.getId(), selectedProduct);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успех");
            alert.setHeaderText("Товар обновлён");
            alert.setContentText("Товар \"" + selectedProduct.getName() + "\" успешно обновлён.");
            alert.showAndWait();

            formLoader.loadProductListForm();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка при редактировании товара");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel() {
        formLoader.loadProductListForm();
    }
}
