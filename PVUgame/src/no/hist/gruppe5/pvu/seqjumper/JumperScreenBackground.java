package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.PVU;

public class JumperScreenBackground {

    private static final float SPEED = 5f;
    private Sprite mBack1;
    private Sprite mBack2;

    public JumperScreenBackground() {
        mBack1 = new Sprite(Assets.seqBackground);
        mBack2 = new Sprite(Assets.seqBackground);

        mBack1.setPosition(0, 0);
        mBack2.setPosition(PVU.GAME_WIDTH, 0);
    }

    public void draw(SpriteBatch batch) {
        mBack1.draw(batch);
        mBack2.draw(batch);
    }
}
