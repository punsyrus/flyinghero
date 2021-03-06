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

public class PauseScene extends Scene{
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
	public Sprite enemy_1Sprite;
	public Sprite humanSprite;
	GameScene scene = (GameScene) GameScene.getSharedInstance();
	EnemyLayer enemy = EnemyLayer.getSharedInstance();
	
	
	PauseScene()
	{
		setBackground(new Background(0.16f, 0.7374f, 0.94f));
		activity = BaseActivity.getSharedInstance();
		ship = Ship.getSharedInstance();
		person = Person.getSharedInstance();
		scene.detachChild(ship.sprite);
		scene.detachChild(person.sprite2);
		
		enemy_1Sprite = new Sprite(0, 0, activity.enemy_1Region, activity.getVertexBufferObjectManager());
		humanSprite = new Sprite(0, 0, activity.humanRegion, activity.getVertexBufferObjectManager());
		
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
	    			ship.sprite.setAlpha(1);
	    			person.sprite2.setAlpha(1);
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
	    			  ship.sprite.setAlpha(1);
	    			  person.sprite2.setAlpha(1);
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
	    			  ship.sprite.setAlpha(1);
	    			  person.sprite2.setAlpha(1);
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
	 
		rect1.setColor(0.64f, 0.81f, 0.81f);
		rect2.setColor(0.64f, 0.81f, 0.81f);
		rect3.setColor(0.64f, 0.81f, 0.81f);
		rect4.setColor(0.64f, 0.81f, 0.81f);
		rect5.setColor(0.64f, 0.81f, 0.81f);
		rect6.setColor(0.64f, 0.81f, 0.81f);
		rect7.setColor(0.64f, 0.81f, 0.81f);
		rect8.setColor(0.64f, 0.81f, 0.81f);
		rect9.setColor(0.64f, 0.81f, 0.81f);
		attachChild(rect1);
		attachChild(rect2);
		attachChild(rect3);
		attachChild(rect4);
		attachChild(rect5);
		attachChild(rect6);
		Text text1 = new Text(rect1.getX()+10, rect1.getY()+10, activity.pauseFontBig, String.valueOf("PAUSE"), activity.getVertexBufferObjectManager());
		Text text2 = new Text(rect2.getX()+10, rect2.getY()+10, activity.pauseFont, String.valueOf("Score"), activity.getVertexBufferObjectManager());
		Text text3 = new Text(rect3.getX()+10, rect3.getY()+10, activity.pauseFont, String.valueOf("Fires"), activity.getVertexBufferObjectManager());
		Text text4 = new Text(rect4.getX()+10, rect4.getY()+10, activity.pauseFont, String.valueOf("Humans"), activity.getVertexBufferObjectManager());
		Text text5 = new Text(rect5.getX()+10, rect5.getY()+10, activity.pauseFont, String.valueOf("Record"), activity.getVertexBufferObjectManager());
		Text text6 = new Text(rect6.getX()+10, rect6.getY()+10, activity.pauseFont, String.valueOf("Theme points"), activity.getVertexBufferObjectManager());
		Text text7 = new Text(rect7.getX()+10, rect7.getY()+10, activity.pauseFontBig, String.valueOf("Change Theme"), activity.getVertexBufferObjectManager());
		Text text8 = new Text(rect8.getX()+10, rect8.getY()+10, activity.pauseFontBig, String.valueOf("Acheivements"), activity.getVertexBufferObjectManager());
		Text text9 = new Text(rect9.getX()+10, rect9.getY()+10, activity.pauseFontBig, String.valueOf("Back >"), activity.getVertexBufferObjectManager());
		text1.setPosition(rect1.getX()+rect1.getWidth()/2-text1.getWidth()/2, rect1.getY()+rect1.getHeight()/2-text1.getHeight()/2);
		text7.setPosition(rect7.getX()+rect7.getWidth()/2-text7.getWidth()/2, rect7.getY()+rect7.getHeight()/2-text7.getHeight()/2);
		text8.setPosition(rect8.getX()+rect8.getWidth()/2-text8.getWidth()/2, rect8.getY()+rect8.getHeight()/2-text8.getHeight()/2);
		text9.setPosition(rect9.getX()+rect9.getWidth()/2-text9.getWidth()/2, rect9.getY()+rect9.getHeight()/2-text9.getHeight()/2);
		Text points = new Text(0, 0, activity.pauseFontPoints, String.valueOf(scene.points), activity.getVertexBufferObjectManager());
		Text highscore = new Text(0,0, activity.pauseFontPoints, String.valueOf(activity.Records.getInt("HighScore", 0)), activity.getVertexBufferObjectManager());
		Text themescore = new Text(0,0, activity.pauseFontPoints, String.valueOf(activity.curTheme.getInt("points_"+activity.curTheme.getString("Theme", "0"),0)), activity.getVertexBufferObjectManager());
		
		Text humans = new Text(0, 0, activity.pauseFontPoints, String.valueOf(scene.allHumans), activity.getVertexBufferObjectManager());
		Text fires = new Text(0,0, activity.pauseFontPoints, String.valueOf(scene.allFires), activity.getVertexBufferObjectManager());	
		
		
		points.setPosition(rect2.getX()+rect2.getWidth()/2-points.getWidth()/2+20, rect2.getY()+rect2.getHeight()/2-points.getHeight()/2);
		highscore.setPosition(rect5.getX()+rect5.getWidth()/2-highscore.getWidth()/2, rect5.getY()+rect5.getHeight()/2-highscore.getHeight()/2+15);
		themescore.setPosition(rect6.getX()+rect6.getWidth()/2-themescore.getWidth()/2, rect6.getY()+rect6.getHeight()/2-themescore.getHeight()/2+15);
		
		humans.setPosition(rect3.getX()+rect3.getWidth()/2-humans.getWidth()/2+20, rect3.getY()+rect3.getHeight()/2-humans.getHeight()/2);
		fires.setPosition(rect4.getX()+rect4.getWidth()/2-fires.getWidth()/2+20, rect4.getY()+rect4.getHeight()/2-fires.getHeight()/2);
		enemy_1Sprite.setScale(1.4f);
		humanSprite.setScale(1.4f);
		enemy_1Sprite.setPosition(rect4.getX()+20, rect4.getY()+rect4.getHeight()/2-enemy_1Sprite.getHeight()/2);
		humanSprite.setPosition(rect3.getX()+20, rect3.getY()+rect3.getHeight()/2-humanSprite.getHeight()/2);
		
		
		attachChild(text1);
		attachChild(text2);
		//attachChild(text3);
		//attachChild(text4);
		attachChild(text5);
		attachChild(text6);
		attachChild(text7);
		attachChild(text8);
		attachChild(text9);
		attachChild(points);
		attachChild(highscore);
		attachChild(humans);
		attachChild(fires);
		attachChild(themescore);
		attachChild(enemy_1Sprite);
		attachChild(humanSprite);
		ship.sprite.setAlpha(175);
		person.sprite2.setAlpha(175);
		attachChild(ship.sprite);
		attachChild(person.sprite2);
	}
	
	
	


}
