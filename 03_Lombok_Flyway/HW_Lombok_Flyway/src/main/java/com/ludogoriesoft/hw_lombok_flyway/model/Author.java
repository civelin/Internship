package com.ludogoriesoft.hw_lombok_flyway.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="authors")
public class Author {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private Integer birthYear;


}
