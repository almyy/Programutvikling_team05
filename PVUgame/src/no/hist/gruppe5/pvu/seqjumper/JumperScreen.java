package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.Input;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.ScoreHandler;
import no.hist.gruppe5.pvu.quiz.QuizHandler;
import no.hist.gruppe5.pvu.umlblocks.ScrollingBackground;

public class JumperScreen extends GameScreen {

    public static final float WORLD_TO_BOX = 3f / 192f / 2f;
    public static final float BOX_TO_WORLD = 192f / 3f;
    public static final float WORLD_WIDTH = 3f;
    public static final float WORLD_HEIGHT = 1.8125f;
    private final float PLATFORM_SIZE = 0.15f;
    private final float BALL_MARGIN_X = 0.02f;
    private final float BALL_MARGIN_Y_BOT = 0.1f;
    private final float BALL_MARGIN_Y_TOP = 0.11f;

    // World
    private World mWorld;
    private OrthographicCamera mGameCam;
    private Room mRoom;
    private Ball mBall;
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
    private Platform mPlatform;
    private ArrayList<Body> mPlatforms;
    private int level = 0;
    // Background
    private ScrollingBackground mBackground;
    // GUI
    private GUI mGui;
    private Sprite mHead;
    private Sprite mLine;
    private Sprite mPowerBar;
    // Gameplay
    private boolean movem = true;
    private boolean[] mPlatformJumped;
    private int mLife;
    private boolean mGameOver;
    private float mPercent;
    // Report score
    private boolean once;
    

