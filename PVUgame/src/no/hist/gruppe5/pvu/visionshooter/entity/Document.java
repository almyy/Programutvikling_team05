package no.hist.gruppe5.pvu.visionshooter.entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import no.hist.gruppe5.pvu.Assets;

/**
 *
 * @author Frode
 */
public class Document extends Element {
    
    public Document(float elementY) {
        super(new Sprite(Assets.visionShooterDocumentRegion),elementY, 77f);
    }

}
