package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.domain.VisitTO;
import com.tecsup.petclinic.entities.Visits;
import com.tecsup.petclinic.exception.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class VisitController {

    private VisitService visitService;
    private VisitMapper mapper;

    /**
     * Constructor
     * @param visitService
     * @param mapper
     */
    public VisitController(VisitService visitService, VisitMapper mapper) {
        this.visitService = visitService;
        this.mapper = mapper;
    }

    /**
     * Get all visits
     *
     * @return
     */
    @GetMapping(value = "/visits")
    public ResponseEntity<List<VisitTO>> findAllVisits() {
        List<Visits> visits = visitService.findAll();
        log.info("visits: " + visits);
        visits.forEach(item -> log.info("Visit >>  {} ", item));

        List<VisitTO> visitsTO = this.mapper.toVisitTOList(visits);
        log.info("visitsTO: " + visitsTO);
        visitsTO.forEach(item -> log.info("VisitTO >>  {} ", item));

        return ResponseEntity.ok(visitsTO);
    }

    /**
     * Create visit
     *
     * @param visitTO
     * @return
     */
    @PostMapping(value = "/visits")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<VisitTO> create(@RequestBody VisitTO visitTO) {
        Visits newVisit = this.mapper.toVisit(visitTO);
        VisitTO newVisitTO = this.mapper.toVisitTO(visitService.create(newVisit));

        return ResponseEntity.status(HttpStatus.CREATED).body(newVisitTO);
    }

    /**
     * Find visit by id
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/visits/{id}")
    ResponseEntity<VisitTO> findById(@PathVariable Integer id) {
        VisitTO visitTO = null;

        try {
            Visits visit = visitService.findById(id);
            visitTO = this.mapper.toVisitTO(visit);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(visitTO);
    }

    /**
     * Update and create visit
     *
     * @param visitTO
     * @param id
     * @return
     */
    @PutMapping(value = "/visits/{id}")
    ResponseEntity<VisitTO> update(@RequestBody VisitTO visitTO, @PathVariable Integer id) {
        VisitTO updatedVisitTO = null;

        try {
            Visits updateVisit = visitService.findById(id);

            updateVisit.setVisitDate(visitTO.getVisitDate());
            updateVisit.setDescription(visitTO.getDescription());

            visitService.update(updateVisit);

            updatedVisitTO = this.mapper.toVisitTO(updateVisit);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedVisitTO);
    }

    /**
     * Delete visit by id
     *
     * @param id
     */
    @DeleteMapping(value = "/visits/{id}")
    ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            visitService.delete(id);
            return ResponseEntity.ok("Deleted Visit with ID: " + id);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