    public JumperScreen(PVU game) {
        super(game);

        mBackground = new ScrollingBackground(Assets.seqBackground);

        mGui = new GUI(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);

        mWorld = new World(new Vector2(0, -10), false);
        mRoom = new Room(mWorld);

        mBall = new Ball(mWorld);
        mBall.getBody().setTransform(0.5f, 0.3f, 0);

        // Platforms
        mPlatform = new Platform(mWorld);
        mPlatforms = new ArrayList<>(5);
        mPlatforms.add(mPlatform.createPlatform(new Vector2(0.5f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(1f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(1.5f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(2f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(2.5f, 0.2f), PLATFORM_SIZE, mWorld, true));

        // Game camera
        mGameCam = new OrthographicCamera();
        mGameCam.setToOrtho(false, 3f, (PVU.SCREEN_HEIGHT / PVU.SCREEN_WIDTH) * 3f);

        // Powerbar
        mPowerBar = mPlatform.createPowerBar();

        // GUI
        mHead = new Sprite(Assets.seqHead);
        mLine = new Sprite(Assets.seqLine);
        mLine.setOrigin(mLine.getWidth() / 2, mLine.getHeight() / 2);
        mLine.setScale(0.1f, 0.2f);
        mHead.setOrigin(38, 36);
        mHead.setScale(0.20f);

        // Gameplay
        mPlatformJumped = new boolean[11];
        mLife = 5;
    }

    @Override
    protected void draw(float delta) {
        clearCamera(0f, 0f, 0f, 1f);

        batch.begin();

        //  Drawing models
        mBackground.draw(batch);
        mPlatform.draw(batch);
        mBall.draw(batch);
        mPowerBar.draw(batch);

        switch (checkCollision()) {
            case 1:
                if (level == 1) {
                    mLine.setScale(0.1f, 0.2f);
                    mHead.setRotation(180f);
                    mHead.setPosition(19f, -12.8f);
                    mLine.setPosition(-82f, -9.5f);
                }
                if (level == 6) {
                    mHead.setRotation(180f);
                    mHead.setPosition(83f, -12.8f);
                    mLine.setScale(0.5f, 0.2f);
                    mLine.setPosition(-60f, -9.5f);
                }
                if (level == 8) {
                    mHead.setRotation(180f);
                    mHead.setPosition(115f, -12.8f);
                    mLine.setScale(0.7f, 0.2f);
                    mLine.setPosition(-50f, -9.5f);
                }
                if (level != 11) {
                    mHead.draw(batch);
                    mLine.draw(batch);
                }
                break;
            case 2:
                mHead.setRotation(180f);
                mHead.setPosition(51f, -12.8f);
                mLine.setPosition(-50f, -9.5f);
                mHead.draw(batch);
                mLine.draw(batch);
                break;
            case 3:
                if (level == 3) {
                    mHead.setRotation(180f);
                    mHead.setPosition(83f, -12.8f);
                    mLine.setPosition(-18f, -9.5f);
                }
                if (level == 5) {
                    mHead.setRotation(0f);
                    mHead.setPosition(1, -12.8f);
                    mLine.setScale(0.3f, 0.2f);
                    mLine.setPosition(-71f, -9.5f);
                }
                mHead.draw(batch);
                mLine.draw(batch);
                break;
            case 4:
                if (level == 4) {
                    mHead.setRotation(0f);
                    mHead.setPosition(66f, -12.8f);
                    mLine.setPosition(-18f, -9.5f);
                }
                if (level == 7) {
                    mHead.setRotation(0f);
                    mHead.setPosition(1, -12.8f);
                    mLine.setScale(0.5f, 0.2f);
                    mLine.setPosition(-60f, -9.5f);
                }
                if (level == 10) {
                    mHead.setRotation(0f);
                    mHead.setPosition(1, -12.8f);
                    mLine.setScale(0.5f, 0.2f);
                    mLine.setPosition(-60f, -9.5f);
                }
                mHead.draw(batch);
                mLine.draw(batch);
                break;
            case 5:
                mHead.setRotation(0);
                mHead.setPosition(97f, -12.8f);
                mLine.setScale(0.1f, 0.2f);
                mLine.setPosition(13f, -9.5f);
                mHead.draw(batch);
                mLine.draw(batch);
                break;
        }
        batch.end();
        if (!mGameOver) {
            mPlatform.drawRedBar();
        }

        //Drawing GUI
        mGui.draw();
    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);

        checkCollision();
        checkInput();

        reportScore();

        mBackground.update(delta);

        // Ball update
        mBall.update(delta);

        // Platform update
        mPlatform.update(delta);

        // GUI update
        mGui.update(delta);
        mGui.setJumps(level);

        // End of game
        if (mLife > 0) {
            mGui.setLife(mLife);
        } else {
            mGui.setGameFeedback();
            mGui.enableIntermediateDisplay();
            gameOver();
        }
        if (level == 11) {
            mGui.setGameFeedback();
            mGui.enableIntermediateDisplay();
            gameOver();
        }
    }

    private int checkCollision() {
        //Platform 1
        if (mBall.getBody().getPosition().x < mPlatforms.get(0).getPosition().x + PLATFORM_SIZE + BALL_MARGIN_X
                && mBall.getBody().getPosition().x > mPlatforms.get(0).getPosition().x - PLATFORM_SIZE - BALL_MARGIN_X
                && mBall.getBody().getPosition().y > mPlatforms.get(0).getPosition().y + BALL_MARGIN_Y_BOT
                && mBall.getBody().getPosition().y < mPlatforms.get(0).getPosition().y + BALL_MARGIN_Y_TOP) {
            if (level == 0 || level == 1 || level == 5 || level == 6 || level == 7 || level == 8 || level == 10 || level == 11) {
                if (level == 0 && mPlatformJumped[0] == false) {
                    level++;
                } else if (level == 5 && mPlatformJumped[5] == false) {
                    level++;
                    mPlatformJumped[4] = true;
                    mPlatformJumped[0] = false;
                } else if (level == 7 && mPlatformJumped[7] == false) {
                    level++;
                    mPlatformJumped[6] = true;
                    mPlatformJumped[5] = false;
                } else if (level == 10 && mPlatformJumped[10] == false) {
                    level++;
                    mPlatformJumped[7] = false;
                } else if (mPlatformJumped[0] == true || mPlatformJumped[5] == true || mPlatformJumped[7] == true || mPlatformJumped[10] == true) {
                    failJump();
                }
            } else {
                failJump();
            }
            return 1;
        }
        //Platform 2
        if (mBall.getBody().getPosition().x < mPlatforms.get(1).getPosition().x + PLATFORM_SIZE + BALL_MARGIN_X
                && mBall.getBody().getPosition().x > mPlatforms.get(1).getPosition().x - PLATFORM_SIZE - BALL_MARGIN_X
                && mBall.getBody().getPosition().y > mPlatforms.get(1).getPosition().y + BALL_MARGIN_Y_BOT
                && mBall.getBody().getPosition().y < mPlatforms.get(1).getPosition().y + BALL_MARGIN_Y_TOP) {
            if (level == 1 || level == 2) {
                if (level == 1 && mPlatformJumped[1] == false) {
                    level++;
                    mPlatformJumped[0] = true;
                } else if (mPlatformJumped[1] == true) {
                    failJump();
                }
            } else {
                failJump();
            }
            return 2;
        }
        //Platform 3
        if (mBall.getBody().getPosition().x < mPlatforms.get(2).getPosition().x + PLATFORM_SIZE + BALL_MARGIN_X
                && mBall.getBody().getPosition().x > mPlatforms.get(2).getPosition().x - PLATFORM_SIZE - BALL_MARGIN_X
                && mBall.getBody().getPosition().y > mPlatforms.get(2).getPosition().y + BALL_MARGIN_Y_BOT
                && mBall.getBody().getPosition().y < mPlatforms.get(2).getPosition().y + BALL_MARGIN_Y_TOP) {
            if (level == 2 || level == 3 || level == 4 || level == 5) {
                if (level == 2 && mPlatformJumped[2] == false) {
                    level++;
                    mPlatformJumped[1] = true;
                } else if (level == 4 && mPlatformJumped[4] == false) {
                    level++;
                    mPlatformJumped[3] = true;
                    mPlatformJumped[2] = false;
                } else if (mPlatformJumped[4] == true || mPlatformJumped[2] == true) {
                    failJump();
                }
            } else {
                failJump();
            }
            return 3;
        }
        //Platform 4
        if (mBall.getBody().getPosition().x < mPlatforms.get(3).getPosition().x + PLATFORM_SIZE + BALL_MARGIN_X
                && mBall.getBody().getPosition().x > mPlatforms.get(3).getPosition().x - PLATFORM_SIZE - BALL_MARGIN_X
                && mBall.getBody().getPosition().y > mPlatforms.get(3).getPosition().y + BALL_MARGIN_Y_BOT
                && mBall.getBody().getPosition().y < mPlatforms.get(3).getPosition().y + BALL_MARGIN_Y_TOP) {
            if (level == 3 || level == 4 || level == 6 || level == 7 || level == 9 || level == 10) {
                if (level == 3 && mPlatformJumped[3] == false) {
                    level++;
                    mPlatformJumped[2] = true;
                } else if (level == 6 && mPlatformJumped[6] == false) {
                    level++;
                    mPlatformJumped[5] = true;
                    mPlatformJumped[3] = false;
                } else if (level == 9 && mPlatformJumped[9] == false) {
                    level++;
                    mPlatformJumped[8] = true;
                    mPlatformJumped[6] = false;
                } else if (mPlatformJumped[6] == true || mPlatformJumped[9] == true) {
                    failJump();
                }
            } else {
                failJump();
            }
            return 4;
        }
        //Platform 5
        if (mBall.getBody().getPosition().x < mPlatforms.get(4).getPosition().x + PLATFORM_SIZE + BALL_MARGIN_X
                && mBall.getBody().getPosition().x > mPlatforms.get(4).getPosition().x - PLATFORM_SIZE - BALL_MARGIN_X
                && mBall.getBody().getPosition().y > mPlatforms.get(4).getPosition().y + BALL_MARGIN_Y_BOT
                && mBall.getBody().getPosition().y < mPlatforms.get(4).getPosition().y + BALL_MARGIN_Y_TOP) {
            if (level == 8 || level == 9) {
                if (level == 8 && mPlatformJumped[8] == false) {
                    level++;
                    mPlatformJumped[7] = true;
                } else if (mPlatformJumped[8] == true) {
                    failJump();
                }
            } else {
                failJump();
            }
            return 5;
        }
        // Ball out of screen
        if (mBall.getBody().getPosition().y < 0) {
            failJump();
            return 1;
        }
        return 0;
    }

    private void failJump() {
        mWorld.destroyBody(mBall.getBody());
        mBall = new Ball(mWorld);
        for (int i = 0; i < level; i++) {
            mPlatformJumped[i] = false;
        }
        // GUI updates
        mGui.setLifeLoss();
        mLife--;
        level = 0;
    }

    private void gameOver() {
        if (!mGameOver) {
            mWorld.destroyBody(mBall.getBody());
        }
        if(!once){
            ScoreHandler.updateScore(ScoreHandler.SEQ, mPercent/100);
            once = true;
        }
        mGameOver = true;
        if (Input.continuousAction()) {
            game.setScreen(PVU.MAIN_SCREEN);
        }
    }

    public void reportScore() {
        if (level - 1 > 0) {
            float startJump = level - 1;
            mPercent = Math.round((float) startJump / (float) 10 * 100f);
        }
        mGui.setSuccess(mPercent);
    }

    private void checkInput() {
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            mBall.getBody().applyForceToCenter(0.01f, 0f, true);
//            movem = true;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            mBall.getBody().applyForceToCenter(-0.01f, 0f, true);
//            movem = true;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            mBall.getBody().applyForceToCenter(0, -0.01f, true);
//            movem = true;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            mBall.getBody().applyForceToCenter(0f, 0.01f, true);
//            movem = true;
//        }
//        if (movem == true) {
//            movem = false;
//        }

        // Ball movement right
        if (Input.continuousRight() && !hasPressedD) {
            if (powerRight < 0.85) {
                powerRight += 0.008;
            }
            if (powerHeight < 2.55) {
                powerHeight += 0.03;
            }
            mPlatform.setRedBar(powerRight);
            loadedD = true;
        }

        if (!Input.continuousRight() && loadedD) {
            hasPressedD = true;
        }

        if (!Input.continuousRight() && hasPressedD) {
            mPlatform.setRedBar(0);
            mBall.getBody().applyForceToCenter(powerRight, powerHeight, true);
            hasPressedD = false;
            powerRight = 0;
            powerHeight = 0;
            loadedD = false;
        }
        // Ball movement left
        if (Input.continuousLeft() && !hasPressedA) {
            if (powerLeft > -0.85) {
                powerLeft -= 0.008;
            }
            if (powerHeight < 2.55) {
                powerHeight += 0.03;
            }
            // Setting power to the bar
            mPlatform.setRedBar(-powerLeft);

            loadedA = true;
        }

        if (!Input.continuousLeft() && loadedA) {
            hasPressedA = true;
        }

        if (!Input.continuousLeft() && hasPressedA) {
            mPlatform.setRedBar(0);
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
