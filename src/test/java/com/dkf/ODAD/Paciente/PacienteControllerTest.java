package com.dkf.ODAD.Paciente;

import com.dkf.ODAD.Paciente.Infraestructure.PacienteRepository;
import com.dkf.ODAD.Paciente.Service.PacienteService;
import com.dkf.ODAD.utils.Reader;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = "johndoe@example.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
@WithMockUser(roles = "PACIENTE")
@SpringBootTest
@AutoConfigureMockMvc
public class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    Reader reader;

    String token = "";

    private void createUnauthorizedPaciente() throws Exception {
        String jsonContent = Reader.readJsonFile("Paciente/post.json");
        jsonContent = reader.updateEmail(jsonContent, "email", "other@example.com");

        mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();
    }

    @BeforeEach
    public void setUp() throws Exception {
        pacienteRepository.deleteAll();

        String jsonContent = Reader.readJsonFile("/paciente/post.json");

        var res = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = jsonObject.getString("token");
        System.out.println("Token: " + token);
    }

    @Test
    public void testAuthorizedAccessToGetOwnPacienteInfo() throws Exception {
        mockMvc.perform(get("/Paciente/me")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void testAuthorizedAccessToGetDriverById() throws Exception {
        Long authorizedPacienteId = pacienteRepository
                .findByEmail("johndoe@example.com")
                .orElseThrow()
                .getId();

        mockMvc.perform(get("/Paciente/{id}", authorizedPacienteId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testUnauthorizedAccessToGetOwnPacienteInfo() throws Exception {
        mockMvc.perform(get("/Paciente/me")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAuthorizedAccessToGetPacientes() throws Exception {
        mockMvc.perform(get("/Paciente/getPacientes")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthorizedAccessToAddPaciente() throws Exception {
        String jsonContent = Reader.readJsonFile("/Paciente/post.json");

        mockMvc.perform(post("/Paciente/addPaciente")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "MEDICO")
    public void testUnauthorizedAccessToDeletePaciente() throws Exception {
        Long authorizedPacienteId = 1L;

        mockMvc.perform(delete("/Paciente/{id}", authorizedPacienteId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAuthorizedAccessToUpdatePacienteInfo() throws Exception {
        String jsonContent = Reader.readJsonFile("/Paciente/patch.json");
        Long authorizedPacienteId = pacienteRepository
                .findByEmail("johndoe@example.com")
                .orElseThrow()
                .getId();

        mockMvc.perform(patch("/Paciente/{id}", authorizedPacienteId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }
}
