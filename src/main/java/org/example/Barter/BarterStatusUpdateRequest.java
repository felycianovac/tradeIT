package org.example.Barter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BarterStatusUpdateRequest {
    private String status;
    private Integer user_id;
}
