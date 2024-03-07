package com.codehustle.chatterpark.model;

import com.codehustle.chatterpark.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel implements Serializable {

    private Long userId;
    private String username;
    private Integer age;
    private Gender gender;
}
