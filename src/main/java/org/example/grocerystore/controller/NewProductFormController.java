// NewProductFormController.java
package org.example.grocerystore.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.grocerystore.model.entity.Product;
import org.example.grocerystore.model.entity.Supplier;
import org.example.grocerystore.service.FormService;
import org.example.grocerystore.service.ProductService;
import org.example.grocerystore.service.SupplierService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class NewProductFormController implements Initializable {

    @FXML private TextField tfName, tfPrice, tfQuantity;
    @FXML private ListView<Supplier> lvSuppliers;

    private final FormService formService;
    private final ProductService productService;
    private final SupplierService supplierService;

    public NewProductFormController(FormService fs, ProductService ps, SupplierService ss) {
        this.formService = fs;
        this.productService = ps;
        this.supplierService = ss;
    }

    @Override
    public void initialize(URL u, ResourceBundle rb) {
        lvSuppliers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Supplier> sup = supplierService.getAllSuppliers();
        lvSuppliers.setItems(FXCollections.observableArrayList(sup));
        lvSuppliers.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Supplier s, boolean empty) {
                super.updateItem(s, empty);
                setText(empty||s==null?null:s.getName());
            }
        });
    }

    @FXML
    private void create() {
        Product p = new Product();
        p.setName(tfName.getText());
        p.getSuppliers().addAll(lvSuppliers.getSelectionModel().getSelectedItems());
        try {
            p.setPrice(Double.parseDouble(tfPrice.getText().trim()));
            p.setQuantity(Integer.parseInt(tfQuantity.getText().trim()));
            p.setStock(p.getQuantity());
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Неверный формат числа!").showAndWait();
            return;
        }
        productService.create(p);
        formService.loadMainForm();
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
