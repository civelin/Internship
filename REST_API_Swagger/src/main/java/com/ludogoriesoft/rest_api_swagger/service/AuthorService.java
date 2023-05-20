package com.ludogoriesoft.rest_api_swagger.service;

import com.ludogoriesoft.rest_api_swagger.dto.AuthorDTO;
import com.ludogoriesoft.rest_api_swagger.entity.Author;
import com.ludogoriesoft.rest_api_swagger.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepo;
    private final ModelMapper modelMapper;


    public List<AuthorDTO> getAllAuthors() {

        return authorRepo.findAll()
                .stream()
                .map(author -> modelMapper.map(author, AuthorDTO.class))
                .toList();
    }

    public AuthorDTO findAuthorById(Long id) {
        Optional<Author> author = authorRepo.findById(id);

        return author.map(value -> modelMapper.map(value, AuthorDTO.class)).orElse(null);

    }

    public AuthorDTO saveAuthor(Author author) {
        Author savedAuthor = authorRepo.save(author);
        return modelMapper.map(savedAuthor, AuthorDTO.class);
    }

    public void updateAuthor(Long id, Author author) {
        Optional<Author> author1 = authorRepo.findById(id);

        if (author1.isPresent()) {

            author1.get().setFirstName(author.getFirstName());
            author1.get().setLastName(author.getLastName());
            author1.get().setBirthYear(author.getBirthYear());

            authorRepo.save(author1.get());
        } else {
            throw new RuntimeException("Author not found");
        }
    }

    public void deleteAuthor(Long id) {
        authorRepo.deleteById(id);
    }
}
