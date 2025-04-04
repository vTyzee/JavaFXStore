package ee.ivkhkdev.nptv23javafx.model.repository;

import ee.ivkhkdev.nptv23javafx.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
