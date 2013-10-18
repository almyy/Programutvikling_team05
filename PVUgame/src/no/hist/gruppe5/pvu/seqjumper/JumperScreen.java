package no.hist.gruppe5.seqjumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

public class JumperScreen extends GameScreen {

    private Sprite ballSprite;
    private float ballHeight = 10f;
    private float ballWidth = 10f;
    private float ballX = 3f;
    private float ballY = 10f;
    private boolean start;
    private Timer.Task task = new Timer.Task() {

        @Override
        public void run() {
            if (isGamePaused()) {
                return;
            }
            //remainingTime--;
        }
    };

    public JumperScreen(PVU game) {
        super(game);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1); // Important
        batch.begin();
        batch.end();
    }

    @Override
    protected void update(float delta) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void cleanUp() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void jump() {
        int power = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            power += 0.01;
        }
        if (!Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            ballSprite.setPosition(ballX, ballY);
        }
    }
}
