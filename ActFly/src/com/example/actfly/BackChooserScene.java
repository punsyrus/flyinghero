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

import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

public class BackChooserScene extends Scene implements IOnSceneTouchListener{
	BaseActivity activity;
	
	public Rectangle back1;
	public Rectangle back2;
	public Rectangle back3;
	public Rectangle back4;
	public Rectangle back5;
	public Rectangle back6;
	public Rectangle back7;
	public Rectangle back8;
	public Rectangle back9;
	public Rectangle back10;
	
	
	GameScene Gscene = (GameScene) GameScene.getSharedInstance();
	EnemyLayer enemy = EnemyLayer.getSharedInstance();
	Camera cam = BaseActivity.getSharedInstance().mCamera;
	
	BackChooserScene()
	{
		Camera cam = BaseActivity.getSharedInstance().mCamera;
		activity = BaseActivity.getSharedInstance();
		setBackground(new Background(0.09804f, 0.6274f, 0));
		
		back1 = new Rectangle(10, 10, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			BaseActivity.getSharedInstance().loadBackSprite("gfx/def_graphics/def");
	    			activity.setCurrentScene(new PauseScene());
			      }
	    			return true;
	    		}};
	    registerTouchArea(back1);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(back1);
	    
	    back2 = new Rectangle(back1.getX()+90, back1.getY(), 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			BaseActivity.getSharedInstance().loadBackSprite("gfx/test_theme/def");
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back2);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("Ach_1", 0)==1) attachChild(back2);
	    
	    back3 = new Rectangle(back2.getX()+90, back2.getY(), 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back3);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("Ach_2", 0)==1) attachChild(back3);
	    
	    back4 = new Rectangle(back3.getX()+90, back3.getY(), 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(back4);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    if (activity.Achievements.getInt("Ach_3", 0)==1) attachChild(back4);
	    
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
	    if (activity.Achievements.getInt("Ach_4", 0)==1)attachChild(back5);
	    
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
	    if (activity.Achievements.getInt("Ach_5", 0)==1) attachChild(back6);
	    
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
	    if (activity.Achievements.getInt("Ach_6", 0)==1) attachChild(back7);
	    
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
	    if (activity.Achievements.getInt("Ach_7", 0)==1) attachChild(back8);
	    
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
	    if (activity.Achievements.getInt("Ach_8", 0)==1) attachChild(back9);
	    
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
	    if (activity.Achievements.getInt("Ach_9", 0)==1) attachChild(back10);
	    
	
}
	
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}}
