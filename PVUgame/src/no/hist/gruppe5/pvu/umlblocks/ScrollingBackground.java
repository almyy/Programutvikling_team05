package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private static final float SPEED = 5f;

    private Sprite mBack1;
    private Sprite mBack2;

    public ScrollingBackground() {
        mBack1 = new Sprite(Assets.ubBackground);
        mBack2 = new Sprite(Assets.ubBackground);

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
