package com.september.tableroids.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.september.tableroids.consts.Constants;
import com.september.tableroids.model.Sprite;
import com.september.tableroids.statics.GameBuilder;
import com.september.tableroids.statics.Scorer;

public class UIWidgtesManager {

	private static UIWidgtesManager INSTANCE;

	private UIWidgtesManager() {}

	public static UIWidgtesManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new UIWidgtesManager();
		}
		return INSTANCE;
	}
	
	static int[] colors = new int[]{Color.GREEN, Color.BLUE, Color.RED};
	static int color = 0;
	
	private void evaluateColor() {
		color++;
		if(color >= colors.length) {
			color = 0;
		}
	}

	private Bitmap buildBitamp(int width, int height){
		Bitmap bitmap =  Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
//		for(int x = 0; x< bitmap.getWidth(); x++) {
//			for(int y = 0; y < bitmap.getHeight(); y++) {
//				if(x%2==0) {
//					bitmap.setPixel(x, y, colors[color]);
//				}
//			}
//		}
//		color++;
//		if(color >= colors.length) {
//			color = 0;
//		}
		return bitmap;
	}

	private Sprite buildStartSprite(Bitmap bitmap,final int question_no, int x, int y) {
		return new Sprite(bitmap, x, y, 1, new int[]{1,1}) {

			@Override
			public void onTouch(MotionEvent event) {
				Constants.QUESTION_NO = question_no;
				setDirty(true);
				Scorer.setReadyToPlay(true);
			}

			@Override
			public void onCollide() {
			}

			@Override
			protected void doUpdate() {
				// TODO Auto-generated method stub

			}


		};

	}


	public void paintStartButton(int id,int question_no, Canvas canvas, int x, int y,int width, int height, String text) {
		
		
		Sprite button = Updater.getInstance().getById(id);
		if(button == null) {
			Bitmap bitmap = buildBitamp(width, height);
			button = buildStartSprite(bitmap,question_no,x,y-height);
			button.setTouchable(true);
			button.setId(id);
			Updater.getInstance().addSprite(button);
		}
		
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
		strokePaint.setColor(Color.WHITE);
	    strokePaint.setTextAlign(Paint.Align.CENTER);
	    strokePaint.setTextSize(height);
	    strokePaint.setTypeface(GameBuilder.getTypeFace());
	    strokePaint.setStyle(Paint.Style.STROKE);
	    strokePaint.setStrokeWidth(10);

	   
	    textPaint.setColor(colors[color]);
	    textPaint.setTextAlign(Paint.Align.CENTER);
	    textPaint.setTextSize(height);
	    textPaint.setTypeface(GameBuilder.getTypeFace());
	    
	    evaluateColor();

	    canvas.drawText(text,  width/2, y, strokePaint);
	    canvas.drawText(text,  width/2, y, textPaint);
		
		button.draw(canvas);
		
	}

}
