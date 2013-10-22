package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import no.hist.gruppe5.pvu.mainroom.BodyEditorLoader;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/17/13
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class Room {

    private Body mBody;
    private Sprite mSprite;
    private Body wall;

    public Room(World world) {
        // Creating the walls
        createWall(new Vector2(0, 0), 2f, world, false); // left wall
        createWall(new Vector2(3f, 0), 2f, world, false); // right wall
    }

    private Body createWall(Vector2 from, float size, World world, boolean flat) {
        BodyDef groundBodyDef =new BodyDef();
        groundBodyDef.position.set(from);
        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();
        if(flat)
            groundBox.setAsBox(size, 0.05f);
        else
            groundBox.setAsBox(0.05f, size);

        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
        return groundBody;
    }
}

