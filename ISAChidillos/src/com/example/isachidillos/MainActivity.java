package com.example.isachidillos;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import com.agimind.widget.SlideHolder;
import products.productObject;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void categorySelection(View v)
	{
		RelativeLayout dynamicLayout = (RelativeLayout) this.findViewById(R.id.dynamicLayout);
		LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dynamicLayout.removeAllViews();
		View newContent = null;
		newContent = layoutInflater.inflate(R.layout.compare_selection,null);	
		//newContent.setId(1);
		dynamicLayout.addView(newContent);
		
	}
}
