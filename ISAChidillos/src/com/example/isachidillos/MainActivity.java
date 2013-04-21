package com.example.isachidillos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agimind.widget.SlideHolder;


public class MainActivity extends Activity {

	public Float energyNormal = (float) 0.0;
	public Float energyIdle = (float) 0.0;
	public ArrayList<String> manufacturers = new ArrayList<String>();
	public int manufacturerIndex=0;
	public ArrayList<String> models = new ArrayList<String>();
	public int modelIndex = 0;
	public int currentCategory = 0;
	public String currentManufacturer="";
	public String currentModel = "";
	public float costPerKWH = 0;
    Handler h;
    String resultado;
    String [] manufacturer = new String [50];
    String [] modelst = new String [50];
    public productObject product = new productObject();
    public String[][] values = new String [250][8];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (android.os.Build.VERSION.SDK_INT > 9) {
		    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		}
		
		
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
		v.setBackgroundColor(0x89AB2B); //doesnt work
		//Change layout to product selection layout
		RelativeLayout dynamicLayout = (RelativeLayout) this.findViewById(R.id.dynamicLayout);
		LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dynamicLayout.removeAllViews();
		View newContent = null;
		newContent = layoutInflater.inflate(R.layout.product_selection,null);
		dynamicLayout.addView(newContent);

		//Set the current category Id to query http://www.tpcdb.com/list.php?type= categoryid
		currentCategory = Integer.parseInt(v.getTag().toString());
		setManufacturerText();
		getManufacturers(currentCategory);
		
		
		//Obtain Manufacturers Array from that category
		
		//Populate the product selection manufacturers text
		
		//Set current manufacture to the first object in manufacturers array
		
