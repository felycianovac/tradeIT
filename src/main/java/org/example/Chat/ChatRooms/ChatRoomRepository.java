package org.example.Chat.ChatRooms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    ChatRoom findByRoomName(String roomName);
    @Query(value = "SELECT c.* FROM ChatRooms c " +
            "JOIN UserChatRoom ucr ON c.RoomID = ucr.RoomID " +
            "WHERE ucr.UserID = :userId", nativeQuery = true)
    List<ChatRoom> findChatRoomsByUserId(@Param("userId") Integer userId);
}