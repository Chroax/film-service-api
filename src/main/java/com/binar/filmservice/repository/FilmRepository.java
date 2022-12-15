package com.binar.filmservice.repository;

import com.binar.filmservice.model.Films;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FilmRepository extends JpaRepository<Films, UUID>
{
    @Query("SELECT f FROM Films f WHERE f.showStatus = (:isShowing)")
    List<Films> findAllShowingFilm(@Param("isShowing") Boolean isShowing);

    @Query("SELECT f FROM Films f WHERE LOWER(f.filmName) LIKE LOWER(:filmName)")
    List<Films> findByName(@Param("filmName") String filmName);
}
