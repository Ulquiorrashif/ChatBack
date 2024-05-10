package com.example.chatback.repository;

import com.example.chatback.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
//     Optional<Chat> findById(Long id);
}
