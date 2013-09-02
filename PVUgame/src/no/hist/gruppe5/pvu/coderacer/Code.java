package no.hist.gruppe5.pvu.coderacer;

import java.util.ArrayList;

public class Code {
    private ArrayList<String> codes;
    private int i = 0;
    private int charCounter = 0;
    private String correct;
    public Code() {
        this.codes = new ArrayList();
        codes.add("du er idioter");
        codes.add("public static void main(String[] args)");
        codes.add("if(andreas == hipster)");
        correct = "";
    }
    public String getCode() {
        return codes.get(i);
    }
    public boolean equals(String input) {
        if(codes.get(i).equals(input)) {
            i++;
            charCounter = 0;
            return true;
        }
        if(input.charAt(input.length()-1) == codes.get(i).charAt(charCounter)) {
            charCounter++;
            correct += input.charAt(input.length()-1);
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
}
