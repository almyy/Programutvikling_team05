package no.hist.gruppe5.pvu.mainroom.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import no.hist.gruppe5.pvu.Assets;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 9/3/13
 * Time: 11:19 AM
 */

public class Animation {
    private int currentFrame = 0;
    private float maxFrameTimeSeconds = 0.15f;
    private float timeSinceLastFrame = 0;

    public TextureRegion[] frames;

    public Animation(int from, int num) {
        frames = new TextureRegion[num];
        for(int i = from; i < from+num; i++) {
            frames[i - from] = Assets.getAvatarRegion(i);
        }
    }

    public void animate(float stateTime) {
        timeSinceLastFrame += stateTime;
        if (timeSinceLastFrame > maxFrameTimeSeconds) {
            currentFrame = (++currentFrame % frames.length);
            timeSinceLastFrame = 0f;
        }
    }

    public TextureRegion getFrame() {
        return frames[currentFrame];
    }

    public void setFrameTime(float frameTime) {
        this.maxFrameTimeSeconds = frameTime;
    }
}