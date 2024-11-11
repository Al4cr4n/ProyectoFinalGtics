package com.example.telexpress.repository;

import com.example.telexpress.entity.ChatMessage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public ChatMessage findLastMessageByDestination(Integer destinationuser) {
        TypedQuery<ChatMessage> query = entityManager.createQuery(
                "SELECT cm FROM ChatMessage cm WHERE cm.destinationuser = :destinationuser ORDER BY cm.id DESC",
                ChatMessage.class
        );
        query.setParameter("destinationuser", destinationuser);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
