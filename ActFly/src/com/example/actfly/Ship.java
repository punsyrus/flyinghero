package com.example.actfly;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;

public class Ship {
    public Sprite sprite;
    public static Ship instance;
    Camera mCamera;
    boolean moveable = true;
    int mSpeedMax = 7;
    int mSpeedMin = 2;
    int mSpeed;
    
   
 
    public static Ship getSharedInstance() {
        if (instance == null)
            instance = new Ship();
        return instance;
    }
 
    private Ship() {
    	
        //sprite = new Rectangle(0, 0, 180, 30, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
        sprite = GameScene.getSharedInstance().loadshipSprite();
        //sprite.setColor(1.0f, 0.99f, 0.7f);
        mCamera = BaseActivity.getSharedInstance().mCamera;
        sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2,
            mCamera.getHeight() - sprite.getHeight() - 10);
    }
    
    private static int sign(float x) {
 	   if (x > 0)
 	      return 1;
 	   else if (x < 0) 
 	      return -1;
 	   return 0;
 	}
    
    public void makeSamll()
    {
    	//sprite = new Rectangle(0, 0, 120, 30, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
    	sprite = GameScene.getSharedInstance().loadship_smSprite();
    	  mCamera = BaseActivity.getSharedInstance().mCamera;
          sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2,
              mCamera.getHeight() - sprite.getHeight() - 10);
    }
    
    public void moveShip(float accelerometerSpeedX) {
        if (!moveable)
            return;
        if (Math.abs(accelerometerSpeedX)<5) mSpeed=mSpeedMin;
        if (Math.abs(accelerometerSpeedX)>=5) mSpeed=mSpeedMax;
     
        if (accelerometerSpeedX != 0) {
            int lL = 0;
            int rL = (int) (mCamera.getWidth() - (int) sprite.getWidth());
            float newX;
     
            // Calculate New X,Y Coordinates within Limits
            if (sprite.getX() >= lL)
                newX = sprite.getX() + sign(accelerometerSpeedX)*mSpeed;
            else
                newX = lL;
            if (newX <= rL)
                newX = sprite.getX() + sign(accelerometerSpeedX)*mSpeed;
            else
                newX = rL;
     
            // Double Check That New X,Y Coordinates are within Limits
            if (newX < lL)
                newX = lL;
            else if (newX > rL)
    newX = rL;
            sprite.setPosition(newX, sprite.getY());
        }
    }
    
    public void slideShip(float touchXpoint) {
    	
    	GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
    	float impulse = -(scene.ship.getSharedInstance().sprite.getX()+(scene.ship.getSharedInstance().sprite.getWidth()/2)-touchXpoint)*5;
    	scene.body2.setLinearVelocity(impulse/32, 0);
      
    }
    
    public void reloadShip()
    {
    	BaseActivity.getSharedInstance().mCurrentScene.detachChild(sprite);
    	
		//shipRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.theme1Atlas, this, path+"_ship.png", 815, 250);
		
    	sprite = GameScene.getSharedInstance().loadshipSprite();
    	  /*mCamera = BaseActivity.getSharedInstance().mCamera;
          sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2,
              mCamera.getHeight() - sprite.getHeight() - 10);*/
          BaseActivity.getSharedInstance().mCurrentScene.attachChild(sprite);
          
    }
    
    public void goDefaultShip()
    {
    	/*sprite = new Rectangle(0, 0, 180, 30, BaseActivity.getSharedInstance()
                .getVertexBufferObjectManager());*/
    	 sprite = GameScene.getSharedInstance().loadshipSprite();
    	//sprite.setColor(1.0f, 0.99f, 0.7f);
            mCamera = BaseActivity.getSharedInstance().mCamera;
            sprite.setPosition(mCamera.getWidth() / 2 - sprite.getWidth() / 2,
                mCamera.getHeight() - sprite.getHeight() - 10);
            moveable = true;
    }
    
    
}