/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.mainroom.MinigameSelectorScreen;

/**
 *
 * @author Frode
 */
public class VisionEnd extends GameScreen {

    private String mPointText = "Points: ";
    private String mPointValue;
    private String mContinue = "Trykk SPACE for aa avslutte";
    private Label mLcontinue;
    private Label mPointTextLabel;
    private Label mPointValueLabel;
    private Stage stage;
    private Label.LabelStyle labelstyle;
    private Texture tex;
    private Skin skin;

    public VisionEnd(PVU game, int points) {
        super(game);
        stage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true, batch);
        mPointTextLabel = makeHeadLabel(mPointText);
        mPointTextLabel.setPosition(PVU.SCREEN_WIDTH / 6, PVU.SCREEN_HEIGHT * 0.8f);
        mPointTextLabel.setFontScale(7f);
        mPointTextLabel.setWrap(true);
        stage.addActor(mPointTextLabel);

        mPointValueLabel = makeLabel(String.valueOf(points));
        mPointValueLabel.setPosition(PVU.SCREEN_WIDTH / 2, PVU.SCREEN_HEIGHT * 0.8f);
        mPointValueLabel.setFontScale(7f);
        mPointValueLabel.setWrap(true);
        stage.addActor(mPointValueLabel);

        mLcontinue = makeLabel(mContinue);
        mLcontinue.setPosition(PVU.SCREEN_WIDTH / 9, PVU.SCREEN_HEIGHT / 2);
        mLcontinue.setFontScale(5f);
        mLcontinue.setWrap(true);
        stage.addActor(mLcontinue);
        

    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        stage.getSpriteBatch().draw(Assets.visionShooterRegion, 0, 0);
        batch.end();
        stage.draw();
    }

    @Override
    protected void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new MinigameSelectorScreen(game));
        }
    }

    @Override
    protected void cleanUp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Label makeHeadLabel(String text) {
        skin = new Skin();
        labelstyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        labelstyle.font = Assets.primaryFont10px;
        labelstyle.fontColor = Color.RED;
        Label l = new Label(text, labelstyle);
        return l;
    }

    private Label makeLabel(String text) {
        skin = new Skin();
        labelstyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        labelstyle.font = Assets.primaryFont10px;
        Label l = new Label(text, labelstyle);
        return l;
    }

    public void setmPointValue(String mPointValue) {
        this.mPointValue = mPointValue;
    }
}
