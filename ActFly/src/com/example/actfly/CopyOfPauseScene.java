package com.example.actfly;

import java.util.Iterator;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;
import org.andengine.engine.camera.Camera;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;

import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

public class CopyOfPauseScene extends Scene implements IOnSceneTouchListener{
	BaseActivity activity;
	public Ship ship;
	public Person person;
	int allHumans,allFires;
	public Rectangle pauseButInv;
	public Rectangle interfaceBut;
	public Rectangle achieveBut;
	GameScene scene = (GameScene) GameScene.getSharedInstance();
	EnemyLayer enemy = EnemyLayer.getSharedInstance();
	
	CopyOfPauseScene()
	{
		Camera cam = BaseActivity.getSharedInstance().mCamera;
		activity = BaseActivity.getSharedInstance();
		setBackground(new Background(0.09804f, 0.6274f, 0));
		Text startButton = new Text(0,0, activity.start, "Paused", activity.getVertexBufferObjectManager());
		Text points = new Text(0, 0, activity.endPoints, String.valueOf("Your score - " + scene.points), activity.getVertexBufferObjectManager());
		Text highscore = new Text(0,0, activity.start, String.valueOf("Highest score - " + activity.Records.getInt("HighScore", 0)), activity.getVertexBufferObjectManager());
		Text humans = new Text(0, 0, activity.endPoints, String.valueOf("Total humans - " + scene.allHumans), activity.getVertexBufferObjectManager());
		Text fires = new Text(0,0, activity.start, String.valueOf("Total fires - " + scene.allFires), activity.getVertexBufferObjectManager());	
		startButton.setPosition(cam.getWidth()/2-startButton.getWidth()/2,10);
		points.setPosition(10,startButton.getY()+startButton.getHeight()+10); 
		highscore.setPosition(points.getX(),points.getY()+points.getHeight()+10);
		humans.setPosition(highscore.getX(),highscore.getY()+highscore.getHeight()+10); 
		fires.setPosition(humans.getX(),humans.getY()+humans.getHeight()+10);
		ship = Ship.getSharedInstance();
		person = Person.getSharedInstance();
		scene.detachChild(ship.sprite);
		scene.detachChild(person.sprite2);
		attachChild(ship.sprite);
		attachChild(person.sprite2);
		attachChild(startButton);
		attachChild(points);
		attachChild(highscore);
		attachChild(humans);
		attachChild(fires);
		
		pauseButInv = new Rectangle(cam.getWidth()-80, 0, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
			    	  //Log.i("Click", "Down");
			    
	    			  activity.mCurrentScene.detachChild(ship.sprite);
	    			  activity.mCurrentScene.detachChild(person.sprite2);
			    	  if (ship.sprite.getParent()!=scene)
			    	  {
			    		scene.attachChild(ship.sprite);
			  		  	
			    	  }
			    	  
			    	  if (person.sprite2.getParent()!=scene)
			    	  {
			    	
			  		  	scene.attachChild(person.sprite2);
			    	  }
			  		  activity.setCurrentScene(scene);
			      }

	    			return true;
	    		}};
	    
	    Text B1 = new Text(pauseButInv.getX()+20,pauseButInv.getY()+20, activity.start, "B", activity.getVertexBufferObjectManager());	
	   
	    registerTouchArea(pauseButInv);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(pauseButInv);
	    attachChild(B1);
		
	    interfaceBut = new Rectangle(10, cam.getHeight()-90, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
			    	  //Log.i("Click", "Down");
			    
	    			  activity.mCurrentScene.detachChild(ship.sprite);
	    			  activity.mCurrentScene.detachChild(person.sprite2);
			    	  
			  		// BaseActivity.getSharedInstance().loadBackSprite("gfx/test_theme/def");
			  		/*ship.reloadShip();
			  		person.reloadPerson();*/
			  		//scene.loadSprites();
			  		//scene.reset();
			  		scene.attachChild(ship.sprite);
			  		scene.attachChild(person.sprite2);
			 		/* GameScene.getSharedInstance().loadSprites();*/
			  		  activity.setCurrentScene(new BackChooserScene());
			      }

	    			return true;
	    		}};
	    
	    Text T1 = new Text(interfaceBut.getX()+20,interfaceBut.getY()+20, activity.start, "T", activity.getVertexBufferObjectManager());	
	    
	    registerTouchArea(interfaceBut);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(interfaceBut);
	    attachChild(T1);
	    
	    achieveBut = new Rectangle(100, cam.getHeight()-90, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
			    	  //Log.i("Click", "Down");
			    
	    			  activity.mCurrentScene.detachChild(ship.sprite);
	    			  activity.mCurrentScene.detachChild(person.sprite2);
			    	  
			  		// BaseActivity.getSharedInstance().loadBackSprite("gfx/test_theme/def");
			  		/*ship.reloadShip();
			  		person.reloadPerson();*/
			  		//scene.loadSprites();
			  		//scene.reset();
			  		scene.attachChild(ship.sprite);
			  		scene.attachChild(person.sprite2);
			 		/* GameScene.getSharedInstance().loadSprites();*/
			  		  activity.setCurrentScene(new AllAchievementsScene());
			      }

	    			return true;
	    		}};
	    
	    Text A1 = new Text(achieveBut.getX()+20,achieveBut.getY()+20, activity.start, "A", activity.getVertexBufferObjectManager());	
	    
	    registerTouchArea(achieveBut);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(achieveBut);
	    attachChild(A1);
	    
}
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}}
