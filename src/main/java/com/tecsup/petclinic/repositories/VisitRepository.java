package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Visits;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visits, Integer> {

    List<Visits> findByDescription(String description);

    List<Visits> findByPet_Id(Integer petId);  // usando 'pet_Id' para acceder al campo de la relación con Pet

    @Override
    List<Visits> findAll();
}
