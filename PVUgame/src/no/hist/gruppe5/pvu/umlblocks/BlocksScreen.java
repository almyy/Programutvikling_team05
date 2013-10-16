package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.mainroom.BodyEditorLoader;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/15/13
 * Time: 9:39 AM
 * To change this template use File | Settings | File Templates.
 */

public class BlocksScreen extends GameScreen {

    private OrthographicCamera mGameCam;

    private static final float WORLD_TO_BOX = 0.064f;
    private static final float BOX_TO_WORLD = 64;

    private World mWorld;

    private ArrayList<Block> mBlocks;

    private Box2DDebugRenderer mDebugRenderer;

    public BlocksScreen(PVU game) {
        super(game);

        mBlocks = new ArrayList<Block>(15);

        mWorld = new World(new Vector2(0, -10), false);
        createFixedBody();
        spawnTestBlock();

        mGameCam = new OrthographicCamera();
        mGameCam.setToOrtho(false, 3f, (PVU.SCREEN_HEIGHT / PVU.SCREEN_WIDTH) * 3f);
        System.out.println(mGameCam.viewportHeight);

        mDebugRenderer = new Box2DDebugRenderer();

        // TODO create world

    }

    private void createFixedBody() {

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
        loader.attachFixture(roomBody, "uml_room", fd, 3f);
    }

    private void spawnTestBlock() {
        Block temp = new Block(mWorld);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1f, 1f, 1f, 1f);

        for(Block b : mBlocks)
            b.draw(batch);

        mDebugRenderer.render(mWorld, mGameCam.combined);
    }

    @Override
    protected void update(float delta) {
        mWorld.step(1 / 60f, 6, 2);

        checkInput();

        for(Block b : mBlocks)
            b.update(delta);
    }

    private void checkInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(PVU.MAIN_SCREEN);
        }
    }

    @Override
    protected void cleanUp() {
    }
}
