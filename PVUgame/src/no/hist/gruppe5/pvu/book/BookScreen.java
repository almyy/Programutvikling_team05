/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.book;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    //private BitmapFont font;
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
        batch.draw(testTexture, 0, 0, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
        batch.end();

        //stage.

        stage.draw();
        //label.draw(batch, 1f);

        //font.drawWrapped(batch, loadText(), 35, 100, 55);

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
        // catch (ParserConfigurationException | SAXException | IOException e) {
        }
        return "";
    }

    private String getPageContent() {
        return null;
    }

    private void initScreen() {
        testTexture = new Texture(Gdx.files.internal("data/book.png"));
        testTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        stage = new Stage();
        File bookContent = new File("data/test.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

        Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        leftPage = new Label(loadText("section1",dbFactory,bookContent), labelStyle);
        labelStyle.font.scale(0.7f);
        leftPage.setWidth(280);
        leftPage.setPosition(170, 250);
        leftPage.setWrap(true);

        rightPage = new Label(loadText("section2",dbFactory,bookContent), labelStyle);
        rightPage.setWidth(280);
        rightPage.setPosition(510, 285);
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
