// SupplierFormController.java
package org.example.grocerystore.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.grocerystore.model.entity.Supplier;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.example.grocerystore.service.SupplierService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SupplierFormController implements Initializable {

    @FXML private TextField tfName, tfContact;

    private final FormService formService;
    private final SupplierService supplierService;
    private final CustomerService customerService;

    public SupplierFormController(FormService fs, SupplierService ss, CustomerService cs) {
        this.formService = fs;
        this.supplierService = ss;
        this.customerService = cs;
    }

    @Override
    public void initialize(URL u, ResourceBundle rb) {
        if (!customerService.currentUserHasAnyRole(
                CustomerService.ROLES.MANAGER, CustomerService.ROLES.ADMINISTRATOR)) {
            new Alert(Alert.AlertType.ERROR, "Нет прав на добавление поставщика!").showAndWait();
            formService.loadMainForm();
        }
    }

    @FXML private void create() {
        if (tfName.getText().trim().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Название пустое.").showAndWait();
            return;
        }
        Supplier s = new Supplier();
        s.setName(tfName.getText());
        s.setContact(tfContact.getText());
        supplierService.add(s);
        formService.loadMainForm();
    }

    @FXML private void goToMainForm() {
        formService.loadMainForm();
    }
}
