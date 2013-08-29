package no.hist.gruppe5.pvu.visionshooter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;

/**
 *
 * @author Rino
 */
public class VisionBullet {
    
    private final float projectileSpeed = 66f;
    
    private float projectileHeight = 8f;
    private float projectileWidth = 8f; 
    
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
        bulletSprite.setPosition(projectileX, projectileY);
    }

    public void setProjectileY(float projectileY) {
        this.projectileY = projectileY;
    }

    public float getProjectileX() {
        return projectileX;
    }    
}
