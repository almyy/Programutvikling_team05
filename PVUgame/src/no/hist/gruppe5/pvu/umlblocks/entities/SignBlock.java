package no.hist.gruppe5.pvu.umlblocks.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.umlblocks.BlocksScreen;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/18/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class SignBlock extends Block {

    private static final float DENSITY = 0.3f;
    private static final float FRICTION = 0.1f;
    private static final float RESTITUTION = 0.01f;

    private Body mStick;

    private float mRotationOffset;
    private Vector2 mStickOffset;

    public SignBlock(World world) {
        super(world);
    }

    @Override
    protected void createSprite() {
        mRotationOffset = 7.5f;

        sprite = new Sprite(Assets.umlBlocks[Assets.UML_BLOCK_4]);
        sprite.setOrigin(sprite.getWidth() / 2, (sprite.getHeight() / 2) + mRotationOffset);
        sprite.setPosition(-100, -100);
    }

    @Override
    public void createBody(World world) {
        mStickOffset = new Vector2(0f, -0.15f);

        //Main square body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        body = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox((sprite.getWidth() * BlocksScreen.WORLD_TO_BOX) - ANTIPADDING,
                ((sprite.getHeight() / 3) * BlocksScreen.WORLD_TO_BOX) - ANTIPADDING);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = DENSITY;
        fixtureDef.friction = FRICTION;
        fixtureDef.restitution = RESTITUTION;
        body.createFixture(fixtureDef);
        body.setActive(false);

        // Stick body
        BodyDef stickDef = new BodyDef();
        stickDef.type = BodyDef.BodyType.DynamicBody;
        mStick = world.createBody(bodyDef);
        mStick.setTransform(mStickOffset, 0);
        mStick.setActive(false);

        PolygonShape stickShape = new PolygonShape();
        stickShape.setAsBox(0.015f, ((sprite.getHeight() / 1.3f) * BlocksScreen.WORLD_TO_BOX) - ANTIPADDING);
        FixtureDef stickFixture = new FixtureDef();
        stickFixture.shape = stickShape;
        stickFixture.density = DENSITY;
        stickFixture.friction = FRICTION;
        stickFixture.restitution = RESTITUTION;
        mStick.createFixture(stickFixture);
        mStick.setActive(false);

        WeldJointDef def = new WeldJointDef();
        def.initialize(body, mStick, new Vector2(0, 0));

        Joint weld = world.createJoint(def);


    }

    @Override
    protected void updateBlock() {
        // Update sprite position based on the Box2d body
        Vector2 pos = body.getTransform().getPosition();
        sprite.setPosition(
                ((pos.x * BlocksScreen.BOX_TO_WORLD) - (sprite.getWidth() / 2)),
                ((pos.y * BlocksScreen.BOX_TO_WORLD) - (sprite.getHeight() / 2) - mRotationOffset)
        );
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        sprite.setScale(1f);
    }

    @Override
    public void destroy(World world) {
        world.destroyBody(body);
        world.destroyBody(mStick);
    }

    @Override
    protected void overridePosition() {
        body.setTransform(overridePosition.x, overridePosition.y, initialRotation);
        mStick.setTransform(overridePosition.x + mStickOffset.x,
                overridePosition.y + mStickOffset.y, initialRotation);
    }

    @Override
    protected void subActivate() {
        super.subActivate();
        mStick.setActive(true);
    }
}
