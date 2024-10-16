package com.greymatter.accounts.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PersonDto {
    private Long id;
    private String doi;
    private String lastName;
    private String firstName;
    private String middleName;
    private String gender;
    private LocalDateTime dateOfBirth;
    private LocalDateTime inscriptionDate;
    private LocalDateTime expirationDate;
}
