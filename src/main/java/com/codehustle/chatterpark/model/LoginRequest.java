package com.codehustle.chatterpark.model;

import com.codehustle.chatterpark.enums.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest implements Serializable {

    private String userEmail;
    private String username;
    private String password;
    private LocalDate dob;
    private Gender gender;
}
