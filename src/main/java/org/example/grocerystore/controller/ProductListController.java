// ProductListController.java
package org.example.grocerystore.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.grocerystore.model.entity.Product;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.example.grocerystore.service.ProductService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ProductListController implements Initializable {

    private final ProductService productService;
    private final FormService formService;
    private final CustomerService customerService;

    @FXML private TableView<Product> tvProduct;
    @FXML private TableColumn<Product,String> tcId, tcName, tcPrice, tcQuantity, tcStock;
    @FXML private Button deleteProductButton;

    public ProductListController(ProductService ps, FormService fs, CustomerService cs) {
        this.productService = ps;
        this.formService = fs;
        this.customerService = cs;
    }

    @Override
    public void initialize(URL u, ResourceBundle rb) {
        tcId.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getId().toString()));
        tcName.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getName()));
        tcPrice.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cd.getValue().getPrice())));
        tcQuantity.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cd.getValue().getQuantity())));
        tcStock.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cd.getValue().getStock())));

        List<Product> list = productService.getAllProducts();
        tvProduct.setItems(FXCollections.observableArrayList(list));
    }

    @FXML private void editSelectedProduct() {
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showAlert("Нет прав на редактирование.");
            return;
        }
        var sel = tvProduct.getSelectionModel().getSelectedItem();
        if (sel!=null) formService.loadEditProductForm(sel);
        else System.out.println("Не выбрано.");
    }

    @FXML private void deleteSelectedProduct() {
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showAlert("Нет прав.");
            return;
        }
        var sel = tvProduct.getSelectionModel().getSelectedItem();
        if (sel!=null) {
            productService.delete(sel.getId());
            tvProduct.setItems(FXCollections.observableArrayList(productService.getAllProducts()));
        } else showAlert("Выберите продукт.");
    }

    @FXML private void goToMainForm() {
        formService.loadMainForm();
    }

    private void showAlert(String m) {
        new Alert(Alert.AlertType.ERROR, m).showAndWait();
    }
}
