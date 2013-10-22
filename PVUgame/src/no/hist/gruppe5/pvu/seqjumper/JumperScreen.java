package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

public class JumperScreen extends GameScreen {

    public static final float WORLD_WIDTH = 3f;
    public static final float WORLD_HEIGHT = 1.8125f;
    private final float PLATFORM_SIZE = 0.1f;
    private final float BALL_MARGIN = 0.02f;
    private World mWorld;
    private OrthographicCamera mGameCam;
    private Room mRoom;
    private Ball mBall;
    // Debug
    private Box2DDebugRenderer mDebugRenderer;
    //Ball movement Y
    private float powerHeight = 0;
    private float startPositionY;
    //Ball movement -X
    private boolean hasPressedA = false;
    private float powerLeft = 0;
    private boolean loadedA = false;
    //Ball movement X
    private boolean hasPressedD = false;
    private boolean loadedD = false;
    private float powerRight = 0;
    private float startPositionX;
    // private Sprite mHead;
    private Platform mPlatform;
    private ArrayList<Body> mPlatforms;
    private int level = 0;
    // Background
    private JumperScreenBackground background;
    // GUI
    private GUI mGui;
    private Sprite mHead;
    private Sprite mLine;
    private boolean movem = true;
    private boolean[] same;
    private int mLife;
    private int mHighscore;

