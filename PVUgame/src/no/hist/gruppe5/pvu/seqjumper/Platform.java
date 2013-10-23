package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;

public class Platform {

    private static final float BOX_MARGIN_X = 0.6f;
    private static final float BOX_MARGIN_Y = -0.1f;
    private BodyDef groundBodyDef;
    private Body groundBody;
    private PolygonShape groundBox;
    private ArrayList<Sprite> mSprite;
    private ArrayList<Vector2> positions;

    public Platform(World world) {
        mSprite = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.mSprite.add(new Sprite(Assets.seqBox));
        }
        this.positions = new ArrayList<>(5);
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < mSprite.size(); i++) {
            mSprite.get(i).draw(batch);
        }
    }

    public void update(float delta) {
        updatePlatform();
    }

    public Body getBody() {
        return groundBody;
    }

    public Body createPlatform(Vector2 from, float size, World world, boolean flat) {
        groundBox = new PolygonShape();
        groundBodyDef = new BodyDef();
        groundBox = new PolygonShape();
        this.positions.add(from);
        groundBodyDef.position.set(from);
        groundBody = world.createBody(groundBodyDef);
        if (flat) {
            groundBox.setAsBox(size, 0.05f);
        } else {
            groundBox.setAsBox(0.05f, size);
        }

        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
        return groundBody;
    }

    protected void updatePlatform() {
        // Update sprite position based on the Box2d body
        for (int i = 0; i < mSprite.size(); i++) {
            Vector2 pos = positions.get(i);
            mSprite.get(i).setPosition((pos.x * JumperScreen.BOX_TO_WORLD) - BOX_MARGIN_X - mSprite.get(i).getWidth() / 2,
                    (pos.y * JumperScreen.BOX_TO_WORLD) - BOX_MARGIN_Y - mSprite.get(i).getHeight() / 2);
            mSprite.get(i).setScale(0.185f, 0.15f);
        }
    }
}
