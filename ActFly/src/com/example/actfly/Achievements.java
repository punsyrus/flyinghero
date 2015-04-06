package com.example.actfly;

import org.andengine.entity.scene.Scene;

import com.example.actfly.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;



public class Achievements{
	
	BaseActivity activity = BaseActivity.getSharedInstance();
	public SharedPreferences Achieve;
	public SharedPreferences curTheme;
	public static Achievements instance = new Achievements();
	
	Achievements()
	{
		instance=this;
        Achieve = activity.Achievements;
        curTheme = activity.curTheme;
	}
	
	public static Achievements getSharedInstance() {
	    return instance;
	}
	
	public boolean checkPoints_200(int points)
	{
		
		if (points>=200) 
			{
			Log.i("Achived","checkPoints_200");
			Editor editor = Achieve.edit();
			editor.putInt("checkPoints_200", 1);
			editor.apply();
			return true;
			}
		else return false;
	}
	
	public boolean checkPoints_500(int points)
	{
		
		if (points>=500) 
		{
			Log.i("Achived","checkPoints_500");
			Editor editor = Achieve.edit();
			editor.putInt("checkPoints_500", 1);
			editor.apply();
			return true;
		}
		else return false;
	}
	
	public boolean checkPoints_1000(int points)
	{
		
		if (points>=1000) 
		{
			Log.i("Achived","checkPoints_1000");
			Editor editor = Achieve.edit();
			editor.putInt("checkPoints_1000", 1);
			editor.apply();
			return true;
		}
		else return false;
	}
	
	public boolean checkPoints_1500(int points)
	{
		
		if (points>=1500) 
		{
			Log.i("Achived","checkPoints_1500");
			Editor editor = Achieve.edit();
			editor.putInt("checkPoints_1500", 1);
			editor.apply();
			return true;
		}
		else return false;
	}
	
	public boolean checkPoints_3000(int points)
	{
		
		if (points>=3000) 
		{
			Log.i("Achived","checkPoints_3000");
			Editor editor = Achieve.edit();
			editor.putInt("checkPoints_3000", 1);
			editor.apply();
			return true;
		}
		else return false;
	}
	
	public boolean checkPoints_5000(int points)
	{
		
		if (points>=5000) 
		{
			Log.i("Achived","checkPoints_5000");
			Editor editor = Achieve.edit();
			editor.putInt("checkPoints_5000", 1);
			editor.apply();
			return true;
		}
		else return false;
	}

	public boolean checkDoubleHit(int hits)
	{
		
		if (hits>=2) 
		{
			Log.i("Achived","checkDoubleHit");
			Editor editor = Achieve.edit();
			editor.putInt("checkDoubleHit", 1);
			editor.apply();
			return true;
		}
		else return false;
	}
	
	public boolean checkTripleHit(int hits)
	{
		
		if (hits>=3) 
		{
			Log.i("Achived","checkTripleHit");
			Editor editor = Achieve.edit();
			editor.putInt("checkTripleHit", 1);
			editor.apply();
			return true;
		}
		else return false;
	}
	
	public boolean checkAllHumansLeft(int humans)
	{
		
		if (humans==0) 
		{
			Log.i("Achived","checkAllHumansLeft");
			Editor editor = Achieve.edit();
			editor.putInt("checkAllHumansLeft", 1);
			editor.apply();
			return true;
		}
		else return false;
	}
	
	public boolean checkAllFiresLeft(int fires, int allFires)
	{
		
		if (fires==allFires) 
		{
			Log.i("Achived","checkAllFiresLeft");
			Editor editor = Achieve.edit();
			editor.putInt("checkAllFiresLeft", 1);
			editor.apply();
			return true;
		}
		else return false;
	}
	
	public boolean checkHumansOneLeft(int humans)
	{
		
		if (humans==4) 
		{
			Log.i("Achived","checkHumansOneLeft");
			Editor editor = Achieve.edit();
			editor.putInt("checkHumansOneLeft", 1);
			editor.apply();
			return true;
		}
		else return false;
	}
	
	public boolean checkFiresOneLeft(int fires)
	{
		
		if (fires==1) 
		{
			Log.i("Achived","checkFiresOneLeft");
			Editor editor = Achieve.edit();
			editor.putInt("checkFiresOneLeft", 1);
			editor.apply();
			return true;
		}
		else return false;
	}


}

