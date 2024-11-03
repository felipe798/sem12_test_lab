package com.tecsup.petclinic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialtyTO {

    private Integer id;

    private String name;

    private String office;

    private int HOpen;

    private int HClose;
}
