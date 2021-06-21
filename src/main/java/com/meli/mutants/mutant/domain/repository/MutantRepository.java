package com.meli.mutants.mutant.domain.repository;

import com.meli.mutants.mutant.infrastructure.repository.dynamodb.entities.DNAEntity;

import java.util.List;

public interface MutantRepository {
    /**
     * Persiste una secuencia de String y un boolean
     *
     * @param  dna
     *         Secuencia de DNA
     * @param  isMutant
     *         Indica si la secuencia de DNA es mutante
     */
    void save(String[] dna, boolean isMutant);

    /**
     * Retorna todas las secuencias de DNA alcamenadas.
     * @return  Retorna una lista DNAEntity de todas las secuencias de DNA alcamenadas.
     */
    List<DNAEntity> findAll();
}
