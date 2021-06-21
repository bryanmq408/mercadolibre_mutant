package com.meli.mutants.mutant.application.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DNA {

    @JsonProperty
    @NotNull
    @NotEmpty
    private String[] dna;
}
