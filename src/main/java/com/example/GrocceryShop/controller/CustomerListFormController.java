package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.service.AppService;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerListFormController {

    private final AppService<Customer> customerService;
    private final FormLoader formLoader;

    @FXML private TableView<Customer> tvCustomers;
    @FXML private TableColumn<Customer, Long> tcId;
    @FXML private TableColumn<Customer, String> tcName;
    @FXML private TableColumn<Customer, String> tcEmail;
    @FXML private TableColumn<Customer, String> tcPhoneNumber;
    @FXML private TableColumn<Customer, String> tcAddress;
    @FXML private Button btnEditCustomer;

    public CustomerListFormController(AppService<Customer> customerService, FormLoader formLoader) {
        this.customerService = customerService;
        this.formLoader = formLoader;
    }

    @FXML
    private void initialize() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadCustomers();
    }

    @FXML
    private void loadCustomers() {
        List<Customer> customers = customerService.getAll();
        ObservableList<Customer> observableList = FXCollections.observableArrayList(customers);
        tvCustomers.setItems(observableList);
    }

    @FXML
    private void editCustomer() {
        Customer selectedCustomer = tvCustomers.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            formLoader.loadEditCustomerForm(selectedCustomer);
        } else {
            showAlert("Ошибка", "Выберите покупателя для редактирования!");
        }
    }

    @FXML
    private void goBackToMainForm() {
        formLoader.loadMainForm();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
