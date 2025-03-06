package com.shop.emilxima.model.service;

import com.shop.emilxima.EmilximaApplication;
import com.shop.emilxima.tools.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FormService {

    private final SpringFXMLLoader springFXMLLoader;

    public FormService(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    private Stage getPrimaryStage() {
        return EmilximaApplication.primaryStage;
    }

    public void loadMainForm() {
        FXMLLoader loader = springFXMLLoader.load("/fxml/mainForm.fxml");
        try {
            Parent root = loader.load();
            getPrimaryStage().setScene(new Scene(root));
            getPrimaryStage().setTitle("Emilxima - Main");
            getPrimaryStage().centerOnScreen();
            getPrimaryStage().show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCustomerForm() {
        FXMLLoader loader = springFXMLLoader.load("/fxml/customerForm.fxml");
        try {
            Parent root = loader.load();
            getPrimaryStage().setScene(new Scene(root));
            getPrimaryStage().setTitle("Customers - Edit");
            getPrimaryStage().centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Аналогично loadProductForm(), loadPurchaseForm(), ...
}
