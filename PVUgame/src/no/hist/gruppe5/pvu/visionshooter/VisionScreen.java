package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.visionshooter.entity.*;

import java.util.Random;

public class VisionScreen extends GameScreen {

    int points = 0;
    private Sprite mVisionDocument;
    private ShooterShip mVisionShooterShip;
    private ArrayList<Bullet> shipProjectiles;
    private long mLastBulletShot = 0;
    private ArrayList<VisionShooterElement> elements;
    private VisionShooterElement[] allElements = new VisionShooterElement[3];
    private long lastElementSpawned = 0;
    int noElements = 40;//Number of elements
    private Random random = new Random();

    public VisionScreen(PVU game) {
        super(game);
        mVisionShooterShip = new ShooterShip();
        shipProjectiles = new ArrayList();
        elements = new ArrayList();
        allElements[0] = new ShooterFacebook(0);
        allElements[1] = new ShooterYoutube(0);
        allElements[2] = new ShooterDokument(0);

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
                Bullet vB = new Bullet();
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
        for (int i = 0; i < elements.size(); i++) {
            for (int j = 0; j < shipProjectiles.size();) {
                if (shipProjectiles.get(j).getBulletSprite().getBoundingRectangle().overlaps(elements.get(i).getElementSprite().getBoundingRectangle())) {
                    shipProjectiles.remove(j);
                    elements.remove(i);
                    i--;
                    break;
                } else {
                    j++;
                }

            }
        }
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i) instanceof ShooterDokument) {
                if (elements.get(i).getElementSprite().getBoundingRectangle().overlaps(mVisionShooterShip.getShipSprite().getBoundingRectangle())) {
                    points += 40;
                    System.out.println("Points" + points);
                    elements.remove(i);
                }
            } else {
                if (elements.get(i).getElementSprite().getBoundingRectangle().overlaps(mVisionShooterShip.getShipSprite().getBoundingRectangle())) {
                    points -= 40;
                    System.out.println("Points" + points);
                    elements.remove(i);
                }
            }
        }


        if (noElements > 0 && (TimeUtils.millis() - lastElementSpawned) > 1500L) {
            int index = random.nextInt(3);
            VisionShooterElement i = allElements[index];
            if (i instanceof ShooterFacebook) {
                ShooterFacebook help = new ShooterFacebook(allElements[index].getElementY());
                help.setElementY(random.nextInt(90));
                help.setElementX(180f);
                elements.add(help);
            } else if (i instanceof ShooterYoutube) {
                ShooterYoutube help = new ShooterYoutube(allElements[index].getElementY());
                help.setElementY(random.nextInt(90));
                help.setElementX(180f);
                elements.add(help);
            } else {
                ShooterDokument help = new ShooterDokument(allElements[index].getElementY());
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
                points -= 20;
                System.out.println("Points:" + points);
                elements.remove(i);
            }
        }

    }

    @Override
    protected void cleanUp() {
    }
}
