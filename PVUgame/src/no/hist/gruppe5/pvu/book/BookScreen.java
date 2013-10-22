package no.hist.gruppe5.pvu.book;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.TimeUtils;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/**
 *
 * @author linnk
 */
public class BookScreen extends GameScreen {

    private int MAX_PAGE;
    private int MIN_PAGE;
    private int currentSectionNumber;
    private boolean inContent;
    private final String[] sections = {"Innledning", "Analyse", "Design", "Implementasjon", "Sluttrapport"};
    private long timeSinceLastAction;
    private final long KEY_INPUT_DELAY = 700;

    private int currentPageNumber;

    private TextButton[] content;

    private TextButtonStyle buttonStyle;
    private TextButtonStyle buttonStylePressed;
    private Skin skin;
    private Stage stage;
    private Label leftPage;
    private Label headerLeftPage;
    private Label rightPage;
    private Label headerRightPage;
    private Label sectionText;

    private Section section;

    public BookScreen(PVU game) {
        super(game);
        timeSinceLastAction = 0;
        currentSectionNumber = 0;
        inContent = true;
        currentPageNumber = 0;
        section = new Section(sections[currentSectionNumber]);
        initPages();
        createContent();
    }

    private void createContent() {
        TextureAtlas atlas = new TextureAtlas("data/bookscreen/button.pack");
        skin = new Skin(atlas);
        stage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_WIDTH, true);
        buttonStyle = new TextButtonStyle();
        buttonStyle.up = skin.getDrawable("buttonprev.up");
        buttonStyle.down = skin.getDrawable("buttonprev.down");
        buttonStyle.pressedOffsetX = -1;
        buttonStyle.pressedOffsetY = -1;
        buttonStyle.font = Assets.primaryFont10px;
        buttonStyle.fontColor = Color.BLACK;
        buttonStylePressed = new TextButtonStyle();
        buttonStylePressed.up = skin.getDrawable("buttonprev.down");
        buttonStylePressed.down = skin.getDrawable("buttonprev.down");
        buttonStylePressed.pressedOffsetX = -1;
        buttonStylePressed.pressedOffsetY = -1;
        buttonStylePressed.font = Assets.primaryFont10px;
        buttonStylePressed.fontColor = Color.BLACK;
        content = new TextButton[5];
        for (int i = 0; i < 5; i++) {
            TextButton temp = new TextButton(sections[i], buttonStyle);
            temp.pad(20);
            temp.setWidth(700f);
            temp.setHeight(170f);
            temp.setPosition(810, 750 - temp.getHeight() * i);
            temp.getLabel().setFontScale(5.6f);
            stage.addActor(temp);
            content[i] = temp;
        }
        content[currentSectionNumber].setStyle(buttonStylePressed);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);
        batch.begin();
        batch.draw(Assets.bookBook, 0, 0, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
        batch.end();
        stage.draw();
    }

    @Override
    protected void update(float delta) {
        if (TimeUtils.millis() - timeSinceLastAction > KEY_INPUT_DELAY && Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && !isGamePaused()) {
            if (inContent) {
                checkActionsInContent();
            } else {
                checkActions();
            }
            timeSinceLastAction = TimeUtils.millis();
        }
    }

    private void checkActionsInContent() {
        if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) && currentSectionNumber > 0) {
            changeSelectedSection(-1);
        } else if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) && currentSectionNumber < sections.length - 1) {
            changeSelectedSection(1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            section = new Section(sections[currentSectionNumber]);
            addPagesToStage();
            flipPage(0);
            sectionText.setText(sections[currentSectionNumber]);
            inContent = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            section = new Section(sections[currentSectionNumber]);
            currentPageNumber = 0;
            addPagesToStage();
            flipPage(0);
            sectionText.setText(sections[currentSectionNumber]);
            inContent = false;
        }
    }

    public void checkActions() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (currentPageNumber < section.getSize() - 1) {
                flipPage(2);
            } else {
                if (currentSectionNumber < sections.length - 1) {
                    currentSectionNumber++;
                    currentPageNumber = 0;
                    section = new Section(sections[currentSectionNumber]);
                    flipPage(0);
                    sectionText.setText(sections[currentSectionNumber]);
                }
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (currentPageNumber == 0 && currentSectionNumber == 0) {
                addContentToStage();
                inContent = true;
            } else {
                if (currentPageNumber > 0) {
                    flipPage(-2);
                } else {
                    if (currentSectionNumber > 0) {
                        currentSectionNumber--;
                        section = new Section(sections[currentSectionNumber]);
                        currentPageNumber = (section.getSize() % 2 == 0) ? section.getSize() - 2 : section.getSize() - 1;
                        System.out.println(section.getSize() % 2 == 0);
                        flipPage(0);
                        sectionText.setText(sections[currentSectionNumber]);
                    }
                }
            }
        }
    }

    private void flipPage(int in) {
        currentPageNumber += in;
        leftPage.setText(section.getPage(currentPageNumber).getContent());
        rightPage.setText(section.getPage(currentPageNumber + 1).getContent());
        headerLeftPage.setText(section.getPage(currentPageNumber).getHeader());
        headerRightPage.setText(section.getPage(currentPageNumber + 1).getHeader());
    }

    private void addPagesToStage() {
        stage.clear();
        stage.addActor(leftPage);
        stage.addActor(rightPage);
        sectionText.setText(sections[currentSectionNumber]);
        stage.addActor(sectionText);
        stage.addActor(headerLeftPage);
        stage.addActor(headerRightPage);
    }

    private void addContentToStage() {
        stage.clear();
        for (TextButton button : content) {
            stage.addActor(button);
        }
    }

    private void changeSelectedSection(int down) {
        content[currentSectionNumber].setStyle(buttonStyle);
        currentSectionNumber += down;
        content[currentSectionNumber].setStyle(buttonStylePressed);
    }

    private void initPages() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        leftPage = new Label("start", labelStyle);
        leftPage.setWidth(PVU.GAME_WIDTH);
        leftPage.setPosition(100, 450);
        leftPage.setWrap(true);
        leftPage.setFontScale(3.4f);

        rightPage = new Label("start", labelStyle);
        rightPage.setWidth(PVU.GAME_WIDTH);
        rightPage.setPosition(850, 450);
        rightPage.setWrap(true);
        rightPage.setFontScale(3.4f);

        sectionText = new Label("", labelStyle);
        sectionText.setPosition(325, 900);
        sectionText.setFontScale(2.5f);

        Label.LabelStyle labelStyle1 = new Label.LabelStyle(Assets.primaryFont10px, Color.DARK_GRAY);
        headerLeftPage = new Label("", labelStyle1);
        headerRightPage = new Label("", labelStyle1);
        headerLeftPage.setPosition(100, 800);
        headerLeftPage.setFontScale(3.8f);
        headerRightPage.setPosition(850, 800);
        headerRightPage.setFontScale(3.8f);
    }

    @Override
    protected void cleanUp() {
    }
}
