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
import no.hist.gruppe5.pvu.book.BookScreen;
import no.hist.gruppe5.pvu.dialogdrawer.DialogDrawer;
import no.hist.gruppe5.pvu.dialogdrawer.PopupBox;
import no.hist.gruppe5.pvu.mainroom.objects.Player;
import no.hist.gruppe5.pvu.mainroom.objects.RayCastManager;
import no.hist.gruppe5.pvu.mainroom.objects.TeamMates;
import no.hist.gruppe5.pvu.mainroom_screens.BurndownScreen;
import no.hist.gruppe5.pvu.mainroom_screens.ScoreScreen;

/**
 * Created with IntelliJ IDEA. User: karl Date: 8/26/13 Time: 10:56 PM
 */
public class MainScreen extends GameScreen {

    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;
    public static final String TRYKK = "Trykk på E for å ";
    public static final String PAA_PC = TRYKK + "jobbe på PC-en";
    public static final String PAA_CART = TRYKK + "se på burndown-cart";
    public static final String PAA_BORD = TRYKK + "se på fremgangen din";
    public static final String PAA_BOK = TRYKK + "lese i boken";
    public static final int OBJECT_PLAYER = 0;
    public static final int OBJECT_ROOM = 1;
    private PopupBox mPopupBox;
    private World mWorld;
    private Box2DDebugRenderer mDebugRenderer;
    private Player mPlayer;
    private TeamMates mTeammates;
    private boolean mInputHandled = false;
    private Sprite mBackground;
    private Sprite mTables;
    private Sprite[] mBurndownCarts;
    private RayCastManager mRayCastManager;
    // DEBUG
    private ShapeRenderer mShapeDebugRenderer;
    private boolean mShowingHint = false;
    private int mCurrentHint = -1;
    private DialogDrawer dialog;

    public MainScreen(PVU game) {
        super(game);

        dialog = new DialogDrawer(batch);
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
        mTables = new Sprite(Assets.msTable);
        mBurndownCarts = new Sprite[Assets.msBurndownCarts.length];
        for (int i = 0; i < Assets.msBurndownCarts.length; i++) {
            mBurndownCarts[i] = new Sprite(Assets.msBurndownCarts[i]);
            mBurndownCarts[i].setPosition(15f, PVU.GAME_HEIGHT - 23f);
        }

        mBackground.setSize(PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
        mBackground.setPosition(0, 0);


        mPlayer = new Player(mWorld);
        mTeammates = new TeamMates();

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

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();

        mBackground.draw(batch);
        mBurndownCarts[0].draw(batch);


        if (mPlayer.getPosition().y < PVU.GAME_HEIGHT / 2) {
            mTables.draw(batch);
            mTeammates.draw(batch);
            mPlayer.draw(batch);
        } else {
            mPlayer.draw(batch);
            mTables.draw(batch);
            mTeammates.draw(batch);
        }

        if (mShowingHint && !mPlayer.isSitting()) {
            mPopupBox.draw(delta);
        }
        dialog.draw(delta);
        batch.end();
        //drawDebug(true);
    }

    private void drawDebug(boolean onlyRayCasts) {
        if (!onlyRayCasts) {
            mDebugRenderer.render(mWorld, camera.combined);
        }

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

        if (mRayCastManager.getInfront() != -1 && !mShowingHint) {
            mShowingHint = true;
            mCurrentHint = mRayCastManager.getInfront();
            switch (mRayCastManager.getInfront()) {
                case RayCastManager.BOOK:
                    mPopupBox.setText(PAA_BOK);
                    mPopupBox.setXY(mPlayer.getPosition());
                    break;
                case RayCastManager.PC:
                    mPopupBox.setText(PAA_PC);
                    mPopupBox.setXY(mPlayer.getPosition());
                    break;
                case RayCastManager.CART:
                    mPopupBox.setText(PAA_CART);
                    mPopupBox.setXY(mPlayer.getPosition());
                    break;
                case RayCastManager.TABLE:
                    mPopupBox.setText(PAA_BORD);
                    mPopupBox.setXY(mPlayer.getPosition());
                    break;
            }
        } else if (mRayCastManager.getInfront() == -1 && mShowingHint) {
            mShowingHint = false;
        }

        if (mShowingHint) {
            recieveHintInput();
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.E)) {
            mInputHandled = false;
        }
        updateMainScreenSoundButton();
        dialog.intro();
        dialog.introNext();
    }

    private void recieveHintInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.E) && !mInputHandled) {
            switch (mRayCastManager.getInfront()) {
                case RayCastManager.BOOK:
                    mInputHandled = true;
                    game.setScreen(new BookScreen(game));
                    break;
                case RayCastManager.PC:
                    game.setScreen(new MinigameSelectorScreen(game));
                    mPlayer.sitDown();
                    mShowingHint = false;
                    mInputHandled = true;
                    //TODO pc screen
                    break;
                case RayCastManager.CART:
                    game.setScreen(new BurndownScreen(game));
                    mInputHandled = true;
                    break;
                case RayCastManager.TABLE:
                    mInputHandled = true;
                    game.setScreen(new ScoreScreen(game));
                    break;
            }
        }
    }

    @Override
    protected void cleanUp() {
    }
}
