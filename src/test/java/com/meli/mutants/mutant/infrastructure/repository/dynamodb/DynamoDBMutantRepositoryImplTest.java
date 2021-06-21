package com.meli.mutants.mutant.infrastructure.repository.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.meli.mutants.mutant.infrastructure.repository.dynamodb.entities.DNAEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DynamoDBMutantRepositoryImpl.class})
class DynamoDBMutantRepositoryImplTest {

    @Autowired
    DynamoDBMutantRepositoryImpl repository;
    @MockBean
    private DynamoDBMapper mapper;

    @Test
    void shouldSaveItem_thenSaveit() {
        String[] dna_mutant = {"ATGAAT", "CAATGA", "TAGCAT", "ATCAGG", "CCCGTA", "CCACTG"};

        Mockito.doNothing().when(mapper).save(Mockito.any(DNAEntity.class));

        repository.save(dna_mutant, true);
    }
}
