package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.TimeUtils;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.coderacer.CoderacerEndScreen;
import no.hist.gruppe5.pvu.coderacer.CoderacerIntroScreen;
import no.hist.gruppe5.pvu.coderacer.CoderacerScreen;
import no.hist.gruppe5.pvu.quiz.QuizHandler;
import no.hist.gruppe5.pvu.quiz.QuizScreen;
import no.hist.gruppe5.pvu.umlblocks.BlocksScreen;
import no.hist.gruppe5.pvu.visionshooter.VisionIntroScreen;
import no.hist.gruppe5.pvu.temp.SeqJumpIntroScreen;

public class MinigameSelectorScreen extends GameScreen {

    public static final int VISIONSHOOTER = 0;
    public static final int REQFINDER = 1;
    public static final int SEQJUMPER = 2;
    public static final int UMLBLOCKS = 3;
    public static final int CODERACER = 4;
    private Skin mQuizSkin = new Skin();
    private TextButtonStyle mMiniGameStylePassed;
    private TextButtonStyle mMiniGameStyleLocked;
    private TextButtonStyle mMiniGameStyleQuizNeeded;
    private String[] mLabels = {"Programmering", "Visjons", "Quiz", "Todo", "todo"};
    private Stage stage;
    private Button mSelector;
    private ArrayList<TextButton> mMiniGames = new ArrayList<>();
    private float mYIncrease = 105f;
    private boolean mSelectorTop = true;
    private boolean mSelectorBottom = false;
    private Group menu;
    private long mLastButtonPressed = 0;

    public MinigameSelectorScreen(final PVU game) {
        super(game);
        menu = new Group();
        stage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_WIDTH, true, batch);

        defineStyles();

        for (int i = 0; i < 5; i++) {
            if (i < QuizHandler.quizzesCompleted) {
                mMiniGames.add(makeButton(mLabels[i], QuizHandler.QUIZ_PASSED, i));
            } else if (i == QuizHandler.quizzesCompleted) {
                mMiniGames.add(makeButton(mLabels[i], QuizHandler.QUIZ_NEEDED, i));
            } else {
                mMiniGames.add(makeButton(mLabels[i], QuizHandler.LOCKED, i));
            }
            menu.addActor(mMiniGames.get(i));
        }

        initMakeButton();

