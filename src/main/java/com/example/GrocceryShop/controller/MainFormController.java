package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.interfaces.OrderService;
import com.example.GrocceryShop.model.Customer;
import com.example.GrocceryShop.model.Order;
import com.example.GrocceryShop.model.Product;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class MainFormController implements Initializable {

    private final OrderService orderService;
    private final FormLoader formLoader;
    private Customer loggedInCustomer;

    @FXML private VBox menuContainer;
    @FXML private TableView<Order> tvOrderList;
    @FXML private TableColumn<Order, Long> tcId;
    @FXML private TableColumn<Order, String> tcCustomer;
    @FXML private TableColumn<Order, String> tcProducts;
    @FXML private TableColumn<Order, Double> tcTotalAmount;

    @Autowired
    public MainFormController(OrderService orderService, FormLoader formLoader) {
        this.orderService = orderService;
        this.formLoader = formLoader;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMenu();
        setupTable();
        refreshOrders();
    }

    public void setLoggedInCustomer(Customer customer) {
        this.loggedInCustomer = customer;
    }


    private void loadMenu() {
        try {
            FXMLLoader loader = formLoader.getSpringFXMLLoader().load("/menu/menuForm.fxml");
            VBox menu = loader.load();
            menuContainer.getChildren().add(menu);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке меню", e);
        }
    }


    private void setupTable() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcCustomer.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        cellData.getValue().getCustomer().getName()));
        tcProducts.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createStringBinding(() ->
                        cellData.getValue().getProduct().getName()));
        tcTotalAmount.setCellValueFactory(cellData ->
                javafx.beans.binding.Bindings.createObjectBinding(() ->
                        cellData.getValue().getProduct().getPrice() * cellData.getValue().getQuantity()));
    }

    @FXML
    private void refreshOrders() {
        List<Order> orders = orderService.getAll();
        ObservableList<Order> observableList = FXCollections.observableArrayList(orders);
        tvOrderList.setItems(observableList);
    }
    @FXML
    private void showProductDetails(Product selectedProduct) {
        if (selectedProduct == null) {
            return;
        }
        try {
            // Загружаем FXML
            FXMLLoader loader = formLoader.getSpringFXMLLoader().load("/fxml/ModalProductDetailsForm.fxml");
            // Обратите внимание, что load(...) может вернуть не сам контроллер, а объект типа Parent,
            // поэтому сначала получаем корень сцены:
            Parent root = loader.load();

            // Получаем контроллер из Loader
            ModalProductDetailsFormController controller = loader.getController();

            // Допустим, вы как-то получаете текущего пользователя:
            // Customer loggedInCustomer = userSessionService.getLoggedInCustomer(); (пример)

            // Передаём данные в модальное окно
            controller.setData(selectedProduct, loggedInCustomer, orderService, this::refreshOrders);

            // Создаём новое окно (Stage)
            Stage modalStage = new Stage();
            modalStage.setTitle("Детали товара");
            modalStage.setScene(new Scene(root));

            // Делаем окно модальным
            modalStage.initModality(Modality.WINDOW_MODAL);
            // Если нужно заблокировать взаимодействие с главным окном:
            // modalStage.initOwner(tvOrderList.getScene().getWindow());

            // Отображаем и ждём, пока пользователь закроет окно
            modalStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
