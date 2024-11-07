package com.tecsup.petclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VisitTO {

    private Integer id;
    private Date visitDate;
    private String description;
    private Integer petId;
}
