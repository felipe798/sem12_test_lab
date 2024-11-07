package com.tecsup.petclinic.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "visits")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Visits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "visit_date")
    private Date visitDate;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    public Visits(Date visitDate, String description, Pet pet) {
        super();
        this.visitDate = visitDate;
        this.description = description;
        this.pet = pet;
    }
}
