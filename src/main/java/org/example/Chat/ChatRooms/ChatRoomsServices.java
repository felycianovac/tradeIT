package org.example.Chat.ChatRooms;

import org.example.Chat.Message.MessageDTO;
import org.example.Chat.Message.MessageRepository;
import org.example.Chat.Message.Messages;
import org.example.Chat.UserChatRoom.UserChatRoomRepository;
import org.example.Chat.UserChatRoom.UserChatRooms;
import org.example.User.User;
import org.example.User.UserDTO;
import org.example.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatRoomsServices {
    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messagesRepository;

    @Autowired
    public ChatRoomsServices(ChatRoomRepository chatRoomRepository, UserChatRoomRepository userChatRoomRepository, UserRepository userRepository, MessageRepository messagesRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.userChatRoomRepository = userChatRoomRepository;
        this.userRepository = userRepository;
        this.messagesRepository = messagesRepository;
    }

    public List<ChatRoomDTO> getChatRoomsByUserId(Integer userId) {
        List<UserChatRooms> userChatRooms = userChatRoomRepository.findByUserId(userId);
        return userChatRooms.stream()
                .map(userChatRoom -> mapToChatRoomDTO(userChatRoom.getChatRoom(), userId))
                .collect(Collectors.toList());
    }

    private ChatRoomDTO mapToChatRoomDTO(ChatRoom chatRoom, Integer userId) {
        List<UserChatRooms> usersInChat = userChatRoomRepository.findByChatRoom(chatRoom);
        UserDTO targetUser = findTargetUser(usersInChat, userId); // Implement this method
        boolean isRead = false;
        List<Messages> messages = messagesRepository.findByChatRoom_IdOrderByTimestamp(chatRoom.getId());
        MessageDTO lastMessage = messages.isEmpty() ? null : MessageDTO.mapFromMessage(messages.get(0));

        return ChatRoomDTO.builder()
                .id(chatRoom.getId())
                .targetUser(targetUser)
                .isRead(isRead)
                .lastMessage(lastMessage)
                .build();
    }
    private UserDTO findTargetUser(List<UserChatRooms> usersInChat, Integer userId) {
        for (UserChatRooms userChatRoom : usersInChat) {
            UserDTO user = UserDTO.fromUser(userChatRoom.getUser());
            if (!user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}
