package com.meli.mutants.mutant.domain.util;

public class Util {

    /**
     * Calcula el ratio entre dos numeros
     *
     * @param  numerator
     *         Numerador de la relación
     * @param  denominator
     *         Denominador de la relación
     * @return  Retorna un valor double que define la proporción del numerador en el denominador
     */
    public static double getRatio(long numerator, long denominator) {
        if(numerator > 0 && denominator > 0)
            return (double) numerator / (double) denominator;
        return 0;
    }

    /**
     * Validar si el valor de entrada cumple con la expresión regular definida
     *
     * @param  sequence
     *         String a evaluar
     * @param  regex
     *         Expresión regular a evaluar
     * @return  Retorna true si sequence cumple la expresión regular regex.
     *          Retorna false si sequence no cumple la expresión regular regex
     */
    public static boolean validateRegex(String sequence, String regex){
        return sequence.matches(regex);
    }
}
