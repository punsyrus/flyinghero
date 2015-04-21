package com.example.actfly;

import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.R;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class Enemy {
	public Rectangle spriteF;
	public Sprite spriteRF;
	public Sprite spriteHp;
	public Sprite spriteHp3;
	public int hp;
	public String id;
	Thread sThr;
	
	//the max health for each enemy
	public int MAX_HEALTH = EnemyLayer.getSharedInstance().MAX_HP;
	
	public Enemy() {
		/*sThr =  new Thread(){
            public void run(){
	            
                BaseActivity.getSharedInstance().mp.start();
            
        }};*/
	    spriteF = new Rectangle(0, 0, 15, 15,BaseActivity.getSharedInstance().getVertexBufferObjectManager());
	    spriteF.setColor(0.09904f, 0.8574f, 0.1786f);
	   // spriteRF = new Rectangle(0, 0, 30, 30,BaseActivity.getSharedInstance().getVertexBufferObjectManager());
	    spriteRF = GameScene.getSharedInstance().loadenemy_2Sprite();
	   // spriteRF.setColor(1.0f, 0.6f, 0.1f);
	    //spriteHp = new Rectangle(0, 0, 20, 20,BaseActivity.getSharedInstance().getVertexBufferObjectManager());
	    spriteHp = GameScene.getSharedInstance().loadenemy_1Sprite();
	    //spriteHp.setColor(1.0f, 0.1f, 0.1f);
	    //spriteHp3 = new Rectangle(0, 0, 35, 35,BaseActivity.getSharedInstance().getVertexBufferObjectManager());
	    spriteHp3 = GameScene.getSharedInstance().loadenemy_3Sprite();
	   // spriteHp3.setColor(1.0f, 0.0f, 0.0f);
	    
	    spriteHp.setZIndex(3);
	    spriteRF.setZIndex(2);
	    spriteHp3.setZIndex(1);
	    
	    
	    init();
	}
	 
	// method for initializing the Enemy object , used by the constructor and
	// the EnemyPool class
	public void init() {
	    hp = MAX_HEALTH;
	    spriteF.setVisible(false);
		
	   /* spriteHp.registerEntityModifier(new LoopEntityModifier(
		        new RotationModifier(1, 360, 0)));
	    spriteRF.registerEntityModifier(new LoopEntityModifier(
		        new RotationModifier(1, 0, 360)));
	    spriteHp3.registerEntityModifier(new LoopEntityModifier(
		        new RotationModifier(1, 360, 0)));*/
	}
	
	public void clean() {
	    spriteF.clearEntityModifiers();
	    spriteF.clearUpdateHandlers();
	    spriteRF.clearEntityModifiers();
	    spriteRF.clearUpdateHandlers();
	    spriteHp.clearEntityModifiers();
	    spriteHp.clearUpdateHandlers();
	}
	
	public void setId(String newid) {
	    id = newid;
	}
	
	public void setHP(int HP) {
	    MAX_HEALTH = HP;
	}
	
	public void playHit() {
		   AudioManager audioManager = (AudioManager) BaseActivity.getSharedInstance().getSystemService(BaseActivity.getSharedInstance().AUDIO_SERVICE);
           float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
           float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
           float leftVolume = curVolume / maxVolume;
           float rightVolume = curVolume / maxVolume;
           int priority = 1;
           int no_loop = 0;
           float normal_playback_rate = 1f;
           BaseActivity.getSharedInstance().mStreamId = BaseActivity.getSharedInstance().mSoundPool.play(BaseActivity.getSharedInstance().mSoundId, leftVolume, rightVolume, priority, no_loop,
                   normal_playback_rate);   
	}
	 
	// method for applying hit and checking if enemy died or not
	// returns false if enemy died
	public boolean gotHit() {
	    synchronized (this) {
	    	if (BaseActivity.getSharedInstance().mCurrentScene.getUserData().equals("GameScene"))
	    	{GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
	    	scene.points+=10;
	    	
	    	
	        hp--;
	        if (hp==1) 
	        	{
	        		spriteHp.setVisible(false);
	        		spriteRF.setVisible(true);
	        	}
	        if (hp==2) 
	        	{
	        		spriteHp3.setVisible(false);
	        		spriteHp.setVisible(true);
	        	}
	        scene.detachChild(scene.tpoints);
			 scene.tpoints = new Text(0, 0, scene.act.gamePoints, String.valueOf(scene.points), scene.act.getVertexBufferObjectManager());
			 scene.tpoints.setPosition(10, 10);
			 scene.attachChild(scene.tpoints);
			scene.updateKit();
			//sThr.start();
		 //playHit(); 
		        
		   
			 
	        if (hp <= 0)
	            return false;
	        else
	            return true;}
	    	return false;
	   }
	}

}
