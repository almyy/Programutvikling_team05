package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/15/13
 * Time: 9:39 AM
 * To change this template use File | Settings | File Templates.
 */

public class BlocksScreen extends GameScreen {

    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    private World mWorld;

    private ArrayList<Block> mBlocks;

    private Box2DDebugRenderer mDebugRenderer;

    public BlocksScreen(PVU game) {
        super(game);

        mBlocks = new ArrayList<Block>(15);

        mWorld = new World(new Vector2(-10, 0), false);

        mDebugRenderer = new Box2DDebugRenderer();

        // TODO create world

    }
    @Override
    protected void draw(float delta) {
        clearCamera(1f, 1f, 1f, 1f);

        for(Block b : mBlocks)
            b.draw();

        mDebugRenderer.render(mWorld, camera.combined);
    }

    @Override
    protected void update(float delta) {
        for(Block b : mBlocks)
            b.update(delta);
    }

    @Override
    protected void cleanUp() {
    }
}
