package no.hist.gruppe5.pvu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/28/13
 * Time: 10:06 AM
 */
public class Assets {

    public static TextureRegion testRegion;
    public static BitmapFont primaryFont16px;
    public static BitmapFont primaryFont10px;
    public static BitmapFont primaryFont5px;
    public static void load() {
        Texture testTexture = new Texture(Gdx.files.internal("data/square.png"));
        testTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        primaryFont16px = new BitmapFont(
                Gdx.files.internal("data/lucidaBitmap.fnt"),
                Gdx.files.internal("data/lucidaBitmap_0.png"), false);
        primaryFont10px = new BitmapFont(
                Gdx.files.internal("data/lucidaBitmap10px.fnt"),
                Gdx.files.internal("data/lucidaBitmap10px_0.png"), false);
        primaryFont5px = new BitmapFont(
                Gdx.files.internal("data/lucidaBitmap5px.fnt"),
                Gdx.files.internal("data/lucidaBitmap5px_0.png"), false);
        primaryFont5px.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        testRegion = new TextureRegion(testTexture, 0, 0, 100, 100);
    }

    public static void dispose() {
    }
}
