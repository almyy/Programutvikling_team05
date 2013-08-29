package no.hist.gruppe5.pvu;

import aurelienribon.bodyeditor.BodyEditorLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;

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

    private boolean left = true;
    private boolean up = true;

    private Sprite mBackground;

    public MainScreen(PVU game) {
        super(game);


        mWorld = new World(new Vector2(0, -1f), true);
        mDebugRenderer = new Box2DDebugRenderer();

        createTestBody();

        // TODO temp
        moveToAssets();
        //mBackground = new Sprite(Assets.testRegion);

        mBackground.setSize(PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
        mBackground.setPosition(0, 0);

    }

    public void moveToAssets() {
        TextureAtlas mainscrenAtlas = new TextureAtlas(Gdx.files.internal("data/main_room.pack")) ;
        TextureRegion mainscreenBackground = mainscrenAtlas.findRegion("main_room");
        mBackground = new Sprite(mainscreenBackground);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        mBackground.draw(batch);
        batch.end();

        mDebugRenderer.render(mWorld, camera.combined);


    }

    private void createTestBody() {
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/pvugame.json"));
        //Ground body
        BodyDef groundBodyDef =new BodyDef();
        groundBodyDef.position.set(new Vector2(0f ,0f));
        Body groundBody = mWorld.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox((camera.viewportWidth), 0.2f);
        groundBody.createFixture(groundBox, 0.0f);

        //Dynamic Body  
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2);
        Body body = mWorld.createBody(bodyDef);
        CircleShape dynamicCircle = new CircleShape();
        dynamicCircle.setRadius(0.2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicCircle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 1;
        body.createFixture(fixtureDef);
        mDebugRenderer = new Box2DDebugRenderer();
    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);
    }

    @Override
    protected void cleanUp() {
    }
}
