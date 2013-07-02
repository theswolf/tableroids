package com.september.tableroids.utils;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Display;
import android.view.Surface;

public class GraphicsUtils {

	/**
	 * @param args
	 */
	
	public static int ONEPERCENTHEIGHT;
	public static int ONEPERCENTWIDTH;
	
	public static int[] getScreenSize(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		
		ONEPERCENTHEIGHT = getOnePInPercent(display.getHeight(),1);
		ONEPERCENTWIDTH =  getOnePInPercent(display.getWidth(),1);
		
		return new int[]{display.getWidth(), display.getHeight()};
	}
	
	public void loadResource(Activity activity,String resourceName,int width,int height) {
		
		int partSize;
		int entitySize;
		
		

		AssetManager manager = activity.getAssets();

		Bitmap bitmap = loadImage(manager, resourceName, width, height, true,1);
		setBitmapResources(resourceName.hashCode(), bitmap);


	}

	private static Bitmap loadImage(AssetManager manager, String fileName, int width, int height, boolean filter, int resize) {
		try {
			
			if(width > 0 && height > 0) {
				return Bitmap.createScaledBitmap(BitmapFactory.decodeStream(manager.open(fileName)), width, height, filter);
			}
			
			else {
				Bitmap original = BitmapFactory.decodeStream(manager.open(fileName));
				int w = original.getWidth()*resize;
				int h = original.getHeight()*resize;
				
				return Bitmap.createScaledBitmap(original, w, h, filter);
			}
			
			
			//return scaleImage(Bitmap.createScaledBitmap(BitmapFactory.decodeStream(manager.open(fileName)), width, height, filter),resize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void setBitmapResources(int id,Bitmap resource) {
		Updater.getInstance().getResources().append(id, resource);
	}
	

	public static Bitmap getBitmapResources(int id,String name, Activity activity) {
		
		Bitmap bmp = Updater.getInstance().getResources().get(id);
		
		if(bmp == null) {
			AssetManager manager = activity.getAssets();
			try {
				bmp = BitmapFactory.decodeStream (manager.open(name));
				setBitmapResources(id, bmp);
				return bmp;
			} catch (IOException e) {
				
			}
		}
		
		return bmp;
		
	}
	
	public static int getOnePInPercent(int value,int scaleSize) {
		//1:100=x:380
		return (value*scaleSize)/100;
	}

}
