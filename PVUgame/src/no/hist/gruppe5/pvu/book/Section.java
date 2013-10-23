package no.hist.gruppe5.pvu.book;

import java.util.ArrayList;
import no.hist.gruppe5.pvu.XmlReader;

/**
 *
 * @author linnk
 */
public class Section {

    private int size;
    private Page[] pages;
    
    public Section(String name) {
        size = XmlReader.getSizeSection(name);
        pages = new Page[size];
        String[] temp = XmlReader.getPages(size, name);
        for(int i = 0; i<size; i++){
            pages[i] = new Page(temp[i+size], temp[i]);
        }
    }
    
    public Page getPage(int pagenumber) {
        if (pagenumber >= size || pagenumber<0) {
            return new Page("","");
        }
        return pages[pagenumber];
    }
    
    public int getSize() {
        return size;
    }
}
