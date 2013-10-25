package no.hist.gruppe5.pvu.coderacer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.ScoreHandler;
import no.hist.gruppe5.pvu.Input;

/**
 * Created with IntelliJ IDEA. User: karl Date: 8/28/13 Time: 10:49 AM
 */
public class CoderacerScreen extends GameScreen {

    ShapeRenderer mShapeRenderer;

    // GUI
    private Stage mStage;
    private Label mFinishedCode;
    private Label mCodeoutput;
    private Label mFirstLine;
    private Label mTimeLabel;
    private Input mInput;

    // Game Variables
    private Code mCode;
    private int mRemainingTime = 30;
    private int mScore = 0;
    private boolean start;
    private boolean mDoneHandled;

    // Time countdown
    private Timer.Task task = new Timer.Task() {

        @Override
        public void run() {
            if(isGamePaused()) return;
            mRemainingTime--;
        }
    };

    public CoderacerScreen(PVU game) {
        super(game);

        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);
        mShapeRenderer = new ShapeRenderer();
        mInput = new Input();
        mCode = new Code();

        Group outputGroup = new Group();
        Group inputGroup = new Group();

        LabelStyle outputStyle = new LabelStyle(Assets.primaryFont10px, Color.GREEN);
        mCodeoutput = new Label("Du har 30 sekunder på å skrive så mye av koden som mulig.\n\nTrykk space for å begynne.", outputStyle);
        mCodeoutput.setFontScale(3f);
        mCodeoutput.setFillParent(true);
        mCodeoutput.setWrap(true);
        mCodeoutput.setAlignment(Align.top | Align.left);

        mFirstLine = new Label(" ", outputStyle);
        mFirstLine.setFontScale(3f);
        mFirstLine.setFillParent(true);
        mFirstLine.setWrap(true);
        mFirstLine.setAlignment(Align.top | Align.left);
        mFirstLine.setY(mFirstLine.getHeight() * 3);

        LabelStyle finishedStyle = new LabelStyle(Assets.primaryFont10px, Color.RED);
        mFinishedCode = new Label("", finishedStyle);
        mFinishedCode.setFontScale(3f);
        mFinishedCode.setFillParent(true);
        mFinishedCode.setWrap(true);
        mFinishedCode.setAlignment(Align.bottom | Align.left);

     

        mTimeLabel = new Label("" + mRemainingTime, finishedStyle);
        mTimeLabel.setFontScale(3f);

        outputGroup.addActor(mCodeoutput);
        outputGroup.setWidth(120);
        outputGroup.setHeight(40);
        outputGroup.setPosition(PVU.SCREEN_WIDTH / 2 - outputGroup.getWidth() * 1.4f, 450 - mFirstLine.getHeight() * 3);

        outputGroup.addActor(mFirstLine);

        inputGroup.addActor(mFinishedCode);
        inputGroup.setWidth(120);
        inputGroup.setHeight(40);
        inputGroup.setPosition(PVU.SCREEN_WIDTH / 2 - inputGroup.getWidth() * 1.4f, 190);


        mStage.addActor(outputGroup);
        mStage.addActor(inputGroup);
        mStage.addActor(mTimeLabel);

        mTimeLabel.setPosition(PVU.SCREEN_WIDTH * 0.9f, PVU.SCREEN_HEIGHT * 0.1f);

        Gdx.input.setInputProcessor(new inputListener());
    }

    private void updateOutput() {
        StringBuilder codeLeft = new StringBuilder(mCode.getLeft());
        int max = (codeLeft.length() >= 18) ? 18 : codeLeft.length();
        mFirstLine.setText("" + codeLeft.subSequence(0, max));
        if (codeLeft.length() > 0 && codeLeft.charAt(0) == " ".charAt(0)) {
            mFirstLine.setX(6f * 3f);
        } else {
            mFirstLine.setX(0f);
        }
        codeLeft.delete(0, max);
        mCodeoutput.setText(codeLeft);
        mFinishedCode.setText(mCode.getCorrect());
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1); // Important
        batch.begin();
        batch.draw(Assets.msPcBackground, 0, 0);
        batch.end();
        mStage.draw();
        if(start && !mDoneHandled) {
            mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            mShapeRenderer.rect(310f, 458f, 6 * 3, 2f);
            mShapeRenderer.end();
        }
    }

    @Override
    protected void update(float delta) {
        if (mInput.action() && !start) {
            mCodeoutput.setText(mCode.getCode());
            start = true;
            updateOutput();
            Timer.schedule(task, 1f, 1f);
        }

        if ((mCode.isFinished() || mRemainingTime <= 0) && !mDoneHandled) {
            ScoreHandler.updateScore(ScoreHandler.CODE, mCode.getGrade(mScore));
            game.setScreen(new CoderacerEndScreen(game, mCode.getGrade(mScore)));
            mDoneHandled = true;
        } else if (!mDoneHandled) {
            mTimeLabel.setText(mRemainingTime + "");
        }
    }

    @Override
    protected void cleanUp() {
    }

    private class inputListener implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            if (!mCode.isFinished() && !isGamePaused()) {
                if (character > 31) {
                    if (mCode.equals(character)) {
                        mScore++;
                        if (!mCode.isFinished()) {
                            updateOutput();
                        }
                    }
                }
                return true;
            }
            return false;
        }

        @Override
        public boolean touchDown(int x, int y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int x, int y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int x, int y, int pointer) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

    }

    
}
