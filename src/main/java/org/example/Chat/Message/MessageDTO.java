package org.example.Chat.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.User.UserDTO;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private Integer messageId;
    private UserDTO sender;
    private String text;
    private LocalDateTime timestamp;

    public static MessageDTO mapFromMessage(Messages message){
        return new MessageDTO(
                message.getId(),
                UserDTO.fromUser(message.getSender()),
                message.getMessageText(),
                message.getTimestamp()
        );
    }
}