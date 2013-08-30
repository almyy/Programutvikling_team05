package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import no.hist.gruppe5.pvu.Assets;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/30/13
 * Time: 9:34 AM
 *
 */

public class Player {

    private static final float PLAYER_SIZE = 8f;

    private SpriteBatch mBatch;

    private Body mPlayerBody;
    private Sprite mPlayerSprite;

    public Player(World world) {
        //Dynamic Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(30f, 30f);
        mPlayerBody = world.createBody(bodyDef);
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(PLAYER_SIZE / 2 , PLAYER_SIZE / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1;
        mPlayerBody.createFixture(fixtureDef);
        mPlayerBody.setFixedRotation(true);

        mPlayerSprite = new Sprite(Assets.testRegion);
        mPlayerSprite.setSize(PLAYER_SIZE, PLAYER_SIZE * 2);

    }

    public void draw(SpriteBatch batch) {
        mPlayerSprite.draw(batch);
    }

    public void update() {
        updatePlayerMovement();

        Vector2 pos = mPlayerBody.getPosition();
        mPlayerSprite.setPosition(pos.x - (mPlayerSprite.getWidth() / 2), pos.y - mPlayerSprite.getHeight() / 4);
    }

    private void updatePlayerMovement() {
        float PLAYER_SPEED = 40f;

        Vector2 newSpeed = new Vector2();

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            newSpeed.y = PLAYER_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newSpeed.y = -PLAYER_SPEED;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newSpeed.x = PLAYER_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newSpeed.x = -PLAYER_SPEED;
        }

        mPlayerBody.setLinearVelocity(newSpeed);

    }
}
