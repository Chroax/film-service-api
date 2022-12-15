package com.binar.filmservice.controller;

import com.binar.filmservice.dto.FilmRequest;
import com.binar.filmservice.dto.FilmResponse;
import com.binar.filmservice.dto.FilmUpdateRequest;
import com.binar.filmservice.dto.MessageModel;
import com.binar.filmservice.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/film", produces = {"application/json"})
public class FilmController
{
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @Autowired
    FilmService filmService;

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Add Film",
                            description = "Menambahkan film baru",
                            value = """
                                      {
                                      "responseCode": 200,
                                      "responseMessage": "Register new film",
                                      "data": [
                                        {
                                          "film_id": 03aad5f0-5dda-11ed-9b6a-0242ac120002,
                                          "film_name": Petualangan Cahyadi,
                                          "show_status": TRUE,
                                          "description": Ini adalah deskripsi filmnya,
                                          "duration_min": 180,
                                          "release_date": "2022-12-05"
                                        }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageModel> addFilm(@RequestBody FilmRequest filmRequest) {
        MessageModel messageModel = new MessageModel();

        FilmResponse filmResponse = filmService.registerFilm(filmRequest);

        if(filmResponse.getMessage() != null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage(filmResponse.getMessage());
            log.error("Failed create new film, error : {}", filmResponse.getMessage());
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Register new film");
            messageModel.setData(filmResponse);
            log.info("Success create new film with id {}", filmResponse.getFilmId());
        }

        return ResponseEntity.ok().body(messageModel);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Data Films",
                            description = "Mendapatkan semua data film",
                            value = """
                                      {
                                      "responseCode": 200,
                                      "responseMessage": "Success get all film",
                                      "data": [
                                        {
                                          "film_id": 03aad5f0-5dda-11ed-9b6a-0242ac120002,
                                          "film_name": Petualangan Cahyadi,
                                          "show_status": TRUE,
                                          "description": Ini adalah deskripsi filmnya,
                                          "duration_min": 180,
                                          "release_date": "2022-12-05"
                                        },
                                        {
                                          "film_id": 04ssd6c1-8dew-13xd-9b6a-0242ac120002,
                                          "film_name": Petualangan Cahyadi III,
                                          "show_status": TRUE,
                                          "description": Ini adalah deskripsi filmnya III,
                                          "duration_min": 180,
                                          "release_date": "2025-12-05"
                                        }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @GetMapping("/get-all")
    public ResponseEntity<MessageModel> getAllFilms(){

        MessageModel messageModel = new MessageModel();
        try {
            List<FilmResponse> filmsGet = filmService.searchAllFilm();
            messageModel.setMessage("Success get all film");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(filmsGet);
            log.info("Success get all film");
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all film");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            log.error("Failed get all film, error : {}", exception.getMessage());
        }

        return ResponseEntity.ok().body(messageModel);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Data Films By Name",
                            description = "Mendapatkan film berdasarkan filter name",
                            value = """
                                      {
                                      "responseCode": 200,
                                      "responseMessage": "Success get all film by name",
                                      "data": [
                                        {
                                          "film_id": 03aad5f0-5dda-11ed-9b6a-0242ac120002,
                                          "film_name": Petualangan Cahyadi,
                                          "show_status": TRUE,
                                          "description": Ini adalah deskripsi filmnya,
                                          "duration_min": 180,
                                          "release_date": "2022-12-05"
                                        },
                                        {
                                          "film_id": 04ssd6c1-8dew-13xd-9b6a-0242ac120002,
                                          "film_name": Petualangan Cahyadi II,
                                          "show_status": TRUE,
                                          "description": Ini adalah deskripsi filmnya,
                                          "duration_min": 180,
                                          "release_date": "2025-12-05"
                                        }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @GetMapping("/name/{filmName}")
    public ResponseEntity<MessageModel> getFilmByName(@PathVariable String filmName){
        MessageModel messageModel = new MessageModel();
        try {
            List<FilmResponse> filmsGet = filmService.searchFilmByName(filmName);
            messageModel.setMessage("Success get film");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(filmsGet);
            log.info("Success get film with name {} ", filmName);
        }
        catch (Exception exception)
        {
            messageModel.setMessage("Failed get film");
            messageModel.setStatus(HttpStatus.NO_CONTENT.value());
            log.error("Failed get film with name {} ", filmName);
        }
        return ResponseEntity.ok().body(messageModel);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Data Film By Id",
                            description = "Pastikan id film yang valid",
                            value = """
                                      {
                                      "responseCode": 200,
                                      "responseMessage": "Success get film",
                                      "data": [
                                        {
                                          "film_id": 03aad5f0-5dda-11ed-9b6a-0242ac120002,
                                          "film_name": Petualangan Cahyadi II,
                                          "show_status": TRUE,
                                          "description": Ini adalah deskripsi filmnya,
                                          "duration_min": 180,
                                          "release_date": "2022-12-05"
                                        }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @GetMapping("/id/{filmId}")
    public ResponseEntity<MessageModel> getFilmById(@PathVariable UUID filmId){
        MessageModel messageModel = new MessageModel();
        try {
            FilmResponse filmsGet = filmService.searchFilmById(filmId);
            messageModel.setMessage("Success get film");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(filmsGet);
            log.info("Success get film with id {} ", filmId);
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get film");
            messageModel.setStatus(HttpStatus.NO_CONTENT.value());
            log.error("Failed get film with id {} ", filmId);
        }
        return ResponseEntity.ok().body(messageModel);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Data Film By Id",
                            description = "Pastikan id film valid, data yang bisa diubah adalah film_name, show_status, description, duration_min dan release_date.",
                            value = """
                                      {
                                      "responseCode": 200,
                                      "responseMessage": "Update film with id : 04ssd6c1-8dew-13xd-9b6a-0242ac120002",
                                      "data": [
                                        {
                                          "film_id": 03aad5f0-5dda-11ed-9b6a-0242ac120002,
                                          "film_name": Petualangan Cahyadi III,
                                          "show_status": TRUE,
                                          "description": Ini adalah deskripsi filmnya,
                                          "duration_min": 180,
                                          "release_date": "2022-12-05"
                                        }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @PutMapping("/update/{filmId}")
    public ResponseEntity<MessageModel> updateFilm(@PathVariable UUID filmId, @RequestBody FilmUpdateRequest filmUpdateRequest) {
        MessageModel messageModel = new MessageModel();
        FilmResponse filmResponse = filmService.updateFilm(filmUpdateRequest, filmId);

        if(filmResponse.getMessage() != null)
        {
            messageModel.setStatus(HttpStatus.CONFLICT.value());
            messageModel.setMessage(filmResponse.getMessage());
            log.error("Failed update film with id {}, error : {} ", filmId, filmResponse.getMessage());
        }
        else
        {
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setMessage("Update film with id : " + filmId);
            messageModel.setData(filmResponse);
            log.info("Success update film with id {}", filmId);
        }

        return ResponseEntity.ok().body(messageModel);
    }

    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                    @ExampleObject(name = "Delete Film",
                            description = "Pastikan id film valid.",
                            value = "{\"responseCode\": 200, \"responseMessage\": \"Success delete film by id : 04ssd6c1-8dew-13xd-9b6a-0242ac120002\"}")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @DeleteMapping("/delete/{filmId}")
    public ResponseEntity<MessageModel> deleteFilm(@PathVariable UUID filmId){
        MessageModel messageModel = new MessageModel();
        Boolean deleteFilm = filmService.deleteFilm(filmId);

        if(Boolean.TRUE.equals(deleteFilm))
        {
            messageModel.setMessage("Success delete film by id : " + filmId);
            messageModel.setStatus(HttpStatus.OK.value());
            log.info("Success delete film with id {}", filmId);
        }
        else
        {
            messageModel.setMessage("Failed delete film by id : " + filmId + ", not found");
            messageModel.setStatus(HttpStatus.NO_CONTENT.value());
            log.error("Failed update film with id {}, error : {} ", filmId, "id not found");
        }

        return ResponseEntity.ok().body(messageModel);
    }


    @Operation(responses = {
            @ApiResponse(responseCode = "200", content = @Content(examples = {
                        @ExampleObject(name = "Showing Films",
                            description = "Mendapatkan film yang sedang tayang",
                            value = """
                                      {
                                      "responseCode": 200,
                                      "responseMessage": "Success get all film showing",
                                      "data": [
                                        {
                                          "film_id": 03aad5f0-5dda-11ed-9b6a-0242ac120002,
                                          "film_name": Petualangan Cahyadi,
                                          "show_status": TRUE,
                                          "description": Ini adalah deskripsi filmnya,
                                          "duration_min": 180,
                                          "release_date": "2022-12-05"
                                        },
                                        {
                                          "film_id": 04ssd6c1-8dew-13xd-9b6a-0242ac120002,
                                          "film_name": Petualangan Cahyadi II,
                                          "show_status": TRUE,
                                          "description": Ini adalah deskripsi filmnya,
                                          "duration_min": 180,
                                          "release_date": "2025-12-05"
                                        }
                                      ]
                                    }""")
            }, mediaType = MediaType.APPLICATION_JSON_VALUE))})
    @GetMapping("/showing")
    public ResponseEntity<MessageModel> getAllShowingFilms(){

        MessageModel messageModel = new MessageModel();
        try {
            List<FilmResponse> filmsGet = filmService.searchFilmShowing();
            messageModel.setMessage("Success get all film showing");
            messageModel.setStatus(HttpStatus.OK.value());
            messageModel.setData(filmsGet);
            log.info("Success get all film showing");
        }catch (Exception exception)
        {
            messageModel.setMessage("Failed get all film showing");
            messageModel.setStatus(HttpStatus.BAD_GATEWAY.value());
            log.error("Failed get all film showing, error : {}", exception.getMessage());
        }

        return ResponseEntity.ok().body(messageModel);
    }
}
