// ChatFormController.java
package org.example.grocerystore.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.grocerystore.service.ChatService;
import org.example.grocerystore.service.CustomerService;
import org.example.grocerystore.service.FormService;
import org.springframework.stereotype.Component;

@Component
public class ChatFormController {

    private final CustomerService customerService;
    private final ChatService chatService;
    private final FormService formService;
    private static boolean serverStarted = false;

    @FXML private Label lblNickname;
    @FXML private TextArea taChat;
    @FXML private TextField tfMessage;
    @FXML private Button btnSend, btnClose;

    private String nickname;

    public ChatFormController(CustomerService cs, ChatService chs, FormService fs) {
        this.customerService = cs;
        this.chatService = chs;
        this.formService = fs;
    }

    @FXML
    public void initialize() {
        if (!serverStarted) {
            new Thread(() -> org.example.grocerystore.ChatServer.startServer()).start();
            serverStarted = true;
        }
        nickname = customerService.getCurrentCustomer().getUsername();
        lblNickname.setText("Вы в чате как: " + nickname);
        chatService.getLastMessages().forEach(m ->
                taChat.appendText("[" + m.getTimestamp().toLocalTime() + "] "
                        + m.getUsername() + ": " + m.getMessage() + "\n")
        );
        btnSend.setOnAction(e -> {
            String t = tfMessage.getText().trim();
            if (t.isEmpty()) return;
            chatService.saveMessage(nickname, t);
            org.example.grocerystore.ChatServer.broadcast(nickname + ": " + t, null);
            taChat.appendText(nickname + ": " + t + "\n");
            tfMessage.clear();
        });
    }

    @FXML private void closeChat() {
        ((Stage)btnClose.getScene().getWindow()).close();
    }

    public void addMessageToChat(String msg) {
        chatService.saveMessage("remote", msg);
        Platform.runLater(() -> taChat.appendText(msg + "\n"));
    }
}
