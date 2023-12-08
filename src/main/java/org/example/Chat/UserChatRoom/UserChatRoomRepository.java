package org.example.Chat.UserChatRoom;

import org.example.Chat.ChatRooms.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChatRoomRepository extends JpaRepository<UserChatRooms, Integer> {
    List<UserChatRooms> findByUserId(Integer userId);
    List<UserChatRooms> findByChatRoom(ChatRoom chatRoom);
}
