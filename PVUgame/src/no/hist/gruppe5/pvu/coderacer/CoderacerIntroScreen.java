/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.coderacer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
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
public class CoderacerIntroScreen extends GameScreen {

    private Stage mStage;
    private Label.LabelStyle mLabelstyle;
    private Skin mSkin;
    private String mIntroText;
    private String mTitleText = "Coderacer \n";
    private String mContinueText = "Trykk SPACE for aa fortsette";
    private Label mContinueLabel;
    private Label mIntroLabel;
    private Label mTitleTextLabel;

    public CoderacerIntroScreen(PVU game) throws FileNotFoundException, IOException {
        super(game);
        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true, batch);
        mTitleTextLabel = makeHeadLabel(mTitleText);
        mTitleTextLabel.setFontScale(6f);
        mTitleTextLabel.setWrap(true);
        mTitleTextLabel.setFillParent(true);
        mTitleTextLabel.setAlignment(Align.top | Align.left);
        mTitleTextLabel.setY(230);

        mIntroText = Assets.readFile("data/coderacer/intro.txt");
        Group inputGroup = new Group();
        mIntroLabel = makeLabel(mIntroText);
        mIntroLabel.setFontScale(2f);
        mIntroLabel.setFillParent(true);
        mIntroLabel.setWrap(true);
        mIntroLabel.setAlignment(Align.top | Align.left);
        mIntroLabel.setY(160);

        mContinueLabel = makeLabel(mContinueText);
        mContinueLabel.setFontScale(2f);
        mContinueLabel.setFillParent(true);
        mContinueLabel.setWrap(true);
        mContinueLabel.setAlignment(Align.top | Align.left);
        mContinueLabel.setY(-50);

        inputGroup.addActor(mIntroLabel);
        inputGroup.addActor(mTitleTextLabel);
        inputGroup.addActor(mContinueLabel);
        inputGroup.setWidth(200);
        inputGroup.setHeight(40);
        inputGroup.setPosition(PVU.SCREEN_WIDTH / 2 - inputGroup.getWidth(), 240);

        mStage.addActor(inputGroup);

    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        batch.draw(Assets.msPcBackground, 0, 0);
        batch.end();
        mStage.draw();
    }

    @Override
    protected void update(float delta) {
        if (Input.action()) {
            game.setScreen(new CoderacerScreen(game));
        }
    }

    @Override
    protected void cleanUp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Label makeHeadLabel(String text) {
        mSkin = new Skin();
        mLabelstyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        mLabelstyle.font = Assets.primaryFont10px;
        mLabelstyle.fontColor = Color.RED;
        Label l = new Label(text, mLabelstyle);
        return l;
    }

    private Label makeLabel(String text) {
        mSkin = new Skin();
        mLabelstyle = new Label.LabelStyle(Assets.primaryFont10px, Color.GREEN);
        mLabelstyle.font = Assets.primaryFont10px;
        Label l = new Label(text, mLabelstyle);
        return l;
    }
}
