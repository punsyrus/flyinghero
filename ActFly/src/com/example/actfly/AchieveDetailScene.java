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
	Rectangle rect9;

	public Achievements ach = Achievements.getSharedInstance();
	//GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
	EnemyLayer enemy = EnemyLayer.getSharedInstance();
	
	AchieveDetailScene(int req)
	{
		Camera cam = BaseActivity.getSharedInstance().mCamera;
		int cond=0;
		activity = BaseActivity.getSharedInstance();
		setBackground(new Background(0.09804f, 0.6274f, 0));
		String[] require = new String[10];
		if (req==1) {cond=1; require[1]="Balls: 100 points";}
		if (req==2) {cond=2; require[1]="Balls: 200 points"; require[2]="Fruits: 200 points";}
		if (req==3) {cond=2; require[1]="Fruits: 300 points"; require[2]="Neon: 400 points";}
		if (req==4) {cond=2; require[1]="Neon: 500 points"; require[2]="Ocean: 500 points";}
		for (int i=1; i<=cond; i++)
		{
		Rectangle rect = new Rectangle(2, 2*i+80*(i-1), 796, 80, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		rect.setColor(0.64f, 0.81f, 0.81f);
		Text fires = new Text(10,10, activity.pauseFontBig, require[i], activity.getVertexBufferObjectManager());
		fires.setPosition(rect.getX()+rect.getWidth()/2-fires.getWidth()/2, rect.getY()+rect.getHeight()/2-fires.getHeight()/2);
		attachChild(rect);
		attachChild(fires);
		}
		rect9 = new Rectangle(600f,	377f,	198f,	101f, BaseActivity.getSharedInstance().getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			  activity.setCurrentScene(new AllAchievementsScene());
			      }
	    			return true;
	    		}};
	    
	    registerTouchArea(rect9);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(rect9);
rect9.setColor(0.64f, 0.81f, 0.81f);
Text text9 = new Text(rect9.getX()+10, rect9.getY()+10, activity.pauseFontBig, String.valueOf("Back >"), activity.getVertexBufferObjectManager());
text9.setPosition(rect9.getX()+rect9.getWidth()/2-text9.getWidth()/2, rect9.getY()+rect9.getHeight()/2-text9.getHeight()/2);
attachChild(text9);
	    
	    
	 
	}
	
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}}
