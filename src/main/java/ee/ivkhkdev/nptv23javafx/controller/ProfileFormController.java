package ee.ivkhkdev.nptv23javafx.controller;

import ee.ivkhkdev.nptv23javafx.interfaces.AppUserService;
import ee.ivkhkdev.nptv23javafx.model.entity.AppUser;
import ee.ivkhkdev.nptv23javafx.tools.FormLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;

import java.util.ResourceBundle;

@Component
public class ProfileFormController implements Initializable {

    private final AppUserService appUserService;
    private final FormLoader formLoader;


    @FXML
    private TextField tfLastname;
    @FXML private TextField tfUsername;
    @FXML private PasswordField pfPassword;
    @FXML private TextField tfFirstname;

    public ProfileFormController(AppUserService appUserService, FormLoader formLoader) {
        this.appUserService = appUserService;
        this.formLoader = formLoader;

    }
    @FXML private void updateProfile() {
        AppUser  appUser = appUserService.getSession().get().getCurrentUser();
        appUser.setFirstname(tfFirstname.getText());
        appUser.setLastname(tfLastname.getText());
        appUser.setUsername(tfUsername.getText());
        if(!pfPassword.getText().isEmpty()){
           appUser.setPassword(pfPassword.getText());
        }
        appUserService.add(appUser);
        if(appUserService.authentication(appUser.getUsername(), appUser.getPassword())){
            formLoader.loadMainForm("Профиль пользователя обновлен");
        }

    }
    @FXML private void showMianForm() {
        formLoader.loadMainForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppUser appUser = appUserService.getSession().get().getCurrentUser();
        if (appUser != null && appUser.getUsername() != "admin") {
            tfFirstname.setText(appUser.getFirstname());
            tfLastname.setText(appUser.getLastname());
            tfUsername.setText(appUser.getUsername());
            pfPassword.setText("");
        }

    }

}
