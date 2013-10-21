package no.hist.gruppe5.pvu.temp;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Frode
 */
public class SeqJumpEndScreen extends GameScreen {
    
    private Stage mStage;
    private Label.LabelStyle mLabelstyle;
    private Skin mSkin;

    public SeqJumpEndScreen(PVU game) {
        super(game);
        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true, batch);
    }

    @Override
    protected void draw(float delta) {
    clearCamera(1, 1, 1, 1);

        batch.begin();
        batch.end();
        mStage.draw();    }

    @Override
    protected void update(float delta) {
  if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
           // game.setScreen(new (game));
        }    }

    @Override
    protected void cleanUp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
