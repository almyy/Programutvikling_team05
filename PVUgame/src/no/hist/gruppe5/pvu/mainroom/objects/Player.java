package no.hist.gruppe5.pvu.mainroom.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.Input;
import no.hist.gruppe5.pvu.mainroom.MainScreen;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/30/13
 * Time: 9:34 AM
 *
 */

public class Player {

    private static boolean mLol = false;
    private static boolean mSpeedLol = false;

    private final float PLAYER_SIZE;

    private final Animation PLAYER_BACK;
    private final Animation PLAYER_LEFT;
    private final Animation PLAYER_RIGHT;
    private final Animation PLAYER_FRONT;
    private final Animation PLAYER_SITTING;

    private Animation mCurrentAnimaion;

    private Animation[] animations;

    private boolean mMoveable;

    private boolean mSitting = false;
    private Body mPlayerBody;

    private Sprite mPlayerSprite;
    private Input mInput;

    public Player(World world) {

        mInput = new Input();

        PLAYER_BACK = new Animation(Assets.MAIN_AVATAR_BACK, 3);
        PLAYER_LEFT = new Animation(Assets.MAIN_AVATAR_SIDE_LEFT, 3);
        PLAYER_RIGHT = new Animation(Assets.MAIN_AVATAR_SIDE_RIGHT, 3);
        PLAYER_FRONT = new Animation(Assets.MAIN_AVATAR_FRONT, 3);
        PLAYER_SITTING = new Animation(Assets.MAIN_AVATAR_SITTING, 1);

        mCurrentAnimaion = PLAYER_FRONT;

        animations = new Animation[]{PLAYER_BACK, PLAYER_LEFT, PLAYER_RIGHT, PLAYER_FRONT, PLAYER_SITTING};

        mPlayerSprite = new Sprite(PLAYER_FRONT.getFrame());
        PLAYER_SIZE = mPlayerSprite.getWidth();

        //Dynamic Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(30f, 60f);
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
        mPlayerBody.setUserData(MainScreen.OBJECT_PLAYER);

    }

    public void draw(SpriteBatch batch) {
        mPlayerSprite.draw(batch);
    }

    public void update() {
        updatePlayerMovement();

        if(!mSitting) {
            Vector2 pos = mPlayerBody.getPosition();
            mPlayerSprite.setPosition(pos.x - (mPlayerSprite.getWidth() / 2), pos.y - mPlayerSprite.getHeight() / 4.6f);
            if(mLol)
                mPlayerSprite.rotate(10f);
        }

        float dt = Gdx.graphics.getDeltaTime();

        if(mSpeedLol)
            dt *= 10;

        for (Animation a : animations)
            a.animate(dt);
    }

    private void updatePlayerMovement() {
        float PLAYER_SPEED;
        if(!mSpeedLol) {
            PLAYER_SPEED = 40f;
        }
        else {
            PLAYER_SPEED = 200f;
        }
        boolean keyPressed = false;

        Vector2 newSpeed = new Vector2();

        if(mMoveable) {
            if(mInput.continuousUp()) {
                newSpeed.y = PLAYER_SPEED;
                mCurrentAnimaion = PLAYER_BACK;
                keyPressed = true;
            } else if (mInput.continuousDown()) {
                newSpeed.y = -PLAYER_SPEED;
                mCurrentAnimaion = PLAYER_FRONT;
                keyPressed = true;
            }

            if (mInput.continuousRight()) {
                newSpeed.x = PLAYER_SPEED;
                mCurrentAnimaion = PLAYER_RIGHT;
                keyPressed = true;
            } else if (mInput.continuousLeft()) {
                newSpeed.x = -PLAYER_SPEED;
                mCurrentAnimaion = PLAYER_LEFT;
                keyPressed = true;
            }
        }

        if(mSitting) {
            mPlayerSprite.setRegion(PLAYER_SITTING.getFrame());
        } else if(keyPressed) {
            mPlayerSprite.setRegion(mCurrentAnimaion.getFrame());
        } else {
            mPlayerSprite.setRegion(mCurrentAnimaion.frames[0]);
        }

        mPlayerBody.setLinearVelocity(newSpeed);

        if(newSpeed.x != 0 || newSpeed.y != 0)
            mSitting = false;

    }

    public float getWidth() {
        return mPlayerSprite.getWidth();
    }

    public Vector2 getPosition() {
        return mPlayerBody.getPosition();
    }

    public void sitDown() {
        mSitting = true;
        mPlayerBody.setTransform(75f, 24f, 0f);
        mPlayerSprite.setPosition(71f, 23f);
        mPlayerSprite.setRegion(PLAYER_SITTING.getFrame());
    }

    public boolean isSitting() {
        return mSitting;
    }

    public void setMoveable(boolean moveable) {
        this.mMoveable = moveable;
    }

    public static void lol() {
        mLol = true;
    }

    public static void speedLol() {
        mSpeedLol = true;
    }
}
