package com.example.isachidillos;

import java.util.ArrayList;

import com.agimind.widget.SlideHolder;
import products.productObject;
import android.app.Activity;
import android.content.Context;
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
	public String currentManufacturer="";
	public String currentModel = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		productObject product = new productObject();
		this.manufacturers.add("Apple");
		this.manufacturers.add("Dell");
		this.models.add("T1");
		this.models.add("T2");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void categorySelection(View v)
	{
		SlideHolder holder = (SlideHolder) this.findViewById(R.id.slideHolder);
		holder.completeClosing();
				
		//Highlight selection
		v.setBackgroundColor(0x89AB2B);
		//Change layout to product selection layout
		RelativeLayout dynamicLayout = (RelativeLayout) this.findViewById(R.id.dynamicLayout);
		LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dynamicLayout.removeAllViews();
		View newContent = null;
		newContent = layoutInflater.inflate(R.layout.product_selection,null);	
		//newContent.setId(1);
		dynamicLayout.addView(newContent);

		//Set the current category Id to query http://www.tpcdb.com/list.php?type= categoryid
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
		text.setText(currentManufacturer);
	}
	
	public void incrementCurrentManufacturer(View v)
	{
		//add a validation max value = arraysize-1
		if(manufacturerIndex != (this.manufacturers.size()-1))
			manufacturerIndex++;
		currentManufacturer = this.manufacturers.get(manufacturerIndex);
		setManufacturerText();
		//
	}
	
	public void decrementCurrentManufacturer(View v)
	{
		//Add a validation min value = 0
		if(manufacturerIndex != 0)
			manufacturerIndex--;
		currentManufacturer = this.manufacturers.get(manufacturerIndex);
		setManufacturerText();
	}
	
	public void setModelText()
	{
		TextView text = (TextView) this.findViewById(R.id.modelText);
		text.setText(currentModel);
	}
	
	public void incrementCurrentModel(View v)
	{
		//add a validation max value = arraysize-1
		if(modelIndex != (this.models.size()-1) )
			modelIndex++;
		currentModel = this.models.get(modelIndex);
		setModelText();
	}
	
	public void decrementCurrentModel(View v)
	{
		//Validation min value = 0
		if(modelIndex!=0)
			modelIndex--;
		currentModel = this.models.get(modelIndex);
		setModelText();
	}
	
	public void okProductButtonClick(View v)
	{
		RelativeLayout dynamicLayout = (RelativeLayout) this.findViewById(R.id.dynamicLayout);
		LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dynamicLayout.removeAllViews();
		View newContent = null;
		newContent = layoutInflater.inflate(R.layout.results,null);	
		
	}

}
