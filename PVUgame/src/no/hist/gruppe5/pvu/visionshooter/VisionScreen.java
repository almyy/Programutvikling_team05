package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/28/13
 * Time: 10:50 AM
 */
public class VisionScreen extends GameScreen {

    public VisionScreen(PVU game) {
        super(game);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1); // Important

        // Draw here
    }

    @Override
    protected void update(float delta) {
    }

    @Override
    protected void cleanUp() {
    }
}
