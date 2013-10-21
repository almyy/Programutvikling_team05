package no.hist.gruppe5.pvu.book;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import no.hist.gruppe5.pvu.XmlReader;
import no.hist.gruppe5.pvu.mainroom.MainScreen;

/**
 *
 * @author linnk
 */
public class BookScreen extends GameScreen {

    /**
     * public static Texture testTexture; private Label leftPage; private Label
     * rightPage; private Stage stage; private int pageNumber; private final
     * String FILE_NAME = "data/test.xml"; private TextButton nextButton;
     * private TextButton prevButton; private TextureAtlas atlas; private Skin
     * skin; private ClickListener listener; private long timeSinceLastAction;
     * private final int MAX_PAGE = 5; private final int MIN_PAGE = 1; private
     * Sounds sound;
     *
     * private String[] sections; private Button[] content;
     *
     * public BookScreen(PVU game) { super(game); sound = new Sounds();
     * pageNumber = 1; initScreen(); timeSinceLastAction = 0; }
     *
     * @Override protected void draw(float delta) { clearCamera(1, 1, 1, 1);
     * batch.begin(); batch.draw(Assets.bookBook, 0, 0, PVU.GAME_WIDTH,
     * PVU.GAME_HEIGHT); batch.end(); leftPage.setText(getPageContent(0));
     * rightPage.setText(getPageContent(1)); stage.draw(); stage.act(delta);
     *
     * if (MAX_PAGE == pageNumber) { nextButton.remove(); } else {
     * stage.addActor(nextButton); } if (MIN_PAGE== pageNumber) {
     * prevButton.remove(); } else { stage.addActor(prevButton); } }
     *
     * @Override protected void update(float delta) { if
     * (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && TimeUtils.millis() -
     * timeSinceLastAction > 700l && TimeUtils.millis() - getTime() > 700l) {
     * game.setScreen(PVU.MAIN_SCREEN); } if (TimeUtils.millis() -
     * timeSinceLastAction > 700l) { if
     * (Gdx.input.isKeyPressed(Input.Keys.RIGHT) ||
     * Gdx.input.isKeyPressed(Input.Keys.D)) { if(pageNumber<MAX_PAGE){
     * getNextPage(); timeSinceLastAction = TimeUtils.millis(); } } if
     * (Gdx.input.isKeyPressed(Input.Keys.LEFT) ||
     * Gdx.input.isKeyPressed(Input.Keys.A)) { if(pageNumber>MIN_PAGE){
     * getPreviousPage(); timeSinceLastAction = TimeUtils.millis(); } } } }
     *
     * @Override protected void cleanUp() { }
     *
     * private String getPageContent(int rightPage) { String name = "section" +
     * (pageNumber + rightPage); String content = XmlReader.loadText(name,
     * FILE_NAME); return content; }
     *
     * private void initScreen() { sound.playSound(3); testTexture = new
     * Texture(Gdx.files.internal("data/book.png"));
     * testTexture.setFilter(Texture.TextureFilter.Linear,
     * Texture.TextureFilter.Linear);
     *
     * stage = new Stage(PVU.GAME_WIDTH * 2.5f, PVU.GAME_HEIGHT * 2.5f, true);
     *
     * Label.LabelStyle labelStyle = new
     * Label.LabelStyle(Assets.primaryFont10px, Color.BLACK); leftPage = new
     * Label(getPageContent(0), labelStyle); leftPage.setWidth(PVU.GAME_WIDTH);
     * leftPage.setPosition(35, stage.getHeight() / 2); leftPage.setWrap(true);
     *
     * rightPage = new Label(getPageContent(1), labelStyle);
     * rightPage.setPosition(110, 0); rightPage.setWidth(PVU.GAME_WIDTH);
     * rightPage.setPosition(stage.getWidth() / 2 + 25, stage.getHeight() / 2);
     * rightPage.setWrap(true);
     *
     * stage.addActor(leftPage); stage.addActor(rightPage);
     *
     * atlas = new TextureAtlas("data/bookscreen/button.pack"); skin = new
     * Skin(atlas);
     *
     * TextButtonStyle styleNext = new TextButton.TextButtonStyle();
     * styleNext.up = skin.getDrawable("buttonnext.up"); styleNext.down =
     * skin.getDrawable("buttonnext.down"); styleNext.pressedOffsetX = -1;
     * styleNext.pressedOffsetY = -1; styleNext.font = Assets.primaryFont10px;
     * styleNext.fontColor = Color.BLACK;
     *
     *
     * nextButton = new TextButton("Next", styleNext); nextButton.pad(20);
     * nextButton.setPosition(stage.getWidth() / 1.2f, 20);
     *
     * TextButtonStyle stylePrev = new TextButtonStyle(); stylePrev.up =
     * skin.getDrawable("buttonprev.up"); stylePrev.down =
     * skin.getDrawable("buttonprev.down"); stylePrev.pressedOffsetX = -1;
     * stylePrev.pressedOffsetY = -1; stylePrev.font = Assets.primaryFont10px;
     * stylePrev.fontColor = Color.BLACK; prevButton = new TextButton("Prev",
     * stylePrev); prevButton.pad(20); prevButton.setPosition(35, 20);
     *
     * Gdx.input.setInputProcessor(stage);
     *
     * if (MAX_PAGE != pageNumber) { stage.addActor(nextButton); } if (MIN_PAGE
     * != pageNumber) { stage.addActor(prevButton); }
     *
     * }
     *
     * public void getNextPage() { sound.playSound(4); pageNumber += 2; }
     *
     * public void getPreviousPage() { sound.playSound(4); pageNumber -= 2; }*
     */
    private int MAX_PAGE;
    private int MIN_PAGE;
    private int currentSectionNumber;
    private boolean inContent;
    private final String[] sections = {"Innledning", "Analyse", "Design", "Implementasjon", "Sluttrapport"};
    private long timeSinceLastAction;
    private final long KEY_INPUT_DELAY = 500;

