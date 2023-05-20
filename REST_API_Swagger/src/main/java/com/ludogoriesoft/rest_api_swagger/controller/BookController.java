package com.ludogoriesoft.rest_api_swagger.controller;

import com.ludogoriesoft.rest_api_swagger.dto.BookDTO;
import com.ludogoriesoft.rest_api_swagger.entity.Book;
import com.ludogoriesoft.rest_api_swagger.service.BookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/library/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    @ApiOperation(value = "Found all books")
    public ResponseEntity<List<BookDTO>> findAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id)")
    @ApiOperation(value = "Find book by id")
    public ResponseEntity<BookDTO> findBookById(@PathVariable Long id) {
        BookDTO book = bookService.findBookById(id);

        if (book != null) {
            return ResponseEntity.ok(book);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    @ApiOperation(value = "Create a book")
    public ResponseEntity<BookDTO> createBook(@RequestBody Book book, UriComponentsBuilder uriComponentsBuilder) {
        BookDTO createdBook = bookService.saveBook(book);

        URI location = uriComponentsBuilder.path("/books/{id}")
                .buildAndExpand(createdBook.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update book by id")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {

        try {
            bookService.updateBook(id, book);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete book by id")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBookById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
