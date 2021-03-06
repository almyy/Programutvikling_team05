package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.PVU;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 21/10/13
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public class ScrollingBackground {

    private static float SPEED = 5f;

    private Sprite mBack1;
    private Sprite mBack2;

    public ScrollingBackground() {
        init(Assets.ubBackground);
    }

    public ScrollingBackground(TextureRegion background) {
        init(background);
    }

    public ScrollingBackground(TextureRegion background, float speed) {
        init(background);
        this.SPEED = speed;
    }

    private void init(TextureRegion background) {
        mBack1 = new Sprite(background);
        mBack2 = new Sprite(background);

        mBack1.setPosition(0, 0);
        mBack2.setPosition(PVU.GAME_WIDTH, 0);
    }

    public void draw(SpriteBatch batch) {
        mBack1.draw(batch);
        mBack2.draw(batch);
    }

    public void update(float delta) {
        mBack1.setX(mBack1.getX() - delta * SPEED);
        mBack2.setX(mBack2.getX() - delta * SPEED);

        if(mBack1.getX() < -PVU.GAME_WIDTH) {
            mBack1.setX(0);
            mBack2.setX(PVU.GAME_WIDTH);
        }


    }
}
