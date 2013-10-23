/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import java.io.FileNotFoundException;
import java.io.IOException;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.ScoreHandler;

/**
 *
 * @author Frode
 */
public class BurndownScreen extends GameScreen {

    private Sprite[] mBurndownCarts;
    private int mCurrentCart = 0;
    private String mText;
    private Label mTextLabel;
    private Label.LabelStyle mLabelstyle;
    private Stage mStage;

    public BurndownScreen(PVU game) {
        super(game);
        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true, batch);

        try {
            mText = Assets.readFile("data/burndownScreen/text.txt");

        } catch (FileNotFoundException e) {
            System.out.println("failure");
        } catch (IOException e) {
            System.out.println("failure");
        }
        mLabelstyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        mTextLabel = new Label(mText, mLabelstyle);
        mTextLabel.setPosition(PVU.SCREEN_WIDTH/4, PVU.SCREEN_HEIGHT/5.3f);
        mTextLabel.setWrap(true);
        mTextLabel.setWidth(PVU.SCREEN_WIDTH/5);
        mTextLabel.setFontScale(3f);

        mStage.addActor(mTextLabel);
        mBurndownCarts = new Sprite[Assets.msBurndownCarts.length];
        for (int i = 0; i < Assets.msBurndownCarts.length; i++) {
            mBurndownCarts[i] = new Sprite(Assets.msBurndownCarts[i]);
            mBurndownCarts[i].setPosition(PVU.GAME_WIDTH / 2.5f - mBurndownCarts[i].getWidth(), PVU.GAME_HEIGHT / 3);
            mBurndownCarts[i].setSize(PVU.GAME_WIDTH / 2, PVU.GAME_HEIGHT / 2);
        }
        checkCompletion();
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);
        batch.begin();
        mBurndownCarts[mCurrentCart].draw(batch);
        batch.end();
        mStage.draw();
    }

    @Override
    protected void update(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(PVU.MAIN_SCREEN);
        }
    }

    @Override
    protected void cleanUp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setBurnDownCart(int num) {
        if (num < 0) {
            num = 0;
        }
        if (num > 4) {
            num = 4;
        }
        mCurrentCart = num;
    }

    private void checkCompletion() {
        for (int i = 0; i < 5; i++) {
            if (ScoreHandler.isMinigameCompleted(i)) {
                setBurnDownCart(++mCurrentCart % 5);
            }
        }
    }
}