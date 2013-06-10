package com.september.tableroids.model.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.september.tableroids.MainGamePanel;
import com.september.tableroids.model.Sprite;

public class Shoot extends Sprite {
	
	private static int toY = 0;

	public Shoot(MainGamePanel panel, Bitmap bitmap, int x, int y, int width,
			int height, int fps, int horizontalFrameCount,
			int verticalFrameCount, int scaleSize) {
		super(panel, bitmap, x, y, width, height, fps, horizontalFrameCount,
				verticalFrameCount, scaleSize);
		
		
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas); 
	}
	
	@Override
	public void update(long gameTime) { 
		if (gameTime > frameTicker + framePeriod) {
			
			if(getY() > 0) {
				setY(getY()-5);
			}
			
			
		}
		
		super.update(gameTime);
	}

}
