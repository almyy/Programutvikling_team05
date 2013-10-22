package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    private World mWorld;
    private OrthographicCamera mGameCam;
    private Stage mStage;
    private Gui mGui;

    private Room mRoom;
    private ArrayList<Block> mActiveBlocks;
    private ArrayList<Block> mBlocksLeft;
    private ScrollingBackground mBackground;

    // Game variables
    private long mLastDrop = -1;
    private float mBlockDropLoc = 1.5f;
    private int mInitialBlockCount = -1;
    private int mBlocksLeftCount = -1;
    private int mBlocksDead = 0;
    private boolean mEasyDone, mMediumDone, mHardDone;
    private float mEasyScore, mMediumScore, mHardScore;
    private int mCurrentGame;
    private boolean mIdleBeforeNextGame = false;

    // Debug
    private Box2DDebugRenderer mDebugRenderer;

    public BlocksScreen(PVU game) {
        super(game);

        mActiveBlocks = new ArrayList<>(30);
        mBlocksLeft = new ArrayList<>(15);

        mWorld = new World(new Vector2(0, -10), false);
        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);
        mGui = new Gui(mStage);

        mBackground = new ScrollingBackground();

        mGameCam = new OrthographicCamera();
        mGameCam.setToOrtho(false, 3f, (PVU.SCREEN_HEIGHT / PVU.SCREEN_WIDTH) * 3f);

        mDebugRenderer = new Box2DDebugRenderer();

        mCurrentGame = Room.EASY;
        startNewGame();

    }

    private void startNewGame() {
        // Start game
        mRoom = new Room(mWorld, mCurrentGame);
        populateBlocksLeft(mCurrentGame);
        popNewBlock();
    }

    private void resetVariables() {
        mBlocksLeftCount = -1;
        mBlocksDead = 0;
        mIdleBeforeNextGame = false;
        mWorld.destroyBody(mRoom.getBody());
        for(Block b : mActiveBlocks) {
            b.destroy(mWorld);
        }
        mActiveBlocks.clear();
    }

    private void populateBlocksLeft(int game_type) {
        switch(game_type) {
            case Room.EASY:
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                break;
            case Room.MEDIUM:
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                break;
            case Room.HARD:
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                break;
        }

        mBlocksLeftCount = mBlocksLeft.size() + 1;
        mInitialBlockCount = mBlocksLeftCount - 1;
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

        // Draw GUI ontop of the rest
        mGui.draw();

        // Render debug outlines
        mDebugRenderer.render(mWorld, mGameCam.combined);
    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);

        checkInput();

        mBackground.update(delta);
        mRoom.update(delta);
        mGui.update(delta);
        mGui.setBlocksLeft(mBlocksLeftCount);
        mGui.setSuccess(mBlocksDead, mInitialBlockCount);

        if(!mActiveBlocks.isEmpty()) {
            Block block = getLastBlock();
            if(block.isLock()) {
                block.setPosition(mBlockDropLoc, BLOCK_DROP_LOCK);
            }
        }

        for(Block b : mActiveBlocks)
            b.update(delta);

        // Check whether or not to end game or add a new block
        if(mBlocksLeft.isEmpty() && isReadyToDrop()&& isPreviousDropped()) {
            if(!mIdleBeforeNextGame)
                gameIsDone();
        } else if(isReadyToDrop() && isPreviousDropped()) {
            popNewBlock();
        }

        // Clean up dead blocks from world and array
        removeDeadBlocks();
    }

    private void popNewBlock() {
        mActiveBlocks.add(mBlocksLeft.remove(0).activate());
        mBlocksLeftCount--;
    }

    private void gameIsDone() {
        mBlocksLeftCount = 0;

        float score = (float) mInitialBlockCount / (float) mBlocksDead;
        switch (mCurrentGame) {
            case Room.EASY:
                mEasyDone = true;
                mEasyScore = score;
                mCurrentGame = Room.MEDIUM;
                break;
            case Room.MEDIUM:
                mMediumDone = true;
                mMediumScore = score;
                mCurrentGame = Room.HARD;
                break;
            case Room.HARD:
                mHardDone = true;
                mHardScore = score;
                mCurrentGame = Room.DONE;
                break;
            case Room.DONE:
                // TODO show end screen
                break;
        }

        mIdleBeforeNextGame = true;

        System.out.println("Yolo");

    }

    private boolean isPreviousDropped() {
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
                mBlocksDead++;
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

        if(mIdleBeforeNextGame && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            resetVariables();
            startNewGame();
        }

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
