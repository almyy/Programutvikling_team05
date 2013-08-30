/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.book;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/**
 *
 * @author linnk
 */
public class BookScreen extends GameScreen {

    public static Texture testTexture;

    public BookScreen(PVU game) {
        super(game);
        testTexture = new Texture(Gdx.files.internal("data/book.png"));
        testTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        batch.draw(testTexture, 0, 0, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
        batch.end();
    }

    @Override
    protected void update(float delta) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }

    @Override
    protected void cleanUp() {
    }
}
