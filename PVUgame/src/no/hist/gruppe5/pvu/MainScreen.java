package no.hist.gruppe5.pvu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/26/13
 * Time: 10:56 PM
 */
public class MainScreen extends GameScreen {


    private boolean left = true;
    private boolean up = true;

    private Sprite mBackground;

    public MainScreen(PVU game) {
        super(game);



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
    }

    @Override
    protected void update(float delta) {

    }

    @Override
    protected void cleanUp() {
    }
}
