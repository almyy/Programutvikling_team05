package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/30/13
 * Time: 1:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class TeamMates {

    Sprite[] mSprites;

    public TeamMates() {
        mSprites = new Sprite[5];

        mSprites[0] = new Sprite(Assets.getAvatarRegion(Assets.SECONDARY_AVATAR_LEFT_1));
        mSprites[1] = new Sprite(Assets.getAvatarRegion(Assets.SECONDARY_AVATAR_LEFT_2));
        mSprites[2] = new Sprite(Assets.getAvatarRegion(Assets.SECONDARY_AVATAR_RIGHT_1));
        mSprites[3] = new Sprite(Assets.getAvatarRegion(Assets.SECONDARY_AVATAR_RIGHT_2));
        mSprites[4] = new Sprite(Assets.getAvatarRegion(Assets.SECONDARY_AVATAR_RIGHT_3));

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

    }
}
