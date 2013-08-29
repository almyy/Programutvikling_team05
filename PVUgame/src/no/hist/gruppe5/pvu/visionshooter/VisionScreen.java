package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.Gdx;
<<<<<<< HEAD
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
=======
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
>>>>>>> b10031027e2d55197f4a20d4e0260ecf33c89727
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/**
 * Created with IntelliJ IDEA. User: karl Date: 8/28/13 Time: 10:50 AM
 */
public class VisionScreen extends GameScreen {
<<<<<<< HEAD
 
    private Sprite mVisionDocument; 
    private Sprite mVisionShooterShip;
    
    public VisionScreen(PVU game) {
        super(game);
        
        mVisionShooterShip = new VisionShooterShip().getShipSprite();
=======

    private Sprite mVisionDocument;
    private VisionShooterShip mVisionShooterShip;
    private ArrayList<VisionBullet> shipProjectiles;
    private long mLastBulletShot = 0; 

    public VisionScreen(PVU game) {
        super(game);
        mVisionShooterShip = new VisionShooterShip();
        shipProjectiles = new ArrayList<>();
>>>>>>> b10031027e2d55197f4a20d4e0260ecf33c89727
    }

    @Override
    protected void draw(float delta) {
<<<<<<< HEAD
        clearCamera(1, 1, 1, 1); 

        batch.begin();
        batch.draw(Assets.visionShooterRegion, 0, 0, PVU.GAME_WIDTH,PVU.GAME_HEIGHT);
       
        mVisionShooterShip.draw(batch); 
=======
        clearCamera(1, 1, 1, 1);

        batch.begin();
        batch.draw(Assets.visionShooterRegion, 0, 0, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);

        mVisionShooterShip.draw(batch);

        if (!shipProjectiles.isEmpty()) {
            for (int i = 0; i < shipProjectiles.size(); i++) {
                shipProjectiles.get(i).draw(batch);
            }
        }

>>>>>>> b10031027e2d55197f4a20d4e0260ecf33c89727
        batch.end();
    }

    @Override
    protected void update(float delta) {
<<<<<<< HEAD
        
=======
        mVisionShooterShip.update(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if ((TimeUtils.millis() - mLastBulletShot) > 1000L) {
                VisionBullet vB = new VisionBullet();
                vB.setProjectileY(mVisionShooterShip.getShipY());
                shipProjectiles.add(vB);
                mLastBulletShot = TimeUtils.millis(); 
            }
        }
        for (int i = 0; i < shipProjectiles.size(); i++) {
            VisionBullet vBHelp = shipProjectiles.get(i);
            if(vBHelp.getProjectileX()>9.6f){
                shipProjectiles.remove(vBHelp);
            }
            vBHelp.update(delta);
        }
>>>>>>> b10031027e2d55197f4a20d4e0260ecf33c89727
    }

    @Override
    protected void cleanUp() {
    }
}
