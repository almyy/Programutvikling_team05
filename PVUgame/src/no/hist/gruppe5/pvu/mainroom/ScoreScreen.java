package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.ScoreHandler;
import no.hist.gruppe5.pvu.mainroom.MainScreen;

public class ScoreScreen extends GameScreen {

    private String text = "Coderacer";
    private String text2 = "Visionshooter";
    private String text3 = "Quiz";
    private String text4 = "Minigame 4";
    private String text5 = "Minigame 5";
    private String titleTextMinigame = "Minigames";
    private Stage stage;
    private Label minigame1;
    private Label minigame2;
    private Label minigame3;
    private Label minigame4;
    private Label minigame5;
    private Label score1;
    private Label score2;
    private Label score3;
    private Label score4;
    private Label score5;
    private Label headMinigames;
    private Texture tex;
    private Skin skin;
    private Label.LabelStyle labelstyle;
    private String mFinalGrade;
    private Label mFinalGradeLabel;
    private Label mGradeTextLabel;

    public ScoreScreen(PVU game) {
        super(game);
        stage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true, batch);
        
        
        headMinigames = makeHeadLabel(titleTextMinigame);
        headMinigames.setPosition(PVU.SCREEN_WIDTH / 3, PVU.SCREEN_HEIGHT * 0.9f);
        headMinigames.setFontScale(6f);
        headMinigames.setWrap(true);
        stage.addActor(headMinigames);


        minigame1 = makeLabel(text);
        minigame1.setPosition(PVU.SCREEN_WIDTH / 3.5f, PVU.SCREEN_HEIGHT * 0.7f);
        minigame1.setFontScale(3f);
        minigame1.setWrap(true);
        stage.addActor(minigame1);

        minigame2 = makeLabel(text2);
        minigame2.setPosition(PVU.SCREEN_WIDTH / 3.5f, PVU.SCREEN_HEIGHT * 0.6f);
        minigame2.setFontScale(3f);
        minigame2.setWrap(true);
        stage.addActor(minigame2);

        minigame3 = makeLabel(text3);
        minigame3.setPosition(PVU.SCREEN_WIDTH / 3.5f, PVU.SCREEN_HEIGHT * 0.5f);
        minigame3.setFontScale(3f);
        minigame3.setWrap(true);
        stage.addActor(minigame3);

        minigame4 = makeLabel(text4);
       minigame4.setPosition(PVU.SCREEN_WIDTH / 3.5f, PVU.SCREEN_HEIGHT * 0.4f);
        minigame4.setFontScale(3f);
        minigame4.setWrap(true);
        stage.addActor(minigame4);

        minigame5 = makeLabel(text5);
         minigame5.setPosition(PVU.SCREEN_WIDTH / 3.5f, PVU.SCREEN_HEIGHT * 0.3f);
        minigame5.setFontScale(3f);
        minigame5.setWrap(true);
        stage.addActor(minigame5);

        score1 = makeLabel("" + ScoreHandler.getMiniGameGrade(ScoreHandler.CODE));
        score1.setPosition(PVU.SCREEN_WIDTH / 1.5f, PVU.SCREEN_HEIGHT * 0.7f);
        score1.setFontScale(3f);
        score1.setWrap(true);
        stage.addActor(score1);

        score2 = makeLabel("" + ScoreHandler.getMiniGameGrade(ScoreHandler.VISION));
        score2.setPosition(PVU.SCREEN_WIDTH / 1.5f, PVU.SCREEN_HEIGHT * 0.6f);
        score2.setFontScale(3f);
        score2.setWrap(true);
        stage.addActor(score2);

        score3 = makeLabel("" + ScoreHandler.getMiniGameGrade(ScoreHandler.QUIZ));
        score3.setPosition(PVU.SCREEN_WIDTH / 1.5f, PVU.SCREEN_HEIGHT * 0.5f);
        score3.setFontScale(3f);
        score3.setWrap(true);
        stage.addActor(score3);

        score4 = makeLabel(text4);
        score4.setPosition(PVU.SCREEN_WIDTH / 1.5f, PVU.SCREEN_HEIGHT * 0.4f);
        score4.setFontScale(3f);
        score4.setWrap(true);
        stage.addActor(minigame4);

        score5 = makeLabel(text5);
          score5.setPosition(PVU.SCREEN_WIDTH / 1.5f, PVU.SCREEN_HEIGHT * 0.3f);
        score5.setFontScale(3f);
        score5.setWrap(true);
        stage.addActor(minigame5);
        
        mFinalGrade = String.valueOf(ScoreHandler.getGrade());
        mFinalGradeLabel = makeHeadLabel(mFinalGrade);
        mFinalGradeLabel.setPosition(PVU.SCREEN_WIDTH / 1.5f, PVU.SCREEN_HEIGHT * 0.1f);
        mFinalGradeLabel.setFontScale(8f);
        stage.addActor(mFinalGradeLabel);
        
        
        mGradeTextLabel = makeLabel("Grade: ");
        mGradeTextLabel.setPosition(PVU.SCREEN_WIDTH / 3.5f, PVU.SCREEN_HEIGHT * 0.1f);
        mGradeTextLabel.setFontScale(6f);
        stage.addActor(mGradeTextLabel);
        //mGradeTextLabel.setPosition(x, y);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        //stage.getSpriteBatch().draw(Assets.msPcBackground, 0, 0);
        batch.end();
        stage.draw();
    }

    @Override
    protected void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(PVU.MAIN_SCREEN);
        }
    }

    @Override
    protected void cleanUp() {
    }

    private Label makeLabel(String text) {
        //tex = new Texture(Gdx.files.internal("data/DialogTextureWithoutFrame.png"));
        skin = new Skin();
        labelstyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        labelstyle.font = Assets.primaryFont10px;
        //skin.add("labelback", new TextureRegion(tex, 10, 10));
        //Drawable d = skin.getDrawable("labelback");
        Label l = new Label(text, labelstyle);
        return l;
    }

    private Label makeHeadLabel(String text) {
        //tex = new Texture(Gdx.files.internal("data/DialogTextureWithoutFrame.png"));
        skin = new Skin();
        labelstyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        labelstyle.font = Assets.primaryFont10px;
        labelstyle.fontColor = Color.RED;
        //skin.add("labelback", new TextureRegion(tex, 10, 10));
        //Drawable d = skin.getDrawable("labelback");
        Label l = new Label(text, labelstyle);
        return l;
    }
}