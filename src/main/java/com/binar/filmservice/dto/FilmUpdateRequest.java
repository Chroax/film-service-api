package com.binar.filmservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FilmUpdateRequest {
    private String filmName;
    private Boolean showStatus;
    private String description;
    private Integer durationMin;
    private LocalDate releaseDate;
}
