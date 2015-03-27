package com.example.actfly;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseCubicIn;
import org.andengine.util.modifier.ease.EaseCubicOut;
import org.andengine.util.modifier.ease.EaseElasticIn;
import org.andengine.util.modifier.ease.EaseElasticOut;
import org.andengine.util.modifier.ease.EaseExponentialIn;
import org.andengine.util.modifier.ease.EaseExponentialOut;

import android.util.Log;

public class Person {
	public Sprite sprite2;
public static Person instance;
Camera mCamera;
boolean moveable = true;
boolean shootable = true;
boolean started = false;
public Ship ship;
float accelerometerSpeedXinternal;
float top = 100;
float bot = 30;
int mSpeedMax = 7;
int mSpeedMin = 2;
int mSpeed;
float sSpeed = 1.2f;
int sRange=100;


public static Person getSharedInstance() {
    if (instance == null)
        instance = new Person();
    return instance;
}

private Person() {
	
    //sprite2 = new Rectangle(0, 0, 40, 40, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
	sprite2 = GameScene.getSharedInstance().loadpersonSprite();
    mCamera = BaseActivity.getSharedInstance().mCamera;
    sprite2.setPosition(mCamera.getWidth() / 2 - sprite2.getWidth() / 2,
        mCamera.getHeight() - sprite2.getHeight() - bot);
    //sprite2.setColor(0.51f, 0.99f, 0.29f);
}

void checkPerson()
{
	
ship = Ship.getSharedInstance();
//Log.e("pos", String.valueOf(shootable + " " + moveable));
if ((sprite2.getY()>=(mCamera.getHeight() - sprite2.getHeight() - (bot+1)))
	&& 
	((sprite2.getX()+sprite2.getWidth()/2)>(ship.sprite.getX())) 
	&&
	((sprite2.getX()+sprite2.getWidth()/2)<(ship.sprite.getX()+ship.sprite.getWidth()))
	)
{
	
moveable=true;
shootable=true;
//if (started == true) shoot();
}
else
{
moveable=false;
shootable=false;
}
}

public void goDefaultPerson()
{
	moveable = true;
	shootable = true;
	started = false;
	top = 100;
	bot = 30;
	mSpeedMax = 7;
	mSpeedMin = 2;
	sSpeed = 1.2f;
	sRange=100;
	//sprite2 = new Rectangle(0, 0, 40, 40, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
	sprite2 = GameScene.getSharedInstance().loadpersonSprite();

	    mCamera = BaseActivity.getSharedInstance().mCamera;
	    sprite2.setPosition(mCamera.getWidth() / 2 - sprite2.getWidth() / 2,
	        mCamera.getHeight() - sprite2.getHeight() - bot);
	    //sprite2.setColor(0.51f, 0.99f, 0.29f);
}


public void movePerson(float accelerometerSpeedX) {
	if (!moveable)
        return;
	accelerometerSpeedXinternal = sign(accelerometerSpeedX)*mSpeed;
	checkPerson();
    
 
    if (Math.abs(accelerometerSpeedX)<5) mSpeed=mSpeedMin;
    if (Math.abs(accelerometerSpeedX)>=5) mSpeed=mSpeedMax;
    
    if (accelerometerSpeedX != 0) {
        int lL = 0;
        int rL = (int) (mCamera.getWidth() - (int) sprite2.getWidth());
        float newX;
 
        // Calculate New X,Y Coordinates within Limits
        if (sprite2.getX() >= lL)
            newX = sprite2.getX() + sign(accelerometerSpeedX)*mSpeed;
        else
            newX = lL;
        if (newX <= rL)
            newX = sprite2.getX() + sign(accelerometerSpeedX)*mSpeed;
        else
            newX = rL;
 
        // Double Check That New X,Y Coordinates are within Limits
        if (newX < lL)
            newX = lL;
        else if (newX > rL)
newX = rL;
        sprite2.setPosition(newX, sprite2.getY());
    }
}

private static int sign(float x) {
	   if (x > 0)
	      return 1;
	   else if (x < 0) 
	      return -1;
	   return 0;
	}

public void shoot(float point) {
	started=true;
	float xMod=0, yMod=0;
	if (!shootable)
        return;
	xMod = point-(BaseActivity.getSharedInstance().mCamera.getWidth()/2);
    GameScene scene = (GameScene) BaseActivity.getSharedInstance().mCurrentScene;
   
    scene.body1.applyForce(1950, xMod*2, 0, 0);
   
}

public void reloadPerson()
{
	BaseActivity.getSharedInstance().mCurrentScene.detachChild(sprite2);
	sprite2 = GameScene.getSharedInstance().loadpersonSprite();
	/* sprite2.setPosition(mCamera.getWidth() / 2 - sprite2.getWidth() / 2,
		        mCamera.getHeight() - sprite2.getHeight() - bot);*/
	 BaseActivity.getSharedInstance().mCurrentScene.attachChild(sprite2);
}


}
