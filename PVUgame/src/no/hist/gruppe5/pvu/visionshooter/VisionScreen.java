package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/28/13
 * Time: 10:50 AM
 */
public class VisionScreen extends GameScreen {
 
    private Sprite mVisionDocument; 
    private Sprite mVisionShooterShip;
    
    public VisionScreen(PVU game) {
        super(game);
        
        mVisionShooterShip = new VisionShooterShip().getShipSprite();
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1); 

        batch.begin();
        batch.draw(Assets.visionShooterRegion, 0, 0, PVU.GAME_WIDTH,PVU.GAME_HEIGHT);
       
        mVisionShooterShip.draw(batch); 
        batch.end();
    }

    @Override
    protected void update(float delta) {
        
    }

    @Override
    protected void cleanUp() {
    }
}
