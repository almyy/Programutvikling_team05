package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/26/13
 * Time: 10:56 PM
 */
public class MainScreen extends GameScreen {

    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    private World mWorld;
    private Box2DDebugRenderer mDebugRenderer;

    Player mPlayer;
    TeamMates mTeammates;

    private boolean left = true;
    private boolean up = true;

    private Sprite mBackground;
    private Sprite[] mBurndownCarts;
    private int mCurrentCart;

    public MainScreen(PVU game) {
        super(game);


        mWorld = new World(new Vector2(0, 0), true);
        mDebugRenderer = new Box2DDebugRenderer();

        // TODO test
        mWorld.rayCast(new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                System.out.println("Collisjion");
                return 0f;
            }
        }, new Vector2(30f, 30f), new Vector2(50f, 50f));

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
        loader.attachFixture(roomBody, "main_room", fd, 192f);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        mBackground.draw(batch);
        mBurndownCarts[mCurrentCart].draw(batch);

        mPlayer.draw(batch);
        mTeammates.draw(batch);
        batch.end();

        mDebugRenderer.render(mWorld, camera.combined);

    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);
        mCurrentCart = (mCurrentCart + 1) % 5;
        mTeammates.update();
        mPlayer.update();

    }

    @Override
    protected void cleanUp() {
    }
}
