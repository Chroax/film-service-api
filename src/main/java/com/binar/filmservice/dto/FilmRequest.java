package com.binar.filmservice.dto;

import com.binar.filmservice.model.Films;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FilmRequest {

    @NotEmpty(message = "Film name is required.")
    private String filmName;

    @NotEmpty(message = "Show status is required.")
    private Boolean showStatus = false;

    @NotEmpty(message = "Description is required.")
    private String description;

    @NotEmpty(message = "Duration is required.")
    private Integer durationMin;

    @NotEmpty(message = "Release date is required.")
    private LocalDate releaseDate;

    public Films toFilms() {
        return Films.builder()
                .filmName(this.filmName)
                .showStatus(this.showStatus)
                .description(this.description)
                .durationMin(this.durationMin)
                .releaseDate(this.releaseDate)
                .build();
    }
}
