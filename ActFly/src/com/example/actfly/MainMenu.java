package com.example.actfly;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.input.touch.TouchEvent;

public class MainMenu extends MenuScene implements IOnMenuItemClickListener{

	BaseActivity activity;
	final int MENU_START = 0;
	public Rectangle interfaceBut;
	public Rectangle achieveBut;
	
	MainMenu()
	{
		super(BaseActivity.getSharedInstance().mCamera);
		activity = BaseActivity.getSharedInstance();
		Camera cam = BaseActivity.getSharedInstance().mCamera;
		setBackground(new Background(0.16f, 0.7374f, 0.94f));
		
		Rectangle rect = new Rectangle(2, cam.getHeight()/2-50, 796, 80, BaseActivity.getSharedInstance().getVertexBufferObjectManager());
		//rect.setColor(0.37f, 0.87f, 0.51f);
		rect.setColor(0.64f, 0.81f, 0.81f);
		IMenuItem startButton = new TextMenuItem(MENU_START, activity.start, "START", activity.getVertexBufferObjectManager());
		startButton.setPosition(rect.getX()+rect.getWidth()/2-startButton.getWidth()/2, rect.getY()+rect.getHeight()/2-startButton.getHeight()/2);
		attachChild(rect);
		addMenuItem(startButton);
		 
		setOnMenuItemClickListener(this);
		
		/*  interfaceBut = new Rectangle(10, cam.getHeight()-90, 80, 80, BaseActivity.getSharedInstance()
		            .getVertexBufferObjectManager()){
		    	@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		    		if (pSceneTouchEvent.isActionDown()) {
				    	  //Log.i("Click", "Down");
				    
		    			
				  		  activity.setCurrentScene(new BackChooserScene());
				      }

		    			return true;
		    		}};
		    
		    
		    registerTouchArea(interfaceBut);
		    //pauseButInv.setVisible(false);
		    setTouchAreaBindingOnActionDownEnabled(true);
		    attachChild(interfaceBut);
		    
		    achieveBut = new Rectangle(100, cam.getHeight()-90, 80, 80, BaseActivity.getSharedInstance()
		            .getVertexBufferObjectManager()){
		    	@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		    		if (pSceneTouchEvent.isActionDown()) {
				    	  //Log.i("Click", "Down");
				    
		    		
				  		  activity.setCurrentScene(new AllAchievementsScene());
				      }

		    			return true;
		    		}};
		    
		    
		    registerTouchArea(achieveBut);
		    //pauseButInv.setVisible(false);
		    setTouchAreaBindingOnActionDownEnabled(true);
		    attachChild(achieveBut);*/
	}

	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1, float arg2, float arg3) {
	    switch (arg1.getID()) {
	        case MENU_START:
	            activity.setCurrentScene(new GameScene());
	            return true;
	            
	        default:
	            break;
	    }
	    return false;
	}
}
