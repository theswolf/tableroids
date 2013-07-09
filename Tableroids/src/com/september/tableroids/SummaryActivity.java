package com.september.tableroids;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.september.tableroids.statics.GameBuilder;
import com.september.tableroids.statics.Scorer;
import com.september.tableroids.statics.Scorer.Response;

public class SummaryActivity extends ListActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.summarylayout);
		
		super.onCreate(savedInstanceState);
//	    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//	        "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//	        "Linux", "OS/2" };
		setContentView(R.layout.summarylayout);
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/WalterTurncoat.ttf");
	    SummaryAdapter adapter = new SummaryAdapter(this, Scorer.getResponses(),tf);
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

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.summary, menu);
//		return true;
//	}

}
