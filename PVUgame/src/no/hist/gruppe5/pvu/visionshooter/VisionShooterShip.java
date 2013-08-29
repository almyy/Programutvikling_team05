/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.visionshooter;

<<<<<<< HEAD
<<<<<<< HEAD
import com.badlogic.gdx.graphics.g2d.Sprite;
=======
=======
>>>>>>> b10031027e2d55197f4a20d4e0260ecf33c89727
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
<<<<<<< HEAD
>>>>>>> b10031027e2d55197f4a20d4e0260ecf33c89727
=======
>>>>>>> b10031027e2d55197f4a20d4e0260ecf33c89727
import no.hist.gruppe5.pvu.Assets;

public class VisionShooterShip{
    
    private Sprite shipSprite;
<<<<<<< HEAD
    private float shipHeight = 0.3f;
    private float shipWidth = 0.3f;
    
    private float shipX = 0.149f;
=======
>>>>>>> b10031027e2d55197f4a20d4e0260ecf33c89727
    
    private float shipHeight = 0.5f;
    private float shipWidth = 0.5f;
    
    private float shipX = 0.149f;
    private float shipY = 1; 
    
    private float shipSpeed = 2.2f;  
    
    public VisionShooterShip(){
        shipSprite = new Sprite(Assets.visionShooterShipRegion); 
        shipSprite.setSize(shipHeight, shipWidth);
<<<<<<< HEAD
        shipSprite.setPosition(shipX,1);
=======
>>>>>>> b10031027e2d55197f4a20d4e0260ecf33c89727
        shipSprite.setPosition(shipX,shipY);
    }

    public Sprite getShipSprite() {
        return shipSprite;
    }

    void draw(SpriteBatch batch) {
        shipSprite.draw(batch);
    }

    void update(float delta) {
        if(Gdx.input.isKeyPressed(Keys.DPAD_UP)){
            shipY += shipSpeed*delta;
        }
        if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)){
            shipY -= shipSpeed*delta; 
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
