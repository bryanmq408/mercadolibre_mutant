package com.meli.mutants.mutant.domain.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Base {
    String base;
    boolean horizontal;
    boolean vertical;
    boolean rightDiagonal;
    boolean leftDiagonal;

    public Base(String baseNitrogenada) {
        this.base = baseNitrogenada;
    }
}
