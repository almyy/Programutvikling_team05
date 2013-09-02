package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.dialogdrawer.PopupBox;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/26/13
 * Time: 10:56 PM
 */
public class MainScreen extends GameScreen {

    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    public static final int OBJECT_PLAYER = 0;
    public static final int OBJECT_ROOM = 1;

    PopupBox mPopupBox;

    private World mWorld;
    private Box2DDebugRenderer mDebugRenderer;

    Player mPlayer;
    TeamMates mTeammates;

    private boolean left = true;
    private boolean up = true;

    private Sprite mBackground;
    private Sprite[] mBurndownCarts;
    private int mCurrentCart;
    private RayCastManager mRayCastManager;

    // DEBUG
    private ShapeRenderer mShapeDebugRenderer;
    private boolean mShowingHint = false;
    private int mCurrentHint = -1;

    public MainScreen(PVU game) {
        super(game);

        mWorld = new World(new Vector2(0, 0), true);

        // DEBUG
        mDebugRenderer = new Box2DDebugRenderer();
        mShapeDebugRenderer = new ShapeRenderer();
        mShapeDebugRenderer.setProjectionMatrix(camera.combined);
        // DEBUG end

        mRayCastManager = new RayCastManager();
        mPopupBox = new PopupBox(batch);

        createRoomBody();

        mBackground = new Sprite(Assets.msBackground);
        mBurndownCarts = new Sprite[Assets.msBurndownCarts.length];

        mBackground.setSize(PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
        mBackground.setPosition(0, 0);
        for (int i = 0; i < Assets.msBurndownCarts.length; i++) {
            mBurndownCarts[i] = new Sprite(Assets.msBurndownCarts[i]);
            mBurndownCarts[i].setPosition(15f, PVU.GAME_HEIGHT - 23f);
        }

        mPlayer = new Player(mWorld);
        mTeammates = new TeamMates();
        mCurrentCart = 0;

    }

    private void createRoomBody() {

        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/pvugame.json"));

        //Room Body
        Body roomBody;

        BodyDef bd = new BodyDef();
        bd.position.set(0, 0);
        bd.type = BodyDef.BodyType.StaticBody;

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0.3f;

        roomBody = mWorld.createBody(bd);
        roomBody.setUserData(OBJECT_ROOM);
        loader.attachFixture(roomBody, "main_room", fd, 192f);
    }

    private void setBurnDownCart(int num) {
        if (num < 0) num = 0;
        if (num > 4) num = 4;
        mCurrentCart = num;
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();

        mBackground.draw(batch);
        mBurndownCarts[mCurrentCart].draw(batch);

        mTeammates.draw(batch);
        mPlayer.draw(batch);

        if(mShowingHint) {
            mPopupBox.draw(delta);
        }

        batch.end();

        drawDebug();
    }

    private void drawDebug() {
        mDebugRenderer.render(mWorld, camera.combined);
        mShapeDebugRenderer.begin(ShapeRenderer.ShapeType.Line);
        mShapeDebugRenderer.setColor(Color.RED);
        for (RayCastManager.RayCast rc : mRayCastManager.getRayCasts()) {
            mShapeDebugRenderer.line(rc.from, rc.to);
        }
        mShapeDebugRenderer.end();
    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);
        mTeammates.update();
        mPlayer.update();
        mRayCastManager.update(delta);

        for (RayCastManager.RayCast rc : mRayCastManager.getRayCasts()) {
            mWorld.rayCast(rc.callBack, rc.from, rc.to);
        }

        if(mRayCastManager.getInfront() != -1 && !mShowingHint) {
            mShowingHint = true;
            mCurrentHint = mRayCastManager.getInfront();
            switch (mRayCastManager.getInfront()) {
                case RayCastManager.BOOK:
                    mPopupBox.setText("Press E to open the book");
                    mPopupBox.setXY(mPlayer.getPosition());
                    break;
                case RayCastManager.PC:
                    mPopupBox.setText("Press E to start working on the PC");
                    mPopupBox.setXY(mPlayer.getPosition());
                    break;
                case RayCastManager.CART:
                    mPopupBox.setText("Press E to look at the burndown cart");
                    mPopupBox.setXY(mPlayer.getPosition());
                    break;
                case RayCastManager.TABLE:
                    mPopupBox.setText("Press E to look at your progress");
                    mPopupBox.setXY(mPlayer.getPosition());
                    break;
            }
            System.out.println("Sou");
        } else if (mRayCastManager.getInfront() == -1 && mShowingHint) {
            //TODO disable all hint showings
            System.out.println("Disable");
            mShowingHint = false;
        }
        
        if(mShowingHint) {
            recieveHintInput();
        }

    }

    private void recieveHintInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            System.out.println("Start thingy");
        }
    }


    @Override
    protected void cleanUp() {
    }
}
