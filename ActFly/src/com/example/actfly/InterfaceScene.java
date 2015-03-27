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
import android.view.MotionEvent;
import android.widget.Toast;

public class InterfaceScene extends Scene implements IOnSceneTouchListener{
	BaseActivity activity;
	
	public Rectangle back;
	public Rectangle ship;
	public Rectangle person;
	public Rectangle fire;
	
	//GameScene scene = (GameScene) BaseActivity.getSharedInstance();
	EnemyLayer enemy = EnemyLayer.getSharedInstance();
	Camera cam = BaseActivity.getSharedInstance().mCamera;
	
	InterfaceScene()
	{
		Camera cam = BaseActivity.getSharedInstance().mCamera;
		activity = BaseActivity.getSharedInstance();
		setBackground(new Background(0.09804f, 0.6274f, 0));
		
		back = new Rectangle(10, 10, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			activity.setCurrentScene(new BackChooserScene());
			      }
	    			return true;
	    		}};
	    registerTouchArea(back);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(back);
	    
	    ship = new Rectangle(back.getX(), back.getY()+90, 80, 80, BaseActivity.getSharedInstance()
	            .getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
	    			activity.setCurrentScene(new PauseScene()); 			    
			      }

	    			return true;
	    		}};
	    
	    
	    registerTouchArea(ship);
	    //pauseButInv.setVisible(false);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(ship);
	    
	
}
	
	
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}}
