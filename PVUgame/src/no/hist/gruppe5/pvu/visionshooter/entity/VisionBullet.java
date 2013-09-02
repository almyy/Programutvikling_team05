package no.hist.gruppe5.pvu.visionshooter.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;

/**
 *
 * @author Rino
 */
public class VisionBullet {
    
    private final float projectileSpeed = 66f;
    
    private float projectileHeight = 4.5f;
    private float projectileWidth = 4.5f; 
    
    private float projectileX; 
    private float projectileY;
    
    Sprite bulletSprite; 

    public VisionBullet(){        
        bulletSprite = new Sprite(Assets.visionShooterBullet); 
        bulletSprite.setSize(projectileHeight, projectileWidth);
        bulletSprite.setPosition(projectileX,projectileY);
    }
    
    public void draw(SpriteBatch batch) {
        bulletSprite.draw(batch);
    }

    public void update(float delta){
        projectileX += projectileSpeed*delta; 
        bulletSprite.setPosition(projectileX, projectileY);
    }

    public void setProjectileY(float projectileY) {
        this.projectileY = projectileY-projectileHeight/2;
    }

    public void setProjectileX(float projectileX) {
        this.projectileX = projectileX;
    }
    

    public float getProjectileX() {
        return projectileX;
    }    

    public Sprite getBulletSprite() {
        return bulletSprite;
    }
    
}
