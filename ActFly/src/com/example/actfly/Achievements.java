package com.example.actfly;

import org.andengine.entity.scene.Scene;

import com.example.actfly.BaseActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;



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
	
	public void add_points(int points, String theme)
	{
		Editor editorTheme = activity.curTheme.edit();
		editorTheme.putInt(theme, activity.curTheme.getInt(activity.curTheme.getString("Theme", "0"),0)+points);
		editorTheme.apply();
	}
	
	public void unlockTheme_1()
	{
		if (activity.curTheme.getInt("balls_theme", 0)>100)
		{
			Log.i("Achieved_points", String.valueOf(activity.curTheme.getInt("balls_theme", 0)));
			Editor editor = activity.Achievements.edit();
			editor.putInt(String.valueOf(activity.themesList.get(1)), 1);
			editor.apply();
			Log.i("Achieved", "1");
			
			activity.runOnUiThread(new Runnable() {
				  public void run() {
					  Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show();
				  }
				});
			
			
		}
	}
	
	public void unlockTheme_2()
	{
		if ((activity.curTheme.getInt("balls_theme", 0)>200) &&
		   (activity.curTheme.getInt("fruit_theme", 0)>200))
		{
			Editor editor = activity.Achievements.edit();
			editor.putInt(String.valueOf(activity.themesList.get(2)), 1);
			editor.apply();
			Log.i("Achieved", "2");
			activity.runOnUiThread(new Runnable() {
				  public void run() {
					  Toast.makeText(activity, "2", Toast.LENGTH_SHORT).show();
				  }
				});
		}
	}
	
	public void unlockTheme_3()
	{
		if ((activity.curTheme.getInt("fruit_theme", 0)>300) &&
		   (activity.curTheme.getInt("neon_theme", 0)>400))
		{
			Editor editor = activity.Achievements.edit();
			editor.putInt(String.valueOf(activity.themesList.get(3)), 1);
			editor.apply();
			Log.i("Achieved", "3");
			activity.runOnUiThread(new Runnable() {
				  public void run() {
					  Toast.makeText(activity, "3", Toast.LENGTH_SHORT).show();
				  }
				});
		}
	}
	
	public void unlockTheme_4()
	{
		if ((activity.curTheme.getInt("ocean_theme", 0)>500) &&
		   (activity.curTheme.getInt("neon_theme", 0)>500))
		{
			Editor editor = activity.Achievements.edit();
			editor.putInt(String.valueOf(activity.themesList.get(4)), 1);
			editor.apply();
			Log.i("Achieved", "4");
			activity.runOnUiThread(new Runnable() {
				  public void run() {
					  Toast.makeText(activity, "4", Toast.LENGTH_SHORT).show();
				  }
				});
		}
	}
	

}

