package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.entities.Visits;
import com.tecsup.petclinic.entities.Pet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VisitControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllVisits() throws Exception {
        this.mockMvc.perform(get("/visits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    public void testFindVisitById() throws Exception {
        int visitId = 1;  // Asume que el ID de la visita existe en la base de datos

        mockMvc.perform(get("/visits/" + visitId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(visitId)));
    }

    @Test
    public void testCreateVisit() throws Exception {
        Date visitDate = new Date();
        String description = "Checkup";
        Pet pet = new Pet();  // Crear o establecer un ID existente de mascota
        pet.setId(1);  // Asume que el ID de la mascota existe

        Visits newVisit = new Visits(visitDate, description, pet);

        mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is(description)));
    }

    @Test
    public void testUpdateVisit() throws Exception {
        Date visitDate = new Date();
        String description = "Initial checkup";
        Pet pet = new Pet();
        pet.setId(1);  // Asume que el ID de la mascota existe

        // Crear una nueva visita primero
        Visits newVisit = new Visits(visitDate, description, pet);
        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer visitId = JsonPath.parse(response).read("$.id");

        // Actualizar la visita creada
        String updatedDescription = "Follow-up visit";
        Visits updatedVisit = new Visits(visitDate, updatedDescription, pet);
        updatedVisit.setId(visitId);

        mockMvc.perform(put("/visits/" + visitId)
                        .content(om.writeValueAsString(updatedVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // Verificar que la visita se actualizó correctamente
        mockMvc.perform(get("/visits/" + visitId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(visitId)))
                .andExpect(jsonPath("$.description", is(updatedDescription)));
    }

    @Test
    public void testDeleteVisit() throws Exception {
        Date visitDate = new Date();
        String description = "Temporary visit";
        Pet pet = new Pet();
        pet.setId(1);  // Asume que el ID de la mascota existe

        Visits newVisit = new Visits(visitDate, description, pet);

        ResultActions mvcActions = mockMvc.perform(post("/visits")
                        .content(om.writeValueAsString(newVisit))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer visitId = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/visits/" + visitId))
                .andExpect(status().isOk());

        // Confirmar que la visita fue eliminada
        mockMvc.perform(get("/visits/" + visitId))
                .andExpect(status().isNotFound());
    }
}
