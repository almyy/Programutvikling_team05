package no.hist.gruppe5.pvu.temp;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.mainroom.MinigameSelectorScreen;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Frode
 */
public class SeqJumpIntroScreen extends GameScreen {

    private Stage stage;
    private Label.LabelStyle labelstyle;
    private Texture tex;
    private Skin skin;

    public SeqJumpIntroScreen(PVU game) {
        super(game);
        stage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true, batch);
    }

    @Override
    protected void draw(float delta) {
    clearCamera(1, 1, 1, 1);

        batch.begin();
        batch.end();
        stage.draw();    }

    @Override
    protected void update(float delta) {
  if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
           game.setScreen(new MinigameSelectorScreen (game));
        }    }

    @Override
    protected void cleanUp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
