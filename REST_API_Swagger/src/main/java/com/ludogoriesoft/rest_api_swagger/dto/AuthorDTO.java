package com.ludogoriesoft.rest_api_swagger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    private Long id;
    private String fistName;
    private String lastName;
    private int birthYear;
}
