package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.domain.SpecialtyTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exception.SpecialtyNotFoundException;
import com.tecsup.petclinic.mapper.SpecialtyMapper;
import com.tecsup.petclinic.services.SpecialtyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class SpecialtyController {

    //@Autowired
    private SpecialtyService specialtyService;

    //@Autowired
    private SpecialtyMapper mapper;

    /**
     *  Change
     * @param specialtyService
     * @param mapper
     */
    public SpecialtyController(SpecialtyService specialtyService, SpecialtyMapper mapper){
        this.specialtyService = specialtyService;
        this.mapper = mapper ;
    }

    /**
     * Get all specialties
     *
     * @return
     */
    @GetMapping(value = "/specialties")
    public ResponseEntity<List<SpecialtyTO>> findAllSpecialties() {

        List<Specialty> specialties = specialtyService.findAll();
        log.info("specialties: " + specialties);
        specialties.forEach(item -> log.info("Specialty >>  {} ", item));

        List<SpecialtyTO> specialtiesTO = this.mapper.toSpecialtyTOList(specialties);
        log.info("specialtiesTO: " + specialtiesTO);
        specialtiesTO.forEach(item -> log.info("SpecialtyTO >>  {} ", item));

        return ResponseEntity.ok(specialtiesTO);

    }

    /**
     * Create specialty
     *
     * @param specialtyTO
     * @return
     */
    @PostMapping(value = "/specialties")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<SpecialtyTO> create(@RequestBody SpecialtyTO specialtyTO) {

        Specialty newSpecialty = this.mapper.toSpecialty(specialtyTO);
        SpecialtyTO newSpecialtyTO = this.mapper.toSpecialtyTO(specialtyService.create(newSpecialty));

        return  ResponseEntity.status(HttpStatus.CREATED).body(newSpecialtyTO);

    }


    /**
     * Find specialty by id
     *
     * @param id
     * @return
     * @throws SpecialtyNotFoundException
     */
    @GetMapping(value = "/specialties/{id}")
    ResponseEntity<SpecialtyTO> findById(@PathVariable Integer id) {

        SpecialtyTO specialtyTO = null;

        try {
            Specialty specialty = specialtyService.findById(id);
            specialtyTO = this.mapper.toSpecialtyTO(specialty);

        } catch (SpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specialtyTO);
    }

    /**
     * Update and create specialty
     *
     * @param specialtyTO
     * @param id
     * @return
     */
    @PutMapping(value = "/specialties/{id}")
    ResponseEntity<SpecialtyTO>  update(@RequestBody SpecialtyTO specialtyTO, @PathVariable Integer id) {

        SpecialtyTO updateSpecialtyTO = null;

        try {

            Specialty updateSpecialty = specialtyService.findById(id);

            updateSpecialty.setName(specialtyTO.getName());
            updateSpecialty.setOffice(specialtyTO.getOffice());
            updateSpecialty.setHOpen(specialtyTO.getHOpen());
            updateSpecialty.setHClose(specialtyTO.getHClose());

            specialtyService.update(updateSpecialty);

            updateSpecialtyTO = this.mapper.toSpecialtyTO(updateSpecialty);

        } catch (SpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updateSpecialtyTO);
    }

    /**
     * Delete specialties by id
     *
     * @param id
     */
    @DeleteMapping(value = "/specialties/{id}")
    ResponseEntity<String> delete(@PathVariable Integer id) {

        try {
            specialtyService.delete(id);
            return ResponseEntity.ok(" Delete ID :" + id);
        } catch (SpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
