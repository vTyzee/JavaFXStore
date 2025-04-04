package ee.ivkhkdev.nptv23javafx.controller;

import ee.ivkhkdev.nptv23javafx.interfaces.AppUserService;
import ee.ivkhkdev.nptv23javafx.interfaces.BookService;
import ee.ivkhkdev.nptv23javafx.model.entity.Book;
import ee.ivkhkdev.nptv23javafx.model.entity.Session;
import ee.ivkhkdev.nptv23javafx.service.HistoryService;
import ee.ivkhkdev.nptv23javafx.tools.FormLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainFormController implements Initializable {

    private final FormLoader formLoader;
    private final BookService bookService;
    private final AppUserService appUserService;
    private final HistoryService historyService;
    private Session session;
    @FXML private VBox vbMainFormRoot;
    @FXML private TableView<Book> tvListBooks;
    @FXML private TableColumn<Book, String> tcId;
    @FXML private TableColumn<Book, String> tcTitle;
    @FXML private TableColumn<Book, String> tcAuthors;
    @FXML private TableColumn<Book, String> tcPublicationYear;
    @FXML private TableColumn<Book, String> tcQuantity;
    @FXML private TableColumn<Book, String> tcCount;
    @FXML private HBox hbEditBook;
    @FXML private Label lbInfo;

    public MainFormController(FormLoader formLoader, AppUserService appUserService, BookService bookService,HistoryService historyService) {
        this.formLoader = formLoader;
        this.appUserService = appUserService;
        this.bookService = bookService;
        this.historyService = historyService;
    }

    @FXML private void showEditBookForm() {
        formLoader.loadEditBookForm(tvListBooks.getSelectionModel().getSelectedItem());
    }
    private void openBookDetails(Book book) {
        boolean readingBook = historyService.isReadingBook(book);
        formLoader.loadSelectedBookFormModality(book,readingBook);
        tvListBooks.refresh();
    }
    public void initTableView(){
        tvListBooks.setItems(bookService.getObservableList());
    }
    public void setInfoMessage(String message){
        lbInfo.setText(message);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Добавляем форму меню первым элементом vbMainFormRoot
        vbMainFormRoot.getChildren().addFirst(formLoader.loadMenuForm());
        this.session = appUserService.getSession().get();

        // Инициируем список книг
        tvListBooks.setItems(bookService.getObservableList());

        // Настраиваем отображение полей книги в столбцах таблицы, для удобства создали StringProperty в Book
        tcId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        tcTitle.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        tcAuthors.setCellValueFactory(cellData -> cellData.getValue().authorsProperty());
        tcPublicationYear.setCellValueFactory(cellData -> cellData.getValue().publicationYearProperty());
        tcQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        tcCount.setCellValueFactory(cellData -> cellData.getValue().countProperty());

        // Навешиваем на таблицу книг обработчик события клика мышкой
        // (логика: при клике проверяет роль и показывает или скрывает HBox с кнопкой Редактировать)
        tvListBooks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Book>() {
            @Override
            public void changed(ObservableValue<? extends Book> observable, Book oldValue, Book newValue) {
                if (newValue != null) {
                    if(session.getCurrentUser().getRoles().contains("MANAGER")){
                        hbEditBook.setVisible(true);
                    }else{
                        hbEditBook.setVisible(false);
                    }
                }
            }
        });
         // Обработка двойного клика -> показывает модальное окно с выбранной книгой
        tvListBooks.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !tvListBooks.getSelectionModel().isEmpty()) {
                Book selectedBook = tvListBooks.getSelectionModel().getSelectedItem();
                try {
                    openBookDetails(selectedBook);
                    lbInfo.setText(selectedBook.getTitle() + " - выдана пользователю "
                            + session.getCurrentUser().getFirstname()
                            + " "
                            + session.getCurrentUser().getLastname());
                }catch (Exception e){
                    lbInfo.setText(selectedBook.getTitle() + " - выдать не удалось");
                }
            }
        });

    }
}
