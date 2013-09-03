package no.hist.gruppe5.pvu.coderacer;

import java.util.ArrayList;

public class Code {

    private ArrayList<String> codes;
    private int i = 0;
    private int charCounter = 0;
    private String correct;
    private String left;

    public Code() {
        this.codes = new ArrayList();
        codes.add("du er idioter");
        codes.add("public static void main(String[] args)");
        codes.add("if(andreas == hipster)");
        correct = "";
        left = getCode();
    }

    public String getCode() {
        return codes.get(i);
    }

    public boolean equals(char input) {
        if (input == codes.get(i).charAt(charCounter)) {
            charCounter++;
            correct += input;
            left = getCode().substring(charCounter, getCode().length());
            if (codes.get(i).equals(correct)) {
                i++;
                charCounter = 0;
                correct = "";
                left = getCode();
            }
            return true;
        }

        return false;
    }

    public int getCharCounter() {
        return charCounter;
    }

    public String getCorrect() {
        return correct;
    }

    public String getLeft() {
        return left;
    }
}
