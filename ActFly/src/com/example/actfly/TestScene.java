package com.example.actfly;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.graphics.Color;

public class TestScene extends Scene{
	BaseActivity activity;
	public Ship ship;
	public Person person;
	public Rectangle rect1;
	public Rectangle rect2;
	public Rectangle rect3;
	public Rectangle rect4;
	public Rectangle rect5;
	public Rectangle rect6;
	public Rectangle rect7;
	public Rectangle rect8;
	public Rectangle rect9;
	GameScene scene = (GameScene) GameScene.getSharedInstance();
	EnemyLayer enemy = EnemyLayer.getSharedInstance();
	
	
	TestScene()
	{
		setBackground(new Background(1f, 1f, 1f));
		activity = BaseActivity.getSharedInstance();
		ship = Ship.getSharedInstance();
		person = Person.getSharedInstance();
		rect1 = new Rectangle(2f,	2f,		796f,	97f, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		rect2 = new Rectangle(2f,	101f,	497f,	90f, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		rect3 = new Rectangle(2f,	193f,	497f,	90f, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		rect4 = new Rectangle(2f,	285f,	497f,	90f, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		rect5 = new Rectangle(501f,	101f,	297f,	136f, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		rect6 = new Rectangle(501f,	239f,	297f,	136f, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		rect7 = new Rectangle(2f,	377f,	297f,	101f, BaseActivity.getSharedInstance().getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
			    	  //Log.i("Click", "Down");
			    
	    			activity.mCurrentScene.detachChild(ship.sprite);
	    			activity.mCurrentScene.detachChild(person.sprite2);
			   		scene.attachChild(ship.sprite);
			  		scene.attachChild(person.sprite2);
			 	    activity.setCurrentScene(new BackChooserScene());
			      }
	    			return true;
	    		}};
	    registerTouchArea(rect7);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(rect7);
	  
		
		
		rect8 = new Rectangle(301f,	377f,	297f,	101f, BaseActivity.getSharedInstance().getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
			    	  activity.mCurrentScene.detachChild(ship.sprite);
	    			  activity.mCurrentScene.detachChild(person.sprite2);
	    			  scene.attachChild(ship.sprite);
			  		  scene.attachChild(person.sprite2);
			 		  activity.setCurrentScene(new AllAchievementsScene());
			      }

	    			return true;
	    		}};
	    registerTouchArea(rect8);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(rect8);
	  	
		
		rect9 = new Rectangle(600f,	377f,	198f,	101f, BaseActivity.getSharedInstance().getVertexBufferObjectManager()){
	    	@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	    		if (pSceneTouchEvent.isActionDown()) {
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
	    
	    registerTouchArea(rect9);
	    setTouchAreaBindingOnActionDownEnabled(true);
	    attachChild(rect9);
	 
		
		
		
		rect1.setColor(0.37f, 0.87f, 0.51f);
		rect2.setColor(0.37f, 0.87f, 0.51f);
		rect3.setColor(0.37f, 0.87f, 0.51f);
		rect4.setColor(0.37f, 0.87f, 0.51f);
		rect5.setColor(0.37f, 0.87f, 0.51f);
		rect6.setColor(0.37f, 0.87f, 0.51f);
		rect7.setColor(0.37f, 0.87f, 0.51f);
		rect8.setColor(0.37f, 0.87f, 0.51f);
		rect9.setColor(0.37f, 0.87f, 0.51f);
		attachChild(rect1);
		attachChild(rect2);
		attachChild(rect3);
		attachChild(rect4);
		attachChild(rect5);
		attachChild(rect6);
		/*attachChild(rect7);
		attachChild(rect8);
		attachChild(rect9);*/
		Text text1 = new Text(rect1.getX()+10, rect1.getY()+10, activity.pauseFont, String.valueOf("PAUSE"), activity.getVertexBufferObjectManager());
		Text text2 = new Text(rect2.getX()+10, rect2.getY()+10, activity.pauseFont, String.valueOf("Score"), activity.getVertexBufferObjectManager());
		Text text3 = new Text(rect3.getX()+10, rect3.getY()+10, activity.pauseFont, String.valueOf("Fires"), activity.getVertexBufferObjectManager());
		Text text4 = new Text(rect4.getX()+10, rect4.getY()+10, activity.pauseFont, String.valueOf("Humans"), activity.getVertexBufferObjectManager());
		Text text5 = new Text(rect5.getX()+10, rect5.getY()+10, activity.pauseFont, String.valueOf("Record"), activity.getVertexBufferObjectManager());
		Text text6 = new Text(rect6.getX()+10, rect6.getY()+10, activity.pauseFont, String.valueOf("Theme points"), activity.getVertexBufferObjectManager());
		Text text7 = new Text(rect7.getX()+10, rect7.getY()+10, activity.pauseFont, String.valueOf("Themes"), activity.getVertexBufferObjectManager());
		Text text8 = new Text(rect8.getX()+10, rect8.getY()+10, activity.pauseFont, String.valueOf("Acheivements"), activity.getVertexBufferObjectManager());
		Text text9 = new Text(rect9.getX()+10, rect9.getY()+10, activity.pauseFont, String.valueOf("Back >"), activity.getVertexBufferObjectManager());
		attachChild(text1);
		attachChild(text2);
		attachChild(text3);
		attachChild(text4);
		attachChild(text5);
		attachChild(text6);
		attachChild(text7);
		attachChild(text8);
		attachChild(text9);
	}
	
	
	


}