        menu.addActor(mSelector);
        menu.setBounds(510, 295, 590, 100);
        stage.addActor(menu);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        stage.getSpriteBatch().draw(Assets.msPcBackground, 0, 0);
        batch.end();
        stage.draw();
    }

    private boolean enoughTimePassed(long time) {
        return (TimeUtils.millis() - mLastButtonPressed) > time;
    }

    @Override
    protected void update(float delta) {
        int miniGameSelected = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainScreen(game));
        }
        if (enoughTimePassed(75L)) {
            if (Gdx.input.isKeyPressed(Input.Keys.S) && !mSelectorBottom) {
                mSelector.setPosition(0, mSelector.getY() - mYIncrease);
                mSelectorTop = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W) && !mSelectorTop) {
                mSelector.setPosition(0, mSelector.getY() + mYIncrease);
                mSelectorBottom = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                miniGameSelected = (int) (mSelector.getY() / 105f);
            }

            if (mSelector.getY() == 420f) {
                mSelectorTop = true;
            } else if (mSelector.getY() == 0f) {
                mSelectorBottom = true;
            }
            mLastButtonPressed = TimeUtils.millis();
        }
        if (miniGameSelected != -1) {
            switch (miniGameSelected) {
                case VISIONSHOOTER:
                    try {
                        game.setScreen(new VisionIntroScreen(game));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(MinigameSelectorScreen.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MinigameSelectorScreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }
        }




        /* if (Gdx.input.isKeyPressed(Input.Keys.S) && !buttonPressedS) {
         buttonPressedS = true;
         if (counter < 5) {
         counter++;
         }
         if (counter == 1) {
         buttonMove.setPosition(button1.getX(), button1.getY());
         }
         if (counter == 2) {
         buttonMove.setPosition(button2.getX(), button2.getY());
         }
         if (counter == 3) {
         buttonMove.setPosition(button3.getX(), button3.getY());
         }
         if (counter == 4) {
         buttonMove.setPosition(button4.getX(), button4.getY());
         }
         if (counter == 5) {
         buttonMove.setPosition(button5.getX(), button5.getY());
         }
         }
         if (Gdx.input.isKeyPressed(Input.Keys.W) && !buttonPressedW) {
         buttonPressedW = true;
         if (counter > 1) {
         counter--;
         }
         if (counter == 1) {
         buttonMove.setPosition(button1.getX(), button1.getY());
         }
         if (counter == 2) {
         buttonMove.setPosition(button2.getX(), button2.getY());
         }
         if (counter == 3) {
         buttonMove.setPosition(button3.getX(), button3.getY());
         }
         if (counter == 4) {
         buttonMove.setPosition(button4.getX(), button4.getY());
         }
         if (counter == 5) {
         buttonMove.setPosition(button5.getX(), button5.getY());
         }
         }
         if (!Gdx.input.isKeyPressed(Input.Keys.S)) {
         buttonPressedS = false;
         }
         if (!Gdx.input.isKeyPressed(Input.Keys.W)) {
         buttonPressedW = false;
         }

         if (Gdx.input.isKeyPressed(Input.Keys.ENTER) && !buttonPressedENTER) {
         buttonPressedENTER = true;
         if (counter == 1) {
         game.setScreen(new CoderacerScreen(game));
                 try {
                    game.setScreen(new CoderacerIntroScreen(game));
                }catch (FileNotFoundException ex) {
                    Logger.getLogger(MinigameSelectorScreen.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MinigameSelectorScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
         }
         if (counter == 2) {
         try {

         game.setScreen(new VisionIntroScreen(game));

         }catch (FileNotFoundException ex) {
         Logger.getLogger(MinigameSelectorScreen.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
         Logger.getLogger(MinigameSelectorScreen.class.getName()).log(Level.SEVERE, null, ex);
         }

         }
         if (counter == 3) {
         try {
         game.setScreen(new QuizScreen(game));
         } catch (FileNotFoundException ex) {
         Logger.getLogger(MinigameSelectorScreen.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
         Logger.getLogger(MinigameSelectorScreen.class.getName()).log(Level.SEVERE, null, ex);
         }
         }
         if (counter == 4) {
         game.setScreen(new BlocksScreen(game));
         }
         if (counter == 5) {
         game.setScreen(new SeqJumpIntroScreen(game));
         }
         }
         if (!Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
         buttonPressedENTER = false;
         }

         if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
         game.setScreen(PVU.MAIN_SCREEN);
         }*/
    }

    @Override
    protected void cleanUp() {
    }

    private void defineStyles() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(com.badlogic.gdx.graphics.Color.DARK_GRAY);
        pixmap.fill();

        mQuizSkin.add("Gray", new Texture(pixmap));

        pixmap.setColor(com.badlogic.gdx.graphics.Color.GREEN);
        pixmap.fill();

        mQuizSkin.add("Green", new Texture(pixmap));

        pixmap.setColor(com.badlogic.gdx.graphics.Color.RED);
        pixmap.fill();

        mQuizSkin.add("Red", new Texture(pixmap));
        mQuizSkin.add("default", Assets.primaryFont10px);

        Drawable gray = mQuizSkin.newDrawable("Gray");
        Drawable green = mQuizSkin.newDrawable("Green");
        Drawable red = mQuizSkin.newDrawable("Red");

        mMiniGameStylePassed = new TextButtonStyle(green, green, green, mQuizSkin.getFont("default"));
        mMiniGameStyleQuizNeeded = new TextButtonStyle(red, red, red, mQuizSkin.getFont("default"));
        mMiniGameStyleLocked = new TextButtonStyle(gray, gray, gray, mQuizSkin.getFont("default"));
    }

    private TextButton makeButton(String text, int status, int counter) {
        TextButtonStyle miniGameStatus = null;
        switch (status) {
            case QuizHandler.LOCKED:
                miniGameStatus = mMiniGameStyleLocked;
                break;
            case QuizHandler.QUIZ_NEEDED:
                miniGameStatus = mMiniGameStyleQuizNeeded;
                break;
            case QuizHandler.QUIZ_PASSED:
                miniGameStatus = mMiniGameStylePassed;
                break;
        }
        TextButton returnedButton = new TextButton(text, miniGameStatus);
        returnedButton.getLabel().setFontScale(5);
        returnedButton.setPosition(0, (4 - counter) * mYIncrease);
        returnedButton.setFillParent(true);
        return returnedButton;
    }

    private void initMakeButton() {
        Skin buttonSkin = new Skin();
        TextureRegion region = new TextureRegion(Assets.borderBorder);

        buttonSkin.add("border", region);
        Drawable standard = buttonSkin.getDrawable("border");

        ButtonStyle buttonStyle = new ButtonStyle(standard, standard, standard);

        mSelector = new Button(buttonStyle);
        mSelector.setFillParent(true);
        mSelector.setPosition(0, 420);
    }
}
