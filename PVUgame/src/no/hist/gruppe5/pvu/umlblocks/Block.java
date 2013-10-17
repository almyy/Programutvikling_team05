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

    private boolean mLock = true;
    private boolean mAlive = true;
    private Vector2 mOverridePosition;

    private Body mBody;
    private Sprite mSprite;

    private float mSize = 0.1f;

    public Block(World world) {
        mOverridePosition = new Vector2();

        mSize *= Math.random();

        //Dynamic Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0.8f, 2f);
        mBody = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(mSize, mSize);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 0.9f;
        fixtureDef.friction = 0.9f;
        fixtureDef.restitution = 0.01f;
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
            mBody.setTransform(mOverridePosition.x, mOverridePosition.y, 0);
        }

        if(mBody.getTransform().getPosition().y < (0 - mSize))
            mAlive = false;

    }

    public void setPosition(Vector2 pos) {
        setPosition(pos.x, pos.y);
    }

    public void setPosition(float x, float y) {
        mOverridePosition.x = x;
        mOverridePosition.y = y;
        mBody.setLinearVelocity(new Vector2(0, 0));

    }

    public Body getBody() {
        return mBody;
    }

    public void lock() {
        mLock = true;
    }

    public void release() {
        mLock = false;
    }

    public boolean isLock() {
        return mLock;
    }

    public boolean isAlive() {
        return mAlive;
    }
}
