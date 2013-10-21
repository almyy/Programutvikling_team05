/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.coderacer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
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
    private String mTitleText = "Coderacer";
    private Label mIntroLabel;
    private Label mTitleTextLabel;

    public CoderacerIntroScreen(PVU game) throws FileNotFoundException, IOException {
        super(game);
        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true, batch);
        mTitleTextLabel = makeHeadLabel(mTitleText);
        mIntroText = Assets.readFile("data/coderacer/intro.txt");
        mIntroLabel = makeLabel(mIntroText);
        mIntroLabel.setScale(3f);
        mIntroLabel.setWrap(true);


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
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
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
