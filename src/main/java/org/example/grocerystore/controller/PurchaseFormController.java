// PurchaseFormController.java
package org.example.grocerystore.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.grocerystore.model.entity.Customer;
import org.example.grocerystore.model.entity.Product;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.example.grocerystore.service.ProductService;
import org.example.grocerystore.service.PurchaseService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class PurchaseFormController implements Initializable {

    @FXML private ComboBox<Customer> cbCustomer;
    @FXML private ComboBox<Product> cbProduct;
    @FXML private TextField tfQuantity;
    @FXML private Label lblPurchaseResult;

    private final PurchaseService purchaseService;
    private final FormService formService;
    private final CustomerService customerService;
    private final ProductService productService;

    public PurchaseFormController(PurchaseService ps, FormService fs,
                                  CustomerService cs, ProductService prod) {
        this.purchaseService = ps;
        this.formService = fs;
        this.customerService = cs;
        this.productService = prod;
    }

    @Override
    public void initialize(URL u, ResourceBundle rb) {
        cbCustomer.setItems(FXCollections.observableArrayList(customerService.getAllCustomers()));
        cbProduct.setItems(productService.getAllProducts());
    }

    @FXML private void handlePurchase() {
        try {
            var cust = cbCustomer.getValue();
            var prod = cbProduct.getValue();
            int qty = Integer.parseInt(tfQuantity.getText().trim());
            if (cust==null||prod==null) {
                lblPurchaseResult.setText("Выберите покупателя и продукт!");
                return;
            }
            if (prod.getStock()<qty) {
                lblPurchaseResult.setText("Недостаточно на складе!");
                return;
            }
            String res = purchaseService.buyProduct(cust.getId(), prod.getId(), qty);
            lblPurchaseResult.setText(res);
        } catch (NumberFormatException ex) {
            lblPurchaseResult.setText("Неверный формат числа!");
        }
    }

    @FXML private void goToMainForm() {
        formService.loadMainForm();
    }
}
