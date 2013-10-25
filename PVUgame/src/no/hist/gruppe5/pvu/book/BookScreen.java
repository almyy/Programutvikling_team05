package no.hist.gruppe5.pvu.book;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.Input;
import no.hist.gruppe5.pvu.PVU;

/**
 *
 * @author linnk
 */
public class BookScreen extends GameScreen {

    // GUI
    private TextButtonStyle mButtonStyle;
    private TextButtonStyle mButtonStylePressed;
    private Skin mSkin;
    private Stage mStage;
    private Label mLeftPage;
    private Label mHeaderLeftPage;
    private Label mRightPage;
    private Label mHeaderRightPage;
    private Label mSectionText;
    private Input mInput;
    private TextButton[] content;

    // Book
    private final String[] sections = {"Innledning", "Analyse", "Design", "Implementasjon", "Sluttrapport"};
    private Section mSection;
    private int currentPageNumber;
    private int currentSectionNumber;
    private boolean inContent;

    public BookScreen(PVU game) {
        super(game);
        mInput = new Input();
        currentSectionNumber = 0;
        inContent = true;
        currentPageNumber = 0;
        mSection = new Section(sections[currentSectionNumber]);
        initPages();
        createContent();
    }

    private void createContent() {
        TextureAtlas atlas = new TextureAtlas("data/menuButtons/menubuttons.pack");
        mSkin = new Skin(atlas);
        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_WIDTH, true);
        mButtonStyle = new TextButtonStyle();
        mButtonStyle.up = mSkin.getDrawable("menubutton.up");
        mButtonStyle.down = mSkin.getDrawable("menubutton.down");
        mButtonStyle.pressedOffsetX = -1;
        mButtonStyle.pressedOffsetY = -1;
        mButtonStyle.font = Assets.primaryFont10px;
        mButtonStyle.fontColor = Color.BLACK;
        mButtonStylePressed = new TextButtonStyle();
        mButtonStylePressed.up = mSkin.getDrawable("menubutton.down");
        mButtonStylePressed.down = mSkin.getDrawable("menubutton.down");
        mButtonStylePressed.pressedOffsetX = -1;
        mButtonStylePressed.pressedOffsetY = -1;
        mButtonStylePressed.font = Assets.primaryFont10px;
        mButtonStylePressed.fontColor = Color.BLACK;
        content = new TextButton[5];
        for (int i = 0; i < 5; i++) {
            TextButton temp = new TextButton(sections[i], mButtonStyle);
            temp.pad(20);
            temp.setWidth(700f);
            temp.setHeight(170f);
            temp.setPosition(810, 750 - temp.getHeight() * i);
            temp.getLabel().setFontScale(5.6f);
            mStage.addActor(temp);
            content[i] = temp;
        }
        content[currentSectionNumber].setStyle(mButtonStylePressed);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);
        batch.begin();
        batch.draw(Assets.bookBook, 0, 0, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
        batch.end();
        mStage.draw();
    }

    @Override
    protected void update(float delta) {
        if (!isGamePaused()) {
            if (inContent) {
                checkActionsInContent();
            } else {
                checkActions();
            }
        }
    }

    private void checkActionsInContent() {
        if (mInput.up() && currentSectionNumber > 0) {
            changeSelectedSection(-1);
        } else if (mInput.down() && currentSectionNumber < sections.length - 1) {
            changeSelectedSection(1);
        } else if (mInput.action()) {
            mSection = new Section(sections[currentSectionNumber]);
            addPagesToStage();
            flipPage(0);
            mSectionText.setText(sections[currentSectionNumber]);
            content[currentSectionNumber].setStyle(mButtonStyle);
            inContent = false;
        } else if (mInput.right()) {
            content[currentSectionNumber].setStyle(mButtonStyle);
            currentSectionNumber = 0;
            mSection = new Section(sections[currentSectionNumber]);
            currentPageNumber = 0;
            addPagesToStage();
            flipPage(0);
            mSectionText.setText(sections[currentSectionNumber]);
            inContent = false;
        }
    }

    public void checkActions() {
        if (mInput.right()) {
            if (currentPageNumber < mSection.getSize() - 2) {
                flipPage(2);
            } else {
                if (currentSectionNumber < sections.length - 1) {
                    currentSectionNumber++;
                    currentPageNumber = 0;
                    mSection = new Section(sections[currentSectionNumber]);
                    flipPage(0);
                    mSectionText.setText(sections[currentSectionNumber]);
                }
            }
        } else if (mInput.left()) {
            if (currentPageNumber == 0 && currentSectionNumber == 0) {
                addContentToStage();
                inContent = true;
            } else {
                if (currentPageNumber > 0) {
                    flipPage(-2);
                } else {
                    if (currentSectionNumber > 0) {
                        currentSectionNumber--;
                        mSection = new Section(sections[currentSectionNumber]);
                        currentPageNumber = (mSection.getSize() % 2 == 0) ? mSection.getSize() - 2 : mSection.getSize() - 1;
                        flipPage(0);
                        mSectionText.setText(sections[currentSectionNumber]);
                    }
                }
            }
        }
    }

    private void flipPage(int in) {
        currentPageNumber += in;
        mLeftPage.setText(mSection.getPage(currentPageNumber).getContent());
        mRightPage.setText(mSection.getPage(currentPageNumber + 1).getContent());
        mHeaderLeftPage.setText(mSection.getPage(currentPageNumber).getHeader());
        mHeaderRightPage.setText(mSection.getPage(currentPageNumber + 1).getHeader());
    }

    private void addPagesToStage() {
        mStage.clear();
        mStage.addActor(mLeftPage);
        mStage.addActor(mRightPage);
        mSectionText.setText(sections[currentSectionNumber]);
        mStage.addActor(mSectionText);
        mStage.addActor(mHeaderLeftPage);
        mStage.addActor(mHeaderRightPage);
    }

    private void addContentToStage() {
        mStage.clear();
        for (TextButton button : content) {
            mStage.addActor(button);
        }content[currentSectionNumber].setStyle(mButtonStylePressed);
    }

    private void changeSelectedSection(int down) {
        content[currentSectionNumber].setStyle(mButtonStyle);
        currentSectionNumber += down;
        content[currentSectionNumber].setStyle(mButtonStylePressed);
    }

    private void initPages() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        mLeftPage = new Label("start", labelStyle);
        mLeftPage.setWidth(PVU.GAME_WIDTH);
        mLeftPage.setPosition(100, 450);
        mLeftPage.setWrap(true);
        mLeftPage.setFontScale(3.4f);

        mRightPage = new Label("start", labelStyle);
        mRightPage.setWidth(PVU.GAME_WIDTH);
        mRightPage.setPosition(850, 450);
        mRightPage.setWrap(true);
        mRightPage.setFontScale(3.4f);

        mSectionText = new Label("", labelStyle);
        mSectionText.setPosition(325, 900);
        mSectionText.setFontScale(2.5f);

        Label.LabelStyle labelStyle1 = new Label.LabelStyle(Assets.primaryFont10px, Color.DARK_GRAY);
        mHeaderLeftPage = new Label("", labelStyle1);
        mHeaderRightPage = new Label("", labelStyle1);
        mHeaderLeftPage.setPosition(100, 800);
        mHeaderLeftPage.setFontScale(3.8f);
        mHeaderRightPage.setPosition(850, 800);
        mHeaderRightPage.setFontScale(3.8f);
    } 

    @Override
    protected void cleanUp() {
    }
}
