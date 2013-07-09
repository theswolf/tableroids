package com.september.tableroids.model.elements;

import android.graphics.Bitmap;

import com.september.tableroids.consts.Constants;
import com.september.tableroids.model.SpriteContainer;
import com.september.tableroids.statics.Scorer;
import com.september.tableroids.statics.Scorer.Response;
import com.september.tableroids.utils.GraphicsUtils;

public class SmileContainer extends SpriteContainer{
	
	private int responseGiven = 0;

	public SmileContainer(Bitmap bitmap, int x, int y, int fps, int[] frameCount) {
		super(bitmap, x, y, fps, frameCount);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update(long gameTime) {
		super.update(gameTime);
		if(Scorer.getResponses().size() > getChildren().size()) {
			responseGiven++;
			Response lastResponse = Scorer.getResponses().get(Scorer.getResponses().size()-1);
			int correctValue = lastResponse.getResponse();
			int myValue = lastResponse.getMoltiplicando()*lastResponse.getMoltiplicatore();
			
			int size = getSpriteWidth()/(Constants.QUESTION_NO/2);
			
			boolean correct = correctValue == myValue;
			
			int sizex = responseGiven > (Constants.QUESTION_NO/2) ? responseGiven - (Constants.QUESTION_NO/2) : responseGiven;
			int sizey = responseGiven > (Constants.QUESTION_NO/2) ? 1 : 0;
			Bitmap bmp = GraphicsUtils.getBitmapResources(correct ? Constants.SMILE_BITMAP : Constants.SAD_BITMAP);
			
			int randomX = Scorer.getR().nextInt(5);
			int randomY = correct ? 0 :  Scorer.getR().nextInt(1)+1;
			
			Smile smile = new Smile(bmp,getSpriteWidth()-(size*sizex),sizey,1,new int[]{5,3},new int[]{randomX,randomY});
			smile.setScaleWidth(size);
			smile.setY(sizey*smile.getResizedHeight());
			appendChildren(smile);
		}
	}

}
