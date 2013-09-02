package no.hist.gruppe5.pvu.visionshooter.entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.visionshooter.VisionShooterElement;

/**
 *
 * @author Frode
 */
public class ShooterDokument extends VisionShooterElement {
    
    public ShooterDokument(float elementY) {
        super(new Sprite(Assets.visionShooterDocumentRegion),elementY, 30f);
    }

}