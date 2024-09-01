package com.ebiz.drivel.domain.chat.repository;

import com.ebiz.drivel.domain.chat.entity.ChatMessage;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    @Query(value = "{ 'meetingId': ?1, '_id': { '$lt': ?0 } ,'isDeleted': false }")
    List<ChatMessage> findPreviousMessages(String lastMessageId, Long meetingId, Pageable pageable);

    @Query(value = "{ 'meetingId': ?0  ,'isDeleted': false } ")
    List<ChatMessage> findLatestMessages(Long meetingId, Pageable pageable);
}
