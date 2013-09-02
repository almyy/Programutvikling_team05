package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

    private static final float PLAYER_SIZE = 12f;

    private final TextureRegion PLAYER_BACK;
    private final TextureRegion PLAYER_LEFT;
    private final TextureRegion PLAYER_RIGHT;
    private final TextureRegion PLAYER_FRONT;

    private SpriteBatch mBatch;

    private Body mPlayerBody;
    private Sprite mPlayerSprite;

    public Player(World world) {
        PLAYER_BACK = Assets.getAvatarRegion(Assets.MAIN_AVATAR_BACK);
        PLAYER_LEFT = Assets.getAvatarRegion(Assets.MAIN_AVATAR_SIDE_LEFT);
        PLAYER_RIGHT = Assets.getAvatarRegion(Assets.MAIN_AVATAR_SIDE_RIGHT);
        PLAYER_FRONT = Assets.getAvatarRegion(Assets.MAIN_AVATAR_FRONT);

        //Dynamic Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(30f, 30f);
        mPlayerBody = world.createBody(bodyDef);
        PolygonShape boxShape = new PolygonShape();
        boxShape.setAsBox(PLAYER_SIZE / 3, PLAYER_SIZE / 3);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boxShape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1;
        mPlayerBody.createFixture(fixtureDef);
        mPlayerBody.setFixedRotation(true);

        mPlayerSprite = new Sprite(PLAYER_FRONT);
        mPlayerSprite.setSize(PLAYER_SIZE, ( mPlayerSprite.getHeight() / mPlayerSprite.getWidth() ) * PLAYER_SIZE) ;

    }

    public void draw(SpriteBatch batch) {
        mPlayerSprite.draw(batch);
    }

    public void update() {
        updatePlayerMovement();

        Vector2 pos = mPlayerBody.getPosition();
        mPlayerSprite.setPosition(pos.x - (mPlayerSprite.getWidth() / 2), pos.y - mPlayerSprite.getHeight() / 4.6f);
    }

    private void updatePlayerMovement() {
        float PLAYER_SPEED = 40f;

        Vector2 newSpeed = new Vector2();

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            newSpeed.y = PLAYER_SPEED;
            mPlayerSprite.setRegion(PLAYER_BACK);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newSpeed.y = -PLAYER_SPEED;
            mPlayerSprite.setRegion(PLAYER_FRONT);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newSpeed.x = PLAYER_SPEED;
            mPlayerSprite.setRegion(PLAYER_RIGHT);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newSpeed.x = -PLAYER_SPEED;
            mPlayerSprite.setRegion(PLAYER_LEFT);
        }

        mPlayerBody.setLinearVelocity(newSpeed);

    }
}
