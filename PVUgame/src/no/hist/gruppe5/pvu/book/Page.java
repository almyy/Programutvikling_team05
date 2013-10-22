package no.hist.gruppe5.pvu.book;

/**
 *
 * @author linnk
 */
public class Page {
    private String header;
    private String content;
    
    public Page(String header, String content){
        this.header = header;
        this.content = content;
    }
    
    public String getHeader(){
        return header;
    }
    
    public String getContent() {
        return content;
    }
}
