package no.hist.gruppe5.pvu.visionshooter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;
/**
 *
 * @author Frode
 */
public class VisionShooterDocument extends VisionShooterElement {
    
    public VisionShooterDocument(float elementY) {
        super(new Sprite(Assets.visionShooterDocumentRegion),elementY, 30f);
    }

}
