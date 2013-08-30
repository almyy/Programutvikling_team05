/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.book;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author linnk
 */
public class BookScreen extends GameScreen {

    public static Texture testTexture;
    private BitmapFont font;

    public BookScreen(PVU game) {
        super(game);
        testTexture = new Texture(Gdx.files.internal("data/book.png"));
        testTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                
        font = Assets.primaryFont16px;
        font.setColor(Color.BLACK);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setScale(0.3f);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        batch.draw(testTexture, 0, 0, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);

        
        font.drawWrapped(batch, loadText(), 35, 100, 55);
        batch.end();


    }

    @Override
    protected void update(float delta) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    @Override
    protected void cleanUp() {
    }

    public String loadText() {
        try {
            File bookContent = new File("data/test.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(bookContent);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("book");

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    return ("Section 1: " + getValue("section1", element));
                }
            }
        } catch (Exception e) {
        }
        return "";
    }
    
    public String getPage(){
        return null;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }
}
