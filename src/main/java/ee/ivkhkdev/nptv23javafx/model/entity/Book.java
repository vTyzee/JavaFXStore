package ee.ivkhkdev.nptv23javafx.model.entity;

import jakarta.persistence.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors= new HashSet<>();
    private int publicationYear;
    private int quantity;
    private int count;

    public Book() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return publicationYear == book.publicationYear && quantity == book.quantity && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, authors, publicationYear, quantity);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + Arrays.toString(authors.toArray()) +
                ", publicationYear=" + publicationYear +
                ", quantity=" + quantity +
                ", count=" + count +
                '}';
    }
    @Transient
    public StringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(id));
    }
    @Transient
    public StringProperty titleProperty() {
        return new SimpleStringProperty(title);
    }
    @Transient
    public StringProperty publicationYearProperty() {
        return new SimpleStringProperty(String.valueOf(publicationYear));
    }@Transient
    public StringProperty quantityProperty() {
        return new SimpleStringProperty(String.valueOf(quantity));
    }
    @Transient
    public StringProperty countProperty() {
        return new SimpleStringProperty(String.valueOf(count));
    }
    @Transient
    public StringProperty authorsProperty() {
        // Преобразуем коллекцию авторов в строку
        String authors = getAuthors().stream()
                .map(author -> author.getFirstname() + " " + author.getLastname())
                .collect(Collectors.joining(", "));
        return new SimpleStringProperty(authors);
    }


}
