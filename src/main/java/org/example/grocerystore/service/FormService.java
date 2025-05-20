package org.example.grocerystore.service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.grocerystore.Main;
import org.example.grocerystore.controller.*;
import org.example.grocerystore.model.entity.Customer;
import org.example.grocerystore.model.entity.Product;
import org.example.grocerystore.model.entity.Supplier;
import org.example.grocerystore.tools.SpringFXMLLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FormService {

    /** Возвращает панель меню для встраивания в MainForm */
    public Parent loadMenuForm() {
        try {
            FXMLLoader loader = springFXMLLoader.load("/menu/menuForm.fxml");
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Показать форму добавления поставщика */
    public void loadSupplierForm() {
        loadScene("/supplier/supplierForm.fxml", "Новый поставщик");
    }
    private final SpringFXMLLoader springFXMLLoader;

    public FormService(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    private Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    private void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = springFXMLLoader.load(fxmlPath);
            Parent root = loader.load();
            Stage stage = getPrimaryStage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadLoginForm() {
        loadScene("/user/loginForm.fxml", "GroceryStore - Вход");
    }

    public void loadMainForm() {
        loadScene("/main/mainForm.fxml", "GroceryStore - Главная");
    }

    public void loadRegistrationForm() {
        loadScene("/user/registrationForm.fxml", "Регистрация");
    }

    public void loadChangePasswordForm() {
        loadScene("/user/changePasswordForm.fxml", "Смена пароля");
    }

    public void loadNewCustomerForm() {
        loadScene("/customer/newCustomerForm.fxml", "Новый покупатель");
    }

    public void loadCustomerListForm() {
        loadScene("/customer/customerList.fxml", "Список покупателей");
    }

    public void loadEditCustomerForm(Customer customer) {
        try {
            FXMLLoader loader = springFXMLLoader.load("/customer/editCustomerForm.fxml");
            Parent root = loader.load();
            EditCustomerFormController ctrl = loader.getController();
            ctrl.setCustomer(customer);
            Stage stage = getPrimaryStage();
            stage.setScene(new Scene(root));
            stage.setTitle("Редактирование покупателя");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadNewSupplierForm() {
        loadScene("/supplier/supplierForm.fxml", "Новый поставщик");
    }

    public void loadSupplierListForm() {
        loadScene("/supplier/supplierList.fxml", "Список поставщиков");
    }

    public void loadEditSupplierForm(Supplier supplier) {
        try {
            FXMLLoader loader = springFXMLLoader.load("/supplier/editSupplierForm.fxml");
            Parent root = loader.load();
            EditSupplierFormController ctrl = loader.getController();
            ctrl.setEditSupplier(supplier);
            Stage stage = getPrimaryStage();
            stage.setScene(new Scene(root));
            stage.setTitle("Редактирование поставщика");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadNewProductForm() {
        loadScene("/product/newProductForm.fxml", "Новый продукт");
    }

    public void loadProductListForm() {
        loadScene("/product/productList.fxml", "Список продуктов");
    }

    public void loadEditProductForm(Product product) {
        try {
            FXMLLoader loader = springFXMLLoader.load("/product/editProductForm.fxml");
            Parent root = loader.load();
            EditProductFormController ctrl = loader.getController();
            ctrl.setProduct(product);
            Stage stage = getPrimaryStage();
            stage.setScene(new Scene(root));
            stage.setTitle("Редактирование продукта");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadSelectedProductForm(Product product) {
        try {
            FXMLLoader loader = springFXMLLoader.load("/product/selectedProductForm.fxml");
            Parent root = loader.load();
            SelectedProductFormController ctrl = loader.getController();
            ctrl.setProduct(product);
            Stage dialog = new Stage();
            dialog.initOwner(getPrimaryStage());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(root));
            dialog.setTitle("Информация о продукте");
            dialog.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPurchaseForm() {
        loadScene("/purchase/purchaseForm.fxml", "Покупка продукта");
    }

    public void loadIncomeForm() {
        loadScene("/purchase/incomeForm.fxml", "Доход магазина");
    }

    public void loadRatingForm() {
        loadScene("/purchase/ratingForm.fxml", "Рейтинг продаваемости продуктов");
    }

    public void loadChatForm() {
        try {
            FXMLLoader loader = springFXMLLoader.load("/chat/chatForm.fxml");
            Parent root = loader.load();
            Stage dialog = new Stage();
            dialog.initOwner(getPrimaryStage());
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(root));
            dialog.setTitle("Чат");
            dialog.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
