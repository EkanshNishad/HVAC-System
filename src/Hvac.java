/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;
/**
 *
 * @author DELL
 */
public class Hvac extends Applet implements Heater, AC, Fan, HumidityController{

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
	boolean startNavigation;
	
	double temp;
	double humid;
	double aqi;
	boolean fanStatus;
	int fanSpeed;
	String mode;
	
	boolean acCompressor;
	boolean acStatus;
	double acTemp;
	boolean acFan = true;
	
	boolean heaterStatus;
	double heaterTemp;
	
	boolean humditiyController;

	
	
    public void init() {
        // TODO start asynchronous download of heavy resources
    	
    }
    
   
   
    
    public static void main(String args[])
    {
    	HvacController hcontrol = new HvacController();
    	HvacNavigation hnav = new HvacNavigation();
    	hcontrol.setVisible(true);
    	hnav.setVisible(true);
    	
    	Hvac hvac = new Hvac();
    	hvac.startNavigation = false;
    	
    	hcontrol.submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				hvac.temp = Double.parseDouble(hcontrol.temperature.getText());
				hvac.humid = Double.parseDouble(hcontrol.humidity.getText());
				hvac.aqi = Double.parseDouble(hcontrol.airQuality.getText());
				if(hcontrol.fanon.isSelected())
				{
					hvac.turnFanOn();
					hvac.setSpeed( hcontrol.fanSpeedSetter.getValue() );
				}
				else
					hvac.turnFanOff();
				
				if(hcontrol.autoMode.isSelected())
					hvac.mode = hcontrol.autoMode.getText();
				else if(hcontrol.summerMode.isSelected())
					hvac.mode = hcontrol.summerMode.getText();
				else
					hvac.mode = hcontrol.winterMode.getText();
				
				
				
			}
			
    		    			
    	});
    	
    	
    	
    	hvac.turnACOn();
    	hvac.turnHumidityControlOn();
    	hnav.currentFanSpeed.setText(Integer.toString(hvac.fanSpeed));
    	if(hvac.mode == "AUTO") {
    		
    		hvac.acFan = true;
    		if(hvac.temp > 29)
    		{
    			hvac.turnHeaterOff();
    			hvac.setACTemp(24);
    			hnav.currentACTemp.setText("24 C");
    			hvac.turnCompressorOn();
    		}
    		else if(hvac.temp < 21)
    		{
    			hvac.turnCompressorOff();
    			if(hvac.temp < 19)
    			{
    				hvac.acFan = false;
    				hvac.turnACOff();
    				hvac.turnHeaterOn();
    			}
    			else
    				hvac.turnHeaterOff();
    		}
    	}
    	else if(hvac.mode == "Winter")
    	{
    		hvac.turnACOff();
    		hnav.currentACTemp.setText(" ");
    		if(hvac.temp < 18)
    		{
    			
    			hvac.turnHeaterOn();
    			hvac.setHeaterTemp(22);
    		}
    	}
    	else
    	{
    		hvac.turnHeaterOff();
    		hvac.acFan = true;
    		if(hvac.temp > 27)
    		{
    			hvac.setACTemp(25);
    			hnav.currentACTemp.setText("25 C");
    			hvac.turnCompressorOn();
    		}
    	}
    	
    	Timer timer = new Timer();
    	TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				
				
				
				
				
				if(hvac.mode == "AUTO")
				{
					if(hvac.heaterStatus)
					{
						if(hvac.heaterTemp > hvac.temp)
						{
							hvac.temp = hvac.temp + 0.004761904;
						}
						else
						{
							hvac.turnHeaterOff();
						}
					}
					
					if(hvac.acStatus)
					{
						if(hvac.acTemp < hvac.temp)
						{
							 hvac.temp = hvac.temp - 0.0019444;
						}
						else
							hvac.turnCompressorOff();
					}
					
					if(hvac.humid < 37.5)
					{
						hvac.increaseHumidity();
					}
					else if(hvac.humid > 56.25)
					{
						hvac.decreaseHumidity();
					}
				}
				else if(hvac.mode == "WINTER")
				{
					
		    		if(hvac.temp < 18)
		    		{
		    			if(hvac.heaterStatus)
		    			{
		    				if(hvac.heaterTemp > hvac.temp)
		    				{
		    					hvac.temp = hvac.temp + 0.004761904;
		    				}
		    			}
		    			else
		    			{
		    				hvac.turnHeaterOn();
		    				hvac.setHeaterTemp(20);
		    			}
		    		}
		    		
		    		
		    		if(hvac.humid < 30)
					{
						hvac.increaseHumidity();
					}
					else if(hvac.humid > 40)
					{
						hvac.decreaseHumidity();
					}
				}
				else
				{
					if(hvac.temp >= hvac.acTemp)
					{
						hvac.temp = hvac.temp - 0.0019444;
					}
					
					if(hvac.humid < 40)
					{
						hvac.increaseHumidity();
					}
					else if(hvac.humid > 60)
					{
						hvac.decreaseHumidity();
					}
				}
				
				hnav.currentTemp.setText(Double.toString(hvac.temp) + " C");
				hnav.currentHumidity.setText(Double.toString(hvac.humid));
				hnav.currentFanSpeed.setText(Integer.toString(hvac.fanSpeed));
				if(hvac.acStatus)
					hnav.currentACTemp.setText(Double.toString(hvac.acTemp) + " C");
				else
					hnav.currentACTemp.setText(" ");
			}
    		
    	};
    	
    	timer.scheduleAtFixedRate(timerTask, 0, 1000);
    			
    	
    	
    	
    	
    	
    	
    }




	@Override
	public void turnFanOn() {
		this.fanStatus = true;
	}




	@Override
	public void turnFanOff() {
		this.fanStatus = false; 
		setSpeed(0);
	}




	@Override
	public void setSpeed(int speed) {
		this.fanSpeed = speed;
		// TODO Auto-generated method stub
		
	}




	



	@Override
	public void setACTemp(double temp) {
		// TODO Auto-generated method stub
		this.acTemp = temp;
	}




	@Override
	public void increaseACTemp(double temp) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void decreaseACTemp(double temp) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public boolean turboMode(boolean turbo) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public boolean ACFan(boolean acFan) {
		// TODO Auto-generated method stub
		return false;
	}




	@Override
	public void turnHeaterOn() {
		this.heaterStatus = true;
	}




	@Override
	public void turnHeaterOff() {
		this.heaterStatus = false;
	}




	@Override
	public void increaseHeaterTemp(double temp) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void decreaseHeaterTemp(double temp) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void setHeaterTemp(double temp) {
		this.heaterTemp = temp;
		
	}




	@Override
	public void turnACOn() {
		this.acStatus = true;
	}




	@Override
	public void turnACOff() {
		this.acFan = false;
		this.acCompressor = false;
		this.acStatus = false;
	}




	@Override
	public void turnCompressorOn() {
		
		this.acCompressor = true;
		this.temp = this.temp - 0.016666;
	}




	@Override
	public void turnCompressorOff() {
		this.acCompressor = false;
		// TODO Auto-generated method stub
		
	}




	@Override
	public void turnHumidityControlOn() {
		this.humditiyController = true;
	}




	@Override
	public void turnHumidityControlOff() {
		this.humditiyController = false;
	}




	@Override
	public void increaseHumidity() {
		this.humid = this.humid + 0.002105379;
	}




	@Override
	public void decreaseHumidity() {
		this.humid = this.humid - 0.0438596;	
	}






    // TODO overwrite start(), stop() and destroy() methods
}
