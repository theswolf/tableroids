package com.september.tableroids.model.elements;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;

import com.september.tableroids.model.Sprite;
import com.september.tableroids.model.SpriteContainer;
import com.september.tableroids.statics.Scorer;

public class ResponseCointaner extends SpriteContainer{

	public ResponseCointaner(Bitmap bitmap, int x, int y, int fps,
			int[] frameCount) {
		super(bitmap, x, y, fps, frameCount);
		setTouchable(true);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isTouchable() {
		return true;
	}
	
	
	@Override 
	public void onTouch(MotionEvent event) { 
		super.onTouch(event);
		
		Bitmap bmp = Bitmap.createBitmap(1,1,Bitmap.Config.RGB_565);
		bmp.setPixel(0, 0, Color.BLACK);
		
		Sprite collider = new Sprite(bmp, (int) event.getX(), (int) event.getY(), 1, new int[]{1,1}) {
			
			@Override
			public void onTouch(MotionEvent event) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCollide() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected void doUpdate() {
				// TODO Auto-generated method stub
				
			}
		};
		
		boolean touched = false;
		for(Sprite s: getChildren()) {
			if(s.collide(collider)) {
				s.onTouch(event);
				touched = true;
			}
			
		}
		
		if(touched) {
			int correctHolder = Scorer.getR().nextInt(3);
			int x = 0;
			for(Sprite s: getChildren())  {
				SquareResponse sr = (SquareResponse) s;
				int value = x == correctHolder ? Scorer.getMoltiplicando() * Scorer.getMoltiplicatore() : Scorer.getR().nextInt(99)+1;
				sr.setValue(value);
				sr.changeColor();
				x++;
			}
		}
		
	}
}
