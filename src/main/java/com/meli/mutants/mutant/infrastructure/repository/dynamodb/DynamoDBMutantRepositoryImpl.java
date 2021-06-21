package com.meli.mutants.mutant.infrastructure.repository.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.meli.mutants.mutant.domain.repository.MutantRepository;
import com.meli.mutants.mutant.infrastructure.repository.dynamodb.entities.DNAEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
public class DynamoDBMutantRepositoryImpl implements MutantRepository {

    @Autowired
    private DynamoDBMapper mapper;

    /**
     * Persiste en una base de datos DynanoDB una secuencia de String y un boolean
     *
     * @param  dna
     *         Secuencia de DNA
     * @param  isMutant
     *         Indica si la secuencia de DNA es mutante
     */
    @Override
    public void save(String[] dna, boolean isMutant) {
        try {
            DNAEntity item = new DNAEntity();
            item.setDna(Arrays.toString(dna));
            item.setMutant(isMutant);

            mapper.save(item);
        }
        catch (Exception e) {
            log.error("Create item failed: " + e.getMessage());
        }
    }

    /**
     * Retorna todas las secuencias de DNA alcamenadas en una base de datos Dynamo.
     * @return  Retorna una lista DNAEntity de todas las secuencias de DNA alcamenadas.
     */
    @Override
    public List<DNAEntity> findAll() {
        try {
            DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
            return mapper.scan(DNAEntity.class, scanExpression);
        }
        catch (Exception e) {
            log.error("Query items failed.: " + e.getMessage());
            return new ArrayList();
        }
    }
}
