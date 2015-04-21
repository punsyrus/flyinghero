package com.example.actfly;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.modifier.ease.EaseBounceIn;
import org.andengine.util.modifier.ease.EaseExponentialIn;
import org.andengine.util.modifier.ease.EaseQuartIn;
import org.andengine.util.modifier.ease.EaseSineIn;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import android.content.SharedPreferences.Editor;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

public class GameScene extends Scene implements IOnSceneTouchListener {
	
	public Ship ship;
	public Person person;
	Camera mCamera;
	float accelerometerSpeedX;
	public LinkedList bulletList;
	int bulletCount;
	int dFire=0;
	boolean started=false;
	Achievements ach = Achievements.getSharedInstance();
	public int points=0;
	public int fires=0;
	Body body1;
	Body body2;
	public Text tpoints;
	public Text thumans;
	public Text tfires;
	public BaseActivity act;
	SensorManager sensorManager;
	public Rectangle pauseBut1;
	public Rectangle pauseBut2;
	public Rectangle pauseButInv;
	public int hits=0;
	boolean kitOpened=false;
	/*sprites*/
	public Sprite foneSprite;
	public Sprite personSprite;
	public Sprite enemy_1Sprite;
	public Sprite enemy_2Sprite;
	public Sprite enemy_3Sprite;
	public Sprite humanSprite;
	public Sprite shipSprite;
	public Sprite ship_smSprite;
	public Sprite bonus_1Sprite;
	public Sprite bonus_2Sprite;
	public Sprite bonus_3Sprite;
	public Sprite flyingSprite;
	public Sprite blockSprite;
	/*sprites*/
	
	public int humansC=0;
	public int allHumans=0;
	public int allFires=0;
	
	public Text kEnemC;
	public Text kHumaC;
	
	HUD hud = new HUD();
	boolean isPaused=false;
	public static GameScene instance;
	BaseActivity activity = BaseActivity.getSharedInstance();
	
	
	public GameScene() {
		instance=this;
		
		setUserData("GameScene");
		activity.themesList.add(0,"balls_theme");
		activity.themesList.add(1,"fruit_theme");
		activity.themesList.add(2,"neon_theme");
		activity.themesList.add(3,"ocean_theme");
		activity.themesList.add(4,"fire_theme");
		
		Editor editor = activity.Achievements.edit();
		editor.clear();
		editor.putInt(String.valueOf(activity.themesList.get(0)), 1);
	/*	editor.putInt(String.valueOf(activity.themesList.get(1)), 1);
		editor.putInt(String.valueOf(activity.themesList.get(2)), 1);
		editor.putInt(String.valueOf(activity.themesList.get(3)), 1);
		editor.putInt(String.valueOf(activity.themesList.get(4)), 1);*/
		
		editor.putInt(String.valueOf(activity.themesList.get(1)), 0);
		editor.putInt(String.valueOf(activity.themesList.get(2)), 0);
		editor.putInt(String.valueOf(activity.themesList.get(3)), 0);
		editor.putInt(String.valueOf(activity.themesList.get(4)), 0);
		
		//editor.putInt("fruit_theme", 1);
		editor.apply();
		Editor editorTheme = activity.curTheme.edit();
		editorTheme.clear();
		if (activity.curTheme.getString("Theme", "0").equals("0"))
		{
			editorTheme.putString("Theme", String.valueOf(activity.themesList.get(0)));
			activity.loadBackSprite("gfx/"+String.valueOf(activity.themesList.get(0))+"/def");
		}
		else 
		{
			activity.loadBackSprite("gfx/"+String.valueOf(activity.curTheme.getString("Theme", "0"))+"/def");
		}
		editorTheme.apply();
		loadSprites();
		
		setBackground(new SpriteBackground(foneSprite));
		setOnSceneTouchListener(this);
		bulletList = new LinkedList();
	    
	 // attaching an EnemyLayer entity with 3 enemies on it
	    attachChild(new EnemyLayer(3));
	    sortChildren(true);
	    mCamera = BaseActivity.getSharedInstance().mCamera;
	    BaseActivity.getSharedInstance().mPhysicsWorld.setContactListener(new MyContactListener());
	    ship = Ship.getSharedInstance();
	    person = Person.getSharedInstance();
	    act = BaseActivity.getSharedInstance();
	    tpoints = new Text(0, 0, act.gamePoints, String.valueOf(points), act.getVertexBufferObjectManager());
		tpoints.setPosition(10, 10);
		thumans = new Text(0, 0, act.gamePoints, String.valueOf(humansC) + "/5" , act.getVertexBufferObjectManager());
		thumans.setPosition(mCamera.getWidth()/3,10);
		tfires = new Text(0, 0, act.gamePoints, String.valueOf(fires) + "/" + String.valueOf(EnemyLayer.getSharedInstance().enemyCount+dFire) , act.getVertexBufferObjectManager());
		tfires.setPosition(mCamera.getWidth()-mCamera.getWidth()/3,10);
		attachChild(tfires);
		attachChild(tpoints);
		attachChild(thumans);
		personSprite = person.sprite2;
		shipSprite = ship.sprite;
	    attachChild(ship.sprite);
	    attachChild(person.sprite2); 
	    
	    kit();
	    
	    pauseBut1 = new Rectangle(mCamera.getWidth()-60, 20, 15, 40, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager());
	    pauseBut2 = new Rectangle(mCamera.getWidth()-35, 20, 15, 40, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager());
	            
	    
	    pauseButInv = new Rectangle(mCamera.getWidth()-80, 0, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if(isPaused) {
						isPaused = false;
						setIgnoreUpdate(false);
						//Log.i("paused", "undone");
					}
					else {
						isPaused = true;
						activity.setCurrentScene(new PauseScene());
						//Log.i("paused", "done");
					}
					break;
				case TouchEvent.ACTION_MOVE:
					break;
				case TouchEvent.ACTION_UP:
					break;
			}

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(pauseButInv);
	    pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    pauseBut1.setColor(0.9f, 0.99f, 0.4f);
	    pauseBut2.setColor(0.9f, 0.99f, 0.4f);
	    attachChild(pauseBut2);
	    attachChild(pauseBut1);
	    attachChild(pauseButInv);
	    
	   // mCamera.setHUD(hud);
	    
	    final FixtureDef PLAYER_1 = PhysicsFactory.createFixtureDef(35.0f, 1f, 5f);
	    final FixtureDef PLAYER_2 = PhysicsFactory.createFixtureDef(9000.0f, 1.2f, 5f);
	    
	    
		body1 = PhysicsFactory.createBoxBody(BaseActivity.getSharedInstance().mPhysicsWorld, person.sprite2,
				BodyType.DynamicBody, PLAYER_1);
	    
		BaseActivity.getSharedInstance().mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(person.sprite2,
				body1, true, false));
		
