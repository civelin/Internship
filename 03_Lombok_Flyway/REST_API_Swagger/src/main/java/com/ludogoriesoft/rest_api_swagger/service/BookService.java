package com.ludogoriesoft.rest_api_swagger.service;

import com.ludogoriesoft.rest_api_swagger.dto.BookDTO;
import com.ludogoriesoft.rest_api_swagger.repository.BookRepository;
import com.ludogoriesoft.rest_api_swagger.entity.Book;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .toList();
    }

    public BookDTO findBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(value -> modelMapper.map(value, BookDTO.class)).orElse(null);
    }

    public BookDTO saveBook(Book book) {

         Book savedBook = bookRepository.save(book);
         return modelMapper.map(savedBook, BookDTO.class);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public void updateBook(Long id, Book book) {
        Optional<Book> book1 = bookRepository.findById(id);

        if (book1.isPresent()) {
            book1.get().setAuthor(book.getAuthor());
            book1.get().setTitle(book.getTitle());
            bookRepository.save(book1.get());
        } else {
            throw new RuntimeException("Book is not found");
        }
    }
}
