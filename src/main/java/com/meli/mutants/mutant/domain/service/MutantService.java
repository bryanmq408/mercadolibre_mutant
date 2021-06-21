package com.meli.mutants.mutant.domain.service;

import com.meli.mutants.mutant.application.response.Stats;

public interface MutantService {

    /**
     * Evalúa si existen secuencias de DNA mutante en array de String.
     *
     * @param  dna
     *         Array de String
     * @return  Retorna true si se excede el limite de secuencias DNA mutantes.
     *          Retorna false si no se encontraron secuencias mutantes.
     */
    boolean isMutant(String[] dna);

    /**
     * Consulta las verificaciones DNA almacenadas y genera estadisticas.
     *
     * @return  Retorna un objeto Stats con las estadisticas de las verificaciones DNA almacenadas.
     */
    Stats getStats();
}
