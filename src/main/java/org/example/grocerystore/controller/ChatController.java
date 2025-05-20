// ChatController.java
package org.example.grocerystore.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.grocerystore.service.ChatClientService;
import org.springframework.stereotype.Component;

@Component
public class ChatController {

    private final ChatClientService clientService;

    @FXML private TextArea taChat;
    @FXML private TextField tfMessage;

    public ChatController(ChatClientService clientService) {
        this.clientService = clientService;
    }

    @FXML
    public void initialize() {
        clientService.connect("localhost", 12345, this::appendMessage);
    }

    private void appendMessage(String msg) {
        taChat.appendText(msg + "\n");
    }

    @FXML
    private void onSend() {
        String t = tfMessage.getText().trim();
        if (!t.isEmpty()) {
            clientService.send(t);
            tfMessage.clear();
        }
    }
}
