package com.example.GrocceryShop.controller;

import com.example.GrocceryShop.interfaces.OrderService;
import com.example.GrocceryShop.tool.FormLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RevenueFormController {

    @FXML private DatePicker dpStartDate;
    @FXML private DatePicker dpEndDate;
    @FXML private Label lblRevenue;

    private final OrderService orderService;
    private final FormLoader formLoader;

    public RevenueFormController(OrderService orderService, FormLoader formLoader) {
        this.orderService = orderService;
        this.formLoader = formLoader;
    }

    @FXML
    private void calculateRevenue() {
        LocalDate startDate = dpStartDate.getValue();
        LocalDate endDate = dpEndDate.getValue();

        if (startDate == null || endDate == null) {
            showErrorAlert("Пожалуйста, укажите начальную и конечную даты.");
            return;
        }
        if (endDate.isBefore(startDate)) {
            showErrorAlert("Конечная дата не может быть раньше начальной.");
            return;
        }

        double revenue = orderService.calculateRevenueBetween(startDate, endDate);
        lblRevenue.setText("Доход: " + revenue + " eur.");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) dpStartDate.getScene().getWindow();
        stage.close();
    }
}
