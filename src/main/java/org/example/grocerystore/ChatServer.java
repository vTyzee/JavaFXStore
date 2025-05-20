// ChatServer.java
package org.example.grocerystore;

import java.io.IOException;
import java.net.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ChatServer {
    private static final int PORT = 12345;
    private static volatile boolean started=false;
    private static final Set<ClientHandler> clients =
            Collections.synchronizedSet(new HashSet<>());

    public static synchronized void startServer() {
        if(started) return;
        started=true;
        try(ServerSocket ss=new ServerSocket(PORT)){
            while(true) {
                Socket s = ss.accept();
                ClientHandler h = new ClientHandler(s);
                clients.add(h);
                h.start();
            }
        } catch(IOException e){ e.printStackTrace(); }
    }

    public static void broadcast(String msg, ClientHandler sender) {
        synchronized(clients){
            for(ClientHandler c:clients)
                if(c!=sender) c.sendMessage(msg);
        }
    }

    public static void removeClient(ClientHandler h) {
        clients.remove(h);
    }
}
