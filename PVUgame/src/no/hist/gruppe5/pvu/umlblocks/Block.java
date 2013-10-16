package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/15/13
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class Block {

    private boolean mLock = false;
    private Vector2 mOverridePosition;

    private Body mBody;
    private Sprite mSprite;

    public Block(World world) {
        mOverridePosition = new Vector2();

        //Dynamic Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0.8f, 2f);
        mBody = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(0.1f, 0.1f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.2f;
        mBody.createFixture(fixtureDef);

    }

    public Block(Body body, Sprite sprite) {
        this.mBody = body;
        this.mSprite = sprite;
    }

    public void draw(SpriteBatch batch) {
        // TODO draw sprite

    }

    public void update(float delta) {
        if(mLock) {
            mBody.getTransform().setPosition(mOverridePosition);
        }

    }

    public void setPosition(Vector2 pos) {
        setPosition(pos.x, pos.y);
    }

    public void setPosition(float x, float y) {
        mOverridePosition.x = x;
        mOverridePosition.y = y;

    }

    public void lock() {
        mLock = true;
    }

    public void release() {
        mLock = false;
    }
}
