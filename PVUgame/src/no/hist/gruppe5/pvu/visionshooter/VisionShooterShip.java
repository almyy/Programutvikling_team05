/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;

public class VisionShooterShip {

    private Sprite shipSprite;
    private float shipHeight = 0.5f;
    private float shipWidth = 0.5f;
    private float shipX = 0.149f;
    private float shipY = 1;
    private float shipSpeed = 3.2f;

    public VisionShooterShip() {
        shipSprite = new Sprite(Assets.visionShooterShipRegion);
        shipSprite.setSize(shipHeight, shipWidth);
        shipSprite.setPosition(shipX, shipY);
    }

    public Sprite getShipSprite() {
        return shipSprite;
    }

    void draw(SpriteBatch batch) {
        shipSprite.draw(batch);
    }

    void update(float delta) {
        if (Gdx.input.isKeyPressed(Keys.DPAD_UP)&&(shipY < 5.8- shipHeight)) {
            shipY += shipSpeed * delta;
            System.out.println("Y: " + shipY);
        }
        if (Gdx.input.isKeyPressed(Keys.DPAD_DOWN) && (shipY > 0)) {
            shipY -= shipSpeed * delta;
        }
        shipSprite.setPosition(shipX, shipY);
    }

    public float getShipX() {
        return shipX;
    }

    public float getShipY() {
        return shipY;
    }
}
