package no.hist.gruppe5.pvu.mainroom.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import no.hist.gruppe5.pvu.Assets;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/30/13
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class TeamMates {

    private Sprite[] mSprites;
    private Animation[] mAnimations;

    public TeamMates() {
        mSprites = new Sprite[5];
        mAnimations = new Animation[5];

        mAnimations[0] = new Animation(Assets.SECONDARY_AVATAR_LEFT_1, 2);
        mAnimations[1] = new Animation(Assets.SECONDARY_AVATAR_LEFT_2, 2);
        mAnimations[2] = new Animation(Assets.SECONDARY_AVATAR_RIGHT_1, 2);
        mAnimations[3] = new Animation(Assets.SECONDARY_AVATAR_RIGHT_2, 2);
        mAnimations[4] = new Animation(Assets.SECONDARY_AVATAR_RIGHT_3, 2);

        for(int i = 0; i < mSprites.length; i++) {
            mSprites[i] = new Sprite(mAnimations[i].getFrame());
            mAnimations[i].setFrameTime((float) Math.random() * 0.2f + 0.2f);
        }

        // Hard coding ftw
        mSprites[0].setPosition(71f, 55f);
        mSprites[1].setPosition(71f, 39f);
        mSprites[2].setPosition(120f, 55f);
        mSprites[3].setPosition(120f, 39f);
        mSprites[4].setPosition(120f, 23f);

    }

    public void draw(SpriteBatch batch) {
        for(Sprite s : mSprites) {
            s.draw(batch);
        }
    }

    public void update() {
        for(int i = 0; i < mSprites.length; i++) {
            mAnimations[i].animate(Gdx.graphics.getDeltaTime());
            mSprites[i].setRegion(mAnimations[i].getFrame());
        }
    }
}
