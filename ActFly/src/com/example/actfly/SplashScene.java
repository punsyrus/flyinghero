package com.example.actfly;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveByModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.ITextureRegion;

public class SplashScene extends Scene{
	BaseActivity activity;
	
	
	
	SplashScene()
	{
		setBackground(new Background(0.09804f, 0.6274f, 0));
		activity = BaseActivity.getSharedInstance();
		Text title1 = new Text(0, 0, activity.mFont, "MyFirst", activity.getVertexBufferObjectManager());
		Text title2 = new Text(0, 0, activity.mFont, "AppEngine", activity.getVertexBufferObjectManager());
		title1.setPosition(-title1.getWidth(), activity.mCamera.getHeight() / 2);
		title2.setPosition(activity.mCamera.getWidth(), activity.mCamera.getHeight() / 2);
		Sprite face = new Sprite(10, 10, activity.mFaceTextureRegion, activity.getVertexBufferObjectManager());
		attachChild(face);
		attachChild(title1);
		attachChild(title2);
		 
		float tX1=face.getX(),tX2=activity.mCamera.getWidth() / 2 - title1.getWidth()-10-face.getWidth()
			 ,tY1=face.getY(),tY2=activity.mCamera.getHeight() / 2
			 ,tX3=0;
		SequenceEntityModifier sequenceEntityModifier = new SequenceEntityModifier(
				new MoveModifier(3, tX1, tX2, tY1, tY2)
				//,new MoveModifier(3, tX2, tX3, tY2, tY2)
				);
		
		face.registerEntityModifier(sequenceEntityModifier);
		title1.registerEntityModifier(new MoveXModifier(1, title1.getX(), activity.mCamera.getWidth() / 2 - title1.getWidth()-10));
		title2.registerEntityModifier(new MoveXModifier(1, title2.getX(), activity.mCamera.getWidth() / 2 + 10));

		//activity.onCreateResources();
		loadResources();
	}
	
	public void loadResources()
	{
		DelayModifier dMod = new DelayModifier(4){
		    @Override
		    protected void onModifierFinished(IEntity pItem) {
		        activity.setCurrentScene(new GameScene());
		    }
		};
		registerEntityModifier(dMod);
	}
	


}
