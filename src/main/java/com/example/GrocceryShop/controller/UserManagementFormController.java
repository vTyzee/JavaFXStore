package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.interfaces.AppUserService;
import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserManagementFormController {

    @FXML private TableView<AppUser> tvUsers;
    @FXML private TableColumn<AppUser, Long> colId;
    @FXML private TableColumn<AppUser, String> colUsername;
    @FXML private TableColumn<AppUser, String> colFirstName;
    @FXML private TableColumn<AppUser, String> colLastName;

    private final AppUserService appUserService;
    private final FormLoader formLoader;

    public UserManagementFormController(AppUserService appUserService, FormLoader formLoader) {
        this.appUserService = appUserService;
        this.formLoader = formLoader;
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        loadUsers();
    }

    private void loadUsers() {
        List<AppUser> users = appUserService.getAll();
        tvUsers.setItems(FXCollections.observableArrayList(users));
    }

    @FXML
    private void editSelectedUser() {
        AppUser selectedUser = tvUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            formLoader.loadEditUserForm(selectedUser);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Выбор пользователя");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберите пользователя для редактирования.");
            alert.showAndWait();
        }
    }

    @FXML
    private void close() {
        Stage stage = (Stage) tvUsers.getScene().getWindow();
        stage.close();
    }
}
