// SupplierListController.java
package org.example.grocerystore.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.grocerystore.model.entity.Supplier;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.example.grocerystore.service.SupplierService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SupplierListController implements Initializable {

    @FXML private TableView<Supplier> tvSupplierList;
    @FXML private TableColumn<Supplier,String> tcId, tcName, tcContact;
    @FXML private Button deleteSupplierButton;

    private final SupplierService supplierService;
    private final FormService formService;
    private final CustomerService customerService;

    public SupplierListController(SupplierService ss, FormService fs, CustomerService cs) {
        this.supplierService = ss;
        this.formService = fs;
        this.customerService = cs;
    }

    @Override
    public void initialize(URL u, ResourceBundle rb) {
        tvSupplierList.setItems(FXCollections.observableArrayList(supplierService.getAllSuppliers()));
        tcId.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getId().toString()));
        tcName.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getName()));
        tcContact.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getContact()));
    }

    @FXML private void editSelectedSupplier() {
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showAlert("Нет прав на редактирование."); return;
        }
        var sel = tvSupplierList.getSelectionModel().getSelectedItem();
        if (sel!=null) formService.loadEditSupplierForm(sel);
    }

    @FXML private void deleteSelectedSupplier() {
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showAlert("Нет прав."); return;
        }
        var sel = tvSupplierList.getSelectionModel().getSelectedItem();
        if (sel!=null) {
            supplierService.deleteSupplier(sel.getId());
            tvSupplierList.setItems(FXCollections.observableArrayList(supplierService.getAllSuppliers()));
        }
    }

    @FXML private void goToMainForm() {
        formService.loadMainForm();
    }

    private void showAlert(String m) {
        new Alert(Alert.AlertType.ERROR, m).showAndWait();
    }
}
