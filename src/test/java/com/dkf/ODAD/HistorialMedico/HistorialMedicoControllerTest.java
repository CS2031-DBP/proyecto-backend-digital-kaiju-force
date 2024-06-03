package com.dkf.ODAD.HistorialMedico;

import com.dkf.ODAD.HistorialMedico.Infraestructure.HistorialMedicoRepository;
import com.dkf.ODAD.HistorialMedico.Service.HistorialMedicoService;
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
public class HistorialMedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HistorialMedicoRepository historialMedicoRepository;

    @Autowired
    private HistorialMedicoService historialMedicoService;

    @Autowired
    Reader reader;

    String token = "";


    @BeforeEach
    public void setUp() throws Exception {
        historialMedicoRepository.deleteAll();

        String jsonContent = Reader.readJsonFile("/historial/post.json");

        var res = mockMvc.perform(post("/auth/register")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andReturn();

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(res.getResponse().getContentAsString()));
        token = jsonObject.getString("token");
        System.out.println("Token: " + token);
    }

    @Test
    public void testAuthorizedAccessToGetHistorialesMedicosByPacienteId() throws Exception {
        Long authorizedPacienteId = 1L;

        mockMvc.perform(get("/historial/paciente/{pacienteId}", authorizedPacienteId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAuthorizedAccessToCreateHistorialMedico() throws Exception {
        String jsonContent = Reader.readJsonFile("/historial/post.json");

        mockMvc.perform(post("/historial")
                        .contentType(APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "PACIENTE")
    public void testPacienteAccessToDeleteHistorialMedico() throws Exception {
        Long authorizedHistorialMedicoId = 1L;

        mockMvc.perform(delete("/historial/{id}", authorizedHistorialMedicoId)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
