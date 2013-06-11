package com.september.tableroids.model.elements;

import android.graphics.Bitmap;

import com.september.tableroids.MainGamePanel;
import com.september.tableroids.model.Sprite;

public class Boom extends Sprite{

	public Boom(MainGamePanel panel, Bitmap bitmap, int x, int y, int width,
			int height, int fps, int horizontalFrameCount,
			int verticalFrameCount, int scaleSize) {
		super(panel, bitmap, x, y, width, height, fps, horizontalFrameCount,
				verticalFrameCount, scaleSize);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(long gameTime) {
		super.update(gameTime);
		if(currentFrame == 0) {
			panel.getSpritesToRemove().add(this);
		}
	}
}
