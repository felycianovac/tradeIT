package org.example.Chat.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessagePayload {
    private Integer roomId;
    private Integer senderId;
    private String message;
    private String language;
}