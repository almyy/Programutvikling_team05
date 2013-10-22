package no.hist.gruppe5.pvu.umlblocks.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.mainroom.BodyEditorLoader;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/17/13
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class Room {

    public static final int DONE = -1000;

    public static final int EASY = 100;
    public static final int MEDIUM = 200;
    public static final int HARD = 300;

    private Body mBody;
    private Sprite mBackground;

    public Room(World world, int difficulty) {
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/pvugame.json"));

        BodyDef bd = new BodyDef();
        bd.position.set(0, 0);
        bd.type = BodyDef.BodyType.StaticBody;

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 0.5f;
        fd.restitution = 0f;

        mBody = world.createBody(bd);
        String stringDiff;
        switch (difficulty) {
            case EASY:
                stringDiff = "easy";
                mBackground = new Sprite(Assets.ubEasy);
                break;
            case MEDIUM:
                stringDiff = "medium";
                mBackground = new Sprite(Assets.ubMedium);
                break;
            case HARD:
                stringDiff = "hard";
                mBackground = new Sprite(Assets.ubHard);
                break;
            default:
                stringDiff = "easy";
        }

        loader.attachFixture(mBody, stringDiff, fd, 3f);

    }

    public void draw(SpriteBatch batch) {
        mBackground.draw(batch);
    }

    public void update(float delta) {
        // TODO
    }

    public Body getBody() {
        return mBody;
    }
}
