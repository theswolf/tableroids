package com.september.tableroids;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.september.tableroids.statics.GameBuilder;
import com.september.tableroids.statics.Scorer;

public class SummaryActivity extends ListActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.summarylayout);
		
		super.onCreate(savedInstanceState);
		
		
		setTitle(getResources().getString(R.string.title_activity_summary));
//	    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//	        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//	        "Linux", "OS/2" };
		setContentView(R.layout.summarylayout);
		
		Button playAgain = (Button) findViewById(R.id.button1);
		
		playAgain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SummaryActivity.this.onBackPressed();
				
			}
		});
		
		//Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/WalterTurncoat.ttf");
	    SummaryAdapter adapter = new SummaryAdapter(this, Scorer.getResponses(),GameBuilder.getTypeFace());
	    setListAdapter(adapter);
		
	}
	
	@Override
	public void onBackPressed() {
		Scorer.reset();
		GameBuilder.setReady(false);
		Scorer.setReadyToPlay(false);
		Intent intent = new Intent(this,MainActivity.class);
		startActivity(intent);
	}
	
	@Override
    protected void onStop() 
    {
//        
//        Scorer.reset();
//		GameBuilder.setReady(false);
//		Scorer.setReadyToPlay(false);
		this.finish();
		super.onStop();
       
        //Log.d(tag, "MYonStop is called");
        // insert here your instructions
    }

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.summary, menu);
//		return true;
//	}

}
