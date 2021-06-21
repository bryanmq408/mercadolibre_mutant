package com.meli.mutants.mutant.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Stats {
    @JsonProperty("count_mutant_dna")
    private long numberMutants;

    @JsonProperty("count_human_dna")
    private long numberHumans;

    @JsonProperty
    private double ratio;

    public Stats(long numberMutants, long numberHumans, double ratio) {
        this.numberMutants = numberMutants;
        this.numberHumans = numberHumans;
        this.ratio = ratio;
    }
}
