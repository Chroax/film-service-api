package com.binar.filmservice.service.impl;

import com.binar.filmservice.dto.FilmRequest;
import com.binar.filmservice.dto.FilmResponse;
import com.binar.filmservice.dto.FilmUpdateRequest;
import com.binar.filmservice.exception.NotFoundException;
import com.binar.filmservice.model.Films;
import com.binar.filmservice.repository.FilmRepository;
import com.binar.filmservice.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FilmServiceImpl implements FilmService
{
    @Autowired
    FilmRepository filmRepository;

    @Override
    public FilmResponse registerFilm(FilmRequest filmRequest) {

        Films films = filmRequest.toFilms();

        try {
            filmRepository.saveAndFlush(films);
            return FilmResponse.builder()
                    .filmId(films.getFilmId())
                    .filmName(films.getFilmName())
                    .showStatus(films.getShowStatus())
                    .description(films.getDescription())
                    .durationMin(films.getDurationMin())
                    .releaseDate(films.getReleaseDate())
                    .build();
        }
        catch(Exception exception)
        {
            return FilmResponse.builder()
                    .message("Film already exist")
                    .build();
        }
    }

    @Override
    public FilmResponse searchFilmById(UUID filmId) {
        Optional<Films> isFilms = filmRepository.findById(filmId);
        if (isFilms.isEmpty()) {
            throw new NotFoundException("Film with id: " + filmId + " not found");
        } else {
            Films films = isFilms.get();
            return FilmResponse.builder()
                    .filmId(films.getFilmId())
                    .filmName(films.getFilmName())
                    .showStatus(films.getShowStatus())
                    .description(films.getDescription())
                    .durationMin(films.getDurationMin())
                    .releaseDate(films.getReleaseDate())
                    .build();
        }
    }

    @Override
    public List<FilmResponse> searchAllFilm() {
        List<Films> allFilm = filmRepository.findAll();
        return toListFilmResponses(allFilm);
    }

    @Override
    public List<FilmResponse> searchFilmByName(String filmName) {
        List<Films> allFilm = filmRepository.findByName(filmName);
        return toListFilmResponses(allFilm);
    }

    @Override
    public List<FilmResponse> searchFilmShowing() {
        List<Films> allFilm = filmRepository.findAllShowingFilm(true);
        return toListFilmResponses(allFilm);
    }

    @Override
    public FilmResponse updateFilm(FilmUpdateRequest filmUpdateRequest, UUID filmId) {
        Optional<Films> isFilm = filmRepository.findById(filmId);
        if (isFilm.isPresent()) {
            Films films = isFilm.get();
            if (filmUpdateRequest.getFilmName() != null)
                films.setFilmName(filmUpdateRequest.getFilmName());
            if (filmUpdateRequest.getShowStatus() != null)
                films.setShowStatus(filmUpdateRequest.getShowStatus());
            if (filmUpdateRequest.getDescription() != null)
                films.setDescription(filmUpdateRequest.getDescription());
            if (filmUpdateRequest.getDurationMin() != null)
                films.setDurationMin(filmUpdateRequest.getDurationMin());
            if (filmUpdateRequest.getReleaseDate() != null)
                films.setReleaseDate(filmUpdateRequest.getReleaseDate());

            filmRepository.saveAndFlush(films);

            return FilmResponse.builder()
                    .filmId(films.getFilmId())
                    .filmName(films.getFilmName())
                    .showStatus(films.getShowStatus())
                    .description(films.getDescription())
                    .durationMin(films.getDurationMin())
                    .releaseDate(films.getReleaseDate())
                    .build();
        } else {
            throw new NotFoundException("Film with id: " + filmId + " not found");
        }
    }

    @Override
    public Boolean deleteFilm(UUID filmId) {
        if(filmRepository.existsById(filmId)) {
            filmRepository.deleteById(filmId);
            return true;
        }
        else
            return false;
    }

    private List<FilmResponse> toListFilmResponses(List<Films> allFilm) {
        List<FilmResponse> allFilmResponse = new ArrayList<>();
        for (Films film : allFilm) {
            FilmResponse filmResponse = FilmResponse.builder()
                    .filmId(film.getFilmId())
                    .filmName(film.getFilmName())
                    .showStatus(film.getShowStatus())
                    .description(film.getDescription())
                    .durationMin(film.getDurationMin())
                    .releaseDate(film.getReleaseDate())
                    .build();
            allFilmResponse.add(filmResponse);
        }
        return allFilmResponse;
    }
}
