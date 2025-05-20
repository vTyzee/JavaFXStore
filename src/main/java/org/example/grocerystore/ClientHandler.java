// ClientHandler.java
package org.example.grocerystore;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String name;

    public ClientHandler(Socket s){
        this.socket=s;
        try {
            in=new BufferedReader(new InputStreamReader(s.getInputStream()));
            out=new PrintWriter(s.getOutputStream(),true);
            this.name=in.readLine();
            ChatServer.broadcast(">>> "+name+" вошёл в чат", this);
        } catch(IOException e){ e.printStackTrace(); }
    }

    @Override
    public void run(){
        try {
            String line;
            while((line=in.readLine())!=null){
                if("/quit".equalsIgnoreCase(line)) break;
                ChatServer.broadcast(name+": "+line, this);
            }
        } catch(IOException e){ e.printStackTrace(); }
        finally {
            ChatServer.broadcast("<<< "+name+" покинул чат", this);
            ChatServer.removeClient(this);
        }
    }

    public void sendMessage(String msg){ out.println(msg); }
}