		body1.setUserData("person");
		
		body2 = PhysicsFactory.createBoxBody(BaseActivity.getSharedInstance().mPhysicsWorld, ship.sprite,
				BodyType.KinematicBody, PLAYER_2);
		
		BaseActivity.getSharedInstance().mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(ship.sprite,
				body2, true, false));
		
		body2.setUserData("ship");
		
		BaseActivity.getSharedInstance().setCurrentScene(this);
	    
	    SensorManager sensorManager = (SensorManager) BaseActivity.getSharedInstance()
	        .getSystemService(BaseGameActivity.SENSOR_SERVICE);
	    SensorListener.getSharedInstance();
	    sensorManager.registerListener(SensorListener.getSharedInstance(),
	    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	    SensorManager.SENSOR_DELAY_GAME);
	    registerUpdateHandler(BaseActivity.getSharedInstance().mPhysicsWorld);
	    
	    registerUpdateHandler(new GameLoopUpdateHandler());
	}
	
	private void kit() {
		// TODO Auto-generated method stub
		kitOpened=false;
		final Rectangle kitbutM = new Rectangle(0, 0, 120, 250, activity.getVertexBufferObjectManager());
		
		
		final Rectangle kitEnem = new Rectangle(0, 0, 70, 70, activity.getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					Log.i("kit1", "touch");
					break;
				case TouchEvent.ACTION_MOVE:
					break;
				case TouchEvent.ACTION_UP:
					break;
	    		}
	    		return true;
	    		}};
	    registerTouchArea(kitEnem);
		
		final Rectangle kitHuma = new Rectangle(0, 0, 70, 70, activity.getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					Log.i("kit2", "touch");
					break;
				case TouchEvent.ACTION_MOVE:
					break;
				case TouchEvent.ACTION_UP:
					break;
	    		}
	    		return true;
	    		}};
	    registerTouchArea(kitHuma);
		
		
		kEnemC = new Text(0,0,activity.gamePoints, String.valueOf(activity.Kits.getInt("boomKit", 0)),activity.getVertexBufferObjectManager());
		kHumaC = new Text(0,0,activity.gamePoints, String.valueOf(activity.Kits.getInt("grabKit", 0)),activity.getVertexBufferObjectManager());
		Rectangle kitbut = new Rectangle(0, 0, 120, 250, activity.getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if (!kitOpened) 
						{
							kitbutM.registerEntityModifier(new MoveXModifier(0.5f, -105, -25)); 
							kitEnem.registerEntityModifier(new MoveXModifier(0.5f, -95, 10)); 
							kitHuma.registerEntityModifier(new MoveXModifier(0.5f, -95, 10)); 
							kEnemC.registerEntityModifier(new MoveXModifier(0.5f, -85, 20)); 
							kHumaC.registerEntityModifier(new MoveXModifier(0.5f, -85, 20)); 
							kitOpened=true;
						}
					else if (kitOpened) 
						{
							kitbutM.registerEntityModifier(new MoveXModifier(0.5f, -25, -105)); 
							kitEnem.registerEntityModifier(new MoveXModifier(0.5f, 10, -95));
							kitHuma.registerEntityModifier(new MoveXModifier(0.5f, 10, -95));
							kEnemC.registerEntityModifier(new MoveXModifier(0.5f, 20, -85)); 
							kHumaC.registerEntityModifier(new MoveXModifier(0.5f, 20, -85)); 
							kitOpened=false;
						}
					break;
				case TouchEvent.ACTION_MOVE:
					break;
				case TouchEvent.ACTION_UP:
					break;
			}

	    			return true;
	    		}};
	    registerTouchArea(kitbut);
	    kitbut.setVisible(false);
		kitbut.setPosition(-105, mCamera.getHeight()/2-kitbut.getHeight()/2);
		kitbutM.setPosition(-105, mCamera.getHeight()/2-kitbut.getHeight()/2);
		kitbutM.setColor(0.64f, 0.81f, 0.81f, 0.5f);
		kitEnem.setPosition(-95, mCamera.getHeight()/2-kitbut.getHeight()/2+10);
		kitEnem.setColor(0.64f, 0.81f, 0.81f, 0.3f);
		kitHuma.setPosition(-95, mCamera.getHeight()/2-kitbut.getHeight()/2+95);
		kitHuma.setColor(0.64f, 0.81f, 0.81f, 0.3f);
		
		kEnemC.setPosition(kitEnem.getX()+15, kitEnem.getY()+15);
		kEnemC.setColor(0.64f, 0.81f, 0.81f);
		kHumaC.setPosition(kitHuma.getX()+15, kitHuma.getY()+15);
		kHumaC.setColor(0.64f, 0.81f, 0.81f);
		
		attachChild(kitbut);
		attachChild(kitbutM);
		attachChild(kitEnem);
		attachChild(kitHuma);
		attachChild(kEnemC);
		attachChild(kHumaC);
	}
	
	public void updateKit()
	{
	/*	Editor editor = activity.Kits.edit();
		editor.clear();
		editor.putInt("boomKit", activity.Kits.getInt("boomKit", 0)+1);
		editor.putInt("grabKit", activity.Kits.getInt("grabKit", 0)+1);
		editor.apply();*/
		detachChild(kEnemC);
		detachChild(kHumaC);
		kEnemC.setText(String.valueOf(activity.Kits.getInt("boomKit", 0)));
		kHumaC.setText(String.valueOf(activity.Kits.getInt("grabKit", 0)));
		attachChild(kEnemC);
		attachChild(kHumaC);
	}

	public void loadSprites()
	{
		loadblockSprite();
		loadbonus_1Sprite();
		loadbonus_2Sprite();
		loadbonus_3Sprite();
		loadenemy_3Sprite();
		loadenemy_2Sprite();
		loadenemy_1Sprite();
		//loadflyingSprite();
		loadfoneSprite();
		loadhumanSprite();
		loadpersonSprite();
		loadship_smSprite();
		loadshipSprite();
	}
	
	public Sprite loadfoneSprite()
	{
		foneSprite = new Sprite(0, 0, activity.foneRegion, activity.getVertexBufferObjectManager());
		return foneSprite;
	}
	
	public Sprite loadpersonSprite()
	{
		personSprite = new Sprite(0, 0, activity.personRegion, activity.getVertexBufferObjectManager());
		return personSprite;
	}
	
	public Sprite loadenemy_1Sprite()
	{
		enemy_1Sprite = new Sprite(0, 0, activity.enemy_1Region, activity.getVertexBufferObjectManager());
		return enemy_1Sprite;
	}
	
	public Sprite loadenemy_2Sprite()
	{
		enemy_2Sprite = new Sprite(0, 0, activity.enemy_2Region, activity.getVertexBufferObjectManager());
		return enemy_2Sprite;
	}
	
	public Sprite loadenemy_3Sprite()
	{
		enemy_3Sprite = new Sprite(0, 0, activity.enemy_3Region, activity.getVertexBufferObjectManager());
		return enemy_3Sprite;
	}
	
	public Sprite loadhumanSprite()
	{
		humanSprite = new Sprite(0, 0, activity.humanRegion, activity.getVertexBufferObjectManager());
		return humanSprite;
	}
	
	public Sprite loadshipSprite()
	{
		shipSprite = new Sprite(0, 0, activity.shipRegion, activity.getVertexBufferObjectManager());
		return shipSprite;
	}
	
	public Sprite loadship_smSprite()
	{
		ship_smSprite = new Sprite(0, 0, activity.ship_smRegion, activity.getVertexBufferObjectManager());
		return ship_smSprite;
	}
	
	public Sprite loadbonus_1Sprite()
	{
		bonus_1Sprite = new Sprite(0, 0, activity.bonus_1Region, activity.getVertexBufferObjectManager());
		return bonus_1Sprite;
	}
	
	public Sprite loadbonus_2Sprite()
	{
		bonus_2Sprite = new Sprite(0, 0, activity.bonus_2Region, activity.getVertexBufferObjectManager());
		return bonus_2Sprite;
	}
	
	public Sprite loadbonus_3Sprite()
	{
		bonus_3Sprite = new Sprite(0, 0, activity.bonus_3Region, activity.getVertexBufferObjectManager());
		return bonus_3Sprite;
	}
	
	public Sprite loadflyingSprite()
	{
		flyingSprite = new Sprite(0, 0, activity.flyingRegion, activity.getVertexBufferObjectManager());
		return flyingSprite;
	}
	
	public Sprite loadblockSprite()
	{
		blockSprite = new Sprite(0, 0, activity.blockRegion, activity.getVertexBufferObjectManager());
		return blockSprite;
	}
	
	public static GameScene getSharedInstance() {
	    return instance;
	}
	
	public void goDefaultScene()
	{
		
		points=0;
		humansC=0;
		allHumans=0;
		fires=0;
		allFires=0;
		dFire=0;
		started=false;
		ship.goDefaultShip();
		person.goDefaultPerson();
		 attachChild(new EnemyLayer(3));
		 sortChildren(true);
		 detachChild(tfires);
		 detachChild(tpoints);
		 detachChild(thumans);
		tpoints = new Text(0, 0, act.gamePoints, String.valueOf(points), act.getVertexBufferObjectManager());
		tpoints.setPosition(10, 10);
		thumans = new Text(0, 0, act.gamePoints, String.valueOf(humansC) + "/5" , act.getVertexBufferObjectManager());
		thumans.setPosition(mCamera.getWidth()/3,10);
		tfires = new Text(0, 0, act.gamePoints, String.valueOf(fires) + "/" + String.valueOf(EnemyLayer.getSharedInstance().enemyCount+dFire) , act.getVertexBufferObjectManager());
		tfires.setPosition(mCamera.getWidth()-mCamera.getWidth()/3,10);
		attachChild(tfires);
		attachChild(tpoints);
		attachChild(thumans);
	    attachChild(ship.sprite);
	    attachChild(person.sprite2); 
	    kit();
	    
	    pauseBut1 = new Rectangle(mCamera.getWidth()-60, 20, 15, 40, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager());
	    pauseBut2 = new Rectangle(mCamera.getWidth()-35, 20, 15, 40, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager());
	    
	    pauseButInv = new Rectangle(mCamera.getWidth()-70, 0, 65, 65, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		switch(pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					if(isPaused) {
						isPaused = false;
						setIgnoreUpdate(false);
						//Log.i("paused", "undone");
					}
					else {
						isPaused = true;
						activity.setCurrentScene(new PauseScene());
						//Log.i("paused", "done");
					}
					break;
				case TouchEvent.ACTION_MOVE:
					break;
				case TouchEvent.ACTION_UP:
					break;
			}

	    			return true;
	    		}};
	   
	    	registerTouchArea(pauseButInv);
	    	pauseButInv.setVisible(false);
	    	setTouchAreaBindingOnActionDownEnabled(true);
	    	pauseBut1.setColor(0.9f, 0.99f, 0.4f);
	    	pauseBut2.setColor(0.9f, 0.99f, 0.4f);
	    	attachChild(pauseBut2);
	    	attachChild(pauseBut1);
	    	attachChild(pauseButInv);
	    
		    final FixtureDef PLAYER_1 = PhysicsFactory.createFixtureDef(35.0f,1f, 5f);
		    final FixtureDef PLAYER_2 = PhysicsFactory.createFixtureDef(9000.0f,1.2f, 5f);
	    
	    
		body1 = PhysicsFactory.createBoxBody(BaseActivity.getSharedInstance().mPhysicsWorld, person.sprite2,
				BodyType.DynamicBody, PLAYER_1);
	    
		BaseActivity.getSharedInstance().mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(person.sprite2,
				body1, true, false));
		
		body1.setUserData("person");
		
		body2 = PhysicsFactory.createBoxBody(BaseActivity.getSharedInstance().mPhysicsWorld, ship.sprite,
				BodyType.KinematicBody, PLAYER_2);
		
		BaseActivity.getSharedInstance().mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(ship.sprite,
				body2, true, false));
		
		body2.setUserData("ship");
		
		
		
	}
	
	void makePause()
	{
		this.setIgnoreUpdate(true);
	}
	
	public void makeSmall() {
		detachChild(ship.sprite);
		Ship.getSharedInstance().makeSamll();
		attachChild(ship.sprite);
		  final FixtureDef PLAYER_2 = PhysicsFactory.createFixtureDef(9000.0f,
					1.5f, 0.5f);
		body2 = PhysicsFactory.createBoxBody(BaseActivity.getSharedInstance().mPhysicsWorld, ship.sprite,
				BodyType.KinematicBody, PLAYER_2);
		
		BaseActivity.getSharedInstance().mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(ship.sprite,
				body2, true, false));
		
		body2.setUserData("ship");
	}
	
	public void moveShip() {
		//ship.moveShip(accelerometerSpeedX);
	}
	
	public void movePerson() {
		
	   // person.movePerson(accelerometerSpeedX);
	}
	
	public static boolean isBetween(int x, int lower, int upper) {
		  return lower <= x && x <= upper;
		}
	
	public void dropHuman(Body b1) {
		
			
			//Rectangle bonus1 = new Rectangle(0, 0, 30, 30, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		//Log.i("Humans", String.valueOf("do"));	
		Sprite bonus1 = loadhumanSprite();
		//Log.i("Humans", String.valueOf("posle"));
			mCamera = BaseActivity.getSharedInstance().mCamera;
			bonus1.setPosition(b1.getPosition().x * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT-bonus1.getWidth()/2,b1.getPosition().y * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT-bonus1.getHeight()/2);
			//Log.i("Humans", String.valueOf("posle"));
			Iterator enIter = EnemyLayer.getSharedInstance().getIteratorHumans();
			while (enIter.hasNext())
			{
				Sprite r = (Sprite) enIter.next();
				if (r.getUserData().equals(b1.getUserData()))
					r.detachSelf();
				//Log.i("Humans", String.valueOf(r.getUserData()));
			}
			b1.setUserData("Delete");
			//bonus1.setColor(0.59904f, 0.8574f, 0.9986f);
			bonus1.registerEntityModifier(new MoveYModifier(2,bonus1.getY(),mCamera.getHeight()-10-ship.sprite.getHeight(),EaseSineIn.getInstance())
			{
				 @Override
			        protected void onModifierStarted(IEntity pItem)
			        {
			                super.onModifierStarted(pItem);
			                // Your action after starting modifier
			        }
			       
			        @Override
			        protected void onModifierFinished(final IEntity pItem)
			        {
			        	
			                super.onModifierFinished(pItem);
			                BaseActivity.getSharedInstance().runOnUpdateThread(new Runnable() {

				                   @Override
				                   public void run() {
				                    //setIgnoreUpdate(true);
				                    pItem.clearUpdateHandlers();
				                    pItem.detachSelf();

				                   }
				               });
			                if ((pItem.getX()>ship.sprite.getX()) && (pItem.getX()<(ship.sprite.getX()+ship.sprite.getWidth())))
			                {
			                	humansC++;
			                	allHumans++;
			                	points+=15;
			                	if ((points>200) && (activity.Records.getInt("Background_1", 0))==0)
			            		{
			            			Editor editor = activity.Records.edit();
			            			editor.putInt("Background_1", 1);
			            			editor.apply();
			            		}
			                detachChild(thumans);
			                thumans = new Text(0, 0, act.gamePoints, String.valueOf(humansC) + "/5" , act.getVertexBufferObjectManager());
			        		thumans.setPosition(mCamera.getWidth()/3,10);
			       		 	attachChild(thumans);
			       		 	
			       		 	detachChild(tpoints);
			       		 	tpoints = new Text(0, 0, act.gamePoints, String.valueOf(points), act.getVertexBufferObjectManager());
			       		 	tpoints.setPosition(10, 10);
			       		 	attachChild(tpoints);
			       		 	
						    Log.i("Humans", "Human" + String.valueOf(humansC));
			               }
			                //detachChild(getChildByTag(pItem.getUserData()));
			                pItem.setUserData("Delete");
			        }
			});
			
			
			bonus1.setUserData("bonus1");
			attachChild(bonus1);
			
			}
	
	
	
	public void dropBonus(Body b1) {
		Random r = new Random();
		int ran = r.nextInt(20);
		float X = b1.getPosition().x * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		float Y = b1.getPosition().y * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
		if (isBetween(ran, 1, 3)) 
			{
			/*Log.i("Bonus", String.valueOf(ran) + " 1");*/
			//Rectangle bonus1 = new Rectangle(0, 0, 25, 25, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
			Sprite bonus1 = loadbonus_1Sprite();
			mCamera = BaseActivity.getSharedInstance().mCamera;
			bonus1.setPosition(X,Y);
			//bonus1.setColor(0.7f, 0.88f, 0.97f);
			bonus1.registerEntityModifier(new MoveYModifier(2,Y,mCamera.getHeight()-10-ship.sprite.getHeight(),EaseSineIn.getInstance())
			{
				 @Override
			        protected void onModifierStarted(IEntity pItem)
			        {
			                super.onModifierStarted(pItem);
			                // Your action after starting modifier
			        }
			       
			        @Override
			        protected void onModifierFinished(final IEntity pItem)
			        {
			        	pItem.setUserData("Delete");
			                super.onModifierFinished(pItem);
			                BaseActivity.getSharedInstance().runOnUpdateThread(new Runnable() {

				                   @Override
				                   public void run() {
				                    //setIgnoreUpdate(true);
				                    pItem.clearUpdateHandlers();
				                    pItem.detachSelf();

				                   }
				               });
			                if ((pItem.getX()>ship.sprite.getX()) && (pItem.getX()<(ship.sprite.getX()+ship.sprite.getWidth())))
			                {
			                EnemyLayer enemy = EnemyLayer.getSharedInstance();
			                Enemy e1 = (Enemy) enemy.enemies.getFirst();
			                //Enemy e2 = (Enemy) enemy.enemies.get(2);
			                Iterator boIter = BaseActivity.getSharedInstance().mPhysicsWorld.getBodies();
							while (boIter.hasNext())
							{
								Body nBody=(Body) boIter.next();
								if (nBody.getUserData()!=null)
								if (nBody.getUserData().equals(String.valueOf(e1.id)))
									nBody.setUserData("Delete");
							}
							e1.spriteF.detachSelf();
							e1.spriteRF.detachSelf();
							e1.spriteHp.detachSelf();
							e1.spriteHp3.detachSelf();
							e1.clean();
						    EnemyLayer.getSharedInstance().enemies.remove(e1);
						    
			               }
			                
			              
			        }
			});
			bonus1.setUserData("bonus1");
			attachChild(bonus1);
			
			}
		else if (isBetween(ran, 4, 5)) 
			{
			/*Log.i("Bonus", String.valueOf(ran) + " 2");
			Log.i("Bonus", String.valueOf(ran) + " 1");*/
			//Rectangle bonus2 = new Rectangle(0, 0, 25, 25, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
			Sprite bonus2 = loadbonus_2Sprite();
			mCamera = BaseActivity.getSharedInstance().mCamera;
			bonus2.setPosition(X,Y);
			//bonus2.setColor(1.0f, 0.0f, 0.0f);
			bonus2.registerEntityModifier(new MoveYModifier(2,Y,mCamera.getHeight()-10-ship.sprite.getHeight(),EaseSineIn.getInstance()){
				 @Override
			        protected void onModifierStarted(IEntity pItem)
			        {
			                super.onModifierStarted(pItem);
			                // Your action after starting modifier
			        }
			       
			        @Override
			        protected void onModifierFinished(final IEntity pItem)
			        {
			                super.onModifierFinished(pItem);
			                if ((pItem.getX()>ship.sprite.getX()) && (pItem.getX()<(ship.sprite.getX()+ship.sprite.getWidth())))
			                {
			                	EnemyLayer.getSharedInstance().addOne();
			                	dFire++;
			                	allFires++;
			                	detachChild(tfires);
								tfires = new Text(0, 0, act.gamePoints, String.valueOf(fires) + "/" + String.valueOf(EnemyLayer.getSharedInstance().enemyCount+dFire) , act.getVertexBufferObjectManager());
								tfires.setPosition(mCamera.getWidth()-mCamera.getWidth()/3,10);
								attachChild(tfires); 
			                }
			                	pItem.setUserData("Delete");
			               BaseActivity.getSharedInstance().runOnUpdateThread(new Runnable() {

			                   @Override
			                   public void run() {
			                    //setIgnoreUpdate(true);
			                    pItem.clearUpdateHandlers();
			                    pItem.detachSelf();

			                   }
			               });
			                
			                
			        }
			});
			bonus2.setUserData("bonus2");
			attachChild(bonus2);
			}
		else if (isBetween(ran, 6, 6)) 
		{
			/*Log.i("Bonus", String.valueOf(ran) + " 3");
			Log.i("Bonus", String.valueOf(ran) + " 1");*/
			//Rectangle bonus3 = new Rectangle(0, 0, 25, 25, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
			Sprite bonus3 = loadbonus_3Sprite();
			mCamera = BaseActivity.getSharedInstance().mCamera;
			bonus3.setPosition(X,Y);
			//bonus3.setColor(1.0f, 0.75f, 0.75f);
			bonus3.registerEntityModifier(new MoveYModifier(2,Y,mCamera.getHeight()-10-ship.sprite.getHeight(),EaseSineIn.getInstance()){
				 @Override
			        protected void onModifierStarted(IEntity pItem)
			        {
			                super.onModifierStarted(pItem);
			                pItem.setUserData("Delete");
			                // Your action after starting modifier
			        }
			       
			        @Override
			        protected void onModifierFinished(final IEntity pItem)
			        {
			                super.onModifierFinished(pItem);
			                if ((pItem.getX()>ship.sprite.getX()) && (pItem.getX()<(ship.sprite.getX()+ship.sprite.getWidth())))
			                {
			                	points+=50;
			                    detachChild(tpoints);
			       			 	tpoints = new Text(0, 0, act.gamePoints, String.valueOf(points), act.getVertexBufferObjectManager());
			       			 	tpoints.setPosition(10, 10);
			       			 	attachChild(tpoints);
			       			 	Random rk = new Random();
			       			 	int ki = rk.nextInt(1);
			       				Editor editor = activity.Kits.edit();
			       				editor.clear();
			       				if (ki==1) editor.putInt("boomKit", activity.Kits.getInt("boomKit", 0)+1);
			       				if (ki==0) editor.putInt("grabKit", activity.Kits.getInt("grabKit", 0)+1);
			       				editor.apply();
			       			 	
			                }
			                	pItem.setUserData("Delete");
			               BaseActivity.getSharedInstance().runOnUpdateThread(new Runnable() {

			                   @Override
			                   public void run() {
			                    //setIgnoreUpdate(true);
			                    pItem.clearUpdateHandlers();
			                    pItem.detachSelf();

			                   }
			               });
			        }
			});
			bonus3.setUserData("bonus3");
			attachChild(bonus3);
		}
		//else Log.i("Bonus", "No bonus");
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		/*if (!CoolDown.getSharedInstance().checkValidity())
		    return false;*/
		
		float touchXpoint = pSceneTouchEvent.getX();
		if (!person.started) person.shoot(touchXpoint);
		ship.slideShip(touchXpoint);
	    return true;
	}
	
	
	public class MyContactListener implements ContactListener{
	    
	     
		@Override
		public void beginContact(Contact contact) {
			// TODO Auto-generated method stub
			//Log.i("Contact", String.valueOf(b1 + " " + b2));
			Body b1 = contact.getFixtureA().getBody();
			Body b2 = contact.getFixtureB().getBody();
			if ((String.valueOf(b1.getUserData()).contains("person")) && (String.valueOf(b2.getUserData()).contains("Enemy"))) 
					{
				 b2 = contact.getFixtureA().getBody();
				 b1 = contact.getFixtureB().getBody();
					}
			Iterator enemies = EnemyLayer.getSharedInstance().getIterator();
			//Log.i("Contact", String.valueOf(b1.getPosition().x + " " + b2.getPosition().y));
			if ((String.valueOf(b2.getUserData()).contains("person")) && (String.valueOf(b1.getUserData()).contains("Enemy")))
			{
				
				//INSERT <<
				
				dropBonus(b1);
				Iterator enIter = EnemyLayer.getSharedInstance().getIterator();
				while (enIter.hasNext())
				{
					Enemy nEnemy = (Enemy) enIter.next();
					if (nEnemy.id.equals(String.valueOf(b1.getUserData())))
					{
						//Log.i("ContactFoundEnemy", String.valueOf(b1.getUserData()));
						nEnemy.gotHit();
						if (nEnemy.hp<=0) 
						{
							//Log.i("ContactKillEnemy", String.valueOf(b1.getUserData()));
							Iterator boIter = BaseActivity.getSharedInstance().mPhysicsWorld.getBodies();
							while (boIter.hasNext())
							{
								Body nBody=(Body) boIter.next();
								if (nBody.getUserData()!=null)
								if (nBody.getUserData().equals(String.valueOf(b1.getUserData())))
								{
									nBody.setUserData("Delete");
									fires++;
									allFires++;
									hits++;
									detachChild(tfires);
									tfires = new Text(0, 0, act.gamePoints, String.valueOf(fires) + "/" + String.valueOf(EnemyLayer.getSharedInstance().enemyCount+dFire) , act.getVertexBufferObjectManager());
									tfires.setPosition(mCamera.getWidth()-mCamera.getWidth()/3,10);
									attachChild(tfires); 
								}
							}
							nEnemy.spriteF.detachSelf();
							nEnemy.spriteRF.detachSelf();
							nEnemy.spriteHp.detachSelf();
							nEnemy.spriteHp3.detachSelf();
							nEnemy.clean();
							
							EnemyLayer.getSharedInstance().enemies.remove(nEnemy);
							
							break;
						}
					}
				}
			}
			
			if ((String.valueOf(b1.getUserData()).contains("person")) && (String.valueOf(b2.getUserData()).contains("human"))) 
			{
		 b2 = contact.getFixtureA().getBody();
		 b1 = contact.getFixtureB().getBody();
			}
	
	//Log.i("Contact", String.valueOf(b1.getPosition().x + " " + b2.getPosition().y));
	if ((String.valueOf(b2.getUserData()).contains("person")) && (String.valueOf(b1.getUserData()).contains("human")))
	{
		
		//INSERT <<
		dropHuman(b1);}
			
	if ((String.valueOf(b1.getUserData()).contains("person")) && (String.valueOf(b2.getUserData()).contains("ship"))) 
	{
		b2 = contact.getFixtureA().getBody();
		b1 = contact.getFixtureB().getBody();
	}

	if ((String.valueOf(b2.getUserData()).contains("person")) && (String.valueOf(b1.getUserData()).contains("ship")))
	{
		//Log.i("Contact", String.valueOf(value) + " " + "Person");
	
	hits=0;
	Log.i("Contact", "Ship" + " " + "Person");
	}
			
		}
		
		

		@Override
		public void endContact(Contact contact) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {
			// TODO Auto-generated method stub
			
		}

	}

}
