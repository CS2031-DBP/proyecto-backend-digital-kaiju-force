package com.dkf.ODAD.Tratamiento;

import com.dkf.ODAD.Tratamiento.Domain.Tratamiento;
import com.dkf.ODAD.Tratamiento.Infraestructure.TratamientoRepository;
import com.dkf.ODAD.Tratamiento.Service.TratamientoService;
import com.dkf.ODAD.utils.Reader;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = "MEDICO")
@SpringBootTest
@AutoConfigureMockMvc
public class TratamientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private TratamientoService tratamientoService;

    @Autowired
    Reader reader;

    String token = "";

    @BeforeEach
    public void setUp() throws Exception {
        tratamientoRepository.deleteAll();

        String jsonContent = Reader.readJsonFile("/tratamiento/post.json");

        ResultActions res = mockMvc.perform(post("/Tratamiento/addTratamiento")
                .contentType(APPLICATION_JSON)
                .content(jsonContent));

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res.andReturn().getResponse().getContentAsString()));
        token = jsonObject.getString("token");
        System.out.println("Token: " + token);
    }

    @Test
    public void testGetAllTratamientos() throws Exception {
        mockMvc.perform(get("/Tratamiento/getTratamientos")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTratamientoById() throws Exception {
        Tratamiento tratamiento = tratamientoRepository.findAll().get(0);
        Long tratamientoId = tratamiento.getId();
        mockMvc.perform(get("/Tratamiento/{id}", tratamientoId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddTratamiento() throws Exception {
        String jsonContent = Reader.readJsonFile("/tratamiento/post.json");
        mockMvc.perform(post("/Tratamiento/addTratamiento")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());
    }

    @Test
    public void testDeleteTratamiento() throws Exception {
        Tratamiento tratamiento = tratamientoRepository.findAll().get(0);
        Long tratamientoId = tratamiento.getId();
        mockMvc.perform(delete("/Tratamiento/{id}", tratamientoId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateTratamiento() throws Exception {
        Tratamiento tratamiento = tratamientoRepository.findAll().get(0);
        Long tratamientoId = tratamiento.getId();
        String jsonContent = Reader.readJsonFile("/tratamiento/patch.json");
        mockMvc.perform(put("/Tratamiento/{id}", tratamientoId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }
}
