package ee.ivkhkdev.nptv23javafx.model.repository;

import ee.ivkhkdev.nptv23javafx.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
