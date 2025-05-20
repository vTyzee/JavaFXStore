// ChangePasswordController.java
package org.example.grocerystore.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.grocerystore.model.entity.Customer;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ChangePasswordController implements Initializable {

    private final CustomerService customerService;
    private final FormService formService;

    public ChangePasswordController(CustomerService customerService, FormService formService) {
        this.customerService = customerService;
        this.formService = formService;
    }

    @FXML
    private ComboBox<Customer> cbUsers;
    @FXML
    private TextField tfUserId;
    @FXML
    private PasswordField pfNewPassword;
    @FXML
    private Label lblResult;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
            List<Customer> all = customerService.getAllCustomers();
            cbUsers.setItems(FXCollections.observableArrayList(all));
            cbUsers.setCellFactory(lv -> new javafx.scene.control.ListCell<>() {
                @Override
                protected void updateItem(Customer c, boolean empty) {
                    super.updateItem(c, empty);
                    setText(empty||c==null?null:c.getUsername());
                }
            });
            cbUsers.setButtonCell(cbUsers.getCellFactory().call(null));
            tfUserId.setVisible(false);
        } else {
            cbUsers.setVisible(false);
            var cur = customerService.getCurrentCustomer();
            if (cur!=null) tfUserId.setText(cur.getUsername());
            tfUserId.setEditable(false);
            tfUserId.setVisible(true);
        }
    }

    @FXML
    private void handleChangePassword() {
        try {
            Long id;
            if (customerService.currentUserHasRole(CustomerService.ROLES.ADMINISTRATOR)) {
                var sel = cbUsers.getValue();
                if (sel==null) { lblResult.setText("Выберите пользователя."); return; }
                id = sel.getId();
            } else {
                id = customerService.getCurrentCustomer().getId();
            }
            String pwd = pfNewPassword.getText().trim();
            if (pwd.isEmpty()) { lblResult.setText("Введите новый пароль."); return; }
            var upd = customerService.changePassword(id, pwd);
            lblResult.setText("Пароль изменён: " + upd.getUsername());
        } catch (Exception ex) {
            lblResult.setText("Ошибка: "+ex.getMessage());
        }
    }

    @FXML
    private void goToMainForm() {
        formService.loadMainForm();
    }
}
