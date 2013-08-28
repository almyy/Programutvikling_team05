package no.hist.gruppe5.pvu.dialogdrawer;

import no.hist.gruppe5.pvu.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/26/13
 * Time: 10:56 PM
 */

public class TestScreen extends GameScreen {

    private boolean left = true;
    private boolean up = true;

    private Sprite mTestSprite;
    private Dialog dialog;
    private Skin skin;
    private BitmapFont titleFont;
    private Color titleFontColor;
    private Drawable background;

    public TestScreen(PVU game) {
        super(game);
        mTestSprite = new Sprite(Assets.testRegion);
        mTestSprite.setSize(1f, 1f);
        mTestSprite.setOrigin(mTestSprite.getWidth() / 2, mTestSprite.getHeight() / 2);
        mTestSprite.setPosition(mTestSprite.getWidth() / 2, camera.viewportHeight / 1.9f);
       // skin = new Skin();
       // skin.add("logo", new Window.WindowStyle(titleFont, titleFontColor, background));
        titleFontColor = new Color(Color.RED);
        titleFont = new BitmapFont();
        background.draw(batch, 30, 30, 30, 30);
        dialog = new Dialog("Dialog", new Window.WindowStyle(titleFont, titleFontColor, background));
        dialog.add("heia beib");
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        mTestSprite.draw(batch);
        batch.end();
    }

    @Override
    protected void update(float delta) {
/*        if (mTestSprite.getX() < 0f && left) {
            left = false;
        } else if (mTestSprite.getX() + mTestSprite.getWidth() > PVU.GAME_WIDTH && !left) {
            left = true;
        }

        if (mTestSprite.getY() < 0f && up) {
            up = false;
        } else if (mTestSprite.getY() + mTestSprite.getHeight() > PVU.GAME_HEIGHT && !up) {
            up = true;
        }

        float dx = (left) ? -1f : 1f;
        float dy = (up) ? -1f : 1f;
        mTestSprite.setPosition(mTestSprite.getX() + dx * delta, mTestSprite.getY() + dy * delta);
        
       */ 
    }

    @Override
    protected void cleanUp() {
    }
}
