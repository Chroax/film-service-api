package com.binar.filmservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "films",
        uniqueConstraints = {
                @UniqueConstraint(name = "film_name", columnNames = "film_name")
        })
public class Films {

    @Id
    @GeneratedValue
    @Column(name = "film_id")
    private UUID filmId;

    @Column(name = "film_name", nullable = false, length = 256)
    private String filmName;

    @Column(name = "show_status", nullable = false)
    private Boolean showStatus;

    @Column(name = "description", nullable = false, columnDefinition="TEXT")
    private String description;

    @Column(name = "duration_min", nullable = false)
    private Integer durationMin;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+7")
    @Column(name = "release_date", nullable = false, columnDefinition="DATE")
    private LocalDate releaseDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
    @Column(name = "modified_at")
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
