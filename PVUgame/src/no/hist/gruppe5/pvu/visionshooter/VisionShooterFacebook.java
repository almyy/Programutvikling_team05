package no.hist.gruppe5.pvu.visionshooter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;
/**
 *
 * @author Frode
 */
public class VisionShooterFacebook extends VisionShooterElement {

    public VisionShooterFacebook( float elementY) {
        super(new Sprite(Assets.visionShooterFacebookRegion), elementY,5f);
    }
 
    
}