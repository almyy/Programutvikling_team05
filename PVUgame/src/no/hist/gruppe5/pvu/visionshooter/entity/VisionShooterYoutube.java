package no.hist.gruppe5.pvu.visionshooter.entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.visionshooter.VisionShooterElement;

/**
 *
 * @author Frode
 */
public class VisionShooterYoutube extends VisionShooterElement {

    public VisionShooterYoutube(float elementY) {
        super(new Sprite(Assets.visionShooterYoutubeRegion), elementY,20f);
    }
}