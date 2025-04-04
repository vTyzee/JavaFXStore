package ee.ivkhkdev.nptv23javafx.controller;

import ee.ivkhkdev.nptv23javafx.interfaces.AuthorService;
import ee.ivkhkdev.nptv23javafx.model.entity.Author;
import ee.ivkhkdev.nptv23javafx.model.entity.Book;
import ee.ivkhkdev.nptv23javafx.tools.FormLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ListAuthorsFormController implements Initializable {
    private FormLoader formLoader;
    private AuthorService authorService;
    @FXML private ListView<Author> lvAuthors;
    @FXML private ListView<Book> lvAuthorBooks;
    @FXML private Label lbInfo;
    @FXML private VBox vbListSelectedAuthorsBooks;

    public ListAuthorsFormController(AuthorService authorService, FormLoader formLoader) {
        this.authorService = authorService;
        this.formLoader = formLoader;
    }
    @FXML private void goToMainForm(){
        formLoader.loadMainForm();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvAuthors.getItems().setAll(authorService.getObservableList());
        lvAuthors.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Author author, boolean empty) {
            super.updateItem(author, empty);
            if (empty || author == null) {
                setText(null);
            } else {
                setText(author.getId() + ". " + author.getFirstname() + " " + author.getLastname());
            }
            }
        });
        lvAuthorBooks.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Book book, boolean empty) {
                super.updateItem(book, empty);
                if (empty || book == null) {
                    setText(null);
                } else {
                    setText(book.getId() + ". " + book.getTitle() + ". " + book.getPublicationYear());
                }
            }
        });
        lvAuthors.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && !lvAuthors.getSelectionModel().isEmpty()) {
                Author selectedAuthor = lvAuthors.getSelectionModel().getSelectedItem();
                lvAuthorBooks.getItems().clear();
                ObservableList<Book> observableList = FXCollections.observableArrayList();
                observableList.setAll(selectedAuthor.getBooks());
                lvAuthorBooks.setItems(observableList);
                vbListSelectedAuthorsBooks.setVisible(true);
            }else if (event.getClickCount() == 2 && !lvAuthors.getSelectionModel().isEmpty()) {
                formLoader.loadSelectedAuthorFormModality(lvAuthors.getSelectionModel().getSelectedItem());
            }
        });

    }
}
