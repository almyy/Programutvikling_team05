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
    //Body
    private BodyDef groundBodyDef;
    private Body groundBody;
    private PolygonShape groundBox;
    //Sprites and positions
    private ArrayList<Sprite> mPlatformSprite;
    private ArrayList<Vector2> mPlatformPositions;
    private ArrayList<Sprite> mPlatformNames;
    //
    private Sprite mPowerBar;

    public Platform(World world) {
        mPlatformSprite = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.mPlatformSprite.add(new Sprite(Assets.seqBox));
        }
        this.mPlatformPositions = new ArrayList<>(5);
        this.mPowerBar = new Sprite(Assets.seqBox);
    }

    public void draw(SpriteBatch batch) {
        for (int i = 0; i < mPlatformSprite.size(); i++) {
            mPlatformSprite.get(i).draw(batch);
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
        this.mPlatformPositions.add(from);
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

    public Body createPlatformName(Vector2 from, float size, World world, boolean flat) {
        groundBox = new PolygonShape();
        groundBodyDef = new BodyDef();
        groundBox = new PolygonShape();
        this.mPlatformPositions.add(from);
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
        for (int i = 0; i < mPlatformSprite.size(); i++) {
            Vector2 pos = mPlatformPositions.get(i);
            mPlatformSprite.get(i).setPosition((pos.x * JumperScreen.BOX_TO_WORLD) - BOX_MARGIN_X - mPlatformSprite.get(i).getWidth() / 2,
                    (pos.y * JumperScreen.BOX_TO_WORLD) - BOX_MARGIN_Y - mPlatformSprite.get(i).getHeight() / 2);
            mPlatformSprite.get(i).setScale(0.185f, 0.15f);
        }
    }
    
    public Sprite createPowerBar(){
        mPowerBar.setPosition(-50f, 3f);
        mPowerBar.setScale(0.5f, 0.3f);
        mPowerBar.rotate(90f);
        return mPowerBar;
    }
}
