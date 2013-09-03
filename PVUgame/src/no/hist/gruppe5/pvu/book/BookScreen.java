/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.book;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private Label leftPage;
    private Label rightPage;
    private Stage stage;

    public BookScreen(PVU game) {
        super(game);
        initScreen();
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);
        batch.begin();
        //DRAW BOOK
        batch.end();
        stage.draw();
    }

    @Override
    protected void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(PVU.MAIN_SCREEN);
        }
    }

    @Override
    protected void cleanUp() {
    }

    public String loadText(String text, DocumentBuilderFactory dbFactory, File bookContent) {
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(bookContent);
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getElementsByTagName("book");
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    return getValue(text, element);
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    private String getPageContent() {
        return null;
    }

    private void initScreen() {
        testTexture = new Texture(Gdx.files.internal("data/book.png"));
        testTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        stage = new Stage(PVU.GAME_WIDTH, PVU.GAME_HEIGHT, true, batch);
        File bookContent = new File("data/test.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        leftPage = new Label(loadText("section1", dbFactory, bookContent).trim(), labelStyle);
        leftPage.setFontScale(0.35f);
        leftPage.setWidth(PVU.GAME_WIDTH);
        leftPage.setPosition(20, PVU.GAME_HEIGHT/2);
        leftPage.setWrap(true);
        
        rightPage = new Label(loadText("section1",dbFactory,bookContent).trim(), labelStyle);
        rightPage.setPosition(110, 0);
        rightPage.setFontScale(0.35f);
        rightPage.setWidth(PVU.GAME_WIDTH);
        rightPage.setPosition(PVU.GAME_WIDTH-(PVU.GAME_WIDTH*0.35f)-20, PVU.GAME_HEIGHT/2);
        rightPage.setWrap(true);

        stage.addActor(leftPage);
        stage.addActor(rightPage);
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }
}