    public JumperScreen(PVU game) {
        super(game);

        mGui = new GUI(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);

        background = new JumperScreenBackground();

        mWorld = new World(new Vector2(0, 0), false);
        mRoom = new Room(mWorld);

        mBall = new Ball(mWorld);
        mBall.getBody().setTransform(0.5f, 0.3f, 0);

        mPlatform = new Platform();
        mPlatforms = new ArrayList<>(5);
        mPlatforms.add(mPlatform.createPlatform(new Vector2(0.5f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(1f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(1.5f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(2f, 0.2f), PLATFORM_SIZE, mWorld, true));
        mPlatforms.add(mPlatform.createPlatform(new Vector2(2.5f, 0.2f), PLATFORM_SIZE, mWorld, true));

        mGameCam = new OrthographicCamera();
        mGameCam.setToOrtho(false, 3f, (PVU.SCREEN_HEIGHT / PVU.SCREEN_WIDTH) * 3f);

        same = new boolean[11];

        mDebugRenderer = new Box2DDebugRenderer();

        startPositionX = mBall.getBody().getPosition().x;
        startPositionY = mBall.getBody().getPosition().y;

        // GUI
        mHead = new Sprite(Assets.seqHead);
        mLine = new Sprite(Assets.seqLine);
        mLine.setOrigin(mLine.getWidth() / 2, mLine.getHeight() / 2);
        mLine.setScale(0.1f, 0.2f);
        mHead.setOrigin(38, 36);
        mHead.setScale(0.20f);
        
        // Gameplay
        mLife = 5;
        mHighscore = 0;
    }

    @Override
    protected void draw(float delta) {
        clearCamera(0f, 0f, 0f, 1f);

        batch.begin();
        background.draw(batch);
        mBall.draw(batch);
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
            case 6:
                mHead.setRotation(180f);
                mHead.setPosition(19f, -12.8f);
                mLine.setPosition(-82f, -9.5f);
                mHead.draw(batch);
                break;
        }
        batch.end();
        mGui.draw();
        mDebugRenderer.render(mWorld, mGameCam.combined);
    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);
        
        checkCollision();
        
        checkInput();
        
        // Ball update
        mBall.update(delta);
        
        // GUI update
        mGui.update(delta);
        mGui.setJumps(level);
        mGui.setSuccess(level);
        mGui.setLife(mLife);
        if (level == 11) {
            mGui.setGameFeedback();
            mGui.enableIntermediateDisplay();
        }
    }

    private int checkCollision() {
        //Platform 1
        if (mBall.getBody().getPosition().x < mPlatforms.get(0).getPosition().x + PLATFORM_SIZE + BALL_MARGIN
                && mBall.getBody().getPosition().x > mPlatforms.get(0).getPosition().x - PLATFORM_SIZE - BALL_MARGIN
                && mBall.getBody().getPosition().y > mPlatforms.get(0).getPosition().y + 0.1
                && mBall.getBody().getPosition().y < mPlatforms.get(0).getPosition().y + 0.2) {
            if (level == 0 || level == 1 || level == 5 || level == 6 || level == 7 || level == 8 || level == 10 || level == 11) {
                if (level == 0 && same[0] == false) {
                    level++;
                } else if (level == 5 && same[5] == false) {
                    level++;
                    same[4] = true;
                    same[0] = false;
                } else if (level == 7 && same[7] == false) {
                    level++;
                    same[6] = true;
                    same[5] = false;
                } else if (level == 10 && same[10] == false) {
                    level++;
                } else if (same[0] == true || same[5] == true || same[7] == true || same[10] == true) {
                    failJump();
                    level = 0;
                }
            } else {
                failJump();
            }
            return 1;
        }
        //Platform 2
        if (mBall.getBody().getPosition().x < mPlatforms.get(1).getPosition().x + PLATFORM_SIZE + BALL_MARGIN
                && mBall.getBody().getPosition().x > mPlatforms.get(1).getPosition().x - PLATFORM_SIZE - BALL_MARGIN
                && mBall.getBody().getPosition().y > mPlatforms.get(1).getPosition().y + 0.1
                && mBall.getBody().getPosition().y < mPlatforms.get(1).getPosition().y + 0.2) {
            if (level == 1 || level == 2) {
                if (level == 1 && same[1] == false) {
                    level++;
                    same[0] = true;
                } else if (same[1] == true) {
                    failJump();
                    level = 0;
                }
            } else {
                failJump();
            }
            return 2;
        }
        //Platform 3
        if (mBall.getBody().getPosition().x < mPlatforms.get(2).getPosition().x + PLATFORM_SIZE + BALL_MARGIN
                && mBall.getBody().getPosition().x > mPlatforms.get(2).getPosition().x - PLATFORM_SIZE - BALL_MARGIN
                && mBall.getBody().getPosition().y > mPlatforms.get(2).getPosition().y + 0.1
                && mBall.getBody().getPosition().y < mPlatforms.get(2).getPosition().y + 0.2) {
            if (level == 2 || level == 3 || level == 4 || level == 5) {
                if (level == 2 && same[2] == false) {
                    level++;
                    same[1] = true;
                } else if (level == 4 && same[4] == false) {
                    level++;
                    same[3] = true;
                    same[2] = false;
                } else if (same[4] == true || same[2] == true) {
                    failJump();
                }
            } else {
                failJump();
            }
            return 3;
        }
        //Platform 4
        if (mBall.getBody().getPosition().x < mPlatforms.get(3).getPosition().x + PLATFORM_SIZE + BALL_MARGIN
                && mBall.getBody().getPosition().x > mPlatforms.get(3).getPosition().x - PLATFORM_SIZE - BALL_MARGIN
                && mBall.getBody().getPosition().y > mPlatforms.get(3).getPosition().y + 0.1
                && mBall.getBody().getPosition().y < mPlatforms.get(3).getPosition().y + 0.2) {
            if (level == 3 || level == 4 || level == 6 || level == 7 || level == 9 || level == 10) {
                if (level == 3 && same[3] == false) {
                    level++;
                    same[2] = true;
                } else if (level == 6 && same[6] == false) {
                    level++;
                    same[5] = true;
                    same[3] = false;
                } else if (level == 9 && same[9] == false) {
                    level++;
                    same[8] = true;
                    same[6] = false;
                } else if (same[6] == true || same[9] == true) {
                    failJump();
                    level = 0;
                }
            } else {
                failJump();
            }
            return 4;
        }
        //Platform 5
        if (mBall.getBody().getPosition().x < mPlatforms.get(4).getPosition().x + PLATFORM_SIZE + BALL_MARGIN
                && mBall.getBody().getPosition().x > mPlatforms.get(4).getPosition().x - PLATFORM_SIZE - BALL_MARGIN
                && mBall.getBody().getPosition().y > mPlatforms.get(4).getPosition().y + 0.1
                && mBall.getBody().getPosition().y < mPlatforms.get(4).getPosition().y + 0.2) {
            if (level == 8 || level == 9) {
                if (level == 8 && same[8] == false) {
                    level++;
                    same[7] = true;
                } else if (same[8] == true) {
                    failJump();
                    level = 0;
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
            same[i] = false;
        }
        mLife--;
        mHighscore = level;
        level = 0;
    }

    private void checkInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            mBall.getBody().applyForceToCenter(0.01f, 0f, true);
            movem = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            mBall.getBody().applyForceToCenter(-0.01f, 0f, true);
            movem = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            mBall.getBody().applyForceToCenter(0, -0.01f, true);
            movem = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            mBall.getBody().applyForceToCenter(0f, 0.01f, true);
            movem = true;
        }
        if (movem == true) {
            movem = false;
        }

        /*
        // Ball movement right
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
        // Ball movement left
         if (Gdx.input.isKeyPressed(Input.Keys.A) && !hasPressedA) {
         if (powerLeft > -0.85) {
         powerLeft -= 0.008;
         }
         if (powerHeight < 2.55) {
         powerHeight += 0.03;
         }
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
         } */
    }

    @Override
    protected void cleanUp() {
    }
}
