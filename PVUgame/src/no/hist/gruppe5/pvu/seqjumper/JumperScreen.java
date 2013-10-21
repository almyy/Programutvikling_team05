package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

public class JumperScreen extends GameScreen {

    private static final float WORLD_TO_BOX = 0.064f;
    private static final float BOX_TO_WORLD = 64;
    public static final float WORLD_WIDTH = 3f;
    public static final float WORLD_HEIGHT = 1.8125f;
    private final float BLOCK_DROP_LOCK = 1.5f;
    private final float PLATFORM_SIZE = 0.1f;
    private final float BALL_MARGIN = 0.02f;
    private World mWorld;
    private OrthographicCamera mGameCam;
    private Room mRoom;
    private Ball mBall;
    // Game variables
    private int mCurrentBlock = -1;
    private float mBlockDropLoc = 1.5f;
    // Debug
    private Box2DDebugRenderer mDebugRenderer;
    //Ball movement Y
    private float powerHeight = 0;
    //Ball movement -X
    private boolean hasPressedA = false;
    private float powerLeft = 0;
    private boolean loadedA = false;
    //Ball movement X
    private boolean hasPressedD = false;
    private boolean loadedD = false;
    private float powerRight = 0;
    private float startPositionX;
    private float startPositionY;
//    private Sprite mSeq;
    private Texture mTex;
    private Sprite mSprite;
    private Platform mPlatform;
    private ArrayList<Body> mPlatforms;
    private int playerMove;

    public JumperScreen(PVU game) {
        super(game);

        mWorld = new World(new Vector2(0, -10), false);
        mRoom = new Room(mWorld);

        mBall = new Ball(mWorld);
        mBall.getBody().setTransform(0.5f, 0.3f, 0);

        mPlatform = new Platform();
        mPlatforms = new ArrayList<Body>(5);
        mPlatforms.add(mPlatform.createPlatform(new Vector2(0.5f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(1f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(1.5f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(2f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(2.5f, 0.2f), PLATFORM_SIZE, mWorld, true));

        mGameCam = new OrthographicCamera();
        mGameCam.setToOrtho(false, 3f, (PVU.SCREEN_HEIGHT / PVU.SCREEN_WIDTH) * 3f);

        mDebugRenderer = new Box2DDebugRenderer();

        startPositionX = mBall.getBody().getPosition().x;
        startPositionY = mBall.getBody().getPosition().y;

        mSprite = new Sprite(Assets.seq);

        mSprite.setOrigin(mSprite.getWidth() / 2, mSprite.getHeight() / 2);
        mSprite.setPosition(0, 5);
        mSprite.setScale(0.20f);
        mSprite.setRotation(180);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(0f, 0f, 0f, 1f);

        batch.begin();
        mBall.draw(batch);
        switch (checkCollision()) {
            case 2:
                mSprite.setScale(0.20f);
                mSprite.setRotation(180);
                mSprite.setPosition(30, 5);
                mSprite.draw(batch);
                break;
            case 3:
                mSprite.setScale(0.5f, 0.2f);
                mSprite.setPosition(25, 5);
                mSprite.setRotation(0);
                mSprite.draw(batch);
                break;
            case 4:
                mSprite.setScale(0.5f, 0.2f);
                mSprite.setPosition(25, 5);
                mSprite.setRotation(0);
                mSprite.draw(batch);
                break;
            case 5:
                mSprite.setScale(0.5f, 0.2f);
                mSprite.setPosition(25, 5);
                mSprite.setRotation(0);
                mSprite.draw(batch);
                break;
            case 6:
                mSprite.setPosition(0, 5);
                mSprite.setScale(0.20f);
                mSprite.setRotation(180);
                mSprite.draw(batch);
                break;
        }
        if(playerMove == 1){
                mSprite.setPosition(0, 5);
                mSprite.setScale(0.20f);
                mSprite.setRotation(180);
                mSprite.draw(batch);
        }
        batch.end();

        mDebugRenderer.render(mWorld, mGameCam.combined);
    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);
        checkCollision();
        checkInput();
        checkIfFail();
        mBall.update(delta);

        //mSprite.setRotation(losangle);
        //losangle++;
    }

    private int checkCollision() {
        if (mBall.getBody().getPosition().x < mPlatforms.get(0).getPosition().x + PLATFORM_SIZE + BALL_MARGIN
                && mBall.getBody().getPosition().x > mPlatforms.get(0).getPosition().x - PLATFORM_SIZE - BALL_MARGIN
                && mBall.getBody().getPosition().y > mPlatforms.get(0).getPosition().y + 0.1) {
            return 1;
        }
        if (mBall.getBody().getPosition().x < mPlatforms.get(1).getPosition().x + PLATFORM_SIZE + BALL_MARGIN
                && mBall.getBody().getPosition().x > mPlatforms.get(1).getPosition().x - PLATFORM_SIZE - BALL_MARGIN
                && mBall.getBody().getPosition().y > mPlatforms.get(1).getPosition().y + 0.1) {
            return 2;
        }
        if (mBall.getBody().getPosition().x < mPlatforms.get(2).getPosition().x + PLATFORM_SIZE + BALL_MARGIN
                && mBall.getBody().getPosition().x > mPlatforms.get(2).getPosition().x - PLATFORM_SIZE - BALL_MARGIN
                && mBall.getBody().getPosition().y > mPlatforms.get(2).getPosition().y + 0.1) {
            return 3;
        }
        if (mBall.getBody().getPosition().x < mPlatforms.get(3).getPosition().x + PLATFORM_SIZE + BALL_MARGIN
                && mBall.getBody().getPosition().x > mPlatforms.get(3).getPosition().x - PLATFORM_SIZE - BALL_MARGIN
                && mBall.getBody().getPosition().y > mPlatforms.get(3).getPosition().y + 0.1) {
            return 4;
        }
        if (mBall.getBody().getPosition().x < mPlatforms.get(4).getPosition().x + PLATFORM_SIZE + BALL_MARGIN
                && mBall.getBody().getPosition().x > mPlatforms.get(4).getPosition().x - PLATFORM_SIZE - BALL_MARGIN
                && mBall.getBody().getPosition().y > mPlatforms.get(4).getPosition().y + 0.1) {
            return 5;
        }
        if (mBall.getBody().getPosition().y < 0) {
            return 6;
        }
        return 0;
    }

    private void checkIfFail() {
        if (mBall.getBody().getPosition().y < 0) {
            mWorld.destroyBody(mBall.getBody());
            mBall = new Ball(mWorld);
        }
    }

    private void checkInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(PVU.MAIN_SCREEN);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D) && !hasPressedD) {
            if (powerRight < 0.85) {
                powerRight += 0.008;
            }
            if (powerHeight < 2.55) {
                powerHeight += 0.03;
            }
            loadedD = true;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.D) && loadedD) {
            hasPressedD = true;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.D) && hasPressedD) {
            mBall.getBody().applyForceToCenter(powerRight, powerHeight, true);
            hasPressedD = false;
            powerRight = 0;
            powerHeight = 0;
            loadedD = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) && !hasPressedA) {
            powerLeft -= 0.005;
            powerHeight += 0.05;
            loadedA = true;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.A) && loadedA) {
            hasPressedA = true;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.A) && hasPressedA) {
            mBall.getBody().applyForceToCenter(powerLeft, powerHeight, true);
            hasPressedA = false;
            powerLeft = 0;
            powerHeight = 0;
            loadedA = false;
        }
    }

    @Override
    protected void cleanUp() {
    }
}
