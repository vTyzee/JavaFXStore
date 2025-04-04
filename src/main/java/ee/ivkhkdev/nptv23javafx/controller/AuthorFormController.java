package ee.ivkhkdev.nptv23javafx.controller;

import ee.ivkhkdev.nptv23javafx.model.entity.Author;
import ee.ivkhkdev.nptv23javafx.interfaces.AuthorService;
import ee.ivkhkdev.nptv23javafx.tools.FormLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AuthorFormController implements Initializable {

    private FormLoader formLoader;
    private AuthorService authorService;
    @FXML private TextField tfFirstname;
    @FXML private TextField tfLastname;

    public AuthorFormController(FormLoader formLoader, AuthorService authorService) {
        this.formLoader = formLoader;
        this.authorService = authorService;
    }

    @FXML private void create(){
        Author author = new Author();
        author.setFirstname(tfFirstname.getText());
        author.setLastname(tfLastname.getText());
        authorService.add(author);
        formLoader.loadMainForm();
    }
    @FXML private void goToMainForm() {
        formLoader.loadMainForm();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfLastname.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                this.create();
            }
        });
    }
}
