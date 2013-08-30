package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

public class VisionScreen extends GameScreen {
    private Sprite mVisionDocument;
    private VisionShooterShip mVisionShooterShip;
    private ArrayList<VisionBullet> shipProjectiles;
    private long mLastBulletShot = 0; 

    public VisionScreen(PVU game) {
        super(game);
        mVisionShooterShip = new VisionShooterShip();
        shipProjectiles = new ArrayList<>();
    }

    @Override
    protected void draw(float delta) { 
        clearCamera(1, 1, 1, 1);

        batch.begin();
        batch.draw(Assets.visionShooterRegion, 0, 0, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);

        if (!shipProjectiles.isEmpty()) {
            for (int i = 0; i < shipProjectiles.size(); i++) {
                shipProjectiles.get(i).draw(batch);
            }
        }
        mVisionShooterShip.draw(batch);
        batch.end();
    }

    @Override
    protected void update(float delta) {
        mVisionShooterShip.update(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if ((TimeUtils.millis() - mLastBulletShot) > 800L) {
                VisionBullet vB = new VisionBullet();
                vB.setProjectileY(mVisionShooterShip.getShipY()+(mVisionShooterShip.getShipHeight()/2));
                vB.setProjectileX(mVisionShooterShip.getShipX());
                shipProjectiles.add(vB);
                mLastBulletShot = TimeUtils.millis(); 
            }
        }
        for (int i = 0; i < shipProjectiles.size(); i++) {
            if(shipProjectiles.get(i).getProjectileX()<196){
                shipProjectiles.get(i).update(delta);
            }            
        }
    }

    @Override
    protected void cleanUp() {
    }
}
