package no.hist.gruppe5.pvu.umlblocks.entities;

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
public abstract class Block {

    protected final float ANTIPADDING = 0.011f;

    protected boolean lock = true;
    protected boolean alive = true;
    protected Vector2 overridePosition;

    protected Body body;
    protected Sprite sprite;

    protected float initialRotation;

    public Block(World world) {
        overridePosition = new Vector2();

        createSprite();
        createBody(world);

        initialRotation = (float) Math.random()*360f;

    }

    protected abstract void createSprite();

    public abstract void createBody(World world);

    protected abstract void updateBlock();

    public abstract void destroy(World world);

    protected void overridePosition() {
        body.setTransform(overridePosition.x, overridePosition.y, initialRotation);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void update(float delta) {
        if(lock) {
            overridePosition();
        }

        if(body.getTransform().getPosition().y < 0) {
            alive = false;
        }

        updateBlock();

    }

    public void setPosition(Vector2 pos) {
        setPosition(pos.x, pos.y);
    }

    public void setPosition(float x, float y) {
        overridePosition.x = x;
        overridePosition.y = y;
        body.setLinearVelocity(new Vector2(0, 0));
        body.setAngularVelocity(0);
    }

    public Body getBody() {
        return body;
    }

    public void lock() {
        lock = true;
    }

    public void release() {
        lock = false;
    }

    public boolean isLock() {
        return lock;
    }

    public boolean isAlive() {
        return alive;
    }

    public Block activate() {
        body.setActive(true);
        subActivate();
        return this;
    }

    // User for more advanced bodies
    protected void subActivate() {
    }
}
