package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import no.hist.gruppe5.pvu.Assets;
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

    public static final float WORLD_TO_BOX = 0.0064f;
    public static final float BOX_TO_WORLD = 64;

    public static final float WORLD_WIDTH = 3f;
    public static final float WORLD_HEIGHT = 1.8125f;

    private final float BLOCK_DROP_LOCK = 1.5f;
    private final int DEFAULT_GAME = 100;
    private final int HARD_GAME = 200;
    private final int LOL_GAME = 300;

    private World mWorld;
    private OrthographicCamera mGameCam;

    private Room mRoom;
    private ArrayList<Block> mActiveBlocks;
    private ArrayList<Sprite> mBlocksLeft;

    // Game variables
    private int mCurrentBlock = -1;
    private long mLastDrop = -1;
    private float mBlockDropLoc = 1.5f;

    // Debug
    private Box2DDebugRenderer mDebugRenderer;

    public BlocksScreen(PVU game) {
        super(game);

        mActiveBlocks = new ArrayList<Block>(30);
        mBlocksLeft = new ArrayList<Sprite>(15);

        populateBlocksLeft(DEFAULT_GAME);

        mWorld = new World(new Vector2(0, -10), false);
        mRoom = new Room(mWorld);

        mGameCam = new OrthographicCamera();
        mGameCam.setToOrtho(false, 3f, (PVU.SCREEN_HEIGHT / PVU.SCREEN_WIDTH) * 3f);

        mDebugRenderer = new Box2DDebugRenderer();

        // Start game
        spawnFirstBlock();

    }

    private void populateBlocksLeft(int game_type) {
        switch(game_type) {
            case DEFAULT_GAME:
                mBlocksLeft.add(new Sprite(Assets.visionShooterFacebookRegion));
                mBlocksLeft.add(new Sprite(Assets.visionShooterDocumentRegion));
                mBlocksLeft.add(new Sprite(Assets.visionShooterShipRegion));
                mBlocksLeft.add(new Sprite(Assets.mainAvatar[0]));
                mBlocksLeft.add(new Sprite(Assets.mainAvatar[1]));
                mBlocksLeft.add(new Sprite(Assets.mainAvatar[2]));
                mBlocksLeft.add(new Sprite(Assets.mainAvatar[3]));
                break;
        }
    }

    private void spawnFirstBlock() {
        mActiveBlocks.add(new Block(mWorld, mBlocksLeft.remove(0)));
        mCurrentBlock = 1;
    }

    @Override
    protected void draw(float delta) {
        clearCamera(0f, 0f, 0f, 1f);


        // Draw all the sprites.
        batch.begin();

        for(Block b : mActiveBlocks)
            b.draw(batch);

        batch.end();

        // Render debug outlines
        mDebugRenderer.render(mWorld, mGameCam.combined);
    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);

        checkInput();

        if(!mActiveBlocks.isEmpty()) {
            Block block = getLastBlock();
            if(block.isLock()) {
                block.setPosition(mBlockDropLoc, BLOCK_DROP_LOCK);
            }
        }

        for(Block b : mActiveBlocks)
            b.update(delta);

        // Add new block after a set time
        if(mBlocksLeft.isEmpty() && readyToDrop())
            gameIsDone();
        else if(readyToDrop() && allDropped())
            mActiveBlocks.add(new Block(mWorld, mBlocksLeft.remove(0)));



        // Clean up dead blocks from world and array
        removeDeadBlocks();
    }

    private boolean allDropped() {
        for(Block b : mActiveBlocks)
            if(b.isLock())
                return false;

        return true;
    }

    private void removeDeadBlocks() {
        for(int i = 0; i < mActiveBlocks.size();) {
            if(!mActiveBlocks.get(i).isAlive()) {
                mWorld.destroyBody(mActiveBlocks.get(i).getBody());
                mActiveBlocks.remove(i);
            } else {
                i++;
            }
        }
    }

    private void checkInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(PVU.MAIN_SCREEN);
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(!mActiveBlocks.isEmpty() && readyToDrop()) {
                getLastBlock().release();
                mLastDrop = TimeUtils.millis();
            }

        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            goLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            goRight();

        }
    }

    private void gameIsDone() {
        //TODO
    }

    private boolean readyToDrop() {
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
        return mActiveBlocks.get(mActiveBlocks.size() - 1);
    }
}
