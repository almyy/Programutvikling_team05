package no.hist.gruppe5.pvu.umlblocks.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.umlblocks.BlocksScreen;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/18/13
 * Time: 12:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class DiamondBlock extends Block {

    private static final float DENSITY = 0.3f;
    private static final float FRICTION = 0.3f;
    private static final float RESTITUTION = 0.01f;

    public DiamondBlock(World world) {
        super(world);
    }

    @Override
    protected void createSprite() {
        sprite = new Sprite(Assets.umlBlocks[Assets.UML_BLOCK_2]);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(-100, -100);
    }

    @Override
    public void createBody(World world) {
        //Dynamic Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0.8f, 2f);
        body = world.createBody(bodyDef);

        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(sprite.getWidth() * BlocksScreen.WORLD_TO_BOX - ANTIPADDING, sprite.getHeight() * BlocksScreen.WORLD_TO_BOX - ANTIPADDING);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = DENSITY;
        fixtureDef.friction = FRICTION;
        fixtureDef.restitution = RESTITUTION;
        body.createFixture(fixtureDef);
        body.setActive(false);

    }

    @Override
    protected void updateBlock() {
        // Update sprite position based on the Box2d body
        Vector2 pos = body.getTransform().getPosition();
        sprite.setPosition((pos.x * BlocksScreen.BOX_TO_WORLD) - sprite.getWidth() / 2,
                (pos.y*BlocksScreen.BOX_TO_WORLD) - sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()) + 45);
        sprite.setScale(1.4f);
    }

    @Override
    public void destroy(World world) {
        world.destroyBody(body);
    }
}
