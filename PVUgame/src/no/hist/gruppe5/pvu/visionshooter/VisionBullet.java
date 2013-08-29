/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;

/**
 *
 * @author Rino
 */
public class VisionBullet {
    
    private final float projectileSpeed = 6.6f;
    
    private float projectileHeight = 0.4f;
    private float projectileWidth = 0.4f; 
    
    private float projectileX; 
    private float projectileY;
    
    Sprite bulletSprite; 

    public VisionBullet(){        
        bulletSprite = new Sprite(Assets.visionShooterDocumentRegion); 
        bulletSprite.setSize(projectileHeight, projectileWidth);
        bulletSprite.setPosition(projectileX,projectileY);
    }
    
    void draw(SpriteBatch batch) {
        bulletSprite.draw(batch);
    }
    void update(float delta){
        projectileX += projectileSpeed*delta; 
        System.out.println("Bullet X: " + projectileX + " Bullet Y: " + projectileY);
        bulletSprite.setPosition(projectileX, projectileY);
    }

    public void setProjectileY(float projectileY) {
        this.projectileY = projectileY;
    }

    public float getProjectileX() {
        return projectileX;
    }    
}
