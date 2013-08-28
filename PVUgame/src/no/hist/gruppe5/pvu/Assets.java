package no.hist.gruppe5.pvu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/28/13
 * Time: 10:06 AM
 */
public class Assets {

    public static TextureRegion testRegion;
    public static TextureRegion visionShooterRegion; 
    public static TextureRegion visionShooterDocumentRegion; 
    public static TextureRegion visionShooterShipRegion; 

    public static void load() {
        Texture testTexture = new Texture(Gdx.files.internal("data/square.png"));
        testTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        
        testRegion = new TextureRegion(testTexture, 0, 0, 100, 100);
        
        
        TextureAtlas visionShooterAtlas = new TextureAtlas(Gdx.files.internal("data/VisionShooterTexture.pack"));
        
        visionShooterRegion = visionShooterAtlas.findRegion("VisionShooterTexture"); 
        visionShooterDocumentRegion = visionShooterAtlas.findRegion("Document");
        visionShooterShipRegion = visionShooterAtlas.findRegion("VisionShooterShip");
    }
    public static void dispose() {
    }
}
