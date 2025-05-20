// MainFormController.java
package org.example.grocerystore.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.grocerystore.model.entity.Product;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.example.grocerystore.service.ProductService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class MainFormController implements Initializable {

    private final FormService formService;
    private final ProductService productService;
    private final CustomerService customerService;

    @FXML private VBox vbMainFormRoot;
    @FXML private TableView<Product> tvProductList;
    @FXML private TableColumn<Product,String> tcId, tcName, tcSuppliers, tcPrice, tcQuantity, tcStock;
    @FXML private HBox hbEditProduct;

    public MainFormController(FormService fs, ProductService ps, CustomerService cs) {
        this.formService = fs;
        this.productService = ps;
        this.customerService = cs;
    }

    @Override
    public void initialize(URL u, ResourceBundle rb) {
        vbMainFormRoot.getChildren().add(0, formService.loadMenuForm());
        tvProductList.setItems(productService.getAllProducts());
        tcId.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getId().toString()));
        tcName.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getName()));
        tcSuppliers.setCellValueFactory(cd -> {
            var p = cd.getValue();
            var s = p.getSuppliers().stream().map(sup->sup.getName()).collect(Collectors.joining(", "));
            return new SimpleStringProperty(s);
        });
        tcPrice.setCellValueFactory(cd -> new SimpleStringProperty(String.valueOf(cd.getValue().getPrice())));
        tcQuantity.setCellValueFactory(cd -> new SimpleStringProperty(String.valueOf(cd.getValue().getQuantity())));
        tcStock.setCellValueFactory(cd -> new SimpleStringProperty(String.valueOf(cd.getValue().getStock())));
        tvProductList.getSelectionModel().selectedItemProperty().addListener(
                (ov, o, n) -> hbEditProduct.setVisible(n!=null)
        );
        tvProductList.setOnMouseClicked(e -> {
            if (e.getClickCount()==2 && tvProductList.getSelectionModel().getSelectedItem()!=null) {
                formService.loadSelectedProductForm(tvProductList.getSelectionModel().getSelectedItem());
            }
        });
    }

    @FXML private void showEditProductForm() { /* аналогично ProductListController */ }

    @FXML private void showSelectedProductForm() {
        var sel = tvProductList.getSelectionModel().getSelectedItem();
        if (sel!=null) formService.loadSelectedProductForm(sel);
    }

    @FXML private void deleteSelectedProduct() {
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            new Alert(Alert.AlertType.ERROR, "Нет прав.").showAndWait(); return;
        }
        var sel = tvProductList.getSelectionModel().getSelectedItem();
        if (sel!=null) {
            productService.delete(sel.getId());
            tvProductList.setItems(productService.getAllProducts());
        }
    }
}
