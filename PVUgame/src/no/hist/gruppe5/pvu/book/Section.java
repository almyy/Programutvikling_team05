package no.hist.gruppe5.pvu.book;

import java.util.ArrayList;
import no.hist.gruppe5.pvu.XmlReader;

/**
 *
 * @author linnk
 */
public class Section {

    private ArrayList<String> pages;
    private int size;
    
    public Section(String name) {
        String content = XmlReader.loadText(name, "data/test.xml");
        pages = new ArrayList<>();
        int index = content.indexOf("#");
        size = 1;
        if(index>0) {
            int index2;
            pages.add(content.substring(0,index));
            while (index!=content.length()) {
                index2 = index;
                index=content.indexOf("#", index+1);
                if(index<0){
                    index = content.length();
                }
                size++;
                pages.add(content.substring(index2+1, index));
            }
        }else {
            pages.add(content);
        }
        System.out.print(size);
    }
    
    public String getPage(int pagenumber) {
        if (pagenumber >= size) {
            return "";
        }
        return pages.get(pagenumber);
    }
    
    public int getSize() {
        return size;
    }
    
    public static void main(String[] args) {
        String content = XmlReader.loadText("Innledning", "data/test.xml");
        System.out.println(content.substring(409,content.indexOf("#",835)));
    }
}
