package org.example.Chat.ChatRooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/chat")
public class ChatRoomsController {
    private final ChatRoomsServices chatRoomService;

    @Autowired
    public ChatRoomsController(ChatRoomsServices chatRoomService) {
        this.chatRoomService = chatRoomService;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<ChatRoomDTO>>getChatRoomsByUserId(@PathVariable Integer userId) {
        List<ChatRoomDTO> chatRoomDTOs = chatRoomService.getChatRoomsByUserId(userId);
        if (chatRoomDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(chatRoomDTOs, HttpStatus.OK);
        }
    }
}