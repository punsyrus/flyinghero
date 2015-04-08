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

public class AchieveDetailScene extends Scene implements IOnSceneTouchListener{
	BaseActivity activity;
	Rectangle pauseButInv;

	public Achievements ach = Achievements.getSharedInstance();
	//GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
	EnemyLayer enemy = EnemyLayer.getSharedInstance();
	
	AchieveDetailScene(int req)
	{
		Camera cam = BaseActivity.getSharedInstance().mCamera;
		activity = BaseActivity.getSharedInstance();
		setBackground(new Background(0.09804f, 0.6274f, 0));
		String require = "";
		if (req==1) require="Balls: 100 points";
		if (req==2) require="Balls: 200 points, Fruits: 200 points";
		if (req==3) require="Fruits: 300 points, Neon: 400 points";
		if (req==4) require="Neon: 500 points, Ocean: 500 points";
		Text fires = new Text(10,10, activity.gamePoints, require, activity.getVertexBufferObjectManager());	
		attachChild(fires);
		
		pauseButInv = new Rectangle(cam.getWidth()-90, cam.getHeight()-90, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
			    	  //Log.i("Click", "Down");
			    
	    			
			  		  activity.setCurrentScene(new AllAchievementsScene());
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(pauseButInv);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(pauseButInv);
	}
	
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}}
