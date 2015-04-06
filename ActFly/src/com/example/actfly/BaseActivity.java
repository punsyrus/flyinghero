package com.example.actfly;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;

import org.andengine.util.debug.Debug;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

public class BaseActivity extends SimpleBaseGameActivity implements OnClickListener {
	
	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;
	 
	public Font mFont;
	public Font endPoints;
	public Font start;
	public Font gamePoints;
	public Camera mCamera;
	public PhysicsWorld mPhysicsWorld;
	public SharedPreferences Records;
	public SharedPreferences Achievements;
	public SharedPreferences curTheme;
	
	private BitmapTextureAtlas theme1Atlas;
	private BitmapTextureAtlas iconsAtlas;
	public TextureRegion foneRegion;
	public TextureRegion personRegion;
	public TextureRegion enemy_1Region;
	public TextureRegion enemy_2Region;
	public TextureRegion enemy_3Region;
	public TextureRegion humanRegion;
	public TextureRegion shipRegion;
	public TextureRegion ship_smRegion;
	public TextureRegion bonus_1Region;
	public TextureRegion bonus_2Region;
	public TextureRegion bonus_3Region;
	public TextureRegion flyingRegion;
	public TextureRegion blockRegion;
	ArrayList themesList = new ArrayList();
	
	
	//A reference to the current scene
	public Scene mCurrentScene;
	private ITexture mTexture;
	public static BaseActivity instance;
	ITextureRegion mFaceTextureRegion;

	@Override
	public EngineOptions onCreateEngineOptions() {
		 instance = this;
		 mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		 return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onCreateResources() {
		//mFont = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 45, Color.parseColor("#00ff00"));
		//mFont.load();
		endPoints = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 50, Color.parseColor("#FFDD6C"));
		endPoints.load();
		start = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 55, Color.parseColor("#FFDD6C"));
		start.load();
		gamePoints = FontFactory.create(this.getFontManager(),this.getTextureManager(), 256, 256,Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 36, Color.parseColor("#D16900"));
		gamePoints.load();
		/*try {
			this.mTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				@Override
				public InputStream open() throws IOException {
					return getAssets().open("gfx/face_box.png");
				}
			});

			this.mTexture.load();
			this.mFaceTextureRegion = TextureRegionFactory.extractFromTexture(this.mTexture);
		} catch (IOException e) {
			Debug.e(e);
		}*/
		
		Records = getSharedPreferences("HighScore", Context.MODE_PRIVATE);
		Achievements = getSharedPreferences("Achievements", Context.MODE_PRIVATE);
		curTheme = getSharedPreferences("CurrentTheme", Context.MODE_PRIVATE);
		Editor editor = Records.edit();
		editor.putInt("Record", 0);
		editor.apply();
		theme1Atlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 512);
		theme1Atlas.load();
		iconsAtlas = new BitmapTextureAtlas(this.getTextureManager(), 256, 128);
		iconsAtlas.load();
		//loadBackSprite("gfx/1_noch_d.jpg");
	}

	public void loadBackSprite(String path) {
		
		
		foneRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_back.png", 0, 0);
		personRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_pers.png", 865, 120);
		enemy_3Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_enem_3.png", 865, 20);
		enemy_2Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_enem_2.png", 810, 195);
		enemy_1Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_enem_1.png", 820, 160);
		humanRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_huma.png", 865, 70);
		shipRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_ship.png", 815, 250);
		ship_smRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_ship.png", 820, 300);
		bonus_1Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_bonu_1.png", 815, 40);
		bonus_2Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_bonu_2.png", 815, 80);
		bonus_3Region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_bonu_3.png", 815, 120);
		//flyingRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_bonu_1.png", 0, 0);
		blockRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_bloc.png", 815, 10);
		
	}
	
public TextureRegion loadIconSprite(String path, int x, int y) {
		
		TextureRegion icon = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.iconsAtlas, this, path+"_pers.png", x, y);
		return icon;
	}

	@Override
	protected Scene onCreateScene() {
				
		mCurrentScene = new MainMenu();
		mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_MARS), false);
		
		createWalls();
		return mCurrentScene;
		
	}
	
	private void createWalls() {
		// TODO Auto-generated method stub
		FixtureDef WALL_FIX_t = PhysicsFactory.createFixtureDef(0.0f, 0f, 0f);
		FixtureDef WALL_FIX_s = PhysicsFactory.createFixtureDef(0.0f, 0f, 0f);
		Rectangle ground = new Rectangle(0, CAMERA_HEIGHT - 15, CAMERA_WIDTH,
				15, this.mEngine.getVertexBufferObjectManager());
		ground.setColor(0.078f, 0.49f, 0.8f);
		PhysicsFactory.createBoxBody(mPhysicsWorld, ground, BodyType.StaticBody,
				WALL_FIX_t);
		ground.setUserData("wall");
		mCurrentScene.attachChild(ground);
		
		Rectangle sillin = new Rectangle(0, 0, CAMERA_WIDTH,
				15, this.mEngine.getVertexBufferObjectManager());
		sillin.setColor(0.078f, 0.49f, 0.8f);
		PhysicsFactory.createBoxBody(mPhysicsWorld, sillin, BodyType.StaticBody,
				WALL_FIX_t);
		sillin.setUserData("wall");
		mCurrentScene.attachChild(sillin);
		
		Rectangle left = new Rectangle(0, 0, 15,
				CAMERA_HEIGHT, this.mEngine.getVertexBufferObjectManager());
		left.setColor(0.078f, 0.49f, 0.8f);
		PhysicsFactory.createBoxBody(mPhysicsWorld, left, BodyType.StaticBody,
				WALL_FIX_s);
		left.setUserData("wall");
		mCurrentScene.attachChild(left);
		
		Rectangle right = new Rectangle(CAMERA_WIDTH-15, 0, 15,
				CAMERA_HEIGHT, this.mEngine.getVertexBufferObjectManager());
		right.setColor(0.078f, 0.49f, 0.8f);
		PhysicsFactory.createBoxBody(mPhysicsWorld, right, BodyType.StaticBody,
				WALL_FIX_s);
		right.setUserData("wall");
		mCurrentScene.attachChild(right);
	}
	
	
	
	public static BaseActivity getSharedInstance() {
	    return instance;
	}
	 
	// to change the current main scene
	public void setCurrentScene(Scene scene) {
	    mCurrentScene = scene;
	    getEngine().setScene(mCurrentScene);
	    getEngine().runOnUpdateThread(new Runnable() 
	    {
	        @Override
	        public void run() 
	        {//Log.i("ContactDelete1", String.valueOf("Deleting..."));
	        	Iterator boIter = BaseActivity.getSharedInstance().mPhysicsWorld.getBodies();
	        	
	    		while (boIter.hasNext())
	    		{
	    			Body nBody=(Body) boIter.next();
	    			if (nBody!=null)
	    			if (nBody.getUserData()!=null)
	    			if (nBody.getUserData().equals("Delete"))
	    			{//Log.i("ContactDelete2", String.valueOf("Deleting..."));
	    				if (nBody!=null)
	    				{
	    					BaseActivity.getSharedInstance().mPhysicsWorld.destroyBody(nBody);
	    					nBody.setUserData(null);
	    					nBody=null;
	    					break;
	    				}
	    			}
	    		}
	        }
	    });
	}
	
	
}


