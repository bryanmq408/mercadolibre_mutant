package com.meli.mutants.mutant.infrastructure.repository.dynamodb.entities;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName="DNACatalog")
public class DNAEntity {
    String dna;
    boolean isMutant;

    @DynamoDBHashKey(attributeName="DNA")
    public String getDna() {
        return dna;
    }

    public void setDna(String dna) {
        this.dna = dna;
    }

    @DynamoDBAttribute(attributeName="isMutant")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    public boolean isMutant() {
        return isMutant;
    }

    public void setMutant(boolean mutant) {
        isMutant = mutant;
    }
}
