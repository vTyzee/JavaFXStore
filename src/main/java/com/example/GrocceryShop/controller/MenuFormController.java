package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.service.AppUserServiceImpl;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MenuFormController implements Initializable {

    private final FormLoader formLoader;
    private final AppUserServiceImpl appUserServiceImpl;

    @FXML private Menu mProducts;
    @FXML private Menu mOrders;
    @FXML private Menu mAdmin;
    @FXML private Menu mCustomers;

    @FXML private MenuItem miAddProduct;
    @FXML private MenuItem miProductList;
    @FXML private MenuItem miAddOrder;
    @FXML private MenuItem miAddCustomer;

    public MenuFormController(FormLoader formLoader, AppUserServiceImpl appUserServiceImpl) {
        this.formLoader = formLoader;
        this.appUserServiceImpl = appUserServiceImpl;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureMenuByRole();
    }

    private void configureMenuByRole() {
        AppUser currentUser = appUserServiceImpl.getCurrentUser();

        // Если пользователь не авторизован — скрываем всё, кроме, возможно, базовых пунктов
        if (currentUser == null) {
            mAdmin.setVisible(false);
            mCustomers.setVisible(false);
            miAddCustomer.setVisible(false);
            miAddOrder.setVisible(false);
            return;
        }


        boolean isAdmin = currentUser.getRoles().contains(AppUserServiceImpl.ROLES.ADMINISTRATOR.name());
        boolean isManager = currentUser.getRoles().contains(AppUserServiceImpl.ROLES.MANAGER.name());

        mAdmin.setVisible(isAdmin);

        mCustomers.setVisible(isAdmin || isManager);
        miAddCustomer.setVisible(isAdmin || isManager);
        miAddOrder.setVisible(isAdmin || isManager);


    }

    @FXML
    private void showNewProductForm() {
        formLoader.loadNewProductForm();
    }

    @FXML
    private void showProductListForm() {
        formLoader.loadProductListForm();
    }

    @FXML
    private void showNewOrderForm() {
        formLoader.loadNewOrderForm();
    }

    @FXML
    private void showNewCustomerForm() {
        formLoader.loadNewCustomerForm();
    }

    @FXML
    private void logout() {
        formLoader.loadLoginForm();
    }

    @FXML
    private void showCustomerListForm() {
        formLoader.loadCustomerListForm();
    }
    @FXML
    private void showUserManagementForm() {
        formLoader.loadUserManagementForm();
    }
    @FXML
    private void showReports() {
        formLoader.loadReportsForm();
    }
}
