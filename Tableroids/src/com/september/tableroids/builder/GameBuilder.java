package com.september.tableroids.builder;

import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.september.tableroids.consts.Constants;
import com.september.tableroids.model.elements.Smile;
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
				int color = Color.TRANSPARENT;
//				if(x == 0 || y == 0 || x == bmp.getWidth() -1 || y == bmp.getHeight() -1) {
//					color = Color.TRANSPARENT;
//				}
				bmp.setPixel(x, y, color);
			}
		}
		
		GraphicsUtils.setBitmapResources(Constants.SQUARED_BITMAP, bmp);
		Square square = new Square(bmp, (GraphicsUtils.ONEPERCENTWIDTH*30), (GraphicsUtils.ONEPERCENTWIDTH*40), Constants.FPS, new int[]{1,1});
		Square square2 = new Square(bmp, (GraphicsUtils.ONEPERCENTWIDTH*70), (GraphicsUtils.ONEPERCENTWIDTH*50), Constants.FPS, new int[]{1,1});
		
		
		Bitmap smileySprites = GraphicsUtils.getBitmapResources(Constants.SMILE_BITMAP,"smiley_sprites.png", activity);
		Bitmap sadSprites = GraphicsUtils.getBitmapResources(Constants.SAD_BITMAP,"smiley_sad_sprites.png", activity);
		
		for(int x = 0; x< 20; x++) {
			
			int size = dim[0]/20;
			Random r = new Random();
			 int rx = r.nextInt(5);
			 int ry = r.nextInt(3);
			
			Smile smile = new Smile(smileySprites,x*size,0,1,new int[]{5,3},new int[]{rx,ry});
			smile.setScaleWidth(size);
			Updater.getInstance().getSprites().add(smile);
		}
		
		for(int x = 0; x< 20; x++) {
			
			int size = dim[0]/20;
			Random r = new Random();
			 int rx = r.nextInt(5);
			 int ry = r.nextInt(3);
			
			Smile smile = new Smile(sadSprites,x*size,size,1,new int[]{5,3},new int[]{rx,ry});
			smile.setScaleWidth(size);
			Updater.getInstance().getSprites().add(smile);
		}
		
//		int size = dim[0]/10;
//		Smile smile = new Smile(smileySprites,0,0,1,new int[]{5,3},new int[]{0,0});
//		smile.setScaleWidth(size);
//		Updater.getInstance().getSprites().add(smile);
//		
//		Smile smile2 = new Smile(smileySprites,size,0,1,new int[]{5,3},new int[]{1,0});
//		smile2.setScaleWidth(size);
//		Updater.getInstance().getSprites().add(smile2);
		
		Updater.getInstance().getSprites().add(square);
		Updater.getInstance().getSprites().add(square2);
	}


}
