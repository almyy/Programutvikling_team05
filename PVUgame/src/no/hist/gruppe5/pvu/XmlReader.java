package no.hist.gruppe5.pvu;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author linnk
 */
public class XmlReader {

    public static String loadText(String text, String fileName) {
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fileName);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("book");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    return getValue(text, element).trim();
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }

    private static Document setConnection() throws Exception{
        File file = new File("data/test.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();
        return doc;
    }

    public static int getSizeSection(String section) {
        int size = 0;
        try {
            Document doc = setConnection();
            NodeList nodes = doc.getElementsByTagName(section);
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    size = Integer.parseInt(element.getAttribute("size"));
                }
            }
        } catch (Exception e) {
        }
        return size;
    }
    
    public static String[] getPages(int size,String section) {
        String[] pages = new String[size*2];
        try {
            Document doc = setConnection();
            NodeList nodes = doc.getElementsByTagName(section);
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    Element temp = element;
                    for(int j = 0; j<size; j++){
                        pages[j] = element.getElementsByTagName("page"+(j+1)).item(0).getTextContent();
                        temp = (Element) element.getElementsByTagName("page"+(j+1)).item(0);
                        pages[j+size] = temp.getAttribute("header");
                    }
                    return pages;
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getSizeSection("Analyse"));
        //System.out.println(getPages(3, "Innledning")[5]);
    }
}