		//Obtain Models Array from http://www.tpcdb.com/list.php?type= categoryid & query=currentManufacture
	}
	
	public void setManufacturerText()
	{
		TextView text = (TextView) this.findViewById(R.id.manufacturerText); //selectionView
		text.setText(currentManufacturer);
		this.product.manufature = currentManufacturer;
		
	}
	
	public void incrementCurrentManufacturer(View v)
	{
		//add a validation max value = arraysize-1
		if(manufacturerIndex != (this.manufacturers.size()-1))
			manufacturerIndex++;
		currentManufacturer = this.manufacturers.get(manufacturerIndex);
		modelIndex = 0;
		getProducts( currentCategory, currentManufacturer );
		setManufacturerText();
		//
	}
	
	public void decrementCurrentManufacturer(View v)
	{
		//Add a validation min value = 0
		if(manufacturerIndex != 0)
			manufacturerIndex--;
		currentManufacturer = this.manufacturers.get(manufacturerIndex);
		modelIndex = 0;
		getProducts( currentCategory, currentManufacturer );
		setManufacturerText();
	}
	
	public void setModelText()
	{
		TextView text = (TextView) this.findViewById(R.id.modelText);
		text.setText(currentModel);
		this.product.modelName = currentManufacturer;
	}
	
	public void incrementCurrentModel(View v)
	{
		//add a validation max value = arraysize-1
		if(modelIndex != (this.models.size()-1) && (this.models.size()!=0) )
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
		//Obtain Data of the currentModel,currentManufacturer,currentCategory
		EditText text = (EditText) this.findViewById(R.id.cost);
		String au= text.getEditableText().toString();		
			if(au==" "||au=="")
				au= "1.0";
			this.costPerKWH= Float.parseFloat(au);
			this.costPerKWH=this.costPerKWH/1000;			
		//obtanin data query
		getInfo(currentCategory, currentManufacturer ,currentModel );
		this.product.normalConsumption = energyNormal; //nW
		this.product.standByConsumption = energyIdle; //sW		
		//Calculate consumption total data 
		this.product.monthlyCost = costPerKWH *((30*this.product.normalUseTime*this.product.normalConsumption)+(30*(24-this.product.normalUseTime)*this.product.standByConsumption));
		this.product.bimestralCost = costPerKWH *((60*this.product.normalUseTime*this.product.normalConsumption)+(60*(24-this.product.normalUseTime)*this.product.standByConsumption));
		this.product.anualCost = costPerKWH *((365*this.product.normalUseTime*this.product.normalConsumption)+(365*(24-this.product.normalUseTime)*this.product.standByConsumption));
		
		//change dynamic content to Results view
		RelativeLayout dynamicLayout = (RelativeLayout) this.findViewById(R.id.dynamicLayout);
		LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dynamicLayout.removeAllViews();
		View newContent = null;
		newContent = layoutInflater.inflate(R.layout.results,null);	
		dynamicLayout.addView(newContent);
		
		//Change textviews in the result window
		TextView manuf = (TextView) this.findViewById(R.id.resultManufacture); //ResultsView
		manuf.setText(currentManufacturer);
		TextView modl = (TextView) this.findViewById(R.id.resultModel); //ResultsView
		modl.setText(currentModel);
		TextView nc = (TextView) this.findViewById(R.id.productNormalConsumption); //ResultsView
		nc.setText("Normal Use Consumtion: "+String.valueOf(this.product.normalConsumption)+" W");
		TextView sc = (TextView) this.findViewById(R.id.productStandByConsumption); //ResultsView
		sc.setText("Stand By Consumption: "+String.valueOf(this.product.standByConsumption)+" W");
		
		//Calculated Results
		TextView results = (TextView) this.findViewById(R.id.cm); //ResultsView
		results.setText("Monthly Cost:   $ "+String.valueOf(this.product.monthlyCost));
		results = (TextView) this.findViewById(R.id.cb); //ResultsView
		results.setText("Bimestral Cost:   $ "+String.valueOf(this.product.bimestralCost));
		results = (TextView) this.findViewById(R.id.cs); //ResultsView
		results.setText("Semestral Cost:   $ "+String.valueOf(this.product.semestralCost));
		results = (TextView) this.findViewById(R.id.ca); //ResultsView
		results.setText("Anual Cost:   $ "+String.valueOf(this.product.anualCost));
		
		
	}
	
	public void incrementHours(View v)
	{
		
		if(this.product.normalUseTime<24)
		{
			this.product.normalUseTime = this.product.normalUseTime + 1;
			
		}
			
		TextView h = (TextView) this.findViewById(R.id.hours);		
		h.setText(String.valueOf(this.product.normalUseTime));
	}
	
	public void decrementHours(View v)
	{
		
		if(this.product.normalUseTime>0)
		{
			this.product.normalUseTime = this.product.normalUseTime - 1;			
		}
		TextView h = (TextView) this.findViewById(R.id.hours);
		h.setText(String.valueOf(this.product.normalUseTime));
	}
	
	
	public void about(View v)
	{
		RelativeLayout dynamicLayout = (RelativeLayout) this.findViewById(R.id.dynamicLayout);
		LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dynamicLayout.removeAllViews();
		View newContent = null;
		newContent = layoutInflater.inflate(R.layout.rename,null);
		dynamicLayout.addView(newContent);
		
	}
	
	
	
	
	

	
	//puller functions
	public int downloadCategoryCatalog(int catPages, int type,String brand){
		int j=0,row=0,column=0;
		
		for(int i=0;i<250;i++){
			for(int k=0;k<8;k++){
				values[i][k]=null;
			}
		}
		try	{
    		
    		
    		while( j < catPages ){
        		URL url;            	
    			
        		if ((brand==null)){
    				url = new URL("http://www.tpcdb.com/list.php?page="+ Integer.toString(catPages) + "&type="+ Integer.toString(type) );
    			}else{
    				url = new URL("http://www.tpcdb.com/list.php?type="+ Integer.toString(type) + "&query="+ brand );
    			}
    			
            	URLConnection conn = url.openConnection();
                
                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String page = "";
                String table ="";
                String value ="";
                
                
                int inicio = 0;
                int fin = 0;
                int finLocal=0;
                
                
                while ((table=rd.readLine()) != null)
                {
                	page += table;
                }
                inicio = fin;
                inicio = page.indexOf("<td>");
                fin = page.indexOf("</table>");
                page = page.substring(inicio,fin);
                
                inicio=0;
               
                finLocal = page.indexOf("</td>");
                page = page.substring(finLocal+5);
                values[row][column] = " ";
                column++;
                
                
                while( page.contains("</td>")  ){
                	
                    
                	finLocal = page.indexOf("</td>");
                    
                    inicio=finLocal;
                    
                    
                    while ( !page.substring(inicio-1, inicio).equals("\"") ){
                    	value = page.substring(inicio-1, inicio);
                    	inicio --;
                    }
                    if ( finLocal-inicio == 1 || column == 0||column == 7){
                    	
                    	values[row][column]= " ";
                    	
                    }else{
                    	
                    	if( page.charAt(finLocal-1) == '>' ){
                    		if (page.charAt(finLocal-2) == 'a'){
                    			values[row][column] = page.substring(inicio+1, finLocal-4);
                    			
                    		}
                    	}else{
                    		values[row][column] = page.substring( inicio+1, finLocal );
                    		
                    	}
                    }
                    if(column!=0){
                    	page = page.substring(finLocal+5);
                    }
                    column ++;
                    if (column ==8){
                    	row++;
                    	column=0;
                    }
                    
                }
                j++;
    		}                  
           
    	}
    	catch (Exception e)	{
    		System.out.print(e);
    	}
		return row;
    	
    }


	public void getManufacturers( int types ) {

		int row=0;
		manufacturerIndex=0;
		manufacturers.clear();
		manufacturers.add("---->");
		int rdRows = downloadCategoryCatalog(1, types,null);
		
		for(row=0;row<rdRows;row++){
			if(!(this.manufacturers.contains(values[row][2]))){
				this.manufacturers.add(values[row][2]);	
			}
		}
	}
	
	public void getProducts( int type, String brand ) {

		int row=0;
		modelIndex = 0;
		
		models.clear();
		models.add("---->");
		int rdRows = downloadCategoryCatalog( 1, type,brand);
		for(row=0;row<rdRows;row++){
			if(!(this.models.contains(values[row][3]))){
				this.models.add(values[row][3]);	
			}
		}
		
	}
	
	public void getInfo( int type, String brand ,String model ) {

		int row=0;
		String aux,aux2;
		modelIndex = 0;
		
		getProducts(type,brand);
		
		
		row=models.indexOf(model);
		
		if (row!=-1){
			aux=values[row-1][5];
			aux2=values[row-1][6];
		
			if(aux==" "||aux==""){
				aux= "0.0 w";
			}else if(aux2==" "||aux==""){
				aux2="0.0 w";
			}
			aux = aux.substring( 0,aux.length()-2);
			aux2 = aux2.substring( 0,aux2.length()-2 );
			energyNormal= Float.parseFloat(aux);
			energyIdle= Float.parseFloat(aux2);
				
			
		}
		
		
	}





}
