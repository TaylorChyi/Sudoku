package com.taylorchyi.sudoku.model;

import java.util.HashSet;
import java.util.Set;

public class SudokuCell {
    private int number; // 确定的数字
    private Set<Integer> candidates; // 备选数字集合

    public SudokuCell() {
        this.number = 0; // 0 表示空白
        this.candidates = new HashSet<>();
    }

    public void setNumber(int number) {
        this.number = number;
        this.candidates.clear();
    }

    public int getNumber() {
        return number;
    }

    public void addCandidate(int candidate) {
        if (!hasNumber()) {
            candidates.add(candidate);
        }
    }

    public void removeCandidate(int candidate) {
        candidates.remove(candidate);
    }

    public Set<Integer> getCandidates() {
        return candidates;
    }

    public boolean hasNumber() {
        return number != 0;
    }

    public void clearAll() {
        clearNumber();
        clearCandidates();
    }

    public void clearNumber() {
        number = 0;
    }

    public void clearCandidates() {
        candidates.clear();
    }
}

