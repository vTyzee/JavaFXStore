// src/main/java/org/example/grocerystore/service/ChatClientService.java
package org.example.grocerystore.service;

import javafx.application.Platform;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

@Service
public class ChatClientService {
    // Перенесём сюда, на уровень класса
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Подключается к серверу и читает входящие строки в отдельном потоке.
     * @param host адрес сервера
     * @param port порт сервера
     * @param onMessage callback, который будет вызван на UI-треде для каждой полученной строки
     */
    public void connect(String host, int port, Consumer<String> onMessage) {
        new Thread(() -> {
            try {
                // Здесь мы присваиваем полям, а не локальным переменным
                socket = new Socket(host, port);
                in     = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out    = new PrintWriter(socket.getOutputStream(), true);

                String line;
                while ((line = in.readLine()) != null) {
                    String msg = line;  // msg — effectively final в этой итерации
                    Platform.runLater(() -> onMessage.accept(msg));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "chat-client-reader").start();
    }

    /** Отправить сообщение на сервер */
    public void send(String msg) {
        if (out != null) {
            out.println(msg);
        }
    }

    /** Закрыть соединение */
    public void close() {
        try {
            if (socket != null) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
