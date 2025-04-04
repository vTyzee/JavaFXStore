package ee.ivkhkdev.nptv23javafx.interfaces;

import ee.ivkhkdev.nptv23javafx.model.entity.Author;
import ee.ivkhkdev.nptv23javafx.model.entity.Book;
import javafx.collections.ObservableList;

public interface AuthorService extends AppService<Author> {
    ObservableList<Author> getObservableList();
}
