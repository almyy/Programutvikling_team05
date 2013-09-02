package no.hist.gruppe5.pvu.visionshooter.entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.visionshooter.VisionShooterElement;

/**
 *
 * @author Frode
 */
public class ShooterFacebook extends VisionShooterElement {

    public ShooterFacebook(float elementY) {
        super(new Sprite(Assets.visionShooterFacebookRegion), elementY,30f);
    }
 
    
}