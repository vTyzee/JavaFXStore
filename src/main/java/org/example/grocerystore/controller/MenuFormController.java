// MenuFormController.java
package org.example.grocerystore.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuFormController implements Initializable {

    private final FormService formService;
    private final CustomerService customerService;

    @FXML private Menu menuAdministrator, menuProducts, menuSuppliers, menuCustomers;
    @FXML private MenuItem miListProducts, miListSuppliers, miListCustomers;

    public MenuFormController(FormService fs, CustomerService cs) {
        this.formService = fs;
        this.customerService = cs;
    }

    @Override
    public void initialize(URL u, ResourceBundle rb) {
        boolean mgrOrAdm = customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER, CustomerService.ROLES.ADMINISTRATOR);
        menuAdministrator.setVisible(customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR));
        menuProducts.setVisible(mgrOrAdm);
        menuSuppliers.setVisible(mgrOrAdm);
        menuCustomers.setVisible(mgrOrAdm);
    }

    @FXML private void showProductForm() {
        formService.loadNewProductForm();
    }
    @FXML private void showProductList() {
        formService.loadProductListForm();
    }
    @FXML private void showSupplierForm() {
        formService.loadSupplierForm();
    }
    @FXML private void showSupplierList() {
        formService.loadSupplierListForm();
    }
    @FXML private void showNewCustomerForm() {
        formService.loadNewCustomerForm();
    }
    @FXML private void showCustomerListForm() {
        formService.loadCustomerListForm();
    }
    @FXML private void showPurchaseForm() {
        formService.loadPurchaseForm();
    }
    @FXML private void showIncomeForm() {
        formService.loadIncomeForm();
    }
    @FXML private void showRatingForm() {
        formService.loadRatingForm();
    }
    @FXML private void someAdminFunction() { }
    @FXML private void showChangePasswordForm() {
        formService.loadChangePasswordForm();
    }
    @FXML private void logout() {
        customerService.logout();
        formService.loadLoginForm();
    }
    @FXML private void showLoginForm() {
        formService.loadLoginForm();
    }
    @FXML private void showChatForm() {
        formService.loadChatForm();
    }
}
