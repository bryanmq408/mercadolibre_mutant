package com.meli.mutants.mutant.domain.service;

import com.meli.mutants.mutant.application.response.Stats;
import com.meli.mutants.mutant.domain.repository.MutantRepository;
import com.meli.mutants.mutant.infrastructure.repository.dynamodb.entities.DNAEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MutantServiceImpl.class, DNAProcessorService.class})
class MutantServiceImplTest {

    @Autowired
    private MutantServiceImpl service;

    @MockBean
    private MutantRepository repository;

    @Test
    void whenRecieveMutantDNA_thenIsMutant() {
        String[] dna_mutant = {"ATGAAT", "CAATGA", "TAGCAT", "ATCAGG", "CCCGTA", "CCACTG"};

        Mockito.doNothing().when(repository).save(dna_mutant, true);

        assertTrue(service.isMutant(dna_mutant));
    }

    @Test
    void whenRecieveHumanDNA_thenHuman() {
        String[] dna_human = {"ATGAAT", "CTATGA", "TAGAAT", "ATCAGG", "CCGGTA", "CCACTG"};

        Mockito.doNothing().when(repository).save(dna_human, false);

        assertFalse(service.isMutant(dna_human));
    }

    @Test
    void whenRecieveInvalidDNA_thenIsNotMutant() {
        String[] invalid_dna = {"ATGAAT", "CTATGA", "TAGDAT", "ATCAGG", "CCGGTA", "CCACTG"};

        assertFalse(service.isMutant(invalid_dna));
    }

    @Test
    void whenGetStats_thenStats() {
        var item = new DNAEntity();
        item.setDna("1");
        item.setMutant(true);

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(item));

        Stats stats = service.getStats();
        assertTrue(stats.getNumberMutants() == 1);
        assertTrue(stats.getNumberHumans() == 0);
        assertTrue(stats.getRatio() == 0);
    }

    @Test
    void whenGetStats_andNotSavedInfo_thenDefaultStats() {
        Mockito.when(repository.findAll()).thenReturn(new ArrayList());

        Stats stats = service.getStats();
        assertTrue(stats.getNumberMutants() == 0);
        assertTrue(stats.getNumberHumans() == 0);
        assertTrue(stats.getRatio() == 0);
    }
}
