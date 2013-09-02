package no.hist.gruppe5.pvu.visionshooter.entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.PVU;

public class VisionShooterShip{
    
    private Sprite shipSprite;
    
    private float shipHeight = 10f;
    private float shipWidth = 10f;
    
    private float shipX = 3f;
    private float shipY = 10; 
    
    private float shipSpeed = 54f;  
    
    public VisionShooterShip(){
        shipSprite = new Sprite(Assets.visionShooterShipRegion); 
        shipSprite.setSize(shipHeight, shipWidth);
        shipSprite.setPosition(shipX,shipY);
    }

    public Sprite getShipSprite() {
        return shipSprite;
    }

    public void draw(SpriteBatch batch) {
        System.out.println();
        shipSprite.draw(batch);
    }

    public void update(float delta) {
        if(Gdx.input.isKeyPressed(Keys.DPAD_UP)){
            if((shipY+shipHeight)<PVU.GAME_HEIGHT){
                shipY += shipSpeed*delta;
            }
        }
        if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)){
            if(shipY>0){
                shipY -= shipSpeed*delta;
            } 
        }
        shipSprite.setPosition(shipX, shipY);
    }

    public float getShipX() {
        return shipX;
    }

    public float getShipY() {
        return shipY;
    }

    public float getShipHeight() {
        return shipHeight;
    }
    
    
}
