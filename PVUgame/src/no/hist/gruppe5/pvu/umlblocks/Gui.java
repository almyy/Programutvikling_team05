package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.PVU;
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

    // Tutorial
    public Label mTutorialText;
    private Label mTutorialSpace;

    // Game
    public Label mBlocksLeft;
    public Label mSuccess;

    // Intermediate
    public Label mGameFeedback;
    public Label mSpaceToContinue;

    // Summairze
    private Label mSummarizedScore;

    // Stages
    private Stage mTutorialStage;
    private Stage mScoreStage;
    private Stage mIntermediateStage;
    private boolean mTutorial = true;
    private boolean mGame = false;

    public Gui(float width, float height, boolean keepAspectRation) {
        this.mTutorialStage =  new Stage(width, height, keepAspectRation);
        this.mScoreStage =  new Stage(width, height, keepAspectRation);
        this.mIntermediateStage = new Stage(width, height, keepAspectRation);

        mLabelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);

        initTutorialStage();
        initScoreStage();
        initIntermediateStage();
    }

    private void initTutorialStage() {
        mTutorialText = new Label("Du skal sette sammen strukuren i arkitekturen for prosjektet ditt! Dette kan være en vanskelig oppgave og det blir fort mange ledd og klosser som skal henge sammen på en bra måte." +
                "\n\n Stable alle klossene uten at de faller av platformene for å få best poengsum. Du skal gjennom 3 nivå!", mLabelStyle);
        mTutorialText.setFontScale(2f);
        mTutorialText.setWrap(true);
        mTutorialText.setWidth(450f);
        System.out.println(mTutorialText.getWidth());
        mTutorialText.setX(50f);
        mTutorialText.setY(PVU.SCREEN_HEIGHT - 250f);

        mTutorialSpace = new Label("Trykk på space for å begynne", mLabelStyle);
        mTutorialSpace.setFontScale(4f);
        mTutorialSpace.setAlignment(Align.center);
        mTutorialSpace.setWidth(PVU.SCREEN_WIDTH);
        mTutorialSpace.setY(PVU.SCREEN_HEIGHT - 400f);

        mTutorialStage.addActor(mTutorialText);
        mTutorialStage.addActor(mTutorialSpace);
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
        mGameFeedback = new Label("", mLabelStyle);
        mGameFeedback.setFontScale(3f);
        mGameFeedback.setAlignment(Align.center);
        mGameFeedback.setWidth(PVU.SCREEN_WIDTH);
        mGameFeedback.setY(PVU.SCREEN_HEIGHT - 100f);

        mSpaceToContinue = new Label("Trykk på space for å fortsette", mLabelStyle);
        mSpaceToContinue.setFontScale(3f);
        mSpaceToContinue.setAlignment(Align.center);
        mSpaceToContinue.setWidth(PVU.SCREEN_WIDTH);
        mSpaceToContinue.setY(PVU.SCREEN_HEIGHT - 200f);

        mSummarizedScore = new Label("HURRA", mLabelStyle);
        mSummarizedScore.setFontScale(3f);
        mSummarizedScore.setAlignment(Align.center);
        mSummarizedScore.setWidth(PVU.SCREEN_WIDTH);
        mSummarizedScore.setY(PVU.SCREEN_HEIGHT - 150f);
        mSummarizedScore.setLayoutEnabled(false);

        mIntermediateStage.addActor(mGameFeedback);
        mIntermediateStage.addActor(mSpaceToContinue);
        mIntermediateStage.addActor(mSummarizedScore);

    }

    public void draw() {
        if(mTutorial)
            mTutorialStage.draw();
        else if(mGame)
            mScoreStage.draw();
        else
            mIntermediateStage.draw();
    }

    public void update(float delta) {
        if(mGame) {
            mScoreStage.act();
        } else {
            mIntermediateStage.act();
        }
    }
    
    public void setBlocksLeft(int blocks) {
        mBlocksLeft.setText(" Remaining: " + blocks);
    }

    public int setSuccess(int dead, int total) {
        int percent = 100 - Math.round((float) dead / (float) total * 100f);
        mSuccess.setText("Success: " + percent + "% ");
        return percent;
    }

    public void setIntermediateText(int score, int level) {
        mGameFeedback.setText("Gratulerer, du fikk " + score + " poeng på nivå " + level + "!");
    }

    public void enableIntermediateDisplay() {
        mGame = false;
    }

    public void enableGameDisplay() {
        mTutorial = false;
        mGame = true;
    }

    public boolean isTutorial() {
        return mTutorial;
    }

    public void enableSummarizeText(int totalScore) {
        mSummarizedScore.setText("Du fikk totalt " + totalScore + " poeng, " + ((totalScore > 70) ? "bra!" : "ikke bra.."));
        mSummarizedScore.setLayoutEnabled(true);
    }
}
