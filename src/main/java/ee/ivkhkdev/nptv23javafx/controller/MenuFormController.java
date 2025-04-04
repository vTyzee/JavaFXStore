package ee.ivkhkdev.nptv23javafx.controller;

import ee.ivkhkdev.nptv23javafx.interfaces.AppUserService;
import ee.ivkhkdev.nptv23javafx.model.entity.AppUser;
import ee.ivkhkdev.nptv23javafx.model.entity.Session;
import ee.ivkhkdev.nptv23javafx.model.repository.SessionRepository;
import ee.ivkhkdev.nptv23javafx.tools.FormLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class MenuFormController implements Initializable {

    private final AppUserService appUserService;
    private final SessionRepository sessionRepository;
    private final FormLoader formLoader;
    private Session session;
    @FXML private Menu mBooks;
    @FXML private Menu mAdmin;
    @FXML private Menu mUsers;
    @FXML private MenuItem miEnter;
    @FXML private MenuItem miProfile;
    @FXML private MenuItem miLogout;


    public MenuFormController(FormLoader formLoader, AppUserService appUserService, SessionRepository sessionRepository) {
        this.formLoader = formLoader;
        this.appUserService = appUserService;
        this.sessionRepository = sessionRepository;
    }

    @FXML private void showAuthorForm(){
        formLoader.loadAuthorForm();
    }
    @FXML private void showBookForm() {
        formLoader.loadNewBookForm();
    }
    @FXML private void showLoginForm(){
        formLoader.loadLoginForm();
    }
    @FXML private void logout(){
        session = new Session(1L);
        sessionRepository.save(session);
        formLoader.loadLoginForm();
    }
    @FXML private void showTakedBookForm(){
        formLoader.loadTakedBookForm();
    }

    @FXML private void showListAuthorsForm(){
        formLoader.loadListAuthorForm();
    }
    @FXML private void showProfileForm(){
        formLoader.loadProfileForm();
    }

    private void initMenuVisible(){
        Optional<Session> optionSession = appUserService.getSession();
        if(optionSession.isPresent()){
            this.session = optionSession.get();
            if(session.getCurrentUser().getRoles().contains("ADMINISTRATOR")){
                mBooks.setVisible(true);
                mAdmin.setVisible(true);
                mUsers.setVisible(true);
                miEnter.setVisible(false);
                miProfile.setVisible(true);
                miLogout.setVisible(true);
            }else if(session.getCurrentUser().getRoles().contains("MANAGER")){
                mBooks.setVisible(true);
                mAdmin.setVisible(false);
                mUsers.setVisible(true);
                miEnter.setVisible(false);
                miProfile.setVisible(true);
                miLogout.setVisible(true);
            }else if(session.getCurrentUser().getRoles().contains("USER")){
                mBooks.setVisible(false);
                mAdmin.setVisible(false);
                mUsers.setVisible(true);
                miEnter.setVisible(false);
                miProfile.setVisible(true);
                miLogout.setVisible(true);
            }
        }

    }

    public void setSession() {
        Optional<Session> optionalSession = appUserService.getSession();
        if(optionalSession.isPresent()){
            if(optionalSession.get().isExpired()){
                logout();
            }else{
                this.session = optionalSession.get();
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initMenuVisible();
    }
}
