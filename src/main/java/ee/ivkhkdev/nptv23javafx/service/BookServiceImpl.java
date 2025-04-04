package ee.ivkhkdev.nptv23javafx.service;

import ee.ivkhkdev.nptv23javafx.interfaces.BookService;
import ee.ivkhkdev.nptv23javafx.model.entity.Author;
import ee.ivkhkdev.nptv23javafx.model.entity.Book;
import ee.ivkhkdev.nptv23javafx.model.repository.AuthorRepository;
import ee.ivkhkdev.nptv23javafx.model.repository.BookRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Optional<Book> add(Book book) {
        try {
            Book addedBook = bookRepository.save(book);
            return Optional.of(addedBook);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Book> getObservableList(){
        ObservableList<Book> observableList = FXCollections.observableArrayList();
        observableList.addAll(this.getList());
        return observableList;
    }

    @Override
    public List<Book> getList() {
        return bookRepository.findAll();
    }


}
