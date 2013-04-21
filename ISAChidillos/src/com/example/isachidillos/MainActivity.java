package com.example.isachidillos;

import java.util.ArrayList;

import products.productObject;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;



public class MainActivity extends Activity {

	public ArrayList<String> manufacturers = new ArrayList<String>();
	public int manufacturerIndex=0;
	public ArrayList<String> models = new ArrayList<String>();
	public int modelIndex = 0;
	public int currentCategory = 0;
	public String lol="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		productObject product = new productObject();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void categorySelection(View v)
	{
		//Highlight selection
		v.setBackgroundColor(0x336600);
		//Change layout to product selection layout
		RelativeLayout dynamicLayout = (RelativeLayout) this.findViewById(R.id.dynamicLayout);
		LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dynamicLayout.removeAllViews();
		View newContent = null;
		newContent = layoutInflater.inflate(R.layout.product_selection,null);	
		dynamicLayout.addView(newContent);
		//Set the current category Id to query http://www.tpcdb.com/list.php?type= categoryid
		lol = v.getTag().toString();
		currentCategory = Integer.parseInt(v.getTag().toString());
		setManufacturerText();
		
		//Obtain Manufacturers Array from that category
		
		//Populate the product selection manufacturers text
		
		//Set current manufacture to the first object in manufacturers array
		
		//Obtain Models Array from http://www.tpcdb.com/list.php?type= categoryid & query=currentManufacture
	}
	
	public void setManufacturerText()
	{
		TextView text = (TextView) this.findViewById(R.id.manufacturerText);
		text.setText(lol);
	}
	
	public void incrementCurrentManufacturer(View v)
	{
		manufacturerIndex++;
		setManufacturerText();
	}
	

}
