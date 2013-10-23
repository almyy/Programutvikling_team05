/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.io.FileNotFoundException;
import java.io.IOException;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.Input;
import no.hist.gruppe5.pvu.PVU;

/**
 *
 * @author Frode
 */
public class VisionIntroScreen extends GameScreen {

    // Stage
    private Stage mStage;

    // Strings for description
    private String mTitleText = "Visjonsdokument";
    private String mIntroText;
    private String mYoutubeText;
    private String mFacebookText;
    private String mVisionDocText;

    // Graphics
    private Label.LabelStyle mLabelStyle;
    private Sprite mYoutube;
    private Sprite mFacebook;
    private Sprite mDoc;
    private Input mInput;

    public VisionIntroScreen(PVU game) throws FileNotFoundException, IOException {
        super(game);

        mInput = new Input();

        // Read descriptions from file
        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);
        mIntroText = Assets.readFile("data/visionScreen_intro/Intro.txt");
        mYoutubeText = Assets.readFile("data/visionScreen_intro/Youtube.txt");
        mFacebookText = Assets.readFile("data/visionScreen_intro/Facebook.txt");
        mVisionDocText = Assets.readFile("data/visionScreen_intro/Vision.txt");

        // Set up everything stage related
        Label title = makeHeadLabel(mTitleText);
        title.setPosition(PVU.SCREEN_WIDTH / 4, PVU.SCREEN_HEIGHT * 0.9f);
        title.setFontScale(6f);
        title.setWrap(true);
        mStage.addActor(title);

        Label intro = makeLabel(mIntroText);
        intro.setPosition(PVU.SCREEN_WIDTH / 10, PVU.SCREEN_HEIGHT * 0.65f);
        intro.setWrap(true);
        intro.setWidth(PVU.GAME_WIDTH * 1.5f);
        intro.setFontScale(3f);
        mStage.addActor(intro);

        Label youtube = makeLabel(mYoutubeText);
        youtube.setPosition(PVU.SCREEN_WIDTH / 6, PVU.SCREEN_HEIGHT / 2.2f);
        youtube.setWrap(true);
        youtube.setWidth(PVU.GAME_WIDTH * 2);
        youtube.setFontScale(2f);
        mStage.addActor(youtube);

        Label facebook = makeLabel(mFacebookText);
        facebook.setPosition(PVU.SCREEN_WIDTH / 6, PVU.SCREEN_HEIGHT / 2.8f);
        facebook.setWrap(true);
        facebook.setWidth(PVU.GAME_WIDTH * 2);
        facebook.setFontScale(2f);
        mStage.addActor(facebook);

        Label vision = makeLabel(mVisionDocText);
        vision.setPosition(PVU.SCREEN_WIDTH / 6, PVU.SCREEN_HEIGHT / 4f);
        vision.setWrap(true);
        vision.setWidth(PVU.GAME_WIDTH * 2);
        vision.setFontScale(2f);
        mStage.addActor(vision);

        Label continueLabel = makeHeadLabel("Trykk SPACE for å fortsette");
        continueLabel.setPosition(PVU.SCREEN_WIDTH / 4, PVU.SCREEN_HEIGHT / 7f);
        continueLabel.setWrap(true);
        continueLabel.setFontScale(3f);
        mStage.addActor(continueLabel);

        // Initialise sprites
        mYoutube = new Sprite(Assets.visionShooterYoutubeRegion);
        mYoutube.setPosition(PVU.GAME_WIDTH / 9, PVU.GAME_HEIGHT / 2.3f);

        mFacebook = new Sprite(Assets.visionShooterFacebookRegion);
        mFacebook.setPosition(PVU.GAME_WIDTH / 9, PVU.GAME_HEIGHT / 3);

        mDoc = new Sprite(Assets.visionShooterDocumentRegion);
        mDoc.setPosition(PVU.GAME_WIDTH / 9.5f, PVU.GAME_HEIGHT / 5);
        mDoc.scale(-0.3f);
    }

    

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        
        batch.draw(Assets.visionShooterRegion, 0, 0);
        mYoutube.draw(batch);
        mFacebook.draw(batch);
        mDoc.draw(batch);

        batch.end();

        mStage.draw();
    }

    @Override
    protected void update(float delta) {
        if (mInput.action()) {
            game.setScreen(new VisionScreen(game));
        }
    }

    @Override
    protected void cleanUp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Label makeHeadLabel(String text) {
        mLabelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        mLabelStyle.font = Assets.primaryFont10px;
        mLabelStyle.fontColor = Color.RED;
        return new Label(text, mLabelStyle);
    }

    private Label makeLabel(String text) {
        mLabelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        mLabelStyle.font = Assets.primaryFont10px;
        return new Label(text, mLabelStyle);
    }
}
