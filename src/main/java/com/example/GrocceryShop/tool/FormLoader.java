package com.example.GrocceryShop.tool;

import com.example.GrocceryShop.GrocceryShopApplication;
import com.example.GrocceryShop.controller.EditCustomerFormController;
import com.example.GrocceryShop.controller.EditProductFormController;
import com.example.GrocceryShop.controller.EditUserFormController;
import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.model.Product;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FormLoader {

    private final SpringFXMLLoader springFXMLLoader;

    public FormLoader(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    public SpringFXMLLoader getSpringFXMLLoader() {
        return springFXMLLoader;
    }

    public void loadNewProductForm() {
        loadForm("/product/newProductForm.fxml", "Добавить новый товар");
    }

    public void loadMainForm() {
        loadForm("/main/mainForm.fxml", "Главное окно магазина цветов");
    }
    public void loadUserMainForm() {
        loadForm("/user/userMainForm.fxml", "Окно пользователя");
    }


    public void loadLoginForm() {
        loadForm("/user/loginForm.fxml", "Вход в систему");
    }

    public void loadRegistrationForm() {
        loadForm("/user/registrationForm.fxml", "Регистрация пользователя");
    }

    public void loadCustomerListForm() {
        loadForm("/customer/customerListForm.fxml", "Список покупателей");
    }

    public void loadProductListForm() {
        loadForm("/product/productListForm.fxml", "Список товаров");
    }

    public void loadNewOrderForm() {
        loadForm("/order/newOrderForm.fxml", "Создать новый заказ");
    }

    public void loadNewCustomerForm() {
        loadForm("/customer/newCustomerForm.fxml", "Добавить покупателя");
    }

    public void loadEditProductForm(Product product) {
        try {
            FXMLLoader loader = springFXMLLoader.load("/product/editProductForm.fxml");
            Parent root = loader.load();
            EditProductFormController controller = loader.getController();
            controller.setProduct(product);
            showStage(root, "Редактировать товар");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке формы: /product/editProductForm.fxml", e);
        }
    }

    public void loadEditCustomerForm(Customer customer) {
        try {
            FXMLLoader loader = springFXMLLoader.load("/customer/editCustomerForm.fxml");
            Parent root = loader.load();
            EditCustomerFormController controller = loader.getController();
            controller.setCustomer(customer);
            showStage(root, "Редактировать покупателя");
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке формы: /customer/editCustomerForm.fxml", e);
        }
    }


    private void loadForm(String fxmlPath, String title) {
        try {
            FXMLLoader loader = springFXMLLoader.load(fxmlPath);
            Parent root = loader.load();
            showStage(root, title);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке формы: " + fxmlPath, e);
        }
    }

    private void showStage(Parent root, String title) {
        Stage stage = getPrimaryStage();
        if (stage == null) {
            throw new RuntimeException("Ошибка: primaryStage не инициализирован!");
        }
        Scene scene = new Scene(root);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
    public void loadEditUserForm(AppUser user) {
        try {
            FXMLLoader loader = getSpringFXMLLoader().load("/user/EditUserForm.fxml");
            Parent root = loader.load();
            EditUserFormController controller = loader.getController();
            controller.setUser(user);

            Stage stage = new Stage();
            stage.setTitle("Редактирование пользователя");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(getPrimaryStage());
            stage.showAndWait();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUserManagementForm() {
        try {
            FXMLLoader loader = getSpringFXMLLoader().load("/user/UserManagementForm.fxml");
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Управление пользователями");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadReportsForm() {
        try {
            FXMLLoader loader = getSpringFXMLLoader().load("/fxml/RevenueForm.fxml");
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Просмотр отчетов");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private Stage getPrimaryStage() {
        if (GrocceryShopApplication.primaryStage == null) {
            throw new RuntimeException("Ошибка: primaryStage не установлен в FlowerShopApplication!");
        }
        return GrocceryShopApplication.primaryStage;
    }
}
