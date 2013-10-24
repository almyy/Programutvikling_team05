package no.hist.gruppe5.pvu.visionshooter.entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import no.hist.gruppe5.pvu.Assets;

/**
 *
 * @author Frode
 */
public class Youtube extends Element {

    public Youtube(float elementY) {
        super(new Sprite(Assets.visionShooterYoutubeRegion), elementY,55f);
    }
}