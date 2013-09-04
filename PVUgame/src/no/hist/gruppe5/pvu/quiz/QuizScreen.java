/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.quiz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.esotericsoftware.tablelayout.Value;
import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

public class QuizScreen extends GameScreen {

    private Stage stage;
    private Group questions;
    private Group answers;
    private ClickListener listener = new ClickListener();
    private Skin mQuizSkin = new Skin();
    private ArrayList<Label> mQuestions = new ArrayList();
    private ArrayList<TextButton> mAnswers = new ArrayList();
    private int[] answersNumbered = {0, 0, 0, 0, 0};
    private LabelStyle outputStyle = new LabelStyle(Assets.primaryFont10px, Color.BLACK);
    private TextButtonStyle answerStyle = new TextButtonStyle();
    private TextButtonStyle answerStyleAnsweredCorrect;
    private TextButtonStyle answerStyleAnsweredWrong;
    private int mQuestionCounter = 0;
    private int mNumberOfCorrectAnswers = 0;
    private long mLastButtonPressed = 0;
    private int answer = -1;
    boolean getNewAnswers = false;

    public QuizScreen(PVU game) {
        super(game);
        stage = new Stage();
        questions = new Group();
        answers = new Group();

        Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();

        mQuizSkin.add("Gray", new Texture(pixmap));

        pixmap.setColor(Color.GREEN);
        pixmap.fill();

        mQuizSkin.add("Green", new Texture(pixmap));

        pixmap.setColor(Color.RED);
        pixmap.fill();

        mQuizSkin.add("Red", new Texture(pixmap));
        mQuizSkin.add("default", Assets.primaryFont10px);

        Drawable gray = mQuizSkin.newDrawable("Gray");
        Drawable green = mQuizSkin.newDrawable("Green");
        Drawable red = mQuizSkin.newDrawable("Red");

        answerStyle = new TextButtonStyle(gray, gray, gray, mQuizSkin.getFont("default"));
        answerStyleAnsweredCorrect = new TextButtonStyle(green, green, green, mQuizSkin.getFont("default"));
        answerStyleAnsweredWrong = new TextButtonStyle(red, red, red, mQuizSkin.getFont("default"));

        mQuizSkin.add("default", answerStyle);

        mQuestions.add(new Label("Hvilket tall er 1", outputStyle));
        mQuestions.add(new Label("Hvilket tall er 2", outputStyle));
        mQuestions.add(new Label("Hvilket tall er 3", outputStyle));
        mQuestions.add(new Label("Hvilket tall er 4", outputStyle));
        mQuestions.add(new Label("Hvilket tall er 5", outputStyle));

        mAnswers.add(new TextButton("1", answerStyle));
        mAnswers.add(new TextButton("2", answerStyle));
        mAnswers.add(new TextButton("3", answerStyle));
        mAnswers.add(new TextButton("4", answerStyle));

        mAnswers.add(new TextButton("1", answerStyle));
        mAnswers.add(new TextButton("2", answerStyle));
        mAnswers.add(new TextButton("3", answerStyle));
        mAnswers.add(new TextButton("4", answerStyle));

        mAnswers.add(new TextButton("1", answerStyle));
        mAnswers.add(new TextButton("2", answerStyle));
        mAnswers.add(new TextButton("3", answerStyle));
        mAnswers.add(new TextButton("4", answerStyle));

        mAnswers.add(new TextButton("1", answerStyle));
        mAnswers.add(new TextButton("2", answerStyle));
        mAnswers.add(new TextButton("3", answerStyle));
        mAnswers.add(new TextButton("4", answerStyle));

        mAnswers.add(new TextButton("1", answerStyle));
        mAnswers.add(new TextButton("2", answerStyle));
        mAnswers.add(new TextButton("3", answerStyle));
        mAnswers.add(new TextButton("4", answerStyle));

        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).setFontScale(5);
            mQuestions.get(i).setAlignment(Align.top);
        }

        questions.addActor(mQuestions.get(0));

        answers.setBounds(200, 200, 300, 100);
        int u = 0; 
        for (int i = 0; i < mAnswers.size(); i++) {
            mAnswers.get(i).setFillParent(true);
            mAnswers.get(i).getLabel().setFontScale(3);
            if(u%4==0){u=0;}
            if (u == 0) {
                mAnswers.get(i).setBounds(0, 105, 300, 100);
            } else if (u==1){
                mAnswers.get(i).setBounds(305, 105, 300, 100);
            } else if (u == 2) {
                mAnswers.get(i).setBounds(0, 0, 300, 100);
            } else if (u == 3) {
                mAnswers.get(i).setBounds(305, 0, 300, 100);
            } u++;
            answers.addActor(mAnswers.get(i));
            if (i > 3) {
                answers.getChildren().items[i].setVisible(false);
            }
        }

        questions.setPosition(stage.getWidth() / 2, stage.getHeight() - 20);

        stage.addActor(questions);
        stage.addActor(answers);

        answers.addListener(listener);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);
        stage.draw();
    }

    @Override
    protected void update(float delta) {
        if ((TimeUtils.millis() - mLastButtonPressed) > 1500L && listener.isOver()) {
            answer = -1;
            if (listener.isOver()) {
                for (int i = 0; i < 4; i++) {
                    if (mAnswers.get(i).isPressed()) {
                        answer = i;
                    }
                    if (i != answersNumbered[mQuestionCounter]) {
                        changeColor((TextButton) answers.getChildren().items[i], answerStyleAnsweredWrong);
                    } else {
                        changeColor((TextButton) answers.getChildren().items[i], answerStyleAnsweredCorrect);
                    }
                }
                mLastButtonPressed = TimeUtils.millis();
            }
            getNewAnswers = true;
        }
        if ((TimeUtils.millis() - mLastButtonPressed) > 1500L && getNewAnswers) {
            if (answer == answersNumbered[mQuestionCounter]) {
                mNumberOfCorrectAnswers++;
            }
            questions.removeActor(mQuestions.get(mQuestionCounter));
            mQuestionCounter++;
            int u = 0;
            for (int i = 0; i < mAnswers.size(); i++) {
                if (u <= 3) {
                    answers.getChildren().items[i].remove();
                    mAnswers.remove(i);
                    if (mQuestionCounter <= 4) {
                        answers.getChildren().items[3].setVisible(true);
                    }
                    i--;
                }
                u++;
            }
            if (mQuestionCounter <= 4) {
                questions.addActor(mQuestions.get(mQuestionCounter));
            }
            mLastButtonPressed = TimeUtils.millis();
            getNewAnswers = false;
        }
        if (mQuestionCounter == 5) {
            System.out.println("Din score ble " + mNumberOfCorrectAnswers + "!");
        }
    }

    @Override
    protected void cleanUp() {
        stage.clear();
    }

    private void changeColor(TextButton button, TextButtonStyle style) {
        button.setStyle(style);
    }
}
