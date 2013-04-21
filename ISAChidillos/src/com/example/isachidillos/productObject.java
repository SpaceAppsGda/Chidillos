package com.example.isachidillos;

import java.lang.String;

public class productObject {

	//Variables to Define product Info
	public int modelId;
	public String modelName;
	public String manufature;
	public int productPrice;
	//Variables to define power consumption
	public float normalConsumption; 
	public float standByConsumption;
	public int normalUseTime;
	//Variables to store total consumption based on energy cost, product usage time-stand by time
	public float monthlyCost;
	public float bimestralCost;
	public float semestralCost;
	public float anualCost;
	
	public productObject()
	{
		modelId = 0;
		productPrice = 0;
		normalConsumption = 0; 
		standByConsumption = 0;
		normalUseTime = 0;		
	}
}
