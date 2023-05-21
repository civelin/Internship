package com.ludogoriesoft.rest_api_swagger.controller;

import com.ludogoriesoft.rest_api_swagger.dto.AuthorDTO;
import com.ludogoriesoft.rest_api_swagger.entity.Author;
import com.ludogoriesoft.rest_api_swagger.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/library/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping("/")
    @ApiOperation(value = "Find all authors")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @ApiOperation(value = "Find an author by id")
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable Long id) {
        AuthorDTO author = authorService.findAuthorById(id);

        if (author != null) {
            return ResponseEntity.ok(author);
        }

        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "Create an author")
    @PostMapping("/")
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody Author author,
                                             UriComponentsBuilder uriComponentsBuilder) {

        URI location = uriComponentsBuilder.path("/authors/{id}")
                .buildAndExpand(authorService.saveAuthor(author).getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update author by id")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id,
                                               @RequestBody Author author) {

        try {
            authorService.updateAuthor(id, author);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete author by id")
    public ResponseEntity<AuthorDTO> deleteAuthor(@PathVariable Long id) {

        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
}
