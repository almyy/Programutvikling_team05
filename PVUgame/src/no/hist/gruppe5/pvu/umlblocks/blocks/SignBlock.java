package no.hist.gruppe5.pvu.umlblocks.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
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


    public SignBlock(World world) {
        super(world);
    }

    @Override
    protected void createSprite() {
        sprite = new Sprite(Assets.umlBlocks[Assets.UML_BLOCK_4]);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(-100, -100);
    }

    @Override
    public void createBody(World world) {
        //Dynamic Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);
        body = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(sprite.getWidth() * BlocksScreen.WORLD_TO_BOX, (sprite.getHeight() / 3) * BlocksScreen.WORLD_TO_BOX);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 0.9f;
        fixtureDef.friction = 0.9f;
        fixtureDef.restitution = 0.02f;
        body.createFixture(fixtureDef);
        body.setActive(false);

        // TEST
        BodyDef bodyDef2 = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body2 = world.createBody(bodyDef);
        body2.setTransform(new Vector2(0f, -0.15f), 0);

        PolygonShape boxShape2 = new PolygonShape();
        boxShape2.setAsBox(0.01f, (sprite.getHeight() / 2) * BlocksScreen.WORLD_TO_BOX);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = boxShape2;
        fixtureDef2.density = 0.9f;
        fixtureDef2.friction = 0.9f;
        fixtureDef2.restitution = 0.02f;
        body2.createFixture(fixtureDef2);

        WeldJointDef def = new WeldJointDef();
        def.initialize(body, body2, new Vector2(0, 0));

        Joint weld = world.createJoint(def);

    }

    @Override
    protected void updateBlock() {
        // Update sprite position based on the Box2d body
        Vector2 pos = body.getTransform().getPosition();
        sprite.setPosition(
                ((pos.x * BlocksScreen.BOX_TO_WORLD) - sprite.getWidth() / 2),
                ((pos.y * BlocksScreen.BOX_TO_WORLD) - sprite.getHeight() / 3)
        );
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        sprite.setScale(1f);
    }

}
