package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Platform {

    private BodyDef groundBodyDef;
    private Body groundBody;
    private PolygonShape groundBox;

    public Body createPlatform(Vector2 from, float size, World world, boolean flat) {
        groundBox = new PolygonShape();
        groundBodyDef = new BodyDef();
        groundBox = new PolygonShape();
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
}
