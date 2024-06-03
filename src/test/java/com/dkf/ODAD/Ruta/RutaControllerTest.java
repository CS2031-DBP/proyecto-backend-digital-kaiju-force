package com.dkf.ODAD.Ruta;

import com.dkf.ODAD.Ruta.Infraestructure.RutaRepository;
import com.dkf.ODAD.Ruta.Service.RutaService;
import com.dkf.ODAD.utils.Reader;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = "johndoe@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
@WithMockUser(roles = "MEDICO")
@SpringBootTest
@AutoConfigureMockMvc
public class RutaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private RutaService rutaService;

    @Autowired
    Reader reader;

    String token = "";


    @BeforeEach
    public void setUp() throws Exception {
        rutaRepository.deleteAll();

        String jsonContent = Reader.readJsonFile("/ruta/post.json");

        var res = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = jsonObject.getString("token");
        System.out.println("Token: " + token);
    }

    @Test
    public void testAuthorizedAccessToGetRutas() throws Exception {
        Long authorizedRutaId = 1L;

        mockMvc.perform(get("/ruta/{id}", authorizedRutaId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthorizedAccessToCreateRuta() throws Exception {
        String jsonContent = Reader.readJsonFile("/ruta/post.json");

        mockMvc.perform(post("/ruta")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "PACIENTE")
    public void testPacienteAccessToDeleteRuta() throws Exception {
        Long authorizedRutaId = 1L;

        mockMvc.perform(delete("/ruta/{id}", authorizedRutaId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
