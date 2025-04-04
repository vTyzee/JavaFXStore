package ee.ivkhkdev.nptv23javafx.controller;

import ee.ivkhkdev.nptv23javafx.interfaces.AppUserService;
import ee.ivkhkdev.nptv23javafx.interfaces.HistoryService;
import ee.ivkhkdev.nptv23javafx.model.entity.Book;
import ee.ivkhkdev.nptv23javafx.model.entity.History;
import ee.ivkhkdev.nptv23javafx.model.entity.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class SelectedBookFromModalityController implements Initializable {
    private final HistoryService historyService;
    private final AppUserService appUserService;
    private final MainFormController mainFormController;
    @FXML private VBox vbSelectedBookRoot;
    @FXML private Button btTakeOnBook;
    @FXML private Button btReturnBook;
    private Book book;

    public SelectedBookFromModalityController(HistoryService historyService, AppUserService appUserService, MainFormController mainFormController) {
        this.historyService = historyService;
        this.appUserService = appUserService;
        this.mainFormController = mainFormController;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @FXML private void takeOnBook() {
        Optional<Session> sessionOptional = appUserService.getSession();
        if(sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            if(!session.isExpired()){
                if(book.getCount() < 1){

                }
                History history = new History();
                history.setBook(book);
                history.setAppUser(session.getCurrentUser());
                history.setTakeOnDate(LocalDate.now());
                try {
                    historyService.add(history);
                }catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        closeModalityWindows();
    }
    @FXML private void returnBook() {
        historyService.returnBook(book);
        mainFormController.initTableView();
        closeModalityWindows();
    }
    private void closeModalityWindows(){
        Stage stage = (Stage) vbSelectedBookRoot.getScene().getWindow();
        stage.close();
    }

    public void setVizibleButtons(boolean readingBook) {
        if (readingBook) {
            btTakeOnBook.setVisible(false);
            btReturnBook.setVisible(true);
        }else{
            btTakeOnBook.setVisible(true);
            btReturnBook.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
