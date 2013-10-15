package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/15/13
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class Block {

    private Body mBody;
    private Sprite mSprite;

    public Block(Body body, Sprite sprite) {
        this.mBody = body;
        this.mSprite = sprite;
    }

    public void draw() {

    }

    public void update(float delta) {

    }
}
