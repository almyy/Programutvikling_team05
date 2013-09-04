/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 *
 * @author linnk
 */
public class PauseScreen extends GameScreen {

    private GameScreen lastScreen;
    private Stage stage;
    private TextureAtlas atlas;
    private Label.LabelStyle labelStyle;
    private Label pauseLabel;

    public PauseScreen(PVU game, GameScreen lastScreen) {
        super(game);
        this.lastScreen = lastScreen;

        stage = new Stage(PVU.GAME_WIDTH * 2.7f, PVU.GAME_HEIGHT * 2.7f, true);
        atlas = new TextureAtlas("data/menuButtons/menubuttons.pack");
        
        labelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        pauseLabel = new Label("PAUSE", labelStyle);
        pauseLabel.setFontScale(1.9f);
        pauseLabel.setPosition(PVU.GAME_WIDTH * 1.225f, PVU.GAME_HEIGHT);
        stage.addActor(pauseLabel);
        
        Gdx.input.setInputProcessor(new inputListener());
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);
        batch.begin();
        batch.draw(Assets.introMainLogo, PVU.GAME_WIDTH / 3, PVU.GAME_HEIGHT / 2, PVU.GAME_WIDTH / 3, PVU.GAME_HEIGHT / 3);
        batch.end();
        stage.draw();
    }

    @Override
    protected void update(float delta) {
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
            if (keycode == Input.Keys.ESCAPE) {
                System.out.println("hei");
                game.setScreen(lastScreen);
            }
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
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
