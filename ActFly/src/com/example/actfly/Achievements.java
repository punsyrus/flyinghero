package com.example.actfly;

import org.andengine.entity.scene.Scene;

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
	
	public void add_points_theme(int points, String theme)
	{
		Editor editorTheme = activity.curTheme.edit();
		editorTheme.putInt("points_"+theme, activity.curTheme.getInt("points_"+activity.curTheme.getString("Theme", "0"),0)+points);
		editorTheme.apply();
	}
	
	public void unlockTheme_1()
	{
		if (activity.curTheme.getInt(String.valueOf(activity.themesList.get(0)), 0)>100)
		{
			Log.i("Achieved_points", String.valueOf(activity.curTheme.getInt("balls_theme", 0)));
			Editor editor = activity.Achievements.edit();
			editor.putInt(String.valueOf(activity.themesList.get(1)), 1);
			editor.apply();
			Log.i("Achieved", "1");
			
			activity.runOnUiThread(new Runnable() {
				  public void run() {
					  
						
						LayoutInflater inflater = activity.getLayoutInflater();
						View layout = inflater.inflate(R.layout.custom_layout,
								(ViewGroup) activity.findViewById(R.id.toast_layout));
						TextView achT = (TextView) layout.findViewById(R.id.text);
						achT.setText("You unlocked " + String.valueOf(activity.themesList.get(1)));
						ImageView achV = (ImageView) layout.findViewById(R.id.image);
						achV.setImageResource(R.drawable.fruits_ic);
						Log.i("view1!11", String.valueOf(achV+ " " + achT));
						Toast toast = new Toast(activity); 
						toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
						toast.setDuration(Toast.LENGTH_LONG);
						
						toast.setView(layout); 
						toast.show(); 
						
					  //Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show();
					 
				  }
				});
			
			
		}
	}
	
	public void unlockTheme_2()
	{
		if ((activity.curTheme.getInt(String.valueOf(activity.themesList.get(0)), 0)>200) &&
		   (activity.curTheme.getInt(String.valueOf(activity.themesList.get(1)), 0)>200))
		{
			Editor editor = activity.Achievements.edit();
			editor.putInt(String.valueOf(activity.themesList.get(2)), 1);
			editor.apply();
			Log.i("Achieved", "2");
			activity.runOnUiThread(new Runnable() {
				  public void run() {
					  
						
						LayoutInflater inflater = activity.getLayoutInflater();
						View layout = inflater.inflate(R.layout.custom_layout,
								(ViewGroup) activity.findViewById(R.id.toast_layout));
						TextView achT = (TextView) layout.findViewById(R.id.text);
						achT.setText("You unlocked " + String.valueOf(activity.themesList.get(2)));
						ImageView achV = (ImageView) layout.findViewById(R.id.image);
						achV.setImageResource(R.drawable.neon_ic);
						Log.i("view1!11", String.valueOf(achV+ " " + achT));
						Toast toast = new Toast(activity); 
						toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
						toast.setDuration(Toast.LENGTH_LONG);
						
						toast.setView(layout); 
						toast.show(); 
						
					  //Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show();
					 
				  }
				});
		}
	}
	
	public void unlockTheme_3()
	{
		if ((activity.curTheme.getInt(String.valueOf(activity.themesList.get(2)), 0)>300) &&
		   (activity.curTheme.getInt(String.valueOf(activity.themesList.get(1)), 0)>400))
		{
			Editor editor = activity.Achievements.edit();
			editor.putInt(String.valueOf(activity.themesList.get(3)), 1);
			editor.apply();
			Log.i("Achieved", "3");
			activity.runOnUiThread(new Runnable() {
				  public void run() {
					  
						
						LayoutInflater inflater = activity.getLayoutInflater();
						View layout = inflater.inflate(R.layout.custom_layout,
								(ViewGroup) activity.findViewById(R.id.toast_layout));
						TextView achT = (TextView) layout.findViewById(R.id.text);
						achT.setText("You unlocked " + String.valueOf(activity.themesList.get(3)));
						ImageView achV = (ImageView) layout.findViewById(R.id.image);
						achV.setImageResource(R.drawable.ocean_ic);
						Log.i("view1!11", String.valueOf(achV+ " " + achT));
						Toast toast = new Toast(activity); 
						toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
						toast.setDuration(Toast.LENGTH_LONG);
						
						toast.setView(layout); 
						toast.show(); 
						
					  //Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show();
					 
				  }
				});
		}
	}
	
	public void unlockTheme_4()
	{
		if ((activity.curTheme.getInt(String.valueOf(activity.themesList.get(2)), 0)>500) &&
		   (activity.curTheme.getInt(String.valueOf(activity.themesList.get(3)), 0)>500))
		{
			Editor editor = activity.Achievements.edit();
			editor.putInt(String.valueOf(activity.themesList.get(4)), 1);
			editor.apply();
			Log.i("Achieved", "4");
			activity.runOnUiThread(new Runnable() {
				  public void run() {
					  
						
						LayoutInflater inflater = activity.getLayoutInflater();
						View layout = inflater.inflate(R.layout.custom_layout,
								(ViewGroup) activity.findViewById(R.id.toast_layout));
						TextView achT = (TextView) layout.findViewById(R.id.text);
						achT.setText("You unlocked " + String.valueOf(activity.themesList.get(4)));
						ImageView achV = (ImageView) layout.findViewById(R.id.image);
						achV.setImageResource(R.drawable.fires_ic);
						Log.i("view1!11", String.valueOf(achV+ " " + achT));
						Toast toast = new Toast(activity); 
						toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
						toast.setDuration(Toast.LENGTH_LONG);
						
						toast.setView(layout); 
						toast.show(); 
						
					  //Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show();
					 
				  }
				});
		}
	}
	
	public void unlockBoost_1()
	{
		if ((activity.curTheme.getInt(String.valueOf(activity.themesList.get(1)), 0)>1000) &&
		   (activity.curTheme.getInt(String.valueOf(activity.themesList.get(2)), 0)>1000))
		{
			Editor editor = activity.Achievements.edit();
			editor.putInt(String.valueOf(activity.themesList.get(4)), 1);
			editor.apply();
			Log.i("Achieved", "5");
			activity.runOnUiThread(new Runnable() {
				  public void run() {
					  
						
						LayoutInflater inflater = activity.getLayoutInflater();
						View layout = inflater.inflate(R.layout.custom_layout,
								(ViewGroup) activity.findViewById(R.id.toast_layout));
						TextView achT = (TextView) layout.findViewById(R.id.text);
						achT.setText("You unlocked more tools");
						ImageView achV = (ImageView) layout.findViewById(R.id.image);
						achV.setImageResource(R.drawable.fires_ic);
						Log.i("view1!11", String.valueOf(achV+ " " + achT));
						Toast toast = new Toast(activity); 
						toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); 
						toast.setDuration(Toast.LENGTH_LONG);
						
						//toast.setView(layout); 
						toast.show(); 
						
					  //Toast.makeText(activity, "1", Toast.LENGTH_SHORT).show();
					 
				  }
				});
		}
	}
	

}

