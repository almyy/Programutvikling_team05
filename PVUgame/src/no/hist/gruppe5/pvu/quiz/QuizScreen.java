/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.quiz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
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
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.ScoreHandler;

public class QuizScreen extends GameScreen {

    private Stage stage;
    private Group questions;
    private Group answers;
    private ClickListener listener = new ClickListener();
    private Skin mQuizSkin = new Skin();
    private ArrayList<Label> mQuestions = new ArrayList();
    private ArrayList<TextButton> mAnswers = new ArrayList();
    private String[] quizNames = {"data/Quizes/quiz_00.txt", "data/Quizes/quiz_01.txt", "data/Quizes/quiz_02.txt", "data/Quizes/quiz_03.txt", "data/Quizes/quiz_04.txt"};
    private final int numberOfQuestions = 5;
    private int[] answersNumbered = new int[numberOfQuestions];
    private LabelStyle outputStyle = new LabelStyle(Assets.primaryFont10px, Color.BLACK);
    private TextButtonStyle answerStyle = new TextButtonStyle();
    private TextButtonStyle answerStyleAnsweredCorrect;
    private TextButtonStyle answerStyleAnsweredWrong;
    private int mQuestionCounter = 0;
    private int mNumberOfCorrectAnswers = 0;
    private long mLastButtonPressed = 0;
    private int answer = -1;
    boolean getNewAnswers = false;

    public QuizScreen(PVU game) throws FileNotFoundException, IOException {
        super(game);

        stage = new Stage();
        questions = new Group();
        answers = new Group();

        defineStyles();
        readQuiz(quizNames[0]);
        initializeQuestions();
        initializeAnswers();

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
        if (mQuestionCounter == numberOfQuestions) {
            Label finishLabel = new Label("Din score ble " + mNumberOfCorrectAnswers + "\n Press space for å avslutte", outputStyle);
            finishLabel.setScale(3);
            questions.addActor(finishLabel);
            if (Gdx.input.isKeyPressed(Keys.SPACE)) {
                game.setScreen(PVU.MAIN_SCREEN);
                float score = mNumberOfCorrectAnswers/numberOfQuestions*100;
                //ScoreHandler.updateScore(3, score);
            }
        }
    }

    @Override
    protected void cleanUp() {
        stage.clear();
    }

    private void changeColor(TextButton button, TextButtonStyle style) {
        button.setStyle(style);
    }

    private void readQuiz(String fileName) throws FileNotFoundException, IOException {
        DataInputStream in = new DataInputStream(new FileInputStream(fileName));
        BufferedReader inBR = new BufferedReader(new InputStreamReader(in));
        String strLine;
        int counter = 0;
        int answerCounter = 0;
        while ((strLine = inBR.readLine()) != null) {
            if (!"".equals(strLine)) {
                if (counter < numberOfQuestions) {
                    mQuestions.add(new Label(strLine, outputStyle));
                } else if (counter < (numberOfQuestions * 4 + 10)) {
                    mAnswers.add(new TextButton(strLine, answerStyle));
                } else {
                    answersNumbered[answerCounter] = Integer.parseInt(strLine);
                    answerCounter++;
                }
            }
            counter++;
        }
    }

    private void defineStyles() {
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
    }

    private void initializeQuestions() {
        mQuizSkin.add("default", answerStyle);

        for (int i = 0; i < mQuestions.size(); i++) {
            mQuestions.get(i).setFontScale(4);
            mQuestions.get(i).setFillParent(true);
            mQuestions.get(i).setWrap(true);
            mQuestions.get(i).setAlignment(Align.top);
        }

        questions.addActor(mQuestions.get(0));
        questions.setWidth(200);
        questions.setPosition(stage.getWidth() / 2 - questions.getWidth() / 2, stage.getHeight() - 20);

    }

    private void initializeAnswers() {
        answers.setBounds(200, 200, 300, 100);
        int u = 0;
        for (int i = 0; i < mAnswers.size(); i++) {
            mAnswers.get(i).getLabel().setFontScale(3);
            if (u % 4 == 0) {
                u = 0;
            }
            if (u == 0) {
                mAnswers.get(i).setBounds(0, 105, 300, 100);
            } else if (u == 1) {
                mAnswers.get(i).setBounds(305, 105, 300, 100);
            } else if (u == 2) {
                mAnswers.get(i).setBounds(0, 0, 300, 100);
            } else if (u == 3) {
                mAnswers.get(i).setBounds(305, 0, 300, 100);
            }
            u++;
            answers.addActor(mAnswers.get(i));
            if (i > 3) {
                answers.getChildren().items[i].setVisible(false);
            }
        }
    }
}
