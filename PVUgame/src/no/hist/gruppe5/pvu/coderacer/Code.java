package no.hist.gruppe5.pvu.coderacer;

import java.util.ArrayList;

public class Code {
    private ArrayList<String> codes;
    private String inputText = "";
    private int i = 0;
    public Code() {
        this.codes = new ArrayList();
        codes.add("du er idioter");
        codes.add("public static void main(String[] args)");
        codes.add("if(andreas == hipster)");
    }
    public String getCode() {
        return codes.get(i);
    }
    public boolean compareChar(char lastChar) {
        
        
        return false;
    }
    public boolean equals(String input) {
        if(codes.get(i).equals(input)) {
            i++;
            return true;
        }
        else return false;
    }
}
