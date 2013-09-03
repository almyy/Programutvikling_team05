/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.quiz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

public class QuizScreen extends GameScreen {

    private Stage stage;
    private Group questions;
    private Group answers;
    private Skin mQuizSkin = new Skin();
    private ArrayList<Label> mQuestions = new ArrayList();
    private ArrayList<TextButton> mAnswers = new ArrayList();
    private LabelStyle outputStyle = new LabelStyle(Assets.primaryFont10px, Color.BLACK);
    private TextButtonStyle answerStyle = new TextButtonStyle();
    private int mQuestionCounter = 0;
    private long mLastButtonPressed = 0;

    public QuizScreen(PVU game) {
        super(game);
        stage = new Stage();
        questions = new Group();
        answers = new Group(); 

        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        
        mQuizSkin.add("white", new Texture(pixmap));
        mQuizSkin.add("default", Assets.primaryFont10px);
        
        answerStyle.up = mQuizSkin.newDrawable("white",Color.DARK_GRAY);
        answerStyle.down = mQuizSkin.newDrawable("white",Color.DARK_GRAY);
        answerStyle.checked = mQuizSkin.newDrawable("white",Color.DARK_GRAY);
        answerStyle.over = mQuizSkin.newDrawable("white",Color.DARK_GRAY);
        answerStyle.font = mQuizSkin.getFont("default");
        
        mQuizSkin.add("default", answerStyle);

        mQuestions.add(new Label("Syrebad! Syrebad! Syrebad! Syrebad! Syrebad! ", outputStyle));
        mQuestions.add(new Label("Ekstra syre!", outputStyle));

        mAnswers.add(new TextButton("Svovelsyre", answerStyle));
        mAnswers.add(new TextButton("Saltpetersyre", answerStyle));
        mAnswers.add(new TextButton("Sitronsyre", answerStyle));
        mAnswers.add(new TextButton("eddiksyre", answerStyle));

        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).setWrap(true);
            mQuestions.get(i).setColor(Color.BLUE);
            mQuestions.get(i).setWidth(PVU.GAME_WIDTH);
        }

        questions.addActor(mQuestions.get(0));
        answers.addActor(mAnswers.get(0));
        
        stage.addActor(questions);
        stage.addActor(answers);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        stage.draw();
    }

    @Override
    protected void update(float delta) {
        if (Gdx.input.isKeyPressed(Keys.A) && (mQuestionCounter < mQuestions.size() - 1)) {
            if ((TimeUtils.millis() - mLastButtonPressed) > 800L) {
                questions.removeActor(mQuestions.get(mQuestionCounter));
                mQuestionCounter++;
                questions.addActor(mQuestions.get(mQuestionCounter));
                mLastButtonPressed = TimeUtils.millis();
            }
        }
    }

    @Override
    protected void cleanUp() {
    }
}
