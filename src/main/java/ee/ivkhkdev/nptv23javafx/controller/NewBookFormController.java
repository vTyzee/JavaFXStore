package ee.ivkhkdev.nptv23javafx.controller;

import ee.ivkhkdev.nptv23javafx.interfaces.BookService;
import ee.ivkhkdev.nptv23javafx.model.entity.Author;
import ee.ivkhkdev.nptv23javafx.model.entity.Book;
import ee.ivkhkdev.nptv23javafx.service.AuthorServiceImpl;
import ee.ivkhkdev.nptv23javafx.service.BookServiceImpl;
import ee.ivkhkdev.nptv23javafx.tools.FormLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class NewBookFormController implements Initializable {

    private FormLoader formLoader;
    private BookService bookService;
    private AuthorServiceImpl auhtorService;

    @FXML private Label lbInfo;
    @FXML private TextField tfTitle;
    @FXML private ListView<Author> lvAuthors;
    @FXML private TextField tfPublicationYear;
    @FXML private TextField tfQuantity;

    public NewBookFormController(FormLoader formLoader, BookService bookService, AuthorServiceImpl authorServiceImpl) {
        this.formLoader = formLoader;
        this.bookService = bookService;
        this.auhtorService = authorServiceImpl;
    }
    @FXML private void create(){
        Book book = new Book();
        if(tfTitle.getText().isEmpty() || tfPublicationYear.getText().isEmpty()
                || tfQuantity.getText().isEmpty() || lvAuthors.getSelectionModel().getSelectedItems().isEmpty()){
            lbInfo.setText("Заполните все поля");
            return;
        }
        book.setTitle(tfTitle.getText());
        book.getAuthors().addAll(lvAuthors.getSelectionModel().getSelectedItems());
        book.setPublicationYear(Integer.parseInt(tfPublicationYear.getText()));
        book.setQuantity(Integer.parseInt(tfQuantity.getText()));
        book.setCount(book.getQuantity());
        bookService.add(book);
        formLoader.loadMainForm();
    }

    @FXML private void goToMainForm(){
        formLoader.loadMainForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lvAuthors.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<Author> authors = auhtorService.getList();
        lvAuthors.getItems().setAll(FXCollections.observableArrayList(authors));
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
        tfQuantity.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                this.create();
            }
        });
    }

}
