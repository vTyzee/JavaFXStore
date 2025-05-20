package org.example.grocerystore.service;

import org.example.grocerystore.ChatServer;
import org.example.grocerystore.controller.ChatFormController;

public class ChatServerRunner implements Runnable {
    private ChatFormController chatFormController;

    // Конструктор теперь принимает ChatFormController
    public ChatServerRunner(ChatFormController chatFormController) {
        this.chatFormController = chatFormController;
    }

    @Override
    public void run() {
        ChatServer.startServer();
    }
}
