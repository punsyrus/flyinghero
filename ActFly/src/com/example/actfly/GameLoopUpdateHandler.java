package com.example.actfly;

import java.util.Iterator;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.text.Text;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GameLoopUpdateHandler implements IUpdateHandler {

	public Person person;
	public Ship ship;
	public EnemyLayer enemies;
	public int MAX_SPEED = 15; 
	public int MIN_SPEED = 2; 
	 
	public int cur_level=1;
	public int fires=0;
	public int humans=0;
	//public int humansColl=0;
	
	
	GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
	   //if (!scene.isPaused) 
	   { 
	    removeBody();
	    stopShip();
	
			checkFall();
	
	    limitSpeed();
	    checkRestart();} 
	   
	    //recalcPoints();
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	public void removeBody()
	{
		Iterator boIter = BaseActivity.getSharedInstance().mPhysicsWorld.getBodies();
		while (boIter.hasNext())
		{
			Body nBody=(Body) boIter.next();
			if (nBody!=null)
			if (nBody.getUserData()!=null)
			if (nBody.getUserData().equals("Delete"))
			{//Log.i("ContactDelete2", String.valueOf("Deleting..."));
				if (nBody!=null)
				{
					BaseActivity.getSharedInstance().mPhysicsWorld.destroyBody(nBody);
					nBody.setUserData(null);
					nBody=null;
					//fires++;
					break;
				}
			}
		}
	}
	
	public void removeWalls()
	{
		Iterator boIter = BaseActivity.getSharedInstance().mPhysicsWorld.getBodies();
		while (boIter.hasNext())
		{
			Body nBody=(Body) boIter.next();
			if (nBody!=null)
			if (nBody.getUserData()!=null)
			//if (nBody.getUserData().equals("wall"))
			if (!nBody.getUserData().equals("ship"))
			if (!nBody.getUserData().equals("person"))
			{Log.i("ContactDelete2", String.valueOf("Deleting..wall"));
				if (nBody!=null)
				{
					BaseActivity.getSharedInstance().mPhysicsWorld.destroyBody(nBody);
					nBody.setUserData("Delete");
					//nBody=null;
					
					//break;
				}
			}
		}
	}
	
	public void removeSprite()
	{
	//	scene.get
	}
	
	public void stopShip()
	{
		ship = Ship.getSharedInstance();
	    float xL,xR;
	    xL=ship.sprite.getX();
	    xR=ship.sprite.getX()+ship.sprite.getWidth();
	    
	    if (xL<0) 
	    {
	    	scene.body2.setLinearVelocity(3, 0);
	    }
	    
	    if (xR>BaseActivity.getSharedInstance().mCamera.getWidth()) 
	    {
	    	scene.body2.setLinearVelocity(-3, 0);
	    }
	    	    
	}
	
	public void checkFall()
	{
		person = Person.getSharedInstance();
	    float y;
	    y=person.sprite2.getY()+person.sprite2.getHeight();
	   // GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
	    if (y>(BaseActivity.getSharedInstance().mCamera.getHeight()-30)) 
	    {
	    	scene.body1.setLinearVelocity(0, 0);
	    	MAX_SPEED=15;
	    	MIN_SPEED=2;
	    	cur_level=1;
	    	EnemyLayer.getSharedInstance().MAX_HP=2;
	    	BaseActivity.getSharedInstance().setCurrentScene(new EndScene());
	    	  
	    	
	               
	             
	    }
	        
	}
	
	
	
	public void limitSpeed()
	{
		person = Person.getSharedInstance();
	  //  GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
	    Vector2 velocity = scene.body1.getLinearVelocity();
	    
	    float speed = velocity.len();
	   // Log.i("Velo", String.valueOf(speed));
	    if (speed > MAX_SPEED) {
	        scene.body1.setLinearVelocity(velocity.mul(MAX_SPEED / speed));
	    }
	    if (scene.started)
	    if (speed < MIN_SPEED) {
	     //   scene.body1.setLinearVelocity(velocity.mul(MIN_SPEED / speed));
	    }
	    	    
	}
	
	public void checkRestart()
	{
		enemies = EnemyLayer.getSharedInstance();
		if ((enemies.isEmpty()) || (scene.humansC==5))
	    	{
				cur_level++;
				makeHarder();
	    		enemies.purgeAndRestart();
	    		scene.humansC=0;
	    	}
	}
	
	public void recalcPoints()
	{
		 scene.detachChild(scene.tpoints);
		 scene.tpoints = new Text(0, 0, scene.act.gamePoints, String.valueOf(scene.points), scene.act.getVertexBufferObjectManager());
		 scene.tpoints.setPosition(10, 10);
		 scene.attachChild(scene.tpoints);
		 
		
	}
	
	public void makeHarder()
	{  //BaseActivity.getSharedInstance().loadBackSprite("gfx/test_theme/def");
		//scene.loadSprites();
		//Ship.getSharedInstance().reloadShip();
		//Person.getSharedInstance().reloadPerson();
		Text levelIntro=null;
		Text speedIntro=null;
		Text firesIntro=null;
		Text strongIntro=null;
		Text platformIntro=null;
		Text LevComp=null;
		removeWalls();
		LevComp = new Text(0, 0, BaseActivity.getSharedInstance().gamePoints, String.valueOf("Level " + (cur_level-1) +  " Complete!"), BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		LevComp.setPosition(10, 10);
		levelIntro = new Text(0, 0, BaseActivity.getSharedInstance().gamePoints, String.valueOf("Go to level " + cur_level + "."), BaseActivity.getSharedInstance().getVertexBufferObjectManager());
    	levelIntro.setPosition(10, 190);
				if (2<=cur_level&&cur_level<=12) 
					{
					enemies.enemyCount++;
			    	firesIntro = new Text(0, 0, BaseActivity.getSharedInstance().gamePoints, String.valueOf("Number of fires increased!"), BaseActivity.getSharedInstance().getVertexBufferObjectManager());
			    	firesIntro.setPosition(10, 70);
					}
				else firesIntro = new Text(0, 0, BaseActivity.getSharedInstance().gamePoints, String.valueOf(""), BaseActivity.getSharedInstance().getVertexBufferObjectManager());
				
				if (2<=cur_level&&cur_level<=13) 
					{
					MAX_SPEED+=1;
					speedIntro = new Text(0, 0, BaseActivity.getSharedInstance().gamePoints, String.valueOf("Flying speed increased!"), BaseActivity.getSharedInstance().getVertexBufferObjectManager());
			    	speedIntro.setPosition(10, 130);
					}
				else speedIntro = new Text(0, 0, BaseActivity.getSharedInstance().gamePoints, String.valueOf(""), BaseActivity.getSharedInstance().getVertexBufferObjectManager());
				
				if (4<=cur_level&&cur_level<=8) MIN_SPEED+=1;
				if (6==cur_level) 
					{
					EnemyLayer.getSharedInstance().MAX_HP=3; 
					strongIntro = new Text(0, 0, BaseActivity.getSharedInstance().gamePoints, String.valueOf("Fires are stronger now!"), BaseActivity.getSharedInstance().getVertexBufferObjectManager());
			    	strongIntro.setPosition(10, 190);
			    	levelIntro.setPosition(10, 250);
					}
				else strongIntro = new Text(0, 0, BaseActivity.getSharedInstance().gamePoints, String.valueOf(""), BaseActivity.getSharedInstance().getVertexBufferObjectManager());
				if (15==cur_level) MAX_SPEED+=1;
				
			if (8==cur_level) 
					{						
						scene.body2.setUserData("Delete");
						scene.makeSmall();
						platformIntro = new Text(0, 0, BaseActivity.getSharedInstance().gamePoints, String.valueOf("Platform is smaller now!"), BaseActivity.getSharedInstance().getVertexBufferObjectManager());
						platformIntro.setPosition(10, 190);
				    	levelIntro.setPosition(10, 250);
					}
			else platformIntro = new Text(0, 0, BaseActivity.getSharedInstance().gamePoints, String.valueOf(""), BaseActivity.getSharedInstance().getVertexBufferObjectManager());
	    	scene.points+=50;
	    	scene.fires=0;
	    	scene.humansC=0;
	    	scene.dFire=0;
	    	Text level = new Text(0, 0, BaseActivity.getSharedInstance().gamePoints, String.valueOf(cur_level), BaseActivity.getSharedInstance().getVertexBufferObjectManager());
	    	level.setPosition(BaseActivity.getSharedInstance().mCamera.getWidth()/2-level.getWidth()/2, 10);
	    	
	    	//scene.attachChild(level);
	    	recalcPoints();
	    	scene.body2.setLinearVelocity(0, 0);
	    	scene.body1.setLinearVelocity(0, 0);
	    	BaseActivity.getSharedInstance().setCurrentScene(new IntroScene());
	    	BaseActivity.getSharedInstance().mCurrentScene.attachChild(speedIntro);
	    	BaseActivity.getSharedInstance().mCurrentScene.attachChild(firesIntro);
	    	BaseActivity.getSharedInstance().mCurrentScene.attachChild(LevComp);
	    	BaseActivity.getSharedInstance().mCurrentScene.attachChild(strongIntro);
	    	BaseActivity.getSharedInstance().mCurrentScene.attachChild(platformIntro);
	    	BaseActivity.getSharedInstance().mCurrentScene.attachChild(levelIntro);
	    	
	    	
	}
	

}
