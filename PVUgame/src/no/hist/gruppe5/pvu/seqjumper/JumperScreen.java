package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;

public class JumperScreen extends GameScreen {

    private static final float WORLD_TO_BOX = 0.064f;
    private static final float BOX_TO_WORLD = 64;
    public static final float WORLD_WIDTH = 3f;
    public static final float WORLD_HEIGHT = 1.8125f;
    private final float BLOCK_DROP_LOCK = 1.5f;
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
    private Sprite mSeq;
    private Texture mTex;
    private Sprite mSprite;

    public JumperScreen(PVU game) {
        super(game);

        mWorld = new World(new Vector2(0, -10), false);
        mRoom = new Room(mWorld);

        mBall = new Ball(mWorld);

        mGameCam = new OrthographicCamera();
        mGameCam.setToOrtho(false, 3f, (PVU.SCREEN_HEIGHT / PVU.SCREEN_WIDTH) * 3f);

        mDebugRenderer = new Box2DDebugRenderer();

        startPositionX = mBall.getBody().getPosition().x;
        startPositionY = mBall.getBody().getPosition().y;

        mTex = Assets.seq.getTexture();
        mSprite = new Sprite(mTex, 20, 20, 50, 50);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(0f, 0f, 0f, 1f);

        
        mBall.draw(batch);
        mSprite.draw(batch);

        mDebugRenderer.render(mWorld, mGameCam.combined);
    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);

        checkInput();
        checkIfFail();
        mBall.update(delta);
    }

    private void checkIfFail() {
        System.out.println(mBall.getBody().getPosition().y);
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
            powerRight += 0.005;
            powerHeight += 0.05;
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
