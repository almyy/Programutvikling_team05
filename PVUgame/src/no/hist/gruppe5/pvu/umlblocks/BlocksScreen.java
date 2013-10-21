package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.umlblocks.blocks.Block;
import no.hist.gruppe5.pvu.umlblocks.blocks.DiamondBlock;
import no.hist.gruppe5.pvu.umlblocks.blocks.SignBlock;
import no.hist.gruppe5.pvu.umlblocks.blocks.SquareBlock;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/15/13
 * Time: 9:39 AM
 * To change this template use File | Settings | File Templates.
 */

public class BlocksScreen extends GameScreen {

    public static final float WORLD_TO_BOX = 3f / 192f / 2f;
    public static final float BOX_TO_WORLD = 192f / 3f;

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
    private ArrayList<Block> mBlocksLeft;
    private ScrollingBackground mBackground;

    // Game variables
    private int mCurrentBlock = -1;
    private long mLastDrop = -1;
    private float mBlockDropLoc = 1.5f;

    // Debug
    private Box2DDebugRenderer mDebugRenderer;

    public BlocksScreen(PVU game) {
        super(game);

        mActiveBlocks = new ArrayList<Block>(30);
        mBlocksLeft = new ArrayList<Block>(15);


        mWorld = new World(new Vector2(0, -10), false);
        mRoom = new Room(mWorld, Room.EASY);
        mBackground = new ScrollingBackground();

        mGameCam = new OrthographicCamera();
        mGameCam.setToOrtho(false, 3f, (PVU.SCREEN_HEIGHT / PVU.SCREEN_WIDTH) * 3f);

        mDebugRenderer = new Box2DDebugRenderer();

        // Start game
        populateBlocksLeft(DEFAULT_GAME);
        popNewBlock();

    }

    private void populateBlocksLeft(int game_type) {
        switch(game_type) {
            case DEFAULT_GAME:
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                break;
        }
    }

    @Override
    protected void draw(float delta) {
        clearCamera(0f, 0f, 0f, 1f);


        // Draw all the sprites.
        batch.begin();

        mBackground.draw(batch);
        mRoom.draw(batch);

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

        mBackground.update(delta);
        mRoom.update(delta);

        if(!mActiveBlocks.isEmpty()) {
            Block block = getLastBlock();
            if(block.isLock()) {
                block.setPosition(mBlockDropLoc, BLOCK_DROP_LOCK);
            }
        }

        for(Block b : mActiveBlocks)
            b.update(delta);

        // Add new block after a set time
        if(mBlocksLeft.isEmpty() && isReadyToDrop())
            gameIsDone();
        else if(isReadyToDrop() && isLastDropped())
            popNewBlock();

        // Clean up dead blocks from world and array
        removeDeadBlocks();
    }

    private void popNewBlock() {
        mActiveBlocks.add(mBlocksLeft.remove(0).activate());
    }

    private boolean isLastDropped() {
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
            if(!mActiveBlocks.isEmpty() && isReadyToDrop()) {
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

    private boolean isReadyToDrop() {
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
