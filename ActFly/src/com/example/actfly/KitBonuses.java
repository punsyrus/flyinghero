package com.example.actfly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;

import com.badlogic.gdx.physics.box2d.Body;
import com.example.actfly.BaseActivity;


















import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class KitBonuses{
	
	static BaseActivity activity = BaseActivity.getSharedInstance();
	static GameScene scene = GameScene.getSharedInstance();
	static Person person = Person.getSharedInstance();
	public SharedPreferences Achieve;
	public SharedPreferences curTheme;
	public static SharedPreferences Kits;
	
	public static void make_boom()
	{  Log.i("how boom start", "go go");
	Kits =activity.Kits;
		if(Kits.getInt("boomKit", 0)>0)
			{
	    int i=0; String toDel = "yo";
		ArrayList<Float> pl = new ArrayList(50);
		
		Iterator boIter = activity.mPhysicsWorld.getBodies();
		while (boIter.hasNext())
			{
				Body nBody=(Body) boIter.next();
				if (nBody!=null)
					if (nBody.getUserData()!=null)
						if (String.valueOf(nBody.getUserData()).contains("Enemy"))
							{   
								float bx = nBody.getPosition().x * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
								float by = nBody.getPosition().y * PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;
								float px = person.sprite2.getX()+person.sprite2.getWidth()/2;
								float py = person.sprite2.getY()+person.sprite2.getHeight()/2;
								float d=(float) Math.sqrt(Math.pow(bx-px,2)+Math.pow(by-py,2));
								
								pl.add(i, d);
								Log.i("How far", String.valueOf(nBody.getUserData()+ " " +d));
								if (i>0) 
									if (((Float)pl.get(i))<((Float)pl.get(i-1)))
										{
											toDel=String.valueOf(nBody.getUserData());
										}
								if (i==0)
									toDel=String.valueOf(nBody.getUserData());
								i++;
							}
			}
		Log.i("How far lowest", toDel);
		
		Iterator boIterDel = activity.mPhysicsWorld.getBodies();
		while (boIter.hasNext())
			{
				Body nBody=(Body) boIter.next();
				if (nBody!=null)
					if (nBody.getUserData()!=null)
						if (String.valueOf(nBody.getUserData()).contains(toDel))
							{   
								nBody.setUserData("Delete");
							}
				
			}
		
		EnemyLayer enemy = EnemyLayer.getSharedInstance();
		Enemy e1=null;
		Iterator enemies = enemy.enemies.iterator();
		while(enemies.hasNext())
		{
			Enemy nE = (Enemy) enemies.next();
			if (String.valueOf(nE.id).contains(toDel))
			{
				e1 = (Enemy) nE;
				
			}
		}
		if (e1!=null){
		
		e1.spriteF.detachSelf();
		e1.spriteRF.detachSelf();
		e1.spriteHp.detachSelf();
		e1.spriteHp3.detachSelf();
		e1.clean();
		enemy.enemies.remove(e1);
		}
		
       
		
		
		Editor editor = Kits.edit();
		
		editor.putInt("boomKit", Kits.getInt("boomKit", 0)-1);
		editor.apply();
		scene.updateKit();
		}
	}
	
	public void make_grab()
	{
		
	}
	

}

