package no.hist.gruppe5.pvu.visionshooter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;

public class VisionShooterShip{
    
    private Sprite shipSprite;
    
    private float shipHeight = 5f;
    private float shipWidth = 5f;
    
    private float shipX = 14.9f;
    private float shipY = 10; 
    
    private float shipSpeed = 2.2f;  
    
    public VisionShooterShip(){
        shipSprite = new Sprite(Assets.visionShooterShipRegion); 
        shipSprite.setSize(shipHeight, shipWidth);
        shipSprite.setPosition(shipX,shipY);
    }

    public Sprite getShipSprite() {
        return shipSprite;
    }

    void draw(SpriteBatch batch) {
        System.out.println();
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
