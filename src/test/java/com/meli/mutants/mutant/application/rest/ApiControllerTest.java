package com.meli.mutants.mutant.application.rest;

import com.meli.mutants.mutant.application.response.Stats;
import com.meli.mutants.mutant.domain.service.MutantService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
class ApiControllerTest {

    String DNA_REQUEST = "{\"dna\":[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]}";
    String DNA_BAD_REQUEST = "{\"dna\":null}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MutantService service;

    @Test
    void whenPostMutant_withValidDNARequest_thenSucceed() throws Exception {
        Mockito.when(service.isMutant(Mockito.any())).thenReturn(true);

        mockMvc.perform(
                post("/mutant")
                .content(DNA_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void whenPostMutant_withValidRequest_thenForbidden() throws Exception {
        Mockito.when(service.isMutant(Mockito.any())).thenReturn(false);

        mockMvc.perform(
                post("/mutant")
                        .content(DNA_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden());
    }

    @Test
    void whenPostMutant_withInvalidRequest_thenBadRequest() throws Exception {
        mockMvc.perform(
                post("/mutant")
                        .content(DNA_BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void whenGetStats_thenSucceed() throws Exception {
        Stats stats = new Stats(1, 1 ,1);

        Mockito.when(service.getStats()).thenReturn(stats);

        mockMvc.perform(
                get("/stats").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void whenPing_thenSucceed() throws Exception {
        mockMvc.perform(
                get("/stats").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
