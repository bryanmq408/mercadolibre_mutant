package com.meli.mutants.mutant.domain.service;

import com.meli.mutants.mutant.application.response.Stats;
import com.meli.mutants.mutant.domain.model.Base;
import com.meli.mutants.mutant.domain.repository.MutantRepository;
import com.meli.mutants.mutant.domain.util.Util;
import com.meli.mutants.mutant.infrastructure.repository.dynamodb.entities.DNAEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class MutantServiceImpl implements MutantService{

    private static final String PATTERN_SPLIT = "";
    private static final String PATTERN_DNA = "^[A|T|C|G]*$";

    private final MutantRepository repository;
    private final DNAProcessorService processor;

    @Autowired
    public MutantServiceImpl(MutantRepository repository, DNAProcessorService processor) {
        this.repository = repository;
        this.processor = processor;
    }

    /**
     * Evalúa si existen secuencias de DNA mutante en array de String
     *
     * @param  dna
     *         Array de String

     * @return  Retorna true si se excede el limite de secuencias DNA mutantes.
     *          Retorna false si no se encontraron secuencias mutantes.
     *          Retorna false si existe una secuencia DNA con algun caracter invalido.
     */
    @Override
    public boolean isMutant(String[] dna){
        Base[][] dnamatrix = createDnaMatrix(dna, dna.length);

        if(dnamatrix.length == dna.length) {
            boolean response = processor.checkDnaSequences(dnamatrix);
            repository.save(dna, response);
            return response;
        }
        return false;
    }

    /**
     * Consulta las verificaciones DNA almacenadas y genera estadisticas.
     *
     * @return  Retorna un objeto Stats con las estadisticas de las verificaciones DNA almacenadas.
     */
    @Override
    public Stats getStats(){
        List<DNAEntity> dna = repository.findAll();

        long numberMutants = dna.stream().filter(DNAEntity::isMutant).count();
        long numberHumans =  dna.stream().filter(i -> !i.isMutant()).count();
        double ratio = Util.getRatio(numberMutants, numberHumans);

        return new Stats(numberMutants, numberHumans, ratio);
    }

    /**
     * Crea una matriz NxN con base a un array de String
     *
     * @param  dna
     *         Matriz de Strings
     * @param  length
     *         Longitud de la matriz
     * @return  Retorna una Matriz de tipo Base.
     */
    private Base[][] createDnaMatrix(String[] dna, int length) {
        var matrixDna = new Base[length][length];

        for (var index = 0; index < length; index++) {
            if(!Util.validateRegex(dna[index], PATTERN_DNA)) {
                log.error("DNA Sequence not allowed ["+dna[index]+"]");
                return new Base[0][0];
            }
            matrixDna[index] = Arrays.stream(dna[index].split(PATTERN_SPLIT)).map(Base::new).toArray(Base[]::new);
        }
        return matrixDna;
    }
}
