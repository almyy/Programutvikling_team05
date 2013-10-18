package no.hist.gruppe5.pvu.umlblocks.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import no.hist.gruppe5.pvu.umlblocks.BlocksScreen;

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

    private float mInitialRotation;

    private float mSize = 0.1f;

    public Block(World world, Sprite sprite) {
        mOverridePosition = new Vector2();

        mSize *= Math.random();

        mSprite = sprite;
        mSprite.setOrigin(mSprite.getWidth() / 2, mSprite.getHeight() / 2);
        mSprite.setPosition(-100, -100);

        //Dynamic Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0.8f, 2f);
        mBody = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(mSprite.getWidth() * BlocksScreen.WORLD_TO_BOX, mSprite.getHeight() * BlocksScreen.WORLD_TO_BOX);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 0.9f;
        fixtureDef.friction = 0.9f;
        fixtureDef.restitution = 0.02f;
        mBody.createFixture(fixtureDef);

        mInitialRotation = (float) Math.random()*360f;

    }

    public Block(Body body, Sprite sprite) {
        this.mBody = body;
        this.mSprite = sprite;
    }

    public void draw(SpriteBatch batch) {
        mSprite.draw(batch);
    }

    public void update(float delta) {
        if(mLock) {
            mBody.setTransform(mOverridePosition.x, mOverridePosition.y, mInitialRotation);
        }

        if(mBody.getTransform().getPosition().y < (0 - mSize))
            mAlive = false;

        // Update sprite position based on the Box2d body
        Vector2 pos = mBody.getTransform().getPosition();
        mSprite.setPosition((pos.x*BlocksScreen.BOX_TO_WORLD) - mSprite.getWidth() / 2,
                (pos.y*BlocksScreen.BOX_TO_WORLD) - mSprite.getHeight() / 2);
        mSprite.setRotation((float) Math.toDegrees(mBody.getAngle()));

    }

    public void setPosition(Vector2 pos) {
        setPosition(pos.x, pos.y);
    }

    public void setPosition(float x, float y) {
        mOverridePosition.x = x;
        mOverridePosition.y = y;
        mBody.setLinearVelocity(new Vector2(0, 0));
        mBody.setAngularVelocity(0);
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
