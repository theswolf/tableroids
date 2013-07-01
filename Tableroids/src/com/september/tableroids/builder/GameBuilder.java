package com.september.tableroids.builder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.september.tableroids.consts.Constants;
import com.september.tableroids.model.elements.Square;
import com.september.tableroids.utils.GraphicsUtils;
import com.september.tableroids.utils.Updater;

public class GameBuilder {

	/**
	 * @param args
	 */
	public static void build(Activity activity) {
		Updater up = Updater.getInstance();
		int[] dim = GraphicsUtils.getScreenSize(activity);
//		int x = dim[0];
//		int y = dim[1];
		
		Bitmap.Config conf = Bitmap.Config.RGB_565; // see other conf types
		Bitmap bmp = Bitmap.createBitmap(GraphicsUtils.ONEPERCENTWIDTH*20, GraphicsUtils.ONEPERCENTWIDTH*20, conf);
		for(int x = 0; x < bmp.getWidth(); x++) {
			for(int y = 0; y < bmp.getHeight(); y++){
				int color = Color.YELLOW;
				if(x == 0 || y == 0 || x == bmp.getWidth() -1 || y == bmp.getHeight() -1) {
					color = Color.BLACK;
				}
				bmp.setPixel(x, y, color);
			}
		}
		
		GraphicsUtils.setBitmapResources(Constants.SQUARED_BITMAP, bmp);
		Square square = new Square(bmp, 5, (GraphicsUtils.ONEPERCENTWIDTH*20)+10, 0, 0, Constants.FPS, 1);
		
		Updater.getInstance().getSprites().add(square);
	}


}
