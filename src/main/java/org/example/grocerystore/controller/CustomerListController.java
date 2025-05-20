// CustomerListController.java
package org.example.grocerystore.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.grocerystore.model.entity.Customer;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class CustomerListController implements Initializable {

    private final CustomerService customerService;
    private final FormService formService;

    @FXML private TableView<Customer> tvCustomerList;
    @FXML private TableColumn<Customer,String> tcId, tcUsername, tcFirstname, tcLastname, tcBalance;
    @FXML private Button editCustomerButton, deleteCustomerButton;

    public CustomerListController(CustomerService cs, FormService fs) {
        this.customerService = cs;
        this.formService = fs;
    }

    @Override
    public void initialize(URL u, ResourceBundle r) {
        List<Customer> list = customerService.getAllCustomers();
        tvCustomerList.setItems(FXCollections.observableArrayList(list));
        tcId.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getId().toString()));
        tcUsername.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getUsername()));
        tcFirstname.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getFirstname()));
        tcLastname.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(cd.getValue().getLastname()));
        tcBalance.setCellValueFactory(cd -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cd.getValue().getBalance())));
    }

    @FXML
    private void editCustomer() {
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showAlert("У вас нет доступа к редактированию покупателей.");
            return;
        }
        var sel = tvCustomerList.getSelectionModel().getSelectedItem();
        if (sel!=null) formService.loadEditCustomerForm(sel);
        else showAlert("Выберите покупателя.");
    }

    @FXML
    private void deleteCustomer() {
        if (!customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            showAlert("У вас нет прав на удаление.");
            return;
        }
        var sel = tvCustomerList.getSelectionModel().getSelectedItem();
        if (sel!=null) {
            customerService.deleteCustomer(sel.getId());
            tvCustomerList.setItems(FXCollections.observableArrayList(customerService.getAllCustomers()));
        } else showAlert("Выберите покупателя.");
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
