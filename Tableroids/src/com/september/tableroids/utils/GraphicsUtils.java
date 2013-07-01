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
	public static int[] getScreenSize(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		return new int[]{display.getWidth(), display.getHeight()};
	}
	
	public void loadResource(Activity activity,String resourceName,int width,int height) {
		
		int partSize;
		int entitySize;
		
		

		AssetManager manager = activity.getAssets();

		Bitmap bitmap = loadImage(manager, "farback.gif", width, height, true,1);
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


	public static Bitmap getBitmap(String name, Activity activity) {
		AssetManager manager = activity.getAssets();
		try {
			return BitmapFactory.decodeStream (manager.open(name));
		} catch (IOException e) {
			return null;
		}
	}

}
