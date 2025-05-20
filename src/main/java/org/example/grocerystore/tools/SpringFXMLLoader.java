// SpringFXMLLoader.java
package org.example.grocerystore.tools;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringFXMLLoader {
    private final ApplicationContext ctx;
    public SpringFXMLLoader(ApplicationContext ctx){ this.ctx = ctx; }
    public FXMLLoader load(String path){
        FXMLLoader f = new FXMLLoader(getClass().getResource(path));
        f.setControllerFactory(ctx::getBean);
        return f;
    }
}
