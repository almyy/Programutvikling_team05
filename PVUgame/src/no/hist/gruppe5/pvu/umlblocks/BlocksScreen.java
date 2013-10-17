package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/15/13
 * Time: 9:39 AM
 * To change this template use File | Settings | File Templates.
 */

public class BlocksScreen extends GameScreen {

    private static final float WORLD_TO_BOX = 0.064f;
    private static final float BOX_TO_WORLD = 64;

    public static final float WORLD_WIDTH = 3f;
    public static final float WORLD_HEIGHT = 1.8125f;

    private final float BLOCK_DROP_LOCK = 1.5f;

    private World mWorld;
    private OrthographicCamera mGameCam;

    private Room mRoom;
    private ArrayList<Block> mBlocks;

    // Game variables
    private int mCurrentBlock = -1;
    private long mLastDrop = -1;
    private float mBlockDropLoc = 1.5f;

    // Debug
    private Box2DDebugRenderer mDebugRenderer;

    public BlocksScreen(PVU game) {
        super(game);

        mBlocks = new ArrayList<Block>(15);

        mWorld = new World(new Vector2(0, -10), false);
        mRoom = new Room(mWorld);

        mGameCam = new OrthographicCamera();
        mGameCam.setToOrtho(false, 3f, (PVU.SCREEN_HEIGHT / PVU.SCREEN_WIDTH) * 3f);

        mDebugRenderer = new Box2DDebugRenderer();

        // Start game
        spawnFirstBlock();

    }

    private void spawnFirstBlock() {
        mBlocks.add(new Block(mWorld));
        mCurrentBlock = 1;
    }

    @Override
    protected void draw(float delta) {
        clearCamera(0f, 0f, 0f, 1f);

        for(Block b : mBlocks)
            b.draw(batch);

        mDebugRenderer.render(mWorld, mGameCam.combined);
    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);

        checkInput();

        if(!mBlocks.isEmpty()) {
            Block block = getLastBlock();
            if(block.isLock()) {
                block.setPosition(mBlockDropLoc, BLOCK_DROP_LOCK);
            }
        }

        for(Block b : mBlocks)
            b.update(delta);

        removeDeadBlocks();
    }

    private void removeDeadBlocks() {
        for(int i = 0; i < mBlocks.size();) {
            if(!mBlocks.get(i).isAlive()) {
                mWorld.destroyBody(mBlocks.get(i).getBody());
                mBlocks.remove(i);
            } else {
                i++;
            }
        }
    }

    private void checkInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(PVU.MAIN_SCREEN);
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(!mBlocks.isEmpty() && readyToDrop()) {
                getLastBlock().release();
                mLastDrop = TimeUtils.millis();
                mBlocks.add(new Block(mWorld));
            }

        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            goLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            goRight();

        }
    }

    private boolean readyToDrop() {
        System.out.println((TimeUtils.millis() - mLastDrop));
        return (TimeUtils.millis() - mLastDrop) > 800L;
    }

    public static final float LOC_SPEED = 0.02f;

    private void goRight() {
        if(mBlockDropLoc < WORLD_WIDTH)
            mBlockDropLoc += LOC_SPEED;
    }

    private void goLeft() {
        if(mBlockDropLoc > 0)
            mBlockDropLoc -= LOC_SPEED;
    }

    @Override
    protected void cleanUp() {
    }

    public Block getLastBlock() {
        return mBlocks.get(mBlocks.size() - 1);
    }
}
