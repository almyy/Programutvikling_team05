package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import no.hist.gruppe5.pvu.Assets;

public class GUI {

    private Label.LabelStyle mLabelStyle;

    // Game
    private Label mSuccess;
    private Label mJumps;
    private Label mLife;

    // Intermediate
    public Label mGameFeedback;
    private Label mPressKey;

    // Stages
    private Stage mScoreStage;
    private Stage mIntermediateStage;
    private boolean mChangeStage;

    public GUI(float width, float height, boolean keepAspectRation) {
        this.mScoreStage = new Stage(width, height, keepAspectRation);
        this.mIntermediateStage = new Stage(width, height, keepAspectRation);

        mLabelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);

        initScoreStage();
        initIntermediateStage();

        mChangeStage = false;
    }

    private void initScoreStage() {
        mJumps = new Label("", mLabelStyle);
        mJumps.setFontScale(3f);
        mJumps.setAlignment(Align.top | Align.left);
        mJumps.setFillParent(true);

        mSuccess = new Label("", mLabelStyle);
        mSuccess.setFontScale(3f);
        mSuccess.setAlignment(Align.top | Align.right);
        mSuccess.setFillParent(true);

        mLife = new Label("", mLabelStyle);
        mLife.setFontScale(3f);
        mLife.setAlignment(Align.top | Align.center);
        mLife.setFillParent(true);

        mScoreStage.addActor(mLife);
        mScoreStage.addActor(mJumps);
        mScoreStage.addActor(mSuccess);
    }

    private void initIntermediateStage() {
        mGameFeedback = new Label("", mLabelStyle);
        mGameFeedback.setFontScale(3f);
        mGameFeedback.setAlignment(Align.center);
        mGameFeedback.setFillParent(true);
        
        mPressKey = new Label("Trykk space for å fortsette!", mLabelStyle);
        mPressKey.setFontScale(3f);
        mPressKey.setPosition(250, -100);
        mPressKey.setFillParent(true);

        mIntermediateStage.addActor(mGameFeedback);
        mIntermediateStage.addActor(mPressKey);
    }

    public void draw() {
        if (!mChangeStage) {
            mScoreStage.draw();
        } else {
            mIntermediateStage.draw();
        }
    }

    public void update(float delta) {
        if (!mChangeStage) {
            mScoreStage.act();
        } else {
            mIntermediateStage.act();
        }
    }

    public void setJumps(int jumps) {
        int level = jumps - 1;
        if (level > 0) {
            mJumps.setText(" Jumps: " + level);
        } else {
            mJumps.setText(" Jumps: " + 0);
        }
    }

    public void setSuccess(float score) {
        mSuccess.setText("Success: " + score + "% ");
    }

    public void setLife(int life) {
        mLife.setText("Life: " + life);
    }

    public void setGameFeedback(float mHighscore) {
        mGameFeedback.setText("Din score ble: " + mHighscore);
    }

    public void setIntermediateText() {
        // TODO
    }

    public void enableIntermediateDisplay() {
        mChangeStage = true;
    }

    public void enableScoreDisplay() {
        mChangeStage = false;
    }
}
