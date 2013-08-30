package no.hist.gruppe5.pvu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
    public static TextureRegion visionShooterRegion; 
    public static TextureRegion visionShooterDocumentRegion; 
    public static TextureRegion visionShooterShipRegion;
    public static TextureRegion visionShooterBullet; 
    public static TextureRegion visionShooterFacebookRegion;
    public static TextureRegion visionShooterYoutubeRegion;
    
    public static TextureRegion mainAvatarFront;
    public static TextureRegion mainAvatarBack;
    public static TextureRegion mainAvatarSideLeft;
    public static TextureRegion mainAvatarSideRight; 
    
    public static TextureRegion secondaryAvatarLeft1;
    public static TextureRegion secondaryAvatarLeft2;
    public static TextureRegion secondaryAvatarRight1;
    public static TextureRegion secondaryAvatarRight2;
    public static TextureRegion secondaryAvatarRight3; 
    
    public static TextureRegion secondaryAvatarLeft1Slide2;
    public static TextureRegion secondaryAvatarLeft2Slide2;
    public static TextureRegion secondaryAvatarRight1Slide2;
    public static TextureRegion secondaryAvatarRight2Slide2;
    public static TextureRegion secondaryAvatarRight3Slide2; 
    
    
    
    
    public static BitmapFont primaryFont16px;
    public static BitmapFont primaryFont10px;
    public static BitmapFont primaryFont5px;
    public static BitmapFont primaryFont;

    public static void load() {
        Texture testTexture = new Texture(Gdx.files.internal("data/square.png"));
        testTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    
        testTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        primaryFont16px = new BitmapFont(
                Gdx.files.internal("data/LucidaBitmap.fnt"),
                Gdx.files.internal("data/LucidaBitmap_0.png"), false);
        primaryFont10px = new BitmapFont(
                Gdx.files.internal("data/LucidaBitmap10px.fnt"),
                Gdx.files.internal("data/LucidaBitmap10px_0.png"), false);
        primaryFont5px = new BitmapFont(
                Gdx.files.internal("data/LucidaBitmap5px.fnt"),
                Gdx.files.internal("data/LucidaBitmap5px_0.png"), false);
        testRegion = new TextureRegion(testTexture, 0, 0, 100, 100);
        
        TextureAtlas visionShooterAtlas = new TextureAtlas(Gdx.files.internal("data/VisionShooterTexture.pack"));
        TextureAtlas mainAvatarAtlas = new TextureAtlas(Gdx.files.internal("data/Avatar.pack"));
        
        visionShooterRegion = visionShooterAtlas.findRegion("VisionShooterTexture"); 
        visionShooterDocumentRegion = visionShooterAtlas.findRegion("Document");
        visionShooterShipRegion = visionShooterAtlas.findRegion("VisionShooterShip");
        visionShooterBullet = visionShooterAtlas.findRegion("Bullet"); 
        visionShooterFacebookRegion = visionShooterAtlas.findRegion("Facebook");
        visionShooterYoutubeRegion = visionShooterAtlas.findRegion("YouTube");
        
        mainAvatarFront = mainAvatarAtlas.findRegion("MainAvatar");
        mainAvatarBack = mainAvatarAtlas.findRegion("MainAvatarBack");
        mainAvatarSideLeft = mainAvatarAtlas.findRegion("MainAvatarSide");
        mainAvatarSideRight = mainAvatarAtlas.findRegion("MainAvatarSideRight");
        
        secondaryAvatarLeft1 = mainAvatarAtlas.findRegion("Avatar1");
        secondaryAvatarLeft2 = mainAvatarAtlas.findRegion("Avatar2");
        secondaryAvatarRight1 = mainAvatarAtlas.findRegion("Avatar3");
        secondaryAvatarRight2 = mainAvatarAtlas.findRegion("Avatar4");
        secondaryAvatarRight3 = mainAvatarAtlas.findRegion("Avatar5");
        
        secondaryAvatarLeft1Slide2 = mainAvatarAtlas.findRegion("Avatar1Slide2");
        secondaryAvatarLeft2Slide2 = mainAvatarAtlas.findRegion("Avatar2Slide2");
        secondaryAvatarRight1Slide2 = mainAvatarAtlas.findRegion("Avatar3Slide2");
        secondaryAvatarRight2Slide2 = mainAvatarAtlas.findRegion("Avatar4Slide2");
        secondaryAvatarRight3Slide2 = mainAvatarAtlas.findRegion("Avatar5Slide2");
    }
    public static void dispose() {
    }
}
