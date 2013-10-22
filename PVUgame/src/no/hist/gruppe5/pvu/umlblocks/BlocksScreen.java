package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.ScoreHandler;
import no.hist.gruppe5.pvu.Settings;
import no.hist.gruppe5.pvu.quiz.QuizHandler;
import no.hist.gruppe5.pvu.umlblocks.entities.*;

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
    private int mCurrentScore = 0;
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
        mGui = new Gui(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);


        mBackground = new ScrollingBackground();

        mGameCam = new OrthographicCamera();
        mGameCam.setToOrtho(false, 3f, (PVU.SCREEN_HEIGHT / PVU.SCREEN_WIDTH) * 3f);

        mDebugRenderer = new Box2DDebugRenderer();

        mCurrentGame = Room.EASY;
        startNewGame(true);

        Settings.setSound(false);
        Settings.setSound(false);
        Settings.setSound(false);

    }

    private void startNewGame(boolean tutorial) {
        // Start game
        if(!tutorial)
            mGui.enableGameDisplay();
        mRoom = new Room(mWorld, mCurrentGame);
        populateBlocksLeft(mCurrentGame);
        popNewBlock();
    }

    private void resetVariables() {
        mBlocksLeftCount = -1;
        mBlocksDead = 0;
        mIdleBeforeNextGame = false;
        mCurrentScore = 0;

        // Destroy all active bodies
        mWorld.destroyBody(mRoom.getBody());
        for(Block b : mActiveBlocks) {
            b.destroy(mWorld);
        }

        // And finally clear the ArrayList
        mActiveBlocks.clear();
    }

    private void populateBlocksLeft(int game_type) {
        switch(game_type) {
            case Room.EASY:
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                break;
            case Room.MEDIUM:
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                break;
            case Room.HARD:
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new SignBlock(mWorld));
                mBlocksLeft.add(new DiamondBlock(mWorld));
                mBlocksLeft.add(new SquareBlock(mWorld));
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

        if(!mGui.isTutorial())
            for(Block b : mActiveBlocks)
                b.draw(batch);

        batch.end();

        // Draw GUI on top of the rest
        mGui.draw();

        // Render debug outlines, this should be disabled for release (duh)
        mDebugRenderer.render(mWorld, mGameCam.combined);
    }

    @Override
    protected void update(float delta) {
        // Chek all user input
        checkInput();

        // Update everything physics related
        mWorld.step(1 / 60f, 6, 2);
        mRoom.update(delta);
        for(Block b : mActiveBlocks)
            b.update(delta);

        // Update block drop positioning
        if(!mActiveBlocks.isEmpty()) {
            Block block = getLastBlock();
            if(block.isLock()) {
                block.setPosition(mBlockDropLoc, BLOCK_DROP_LOCK);
            }
        }

        // Check whether or not to end game or add a new block
        if(mBlocksLeft.isEmpty() && isReadyToDrop() && isPreviousDropped() && hasBodiesHitTheFloor()) {
            if(!mIdleBeforeNextGame)
                gameIsDone();
        } else if(isReadyToDrop() && isPreviousDropped() && hasBodiesHitTheFloor()) {
            popNewBlock();
        }

        // Update everything background and bg related
        mBackground.update(delta);
        mGui.update(delta);
        mGui.setBlocksLeft(mBlocksLeftCount);
        mCurrentScore = mGui.setSuccess(mBlocksDead, mInitialBlockCount);

        // Clean up dead blocks from world and array
        removeDeadBlocks();
    }

    private boolean hasBodiesHitTheFloor() {
        if(mActiveBlocks.isEmpty()) return true;

        for(Block b : mActiveBlocks) {
            if(b.isMoving())
                return false;
        }

        return true;

    }

    private void popNewBlock() {
        mActiveBlocks.add(mBlocksLeft.remove(0).activate());
        mBlocksLeftCount--;
    }

    private void gameIsDone() {
        mBlocksLeftCount = 0;

        int score = mCurrentScore;

        mGui.enableIntermediateDisplay();
        mGui.setIntermediateText(score, mCurrentGame / 100);

        switch (mCurrentGame) {
            case Room.EASY:
                mEasyScore = score;
                mCurrentGame = Room.MEDIUM;
                break;
            case Room.MEDIUM:
                mMediumScore = score;
                mCurrentGame = Room.HARD;
                break;
            case Room.HARD:
                mHardScore = score;
                mCurrentGame = Room.DONE;
                mGui.enableSummarizeText(Math.round(getTotalScore()));
                break;
            case Room.DONE:
                // TODO?
                break;
        }

        mIdleBeforeNextGame = true;
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
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(mGui.isTutorial()) {
                mGui.enableGameDisplay();
                mLastDrop = TimeUtils.millis();
            } else if(!mActiveBlocks.isEmpty() && isReadyToDrop()) {
                getLastBlock().release();
                mLastDrop = TimeUtils.millis();
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            goLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            goRight();
        }

        if(mIdleBeforeNextGame && Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(mCurrentGame != Room.DONE) {
                resetVariables();
                startNewGame(false);
                mLastDrop = TimeUtils.millis();
            } else {
                // Report the final score
                float totalScore = getTotalScore();
                QuizHandler.updateQuizScore(Math.round(totalScore), ScoreHandler.UMLBLOCKS);
                ScoreHandler.updateScore(ScoreHandler.UMLBLOCKS, totalScore / 100);
                ScoreHandler.getMiniGameGrade(ScoreHandler.UMLBLOCKS);

                System.out.println(ScoreHandler.getMiniGameGrade(ScoreHandler.UMLBLOCKS));

                // Let's go back.
                game.setScreen(PVU.MAIN_SCREEN);
            }
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

    private float getTotalScore() {
        return ((mEasyScore + mMediumScore + mHardScore) / 3);
    }
}
