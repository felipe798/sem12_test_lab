package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Visits;
import com.tecsup.petclinic.exception.VisitNotFoundException;

import java.util.List;

public interface VisitService {

    /**
     * Crear una visita
     *
     * @param visit
     * @return
     */
    Visits create(Visits visit);

    /**
     * Actualizar una visita
     *
     * @param visit
     * @return
     */
    Visits update(Visits visit);

    /**
     * Eliminar una visita por ID
     *
     * @param id
     * @throws VisitNotFoundException
     */
    void delete(Integer id) throws VisitNotFoundException;

    /**
     * Buscar una visita por ID
     *
     * @param id
     * @return
     * @throws VisitNotFoundException
     */
    Visits findById(Integer id) throws VisitNotFoundException;

    /**
     * Buscar visitas por descripción
     *
     * @param description
     * @return
     */
    List<Visits> findByDescription(String description);

    /**
     * Buscar visitas por ID de la mascota
     *
     * @param petId
     * @return
     */
    List<Visits> findByPetId(Integer petId);

    /**
     * Obtener todas las visitas
     *
     * @return
     */
    List<Visits> findAll();
}
