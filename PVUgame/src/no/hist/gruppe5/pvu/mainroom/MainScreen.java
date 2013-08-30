package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
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

    private boolean left = true;
    private boolean up = true;

    private Sprite mBackground;

    public MainScreen(PVU game) {
        super(game);


        mWorld = new World(new Vector2(0, 0), true);
        mDebugRenderer = new Box2DDebugRenderer();

        createRoomBody();

        // TODO temp
        moveToAssets();
        //mBackground = new Sprite(Assets.testRegion);

        mBackground.setSize(PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
        mBackground.setPosition(0, 0);

        mPlayer = new Player(mWorld);

    }

    public void moveToAssets() {
        TextureAtlas mainscrenAtlas = new TextureAtlas(Gdx.files.internal("data/main_room.pack")) ;
        TextureRegion mainscreenBackground = mainscrenAtlas.findRegion("main_room");
        mBackground = new Sprite(mainscreenBackground);
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
        mPlayer.draw(batch);
        batch.end();

        mDebugRenderer.render(mWorld, camera.combined);

    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);
        mPlayer.update();
    }

    @Override
    protected void cleanUp() {
    }
}
