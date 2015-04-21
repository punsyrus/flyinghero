package com.example.actfly;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class EnemyLayer extends Entity{
	
	public static LinkedList enemies;
	public static LinkedList humans;
	public static EnemyLayer instance;
	public static int enemyCount;
	public static int basicCount;
	Body bodyFire;
	static int eId=0;
	static int hId=0;
	static int MAX_HP =2;
	
	public EnemyLayer(int x) {
		
		setVisible(true);
		//setPosition(50, 30);
		 
		MoveXModifier movRight = new MoveXModifier(1, 50, 120);
		MoveXModifier movLeft = new MoveXModifier(1, 120, 50);
		MoveYModifier moveDown = new MoveYModifier(1, 30, 100);
		MoveYModifier moveUp = new MoveYModifier(1, 100, 30);
		 
		/*registerEntityModifier(new LoopEntityModifier(
		    new SequenceEntityModifier(movRight, moveDown, movLeft, moveUp)));*/
	    enemies = new LinkedList();
	    humans = new LinkedList();
	    instance = this;
	    enemyCount = x;
	    basicCount = x;
	    restart();
	    
	}
	
	public static void purgeAndRestart() {
	    instance.purge();
	    instance.restart();
	   
	}
	
	public static void goDefault() {
	    instance.purge();
	   // instance.restart();
	    enemies = new LinkedList();
	    humans = new LinkedList();
	    eId=0;
	    hId=0;
		MAX_HP =2;
		enemyCount=basicCount;
	}
	
	@Override
    public void onDetached() {
    purge();
    super.onDetached();
}
	 
	public void restart(){
		
		for (int i = 0; i < enemyCount; i++) {
		    Enemy e = new Enemy(); 
		    e.setHP(MAX_HP);
		    eId++;
		    float finalPosX = (i % 6) * 4 * e.spriteF.getWidth();
		    float finalPosY = ((int) (i / 6)) * e.spriteF.getHeight() * 2;
		 
		    Random r = new Random();
		    int xF,yF;
		    xF = 50+r.nextInt((BaseActivity.CAMERA_WIDTH-100)/15)*15;
		    yF = 50+r.nextInt(BaseActivity.CAMERA_HEIGHT/20)*10;
		    //Log.i("loca", String.valueOf(xF + " " + yF));
		   //Log.i("loca", String.valueOf(xF + " " + yF));
		    e.spriteF.setPosition(xF,yF);
		    e.spriteF.setVisible(false);
		    e.spriteRF.setPosition(xF-e.spriteRF.getWidth()/2,yF-e.spriteRF.getHeight()/2);
		    e.spriteRF.setVisible(false);
		    e.spriteHp.setPosition(xF-e.spriteHp.getWidth()/2,yF-e.spriteHp.getHeight()/2);
		    e.spriteHp.setVisible(false);
		    e.spriteHp3.setPosition(xF-e.spriteHp3.getWidth()/2,yF-e.spriteHp3.getHeight()/2);
		    e.spriteHp3.setVisible(false);
		 
		    attachChild(e.spriteF);
		    sortChildren();
		  if (e.MAX_HEALTH==3) 
		    	{
		    		attachChild(e.spriteHp3); 
		    		sortChildren(); 
		    		e.spriteHp3.setVisible(true);
		    	} 
		    else 
		    	e.spriteHp.setVisible(true);
		    attachChild(e.spriteRF); sortChildren();
		    attachChild(e.spriteHp); sortChildren(true);
		    
		    final FixtureDef FIRE = PhysicsFactory.createFixtureDef(9000.0f,
					0.9f, 0.5f);
		    bodyFire = PhysicsFactory.createBoxBody(BaseActivity.getSharedInstance().mPhysicsWorld, e.spriteF,
					BodyType.StaticBody, FIRE);
		    
		    bodyFire.setUserData("Enemy"+String.valueOf(eId));
		    e.setId("Enemy"+String.valueOf(eId));
		    enemies.add(e);
		   
		 
		   
		}
		
		for (int i = 0; i < 5; i++) {
		    
		    hId++;
		   
		 
		    Random r = new Random();
		    int xF,yF;
		    xF = 50+r.nextInt((BaseActivity.CAMERA_WIDTH-100)/15)*15;
		    yF = 50+r.nextInt(BaseActivity.CAMERA_HEIGHT/20)*10;
		    //Rectangle spriteH = new Rectangle(0, 0, 30, 30,BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		    Sprite spriteH = GameScene.getSharedInstance().loadhumanSprite();
		    //spriteH.setColor(0.09904f, 0.8574f, 0.9986f);
		    spriteH.setPosition(xF,yF);
		    spriteH.setVisible(true);
		   
		    spriteH.setUserData("human"+String.valueOf(hId));
		    //spriteH.setTag(hId);
		    attachChild(spriteH);
		    sortChildren(true);
		    
		    final FixtureDef FIRE = PhysicsFactory.createFixtureDef(9000.0f,
					0.9f, 0.5f);
		    bodyFire = PhysicsFactory.createBoxBody(BaseActivity.getSharedInstance().mPhysicsWorld, spriteH,
					BodyType.StaticBody, FIRE);
		    
		    bodyFire.setUserData("human"+String.valueOf(hId));
		   
		   humans.add(spriteH);
		   
		 
		   
		}
		
for (int i = 0; i < 3; i++) {
		    
		   
		   
		 
		    Random r = new Random();
		    int xF,yF;
		    xF = 50+r.nextInt((BaseActivity.CAMERA_WIDTH-100)/15)*15;
		    yF = 50+r.nextInt(BaseActivity.CAMERA_HEIGHT/20)*10;
		    //Rectangle spriteB = new Rectangle(0, 0, 15, 15,BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		    Sprite spriteB = GameScene.getSharedInstance().loadblockSprite();
		   // spriteB.setColor(0.44f, 0.23f, 0.02f);
		    spriteB.setPosition(xF,yF);
		    spriteB.setVisible(true);
		   
		 
		    attachChild(spriteB);
		    sortChildren(true);
		    
		    final FixtureDef FIRE = PhysicsFactory.createFixtureDef(9000.0f,
					0.9f, 0.5f);
		    bodyFire = PhysicsFactory.createBoxBody(BaseActivity.getSharedInstance().mPhysicsWorld, spriteB,
					BodyType.StaticBody, FIRE);
		    
		    bodyFire.setUserData("wall");
		   
		   
		   
		 
		   
		}
		
	}
	
	public void addOne()
	{
		 Enemy e = new Enemy(); 
		    e.setHP(MAX_HP);
		    eId++;
		  	 
		    Random r = new Random();
		    int xF,yF;
		    xF = 50+r.nextInt((BaseActivity.CAMERA_WIDTH-100)/15)*15;
		    yF = 50+r.nextInt(BaseActivity.CAMERA_HEIGHT/20)*10;
		    //Log.i("loca", String.valueOf(xF + " " + yF));
		  // Log.i("loca", String.valueOf(xF + " " + yF));
		    e.spriteF.setPosition(xF,yF);
		    e.spriteF.setVisible(false);
		    e.spriteRF.setPosition(xF-e.spriteRF.getWidth()/2,yF-e.spriteRF.getHeight()/2);
		    e.spriteRF.setVisible(false);
		    e.spriteHp.setPosition(xF-e.spriteHp.getWidth()/2,yF-e.spriteHp.getHeight()/2);
		    e.spriteHp.setVisible(false);
		    e.spriteHp3.setPosition(xF-e.spriteHp3.getWidth()/2,yF-e.spriteHp3.getHeight()/2);
		    e.spriteHp3.setVisible(false);
		 
		    attachChild(e.spriteF);
		    attachChild(e.spriteHp); sortChildren();
		    attachChild(e.spriteRF); sortChildren(true);
		    sortChildren(true);
		    if (e.MAX_HEALTH==3) 
		    	{
		    		attachChild(e.spriteHp3); 
		    		sortChildren(); 
		    		e.spriteHp3.setVisible(true);
		    	} 
		    else 
		    	e.spriteHp.setVisible(true);
		   
		    
		    
		    
		    final FixtureDef FIRE = PhysicsFactory.createFixtureDef(9000.0f,
					0.5f, 0.3f);
		    bodyFire = PhysicsFactory.createBoxBody(BaseActivity.getSharedInstance().mPhysicsWorld, e.spriteF,
					BodyType.StaticBody, FIRE);
		    
		    bodyFire.setUserData("Enemy"+String.valueOf(eId));
		    e.setId("Enemy"+String.valueOf(eId));
		    enemies.add(e);
	}
	 
	public static EnemyLayer getSharedInstance() {
	    return instance;
	}
	
	public static boolean isEmpty() {
	    if (instance.enemies.size() == 0)
	        return true;
	    return false;
	}
	
	public static Iterator getIterator() {
		return instance.enemies.iterator();
		}
	
	public static Iterator getIteratorHumans() {
		return instance.humans.iterator();
		}
	
	public void purge() {
	    detachChildren();
	    for (Object e : enemies) {
	        EnemyPool.sharedEnemyPool().recyclePoolItem(e);
	    }
	    enemies.clear();
	    eId=0;
	    
	    for (Object e : humans) {
	        EnemyPool.sharedEnemyPool().recyclePoolItem(e);
	    }
	    humans.clear();
	    hId=0;
	}

}
