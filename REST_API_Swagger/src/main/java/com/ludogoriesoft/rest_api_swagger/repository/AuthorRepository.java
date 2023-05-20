package com.ludogoriesoft.rest_api_swagger.repository;

import com.ludogoriesoft.rest_api_swagger.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