    private int currentPageNumber;

    private TextButton[] content;

    private TextButtonStyle buttonStyle;
    private TextButtonStyle buttonStylePressed;
    private Skin skin;
    private Stage stage;
    private Label leftPage;
    private Label rightPage;

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
        stage = new Stage(PVU.GAME_WIDTH, PVU.GAME_HEIGHT, true);
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
            temp.setWidth(60f);
            temp.setPosition(PVU.GAME_WIDTH * 3 / 4 - temp.getWidth() / 2, PVU.GAME_HEIGHT - 23 - i * temp.getHeight());
            temp.getLabel().setFontScale(0.6f);
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
        if (TimeUtils.millis() - timeSinceLastAction > KEY_INPUT_DELAY) {
            if (inContent) {
                if ((Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) && currentSectionNumber > 0) {
                    changeSelectedSection(-1);
                } else if ((Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) && currentSectionNumber < sections.length - 1) {
                    changeSelectedSection(1);
                } else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
                    section = new Section(sections[currentSectionNumber]);
                    
                    leftPage.setText(section.getPage(currentPageNumber));
                    rightPage.setText(section.getPage(currentPageNumber+1));
                    addPagesToStage();
                    inContent = false;
                    timeSinceLastAction = TimeUtils.millis();
                }
            }else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                if (currentPageNumber < section.getSize()) {
                    flipPage(2);
                }else{
                    if(currentSectionNumber<sections.length){
                        currentSectionNumber++;
                        currentPageNumber=0;
                        section=new Section(sections[currentSectionNumber]);
                        flipPage(0);
                    }
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                if (currentPageNumber == 0 && currentSectionNumber == 0) {
                    addContentToStage();
                    inContent = true;
                    timeSinceLastAction = TimeUtils.millis();
                }else{
                    if(currentPageNumber>0){
                        flipPage(-2);
                    }else{
                        
                    }
                }
            }
        }
    }
    
    private void flipPage(int in){
        currentPageNumber+=in;
        leftPage.setText(section.getPage(currentPageNumber));
        rightPage.setText(section.getPage(currentPageNumber+1));
        timeSinceLastAction = TimeUtils.millis();
    }

    private void addPagesToStage() {
        stage.clear();
        stage.addActor(leftPage);
        stage.addActor(rightPage);
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
        timeSinceLastAction = TimeUtils.millis();
    }

    private void initPages() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        leftPage = new Label("start", labelStyle);
        leftPage.setWidth(PVU.GAME_WIDTH);
        leftPage.setPosition(12, PVU.GAME_HEIGHT / 2);
        leftPage.setWrap(true);
        leftPage.setFontScale(0.4f);

        rightPage = new Label("start", labelStyle);
        rightPage.setWidth(PVU.GAME_WIDTH);
        rightPage.setPosition(PVU.GAME_WIDTH/2+12, PVU.GAME_HEIGHT / 2);
        rightPage.setWrap(true);
        rightPage.setFontScale(0.4f);
    }

    @Override
    protected void cleanUp() {
    }
}
