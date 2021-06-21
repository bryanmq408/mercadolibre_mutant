package com.meli.mutants.mutant.domain.service;

import com.meli.mutants.mutant.domain.model.Base;
import org.springframework.stereotype.Component;

@Component
public class DNAProcessor {

    private final int maxSequencesRequired = 2;
    private final int patternlength = 3;

    /**
     * Evalúa si existen secuencias de DNA mutante en una matriz NxN
     *
     * @param  dna
     *         Matriz de DNA

     * @return  Retorna true si se excede el limite de secuencias DNA mutantes.
     *          Retorna false si no se encontraron secuencias mutantes.
     */
    public boolean checkDnaSequences(Base[][] dna) {
        int length = dna.length;
        int total = 0;

        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {

                if(row >= length - patternlength && col >= length - patternlength){
                    break;
                }

                total += checkHorizontalSequence(dna, row, col, length);
                total += checkVerticalSequence(dna, row, col, length);
                total += checkRightDiagonalSequence(dna, row, col, length);
                total += checkLeftDiagonalSequence(dna, row, col, length);

                if (total >= maxSequencesRequired) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Evalúa si existe una secuencia mutante de forma horizontal
     *
     * @param  dna
     *         Matriz de DNA
     * @param  row
     *         Numero de la fila
     * @param  col
     *         Numero de la columna
     * @param  length
     *         Longitud de la matriz
     * @return  Retorna 0 si no encontró una secuencia mutante.
     *          Retorna 1 si se encuentra una secuencia mutante
     */
    private int checkHorizontalSequence(Base[][] dna, int row, int col, int length) {

        Base firstElement = dna[row][col];

        if(firstElement.isHorizontal()) {
            return 0;
        }

        if (col > 0 && dna[row][col - 1].isHorizontal()){
            dna[row][col].setHorizontal(true);
            return 0;
        }

        if (col + patternlength >= length) {
            return 0;
        }

        Base lastElement = dna[row][col + patternlength];

        if (firstElement.getBase().equals(lastElement.getBase()) && checkHorizontalHintSequence(dna, row, col, col + patternlength)) {
            return 1;
        }

        return 0;
    }

    /**
     * Evalúa si existe una secuencia mutante de forma vertical
     *
     * @param  dna
     *         Matriz de DNA
     * @param  row
     *         Numero de la fila
     * @param  col
     *         Numero de la columna
     * @param  length
     *         Longitud de la matriz
     * @return  Retorna 0 si no encontró una secuencia mutante.
     *          Retorna 1 si se encuentra una secuencia mutante
     */
    private int checkVerticalSequence(Base[][] dna, int row, int col, int length) {
        Base firstElement = dna[row][col];

        if(firstElement.isVertical()) {
            return 0;
        }

        if (row > 0 && dna[row - 1][col].isVertical()) {
            dna[row][col].setVertical(true);
            return 0;
        }

        if (row + patternlength >= length) {
            return 0;
        }

        Base lastElement = dna[row + patternlength][col];

        if (firstElement.getBase().equals(lastElement.getBase()) && checkVerticalHintSequence(dna, row, col, row + patternlength)) {
            return 1;
        }

        return 0;
    }

    /**
     * Evalúa si existe una secuencia mutante de forma diagonal hacia la derecha
     *
     * @param  dna
     *         Matriz de DNA
     * @param  row
     *         Numero de la fila
     * @param  col
     *         Numero de la columna
     * @param  length
     *         Longitud de la matriz
     * @return  Retorna 0 si no encontró una secuencia mutante.
     *          Retorna 1 si se encuentra una secuencia mutante
     */
    private int checkRightDiagonalSequence(Base[][] dna, int row, int col, int length) {

        Base firstElement = dna[row][col];

        if(firstElement.isRightDiagonal()) {
            return 0;
        }

        if ((row > 0 && col > 0) && dna[row - 1][col - 1].isRightDiagonal()) {
            dna[row][col].setRightDiagonal(true);
            return 0;
        }

        if (col + patternlength >= length || row + patternlength >= length) {
            return 0;
        }

        Base lastElement = dna[row + patternlength][col + patternlength];


        if (firstElement.getBase().equals(lastElement.getBase()) && checkRightDiagonalHintSequence(dna, row, col, row + patternlength, col + patternlength)) {
            return 1;
        }

        return 0;
    }

    /**
     * Evalúa si existe una secuencia mutante de forma diagonal hacia la izquierda
     *
     * @param  dna
     *         Matriz de DNA
     * @param  row
     *         Numero de la fila
     * @param  col
     *         Numero de la columna
     * @param  length
     *         Longitud de la matriz
     * @return  Retorna 0 si no encontró una secuencia mutante.
     *          Retorna 1 si se encuentra una secuencia mutante
     */
    private int checkLeftDiagonalSequence(Base[][] dna, int row, int col, int length) {


        Base firstElement = dna[row][col];

        if(firstElement.isLeftDiagonal()) {
            return 0;
        }

        if ((row > 0 && col < length - 1) && dna[row - 1][col + 1].isLeftDiagonal()) {
            dna[row][col].setLeftDiagonal(true);
            return 0;
        }

        if (row + patternlength >= length || col - patternlength < 0) {
            return 0;
        }

        Base lastElement = dna[row + patternlength][col - patternlength];

        if (firstElement.getBase().equals(lastElement.getBase()) && checkLeftDiagonalHintSequence(dna, row, col, row + patternlength, col - patternlength)) {
            return 1;
        }

        return 0;
    }

    /**
     * Evalúa si el segmento diagonal izquierdo de una matriz es una secuencia de DNA mutante
     *
     * @param  dna
     *         Matriz de DNA
     * @param  row
     *         Numero de la fila
     * @param  col
     *         Numero de la columna
     * @param  rowLimit
     *         Longitud maxima de la fila
     * @param  colLimit
     *         Longitud maxima de la columna
     * @return  Retorna true si el segmento es una secuencia de DNA mutante.
     *          Retorna false si el segmento no es una secuencia de DNA mutante.
     */
    private boolean checkLeftDiagonalHintSequence(Base[][] dna, int row, int col, int rowLimit, int colLimit) {
        if(row == rowLimit && col == colLimit){
            return true;
        }

        if(dna[row][col].getBase().equals(dna[row + 1][col - 1].getBase())){
            boolean state = checkLeftDiagonalHintSequence(dna, row + 1, col - 1, rowLimit, colLimit);
            dna[row][col].setLeftDiagonal(state);
            dna[row + 1][col - 1].setLeftDiagonal(state);

            return state;
        }else{
            return false;
        }
    }

    /**
     * Evalúa si el segmento diagonal derecho de una matriz es una secuencia de DNA mutante
     *
     * @param  dna
     *         Matriz de DNA
     * @param  row
     *         Numero de la fila
     * @param  col
     *         Numero de la columna
     * @param  rowLimit
     *         Longitud maxima de la fila
     * @param  colLimit
     *         Longitud maxima de la columna
     * @return  Retorna true si el segmento es una secuencia de DNA mutante.
     *          Retorna false si el segmento no es una secuencia de DNA mutante.
     */
    private boolean checkRightDiagonalHintSequence(Base[][] dna, int row, int col, int rowLimit, int colLimit) {
        if(row == rowLimit && col == colLimit){
            return true;
        }

        if(dna[row][col].getBase().equals(dna[row + 1][col + 1].getBase())){
            boolean state = checkRightDiagonalHintSequence(dna, row + 1, col + 1, rowLimit, colLimit);
            dna[row][col].setRightDiagonal(state);
            dna[row + 1][col + 1].setRightDiagonal(state);

            return state;
        }else{
            return false;
        }
    }

    /**
     * Evalúa si el segmento vertical de una matriz es una secuencia de DNA mutante
     *
     * @param  dna
     *         Matriz de DNA
     * @param  row
     *         Numero de la fila
     * @param  col
     *         Numero de la columna
     * @param  limit
     *         Longitud maxima de la fila
     * @return  Retorna true si el segmento es una secuencia de DNA mutante.
     *          Retorna false si el segmento no es una secuencia de DNA mutante.
     */
    private boolean checkVerticalHintSequence(Base[][] dna, int row, int col, int limit) {

        if(row == limit){
            return true;
        }

        if(dna[row][col].getBase().equals(dna[row + 1][col].getBase())){
            boolean state = checkVerticalHintSequence(dna, row + 1, col, limit);
            dna[row][col].setVertical(state);
            dna[row + 1][col].setVertical(state);

            return state;
        }else{
            return false;
        }
    }

    /**
     * Evalúa si el segmento horizontal de una matriz es una secuencia de DNA mutante
     *
     * @param  dna
     *         Matriz de DNA
     * @param  row
     *         Numero de la fila
     * @param  col
     *         Numero de la columna
     * @param  limit
     *         Longitud maxima de la columna
     * @return  Retorna true si el segmento es una secuencia de DNA mutante.
     *          Retorna false si el segmento no es una secuencia de DNA mutante.
     */
    private boolean checkHorizontalHintSequence(Base[][] dna, int row, int col, int limit) {

        if(col == limit){
            return true;
        }

        if(dna[row][col].getBase().equals(dna[row][col + 1].getBase())){
            boolean state = checkHorizontalHintSequence(dna, row, col + 1, limit);
            dna[row][col].setHorizontal(state);
            dna[row][col + 1].setHorizontal(state);

            return state;
        }else{
            return false;
        }
    }
}
