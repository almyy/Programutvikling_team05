/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import no.hist.gruppe5.pvu.Assets;

public class VisionShooterShip{
    
    private Sprite shipSprite;
    private float shipHeight = 0.3f;
    private float shipWidth = 0.3f;
    
    private float shipX = 0.149f;
    
    public VisionShooterShip(){
        shipSprite = new Sprite(Assets.visionShooterShipRegion); 
        shipSprite.setSize(shipHeight, shipWidth);
        shipSprite.setPosition(shipX,1);
    }

    public Sprite getShipSprite() {
        return shipSprite;
    }
    
}
