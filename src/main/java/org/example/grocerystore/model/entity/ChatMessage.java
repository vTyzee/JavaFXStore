// ChatMessage.java
package org.example.grocerystore.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatMessage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String message;
    private LocalDateTime timestamp;

    public ChatMessage() {}
    public ChatMessage(String u, String m) { this.username=u; this.message=m; this.timestamp=LocalDateTime.now(); }
    public Long getId(){return id;}
    public String getUsername(){return username;}
    public void setUsername(String u){this.username=u;}
    public String getMessage(){return message;}
    public void setMessage(String m){this.message=m;}
    public LocalDateTime getTimestamp(){return timestamp;}
    public void setTimestamp(LocalDateTime t){this.timestamp=t;}
}
