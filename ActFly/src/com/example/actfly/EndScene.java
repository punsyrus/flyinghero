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

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class EndScene extends Scene implements IOnSceneTouchListener{
	BaseActivity activity;
	public Ship ship;
	public Person person;
	public Rectangle firesBack;
	public Rectangle pointsBack;
	public Achievements ach = Achievements.getSharedInstance();
	GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
	EnemyLayer enemy = EnemyLayer.getSharedInstance();
	
	EndScene()
	{
		Camera cam = BaseActivity.getSharedInstance().mCamera;
		activity = BaseActivity.getSharedInstance();
		//ach = Achievements.getSharedInstance();
		setBackground(new Background(0.09804f, 0.6274f, 0));
		Log.i("high", String.valueOf(scene.points + " " + activity.Records.getInt("HighScore", 0)));
		if (scene.points>activity.Records.getInt("HighScore", 0))
		{
			Editor editor = activity.Records.edit();
			editor.putInt("HighScore", scene.points);
			editor.apply();
		}
		
		ach.add_points(scene.points, ach.curTheme.getString("Theme", "0"));
		Log.i("EndScene", String.valueOf(scene.points + " " + ach.curTheme.getString("Theme", "0")));
		ach.unlockTheme_1();
		ach.unlockTheme_2();
		ach.unlockTheme_3();
		ach.unlockTheme_4();
		//ach.unlockTheme_5();
		
		Text startButton = new Text(0,0, activity.start, "Click to try again!", activity.getVertexBufferObjectManager());
		Text points = new Text(0, 0, activity.endPoints, String.valueOf(scene.points), activity.getVertexBufferObjectManager());
		Text fires = new Text(0, 0, activity.endPoints, String.valueOf(scene.points), activity.getVertexBufferObjectManager());
		startButton.setPosition(cam.getWidth() / 2 - startButton.getWidth() / 2, cam.getHeight() / 2 - startButton.getHeight() / 2);
		points.setPosition(cam.getWidth() / 2 - points.getWidth() / 2, cam.getHeight() / 3 - startButton.getHeight()); 
		ship = Ship.getSharedInstance();
		person = Person.getSharedInstance();
		  firesBack = new Rectangle(0, 20, cam.getWidth()/2, 50, BaseActivity.getSharedInstance()
		            .getVertexBufferObjectManager());
		  pointsBack = new Rectangle(0, 85, cam.getWidth()/2, 50, BaseActivity.getSharedInstance()
		            .getVertexBufferObjectManager());
		scene.detachChild(ship.sprite);
		scene.detachChild(person.sprite2);
		//attachChild(firesBack);
		//attachChild(pointsBack);
		
		attachChild(ship.sprite);
		attachChild(person.sprite2);
		attachChild(startButton);
		attachChild(points);
		setOnSceneTouchListener(new IOnSceneTouchListener(){
		    @Override public boolean onSceneTouchEvent(    final Scene pScene,    final TouchEvent pSceneTouchEvent){
		      if (pSceneTouchEvent.isActionDown()) {
		    	  //Log.i("Click", "Down");
		    	  
		    	  //clearPhysicsWorld(BaseActivity.getSharedInstance().mPhysicsWorld);
		    	  removeEnemies();
		    	  scene.detachChildren();
		    	  scene.clearEntityModifiers();
		    	  scene.clearTouchAreas();
		    	  
		    	  enemy.goDefault();
		    	  //scene.clearUpdateHandlers();
		    	  scene.goDefaultScene();
		    	  activity.setCurrentScene(scene);
		      }
		      return true;
		    }
		  }
		);
		
		

}
	
	
	
	public void clearScene()
	{
		activity.runOnUpdateThread(new Runnable()
		{
			@Override
			public void run()
			{
				//onCleanScene();
				//cleanEntities();
				clearTouchAreas();
				clearUpdateHandlers();
				System.gc();
			}
		});
	}

	protected void clearPhysicsWorld(PhysicsWorld physicsWorld)
	{
		Iterator<Joint> allMyJoints = physicsWorld.getJoints();
		while (allMyJoints.hasNext())
		{
			try
			{
				final Joint myCurrentJoint = allMyJoints.next();
				physicsWorld.destroyJoint(myCurrentJoint);
			} 
			catch (Exception localException)
			{
				Debug.d("SPK - THE JOINT DOES NOT WANT TO DIE: " + localException);
			}
		}
		
		Iterator<Body> localIterator = physicsWorld.getBodies();
		while (true)
		{
			if (!localIterator.hasNext())
			{
				physicsWorld.clearForces();
				physicsWorld.clearPhysicsConnectors();
				physicsWorld.reset();
				physicsWorld.dispose();
				physicsWorld = null;
				return;
			}
			try
			{
				physicsWorld.destroyBody(localIterator.next());
			} 
			catch (Exception localException)
			{
				Debug.d("SPK - THE BODY DOES NOT WANT TO DIE: " + localException);
			}
		}
	}
	
	public void removeEnemies()
	{
		//Iterator enemies = EnemyLayer.getSharedInstance().getIterator();
		Iterator boIter = BaseActivity.getSharedInstance().mPhysicsWorld.getBodies();
		while (boIter.hasNext())
		{
			Body nBody=(Body) boIter.next();
			if (nBody!=null)
			if (nBody.getUserData()!=null)
			
			{//Log.i("ContactDelete2", String.valueOf("Deleting..."));
				if (nBody!=null)
				{
					nBody.setUserData("Delete");
				}
			}
		}
	}
			
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}}
