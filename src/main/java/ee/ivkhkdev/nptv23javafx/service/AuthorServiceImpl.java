package ee.ivkhkdev.nptv23javafx.service;

import ee.ivkhkdev.nptv23javafx.interfaces.AuthorService;
import ee.ivkhkdev.nptv23javafx.model.entity.Author;
import ee.ivkhkdev.nptv23javafx.model.entity.Book;
import ee.ivkhkdev.nptv23javafx.model.repository.AuthorRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> add(Author author) {
        return Optional.of(authorRepository.save(author));
    }
    public List<Author> getList() {
        return authorRepository.findAll();
    }

    @Override
    public ObservableList<Author> getObservableList() {
        ObservableList<Author> observableList = FXCollections.observableArrayList();
        observableList.addAll(this.getList());
        return observableList;
    }
}
