package no.hist.gruppe5.pvu.scorescreen;

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
    private String titleTextScore = "Score";
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
    private Label headScore;
    private Label headMinigames;
    private Texture tex;
    private Skin skin;
    private Label.LabelStyle labelstyle;

    public ScoreScreen(PVU game) {
        super(game);

        stage = new Stage(PVU.GAME_WIDTH, PVU.GAME_HEIGHT, true, batch);

        headMinigames = makeHeadLabel(titleTextMinigame);
        headMinigames.setPosition(10, 96);
        stage.addActor(headMinigames);

        headScore = makeHeadLabel(titleTextScore);
        headScore.setPosition(120, 96);
        stage.addActor(headScore);

        minigame1 = makeLabel(text);
        minigame1.setPosition(10, 78);
        stage.addActor(minigame1);

        minigame2 = makeLabel(text2);
        minigame2.setPosition(10, 65);
        stage.addActor(minigame2);

        minigame3 = makeLabel(text3);
        minigame3.setPosition(10, 52);
        stage.addActor(minigame3);

        minigame4 = makeLabel(text4);
        minigame4.setPosition(10, 39);
        stage.addActor(minigame4);

        minigame5 = makeLabel(text5);
        minigame5.setPosition(10, 26);
        stage.addActor(minigame5);

        score1 = makeLabel(""+ ScoreHandler.getMiniGameGrade(ScoreHandler.CODE));
        score1.setPosition(120, 78);
        stage.addActor(score1);

        score2 = makeLabel(""+ ScoreHandler.getMiniGameGrade(ScoreHandler.VISION));
        score2.setPosition(120, 65);
        stage.addActor(score2);

        score3 = makeLabel(""+ ScoreHandler.getMiniGameGrade(ScoreHandler.QUIZ));
        score3.setPosition(120, 52);
        stage.addActor(score3);

        score4 = makeLabel(text4);
        score4.setPosition(10, 39);
        stage.addActor(minigame4);

        score4 = makeLabel(text5);
        score4.setPosition(10, 26);
        stage.addActor(minigame5);
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
