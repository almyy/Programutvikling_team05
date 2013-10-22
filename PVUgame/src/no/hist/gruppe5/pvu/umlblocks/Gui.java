package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import no.hist.gruppe5.pvu.Assets;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 21/10/13
 * Time: 12:29
 * To change this template use File | Settings | File Templates.
 */
public class Gui {

    private Label.LabelStyle mLabelStyle;

    public Label mBlocksLeft;
    public Label mSuccess;

    private Stage mScoreStage;
    private Stage mIntermediateStage;

    public Gui(Stage stage) {
        this.mScoreStage = stage;
        this.mIntermediateStage = new Stage(stage.getWidth(), stage.getHeight(), true);

        mLabelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);

        initScoreStage();
        initIntermediateStage();
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
        // TODO
    }

    public void draw() {
        mScoreStage.draw();
    }

    public void update(float delta) {
        mScoreStage.act(delta);
    }
    
    public void setBlocksLeft(int blocks) {
        mBlocksLeft.setText(" Remaining: " + blocks);
    }

    public void setSuccess(int dead, int total) {
        int percent = 100 - Math.round((float) dead / (float) total * 100f);
        mSuccess.setText("Success: " + percent + "% ");
    }

}
