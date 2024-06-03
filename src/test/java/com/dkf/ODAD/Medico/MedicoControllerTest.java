package com.dkf.ODAD.Medico;

import com.dkf.ODAD.Medico.Domain.Medico;
import com.dkf.ODAD.Medico.Infraestructure.MedicoRepository;
import com.dkf.ODAD.Medico.Service.MedicoService;
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
@WithMockUser(roles = "MEDICO")
@SpringBootTest
@AutoConfigureMockMvc
public class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    Reader reader;

    String token = "";

    private void createUnauthorizedMedico() throws Exception {
        String jsonContent = Reader.readJsonFile("/medico/post.json");
        jsonContent = reader.updateEmail(jsonContent, "email", "other@example.com");

        mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();
    }

    @BeforeEach
    public void setUp() throws Exception {
        medicoRepository.deleteAll();

        String jsonContent = Reader.readJsonFile("/medico/post.json");

        var res = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = jsonObject.getString("token");
        System.out.println("Token: " + token);
    }

    @Test
    public void testAuthorizedAccessToGetMedicoById() throws Exception {
        Long authorizedMedicoId = medicoRepository
                .findByEmail("johndoe@example.com")
                .orElseThrow()
                .getId();

        mockMvc.perform(get("/medico/{id}", authorizedMedicoId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "PACIENTE")
    public void testPacienteAccessToGetMedicoById() throws Exception {
        mockMvc.perform(get("/medico/{id}", 1)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "other@example.com", roles = "MEDICO")
    public void testUnauthorizedAccessToDeleteMedicoById() throws Exception {
        createUnauthorizedMedico();
        Medico authorizedMedico = medicoRepository
                .findByEmail("johndoe@example.com")
                .orElseThrow();

        mockMvc.perform(delete("/medico/{id}", authorizedMedico.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAuthorizedAccessToGetOwnMedicoInfo() throws Exception {
        mockMvc.perform(get("/medico/me")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testUnauthorizedAccessToGetOwnMedicoInfo() throws Exception {
        mockMvc.perform(get("/medico/me")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(value = "other@example.com", roles = "MEDICO")
    public void testUnauthorizedAccessToDeleteMedico() throws Exception {
        createUnauthorizedMedico();
        Long authorizedMedicoId = medicoRepository
                .findByEmail("johndoe@example.com")
                .orElseThrow()
                .getId();

        mockMvc.perform(delete("/medico/{id}", authorizedMedicoId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAuthorizedAccessToUpdateMedicoInfo() throws Exception {
        String jsonContent = Reader.readJsonFile("/medico/patch.json");
        Long authorizedMedicoId = medicoRepository
                .findByEmail("johndoe@example.com")
                .orElseThrow()
                .getId();

        mockMvc.perform(patch("/medico/{id}", authorizedMedicoId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "other@example.com", roles = "MEDICO")
    public void testUnauthorizedAccessToUpdateMedicoInfo() throws Exception {
        createUnauthorizedMedico();
        String jsonContent = Reader.readJsonFile("/medico/patch.json");
        Long authorizedMedicoId = medicoRepository
                .findByEmail("johndoe@example.com")
                .orElseThrow()
                .getId();

        mockMvc.perform(patch("/medico/{id}", authorizedMedicoId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testAuthorizedAccessToGetVisitas() throws Exception {
        Long authorizedMedicoId = medicoRepository
                .findByEmail("johndoe@example.com")
                .orElseThrow()
                .getId();

        mockMvc.perform(get("/medico/{id}/visitas", authorizedMedicoId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthorizedAccessToAddVisita() throws Exception {
        Long authorizedMedicoId = medicoRepository
                .findByEmail("johndoe@example.com")
                .orElseThrow()
                .getId();

        String jsonContent = Reader.readJsonFile("/visita/post.json");

        mockMvc.perform(post("/medico/{id}/visitas", authorizedMedicoId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthorizedAccessToGetRutas() throws Exception {
        Long authorizedMedicoId = medicoRepository
                .findByEmail("johndoe@example.com")
                .orElseThrow()
                .getId();

        mockMvc.perform(get("/medico/{id}/rutas", authorizedMedicoId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthorizedAccessToAddRuta() throws Exception {
        Long authorizedMedicoId = medicoRepository
                .findByEmail("johndoe@example.com")
                .orElseThrow()
                .getId();

        String jsonContent = Reader.readJsonFile("/ruta/post.json");

        mockMvc.perform(post("/medico/{id}/rutas", authorizedMedicoId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }
}
