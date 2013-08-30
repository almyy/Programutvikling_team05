package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import java.util.Random;

public class VisionScreen extends GameScreen {

    private Sprite mVisionDocument;
    private VisionShooterShip mVisionShooterShip;
    private ArrayList<VisionBullet> shipProjectiles;
    private long mLastBulletShot = 0;
    private ArrayList<VisionShooterElement> elements;
    private VisionShooterElement[] allElements = new VisionShooterElement[3];
    private long lastElementSpawned = 0;
    int noElements = 40;//Number of elements
    private Random random = new Random();

    public VisionScreen(PVU game) {
        super(game);
        mVisionShooterShip = new VisionShooterShip();
        shipProjectiles = new ArrayList<>();
        elements = new ArrayList<>();
        allElements[0] = new VisionShooterFacebook(0);
        allElements[1] = new VisionShooterYoutube(0);
        allElements[2] = new VisionShooterDocument(0);

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
        if (!elements.isEmpty()) {
            for (int i = 0; i < elements.size(); i++) {
                elements.get(i).draw(batch);
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
                vB.setProjectileY(mVisionShooterShip.getShipY() + (mVisionShooterShip.getShipHeight() / 2));
                vB.setProjectileX(mVisionShooterShip.getShipX());
                shipProjectiles.add(vB);
                mLastBulletShot = TimeUtils.millis();
            }
        }
        for (int i = 0; i < shipProjectiles.size(); i++) {
            if (shipProjectiles.get(i).getProjectileX() < 196) {
                shipProjectiles.get(i).update(delta);
            } else {
                shipProjectiles.remove(i);
            }
        }
        for(int i=0;i<elements.size();i++){
            for(int j = 0; j<shipProjectiles.size();){
                if(shipProjectiles.get(j).getBulletSprite().getBoundingRectangle().overlaps(elements.get(i).getElementSprite().getBoundingRectangle())){
                    shipProjectiles.remove(j);
                     elements.remove(i);
                     i--;
                     break;
                }else{
                    j++;
                }
                
            }
        }
                
        


        if (noElements > 0 && (TimeUtils.millis() - lastElementSpawned) > 1500L) {
            int index = random.nextInt(3);
            VisionShooterElement i = allElements[index];
            if (i instanceof VisionShooterFacebook) {
                VisionShooterFacebook help = new VisionShooterFacebook(allElements[index].getElementY());
                help.setElementY(random.nextInt(90));
                help.setElementX(180f);
                elements.add(help);
            }
            else if(i instanceof VisionShooterYoutube){
                VisionShooterYoutube help = new VisionShooterYoutube(allElements[index].getElementY());
                help.setElementY(random.nextInt(90));
                help.setElementX(180f);
                elements.add(help);
            }else{
                VisionShooterDocument help = new VisionShooterDocument(allElements[index].getElementY());
                help.setElementY(random.nextInt(90));
                help.setElementX(180f);
                elements.add(help);
            }
            
            noElements--;
            lastElementSpawned = TimeUtils.millis();
        }
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getElementX() > 0) {
                elements.get(i).update(delta);
            } else {
                elements.remove(i);
            }
        }

    }

    @Override
    protected void cleanUp() {
    }
}
