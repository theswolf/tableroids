package com.september.tableroids.model;


import android.graphics.Bitmap;

import com.september.tableroids.MainGamePanel;

public class SpriteFixedFrame extends Sprite{



	public SpriteFixedFrame(MainGamePanel panel, Bitmap bitmap, int x, int y,
			int fps, int horizontalFrameCount, int verticalFrameCount,
			int scaleSize) {
		super(panel, bitmap, x, y, fps, horizontalFrameCount, verticalFrameCount,
				scaleSize);
		// TODO Auto-generated constructor stub
	}

	public void setFixedFrame(int fixedFrame) {
		this.currentFrame = fixedFrame;
	}

	public void update(long gameTime) {
		int[] evaluated = findInMatrix(currentFrame);

		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
		}

		// define the rectangle to cut out sprite
		this.sourceRect.left = (evaluated[0]) * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;

		this.sourceRect.top = (evaluated[1]) * spriteHeight;
		this.sourceRect.bottom = this.sourceRect.top +spriteHeight;
	}

}
