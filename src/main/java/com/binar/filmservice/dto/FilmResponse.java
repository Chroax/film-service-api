package com.binar.filmservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FilmResponse {

    private UUID filmId;
    private String filmName;
    private Boolean showStatus;
    private String description;
    private Integer durationMin;
    private LocalDate releaseDate;
    private String message;
}
