package com.example.actfly;

import java.util.Iterator;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
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
import android.view.MotionEvent;
import android.widget.Toast;

public class IntroScene extends Scene implements IOnSceneTouchListener{
	BaseActivity activity;
	public Ship ship;
	public Person person;
	public Achievements ach = Achievements.getSharedInstance();
	GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
	EnemyLayer enemy = EnemyLayer.getSharedInstance();
	
	IntroScene()
	{
		Camera cam = BaseActivity.getSharedInstance().mCamera;
		activity = BaseActivity.getSharedInstance();
		setBackground(new Background(0.09804f, 0.6274f, 0));
		ship = Ship.getSharedInstance();
		person = Person.getSharedInstance();
		scene.detachChild(ship.sprite);
		scene.detachChild(person.sprite2);
		attachChild(ship.sprite);
		attachChild(person.sprite2);
		
		ach.add_points(scene.points, ach.curTheme.getString("Theme", "0"));
		Log.i("EndScene", String.valueOf(scene.points + " " + ach.curTheme.getString("Theme", "0")));
		if (activity.curTheme.getInt(String.valueOf(activity.themesList.get(1)), 0)==0) ach.unlockTheme_1();
		if (activity.curTheme.getInt(String.valueOf(activity.themesList.get(2)), 0)==0) ach.unlockTheme_2();
		if (activity.curTheme.getInt(String.valueOf(activity.themesList.get(3)), 0)==0) ach.unlockTheme_3();
		if (activity.curTheme.getInt(String.valueOf(activity.themesList.get(4)), 0)==0) ach.unlockTheme_4();
		
		setOnSceneTouchListener(new IOnSceneTouchListener(){
		    @Override public boolean onSceneTouchEvent(    final Scene pScene,    final TouchEvent pSceneTouchEvent){
		      if (pSceneTouchEvent.isActionDown()) {
		    	  //Log.i("Click", "Down");
		    
		    	  detachChild(ship.sprite);
		  		  detachChild(person.sprite2);
		  		  detachChildren();
		    	  scene.attachChild(ship.sprite);
		  		  scene.attachChild(person.sprite2);
		  		  activity.setCurrentScene(scene);
		  		  
		  		 scene.detachChild(scene.tfires);
		  		 scene.detachChild(scene.tpoints);
		  		 scene.detachChild(scene.thumans);
				 scene.tpoints = new Text(0, 0, scene.act.gamePoints, String.valueOf(scene.points), scene.act.getVertexBufferObjectManager());
				 scene.tpoints.setPosition(10, 10);
				 scene.thumans = new Text(0, 0, scene.act.gamePoints, String.valueOf(scene.humansC) + "/5" , scene.act.getVertexBufferObjectManager());
				 scene.thumans.setPosition(scene.mCamera.getWidth()/3,10);
				 scene.tfires = new Text(0, 0, scene.act.gamePoints, String.valueOf(scene.fires) + "/" + String.valueOf(EnemyLayer.getSharedInstance().enemyCount) , scene.act.getVertexBufferObjectManager());
				 scene.tfires.setPosition(scene.mCamera.getWidth()-scene.mCamera.getWidth()/3,10);
				 scene.attachChild(scene.tfires);
				 scene.attachChild(scene.tpoints);
				 scene.attachChild(scene.thumans);
				
		      }
		      return true;
		    }
		  }
		);

}
	
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}}
