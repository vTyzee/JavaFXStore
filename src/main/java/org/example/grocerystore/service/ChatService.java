// ChatService.java
package org.example.grocerystore.service;

import org.example.grocerystore.model.entity.ChatMessage;
import org.example.grocerystore.model.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatMessageRepository repo;
    public ChatService(ChatMessageRepository r){ this.repo=r; }
    public ChatMessage saveMessage(String u,String m){ return repo.save(new ChatMessage(u,m)); }
    public List<ChatMessage> getLastMessages(){ return repo.findTop50ByOrderByTimestampAsc(); }
}
