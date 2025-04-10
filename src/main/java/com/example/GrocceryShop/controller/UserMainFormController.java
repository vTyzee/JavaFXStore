package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.interfaces.CustomerService;
import com.example.GrocceryShop.interfaces.OrderService;
import com.example.GrocceryShop.interfaces.ProductService;
import com.example.GrocceryShop.model.AppUser;
import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.model.Product;
import com.example.GrocceryShop.service.AppUserServiceImpl;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class UserMainFormController implements Initializable {

    @FXML private Label lblBalance;
    @FXML private FlowPane fpCards;
    @FXML private Button btnRefresh;
    @FXML private Button btnLogout;
    @FXML private Button btnProfile;


    private final ProductService productService;
    private final OrderService orderService;
    private final AppUserServiceImpl appUserServiceImpl;
    private final CustomerService customerService;
    private final FormLoader formLoader;


    private Customer currentCustomer;

    public UserMainFormController(
            ProductService productService,
            OrderService orderService,
            AppUserServiceImpl appUserServiceImpl,
            CustomerService customerService,
            FormLoader formLoader
    ) {
        this.productService = productService;
        this.orderService = orderService;
        this.appUserServiceImpl = appUserServiceImpl;
        this.customerService = customerService;
        this.formLoader = formLoader;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Получаем текущего покупателя
        currentCustomer = customerService.getCustomerByUser(appUserServiceImpl.getCurrentUser());
        updateBalanceLabel();

        // Действие на кнопку "Обновить"
        btnRefresh.setOnAction(e -> refreshProducts());

        // Подгружаем товары при запуске
        refreshProducts();
    }

    private void refreshProducts() {
        fpCards.getChildren().clear();
        List<Product> products = productService.getAll();

        // Для каждого товара создаём карточку
        for (Product product : products) {
            VBox card = createProductCard(product);
            fpCards.getChildren().add(card);
        }

        // Ещё раз получаем обновлённого Customer (на всякий случай)
        currentCustomer = customerService.getCustomerByUser(appUserServiceImpl.getCurrentUser());
        updateBalanceLabel();
    }

    private VBox createProductCard(Product product) {
        VBox card = new VBox(8);
        card.setStyle("""
        -fx-background-color: #fff;
        -fx-padding: 16;
        -fx-spacing: 10;
        -fx-border-color: #ddd;
        -fx-border-radius: 10;
        -fx-background-radius: 10;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 6, 0, 2, 2);
    """);

        Image image;
        try {
            if (product.getImagePath() != null
                    && !product.getImagePath().equalsIgnoreCase("default")
                    && new File(product.getImagePath()).exists()) {
                image = new Image(
                        new File(product.getImagePath()).toURI().toString(),
                        0, 0, true, true, false
                );
            } else {
                image = new Image(getClass().getResource("/img/noimg.png").toExternalForm());
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки изображения: " + e.getMessage());
            image = new Image(getClass().getResource("/img/noimg.png").toExternalForm());
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(120);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);

        Label lblName = new Label(product.getName());
        lblName.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        Label lblCategory = new Label("Категория: " + product.getCategory());
        Label lblPrice = new Label("Цена: " + product.getPrice());
        Label lblQuantity = new Label("В наличии: " + product.getQuantity());

        Button btnBuy = new Button("Купить");
        btnBuy.setStyle("""
        -fx-background-color: #27ae60;
        -fx-text-fill: white;
        -fx-font-weight: bold;
        -fx-padding: 6 20;
        -fx-background-radius: 5;
    """);
        btnBuy.setOnAction(e -> handleBuy(product));

        card.getChildren().addAll(imageView, lblName, lblCategory, lblPrice, lblQuantity, btnBuy);
        return card;
    }


    private void handleBuy(Product product) {
        try {
            FXMLLoader loader = formLoader.getSpringFXMLLoader().load("/fxml/ModalProductDetailsForm.fxml");
            Parent root = loader.load();

            ModalProductDetailsFormController controller = loader.getController();
            controller.setData(product, currentCustomer, orderService, this::refreshProducts);

            Stage modalStage = new Stage();
            modalStage.setTitle("Детали товара");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(fpCards.getScene().getWindow());
            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        try {
            FXMLLoader loader = formLoader.getSpringFXMLLoader().load("/user/loginForm.fxml");
            Parent root = loader.load();

            Stage stage = (Stage) btnLogout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Вход");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openProfile() {
        try {
            FXMLLoader loader = formLoader.getSpringFXMLLoader().load("/user/ProfileForm.fxml");
            Parent root = loader.load();

            ProfileFormController controller = loader.getController();

            AppUser user = appUserServiceImpl.getCurrentUser(); // например, так
            controller.setUser(user);

            Stage stage = new Stage();
            stage.setTitle("Мой профиль");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(fpCards.getScene().getWindow());
            stage.showAndWait();

            refreshProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateBalanceLabel() {
        if (currentCustomer != null) {
            lblBalance.setText("Баланс: " + currentCustomer.getBalance());
        } else {
            lblBalance.setText("Баланс: неизвестен");
        }
    }
}
