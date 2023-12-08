package org.example.Chat.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Messages, Integer> {

    List<Messages> findByChatRoom_IdOrderByTimestamp(Integer room_id);

}