package ee.ivkhkdev.nptv23javafx.interfaces;

import ee.ivkhkdev.nptv23javafx.model.entity.Book;
import javafx.collections.ObservableList;

public interface BookService extends AppService<Book> {
    ObservableList<Book> getObservableList();
}
