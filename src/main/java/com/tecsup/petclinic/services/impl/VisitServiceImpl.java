package com.tecsup.petclinic.services.impl;

import com.tecsup.petclinic.entities.Visits;
import com.tecsup.petclinic.exception.VisitNotFoundException;
import com.tecsup.petclinic.repositories.VisitRepository;
import com.tecsup.petclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    /**
     * Crear una visita
     *
     * @param visit
     * @return
     */
    @Override
    public Visits create(Visits visit) {
        return visitRepository.save(visit);
    }

    /**
     * Actualizar una visita
     *
     * @param visit
     * @return
     */
    @Override
    public Visits update(Visits visit) {
        return visitRepository.save(visit);
    }

    /**
     * Eliminar una visita por ID
     *
     * @param id
     * @throws VisitNotFoundException
     */
    @Override
    public void delete(Integer id) throws VisitNotFoundException {
        Visits visit = findById(id);
        visitRepository.delete(visit);
    }

    /**
     * Buscar una visita por ID
     *
     * @param id
     * @return
     * @throws VisitNotFoundException
     */
    @Override
    public Visits findById(Integer id) throws VisitNotFoundException {
        Optional<Visits> visit = visitRepository.findById(id);

        if (!visit.isPresent())
            throw new VisitNotFoundException("Visita no encontrada!");

        return visit.get();
    }

    /**
     * Buscar visitas por descripción
     *
     * @param description
     * @return
     */
    @Override
    public List<Visits> findByDescription(String description) {
        List<Visits> visits = visitRepository.findByDescription(description);

        visits.forEach(visit -> log.info("" + visit));

        return visits;
    }

    /**
     * Buscar visitas por ID de la mascota
     *
     * @param petId
     * @return
     */
    @Override
    public List<Visits> findByPetId(Integer petId) {
        List<Visits> visits = visitRepository.findByPet_Id(petId);

        visits.forEach(visit -> log.info("" + visit));

        return visits;
    }

    /**
     * Obtener todas las visitas
     *
     * @return
     */
    @Override
    public List<Visits> findAll() {
        return visitRepository.findAll();
    }
}
