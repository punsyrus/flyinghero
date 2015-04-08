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
import org.andengine.entity.scene.background.SpriteBackground;
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

import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class AllAchievementsScene extends Scene implements IOnSceneTouchListener{
	BaseActivity activity;
	
	
	public Text notavail;
	public Rectangle interfaceBut;
	
	GameScene Gscene = (GameScene) GameScene.getSharedInstance();
	EnemyLayer enemy = EnemyLayer.getSharedInstance();
	Camera cam = BaseActivity.getSharedInstance().mCamera;
	
	AllAchievementsScene()
	{
		Camera cam = BaseActivity.getSharedInstance().mCamera;
		activity = BaseActivity.getSharedInstance();
		setBackground(new Background(0.09804f, 0.6274f, 0));
		
		int i=0, j=1, l=0;
		
		for (i=10; i<=800; i+=158)
		if (j<activity.themesList.size())
		{   
			final int tn=j;

		    //if (activity.Achievements.getInt(String.valueOf(activity.themesList.get(tn)), 0)==1) 
		    {
			Rectangle backrect = new Rectangle(i, 10, 148, 148, BaseActivity.getSharedInstance()
		            .getVertexBufferObjectManager()){
		    	@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		    		if (pSceneTouchEvent.isActionDown()) {
		    		/*	BaseActivity.getSharedInstance().loadBackSprite("gfx/"+activity.themesList.get(tn)+"/def");
		    			Editor editorTheme = activity.curTheme.edit();
		    			editorTheme.putString("Theme", String.valueOf(activity.themesList.get(tn)));
		    			editorTheme.apply();*/
		    			activity.setCurrentScene(new AchieveDetailScene(tn));
				      }
		    			return true;
		    		}};
		    
		    	registerTouchArea(backrect);
		    	backrect.setColor(0.15f, 0.15f, 0.15f);
		    	setTouchAreaBindingOnActionDownEnabled(true);
		    	attachChild(backrect);
		    	//Sprite backIcon = new Sprite(backrect.getX()+backrect.getWidth()/2-20, backrect.getY()+backrect.getHeight()/2-20, activity.loadIconSprite(String.valueOf("gfx/"+activity.themesList.get(tn)+"/def")), activity.getVertexBufferObjectManager());
		    	attachChild(new Sprite(backrect.getX()+backrect.getWidth()/2-20, backrect.getY()+backrect.getHeight()/2-20, activity.loadIconSprite(String.valueOf("gfx/"+activity.themesList.get(tn)+"/def"),j*40+1,l+1), activity.getVertexBufferObjectManager()));
		    	if (activity.Achievements.getInt(String.valueOf(activity.themesList.get(tn)), 0)==0)
		    	{
		    		attachChild(new Text(backrect.getX()+5, backrect.getY()+5, activity.gamePoints,"Not achieved",activity.getVertexBufferObjectManager()));
		    	}
		    }
				j++;
		}
		
		   interfaceBut = new Rectangle(cam.getWidth()-90, cam.getHeight()-90, 80, 80, BaseActivity.getSharedInstance()
		            .getVertexBufferObjectManager()){
		    	@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		    		if (pSceneTouchEvent.isActionDown()) {
				    	  //Log.i("Click", "Down");
				    
				  		  activity.setCurrentScene(new PauseScene());
				      }

		    			return true;
		    		}};
		    
		    Text B1 = new Text(interfaceBut.getX()+20,interfaceBut.getY()+20, activity.start, "B", activity.getVertexBufferObjectManager());	
		    registerTouchArea(interfaceBut);
		    //pauseButInv.setVisible(false);
		    setTouchAreaBindingOnActionDownEnabled(true);
		    attachChild(interfaceBut);
		    attachChild(B1);
		
		//notavail = new Text(0,0,activity.gamePoints,"?",BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		
	/*
		
		
		back1 = new Rectangle(10, 10, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			BaseActivity.getSharedInstance().loadBackSprite("gfx/balls_theme/def");
	    			
	    			activity.setCurrentScene(new PauseScene());
			      }
	    			return true;
	    		}};
	    		
	    registerTouchArea(back1);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(back1);
	    backT1 = new Sprite(25, 25, activity.personRegion, activity.getVertexBufferObjectManager());
		attachChild(backT1);
	    
	    
	    back2 = new Rectangle(back1.getX()+90, back1.getY(), 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			BaseActivity.getSharedInstance().loadBackSprite("gfx/fruit_theme/def");
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back2);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("fruit_theme", 0)==1) 
	    	attachChild(back2);
	    	 backT2 = new Sprite(back2.getX()+15, back2.getY()+15, activity.personRegion, activity.getVertexBufferObjectManager());
	 		attachChild(backT2);
	    
	    back3 = new Rectangle(back2.getX()+90, back2.getY(), 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			BaseActivity.getSharedInstance().loadBackSprite("gfx/neon_theme/def");
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back3);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("neon_theme", 0)==1) 
	    	attachChild(back3);
	    	backT3 = new Sprite(back3.getX()+15, back3.getY()+15, activity.personRegion, activity.getVertexBufferObjectManager());
	 		attachChild(backT3);
	    
	    back4 = new Rectangle(back3.getX()+90, back3.getY(), 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			BaseActivity.getSharedInstance().loadBackSprite("gfx/ocean_theme/def");
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back4);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("ocean_theme", 0)==1) 
	    	attachChild(back4);
	    	 backT4 = new Sprite(back4.getX()+15, back4.getY()+15, activity.personRegion, activity.getVertexBufferObjectManager());
		 		attachChild(backT4);
	    
	    back5 = new Rectangle(back4.getX()+90, back4.getY(), 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back5);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("Ach_4", 0)==1)
	    	attachChild(back5);
	    
	    back6 = new Rectangle(back1.getX(), back1.getY()+90, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back6);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("Ach_5", 0)==1) 
	    	attachChild(back6);
	    
	    back7 = new Rectangle(back2.getX(), back2.getY()+90, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back7);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("Ach_6", 0)==1) 
	    	attachChild(back7);
	    
	    back8 = new Rectangle(back3.getX(), back3.getY()+90, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back8);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("Ach_7", 0)==1) 
	    	attachChild(back8);
	    
	    back9 = new Rectangle(back4.getX(), back4.getY()+90, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back9);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("Ach_8", 0)==1) 
	    	attachChild(back9);
	    
	    back10 = new Rectangle(back5.getX(), back5.getY()+90, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back10);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("Ach_9", 0)==1) 
	    	attachChild(back10);
	    */
	
}
	
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}}
