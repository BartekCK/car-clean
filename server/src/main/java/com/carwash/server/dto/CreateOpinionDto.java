package com.carwash.server.dto;

import com.carwash.server.models.Opinion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateOpinionDto {
    private String username;
    private int mark;
    private LocalDateTime date;
    private String description;
    private String image;

    public static CreateOpinionDto build(Opinion opinion) {
        return new CreateOpinionDto(
                opinion.getUser().getUsername(),
                opinion.getMark(),
                opinion.getDate(),
                opinion.getDescription(),
                opinion.getImage()
        );
    }
}
