package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.umlblocks.entities.Block;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 21/10/13
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */
public class Gui {

    private Label.LabelStyle mLabelStyle;

    // Game
    public Label mBlocksLeft;
    public Label mSuccess;

    // Intermediate
    public Label mGameFeedback;

    // Stages
    private Stage mScoreStage;
    private Stage mIntermediateStage;
    private boolean mGame;

    public Gui(float width, float height, boolean keepAspectRation) {
        this.mScoreStage =  new Stage(width, height, keepAspectRation);
        this.mIntermediateStage = new Stage(width, height, keepAspectRation);

        mLabelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);

        initScoreStage();
        initIntermediateStage();

        mGame = false;
    }

    private void initScoreStage() {
        mBlocksLeft = new Label("", mLabelStyle);
        mBlocksLeft.setFontScale(3f);
        mBlocksLeft.setAlignment(Align.bottom | Align.left);
        mBlocksLeft.setFillParent(true);

        mSuccess = new Label("", mLabelStyle);
        mSuccess.setFontScale(3f);
        mSuccess.setAlignment(Align.bottom | Align.right);
        mSuccess.setFillParent(true);

        mScoreStage.addActor(mBlocksLeft);
        mScoreStage.addActor(mSuccess);
    }

    private void initIntermediateStage() {
        mGameFeedback = new Label("Gratuleren, du er best", mLabelStyle);
        mGameFeedback.setFontScale(3f);
        mGameFeedback.setAlignment(Align.center);
        mGameFeedback.setFillParent(true);

        mIntermediateStage.addActor(mGameFeedback);

    }

    public void draw() {
        if(mGame)
            mScoreStage.draw();
        else
            mIntermediateStage.draw();
    }

    public void update(float delta) {
        if(mGame)
            mScoreStage.act();
        else
            mIntermediateStage.act();
    }
    
    public void setBlocksLeft(int blocks) {
        mBlocksLeft.setText(" Remaining: " + blocks);
    }

    public void setSuccess(int dead, int total) {
        int percent = 100 - Math.round((float) dead / (float) total * 100f);
        mSuccess.setText("Success: " + percent + "% ");
    }

    public void setIntermediateText() {
           // TODO
    }

    public void enableIntermediateDisplay() {
        mGame = false;
    }

    public void enableGameDisplay() {
        mGame = true;
    }

}
