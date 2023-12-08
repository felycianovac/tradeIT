package org.example.Barter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BarterDTO {

    private Integer id;

    private BarterProductDTO offered_by;

    private BarterProductDTO requested_from;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date initiated_at;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Date last_updated;

    private String message;

    public static BarterDTO fromEntity(Barter barter) {
        return BarterDTO.builder()
                .id(barter.getId())
                .offered_by(BarterProductDTO.fromEntity(barter.getOfferedId()))
                .requested_from(BarterProductDTO.fromEntity(barter.getRequestedId()))
                .status(barter.getStatus())
                .initiated_at(barter.getInitialize_at())
                .last_updated(barter.getLast_updated())
                .message(barter.getMessage())
                .build();
    }
}
