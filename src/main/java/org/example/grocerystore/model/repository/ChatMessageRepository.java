// ChatMessageRepository.java
package org.example.grocerystore.model.repository;

import org.example.grocerystore.model.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findTop50ByOrderByTimestampAsc();
}
