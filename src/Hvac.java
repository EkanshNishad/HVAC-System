/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Timer;
/**
 *
 * @author DELL
 */
public class Hvac implements Heater, AC, Fan, HumidityController, Ventilation{

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
	boolean startNavigation = false;
	
	double temp;
	double humid;
	int aqi;
	String aqiWarning;
	
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

	boolean ventilator;
	boolean exhaustStatus;
	int exhaustSpeed;
	
	
	
    public void init() {
        // TODO start asynchronous download of heavy resources
    	
    }
    
   
   
    
    public static void main(String args[])
    {
    	HvacController hcontrol = new HvacController();
    	HvacNavigation hnav = new HvacNavigation();
    	hnav.setVisible(true);
    	hcontrol.setVisible(true);
    	
    	hcontrol.setTitle("HVAC Controller");
    	hnav.setTitle("HVAC Navigation");
    	
    	Hvac hvac = new Hvac();
    	hvac.startNavigation = false;
    	
        ImageIcon img = new ImageIcon("res/Campus.png");
        
        JLabel thumb = new JLabel();
        thumb.setIcon(img);
    	Calendar calendar = Calendar.getInstance();
    	String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    	String[] months = new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    	SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
    	Date date = new Date();
    	hcontrol.jTextArea1.setText(days[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", " + calendar.get(Calendar.DATE) + " " + 
    	months[calendar.get(Calendar.MONTH) - 1] +" " + calendar.get(Calendar.YEAR) + "\n" + formatter.format(date));

    	hcontrol.getInput.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(hcontrol.autoMode.isSelected() || hcontrol.winterMode.isSelected() || hcontrol.summerMode.isSelected())
				{
					String tempMode;
				
					double temptemp, temphumid;
					int tempaqi;
					if(hcontrol.autoMode.isSelected())
						tempMode = hcontrol.autoMode.getText();
					else if(hcontrol.summerMode.isSelected())
						tempMode = hcontrol.summerMode.getText();
					else
						tempMode = hcontrol.winterMode.getText();
					
					if(tempMode == "AUTO")
					{
						Random rand = new Random();
						temptemp = rand.nextDouble()*98 - 49;
						temphumid = rand.nextDouble()*90 + 5;
						tempaqi = rand.nextInt(495);
					}
					else if(tempMode == "WINTER")
					{
						Random rand = new Random();
						temptemp = rand.nextDouble()*40 - 20;
						temphumid = rand.nextDouble()*70 + 25;
						
						tempaqi = rand.nextInt(495);
				
					}
					else {
						Random rand = new Random();
						temptemp = rand.nextDouble()*27 + 22;
						temphumid = rand.nextDouble()*45 + 5;
						tempaqi = rand.nextInt(495);
				
					}
				
					temptemp = ((double)Math.round(temptemp * 100))/100;
					temphumid = ((double)Math.round(temphumid * 100))/100;
				
					hcontrol.temperature.setText(Double.toString(temptemp));
					hcontrol.humidity.setText(Double.toString(temphumid));
					hcontrol.airQuality.setText(Integer.toString(tempaqi));
				
				}
				else {
					String message = "Please select any mode";
					JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
    		
    	});
    	

    	
    	
    	hcontrol.submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
				hvac.temp = Double.parseDouble(hcontrol.temperature.getText());
				hvac.humid = Double.parseDouble(hcontrol.humidity.getText());
				hvac.aqi = Integer.parseInt(hcontrol.airQuality.getText());
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
				if(hvac.temp > 55 || hvac.temp < -50)
				{
					String message = "Temperature should be between -50 to 50 C.";
					JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				else if(hvac.humid < 0 || hvac.humid > 100)
				{
					String message = "Humidity should be between 0 to 100 %.";
					JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
					
				}
				else if(hvac.aqi < 0 || hvac.aqi > 500)
				{
					String message = "AQI level should be between 0 to 500.";
					JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
					
					
				}
				else
				{
					hvac.startNavigation = true;
				
				
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
                    		hvac.turnACOff();
                    		hvac.turnCompressorOff();
                    		hvac.turnHeaterOn();
                    		hvac.setHeaterTemp(24);
		    			
                    	}
                    	else {
                    		hvac.turnACOff();
                    	}
                    }
                    else if(hvac.mode == "WINTER")
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
                    		hnav.currentACTemp.setText(Double.toString(hvac.acTemp));
                    		hvac.turnCompressorOn();
                    	}
                    }
                        
                        if(hvac.acStatus == true)
                        {
                            hnav.currentACStatus.setText("ON");
                            if(hvac.acCompressor == true)
                                hnav.currentACTemp.setText(Double.toString(hvac.acTemp) + " C");
                            else
                                hnav.currentACTemp.setText(" ");
                        }
                        else
                        {
                            hnav.currentACStatus.setText("OFF");
                            hnav.currentACTemp.setText(" ");
                        }
                        
                        if(hvac.heaterStatus == true)
                        {
                             hnav.currentHeaterStatus.setText("ON");
                             hnav.currentHeaterTemp.setText(Double.toString(hvac.heaterTemp) + " C");
                        }
                        else
                        {
                            hnav.currentHeaterStatus.setText("OFF");
                            hnav.currentHeaterTemp.setText(" ");
                        }
                        
                        if(hvac.aqi <= 50)
						{
							hvac.setAqiWarning("Good air quality.");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							hnav.aqiStatus.setBackground(Color.GREEN);
							hnav.aqiStatus.setForeground(Color.WHITE);
							System.out.println(hvac.aqiWarning);
						}
						else if(hvac.aqi <= 100)
						{
							hvac.setAqiWarning("Air Quality is acceptable.");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							hnav.aqiStatus.setBackground(Color.YELLOW);
							hnav.aqiStatus.setForeground(Color.BLACK);
							System.out.println(hvac.aqiWarning);
							
						}
						else if(hvac.aqi <= 150)
						{
							hvac.setAqiWarning("Air Quality is unhealty for sensitive groups. "
									+ "Sensitive group must take necessary precautions.");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							hnav.aqiStatus.setBackground(Color.ORANGE);
							hnav.aqiStatus.setForeground(Color.WHITE);
							System.out.println(hvac.aqiWarning);
							
						}
						else if(hvac.aqi <= 200)
						{
							hvac.setAqiWarning("Air Quality is unhealthy for everyone."
									+ " Faculties should ensure that no classes are being conducted.");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							hnav.aqiStatus.setBackground(Color.RED);
							hnav.aqiStatus.setForeground(Color.WHITE);
							System.out.println(hvac.aqiWarning);
						}
						else if(hvac.aqi <= 300)
						{
							hvac.setAqiWarning("Health Alert!!! Dont enter the CC3 building if there isnt any need.");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							hnav.aqiStatus.setBackground(new Color(172,95,227));
							hnav.aqiStatus.setForeground(Color.WHITE);
							System.out.println(hvac.aqiWarning);
						}
						else
						{
							hvac.setAqiWarning("Hazardous Air Quality. Evacuate the CC3 building as soon as possible");
							hnav.aqiStatus.setText(hvac.aqiWarning);
							hnav.aqiStatus.setBackground(new Color(135, 45, 45));
							hnav.aqiStatus.setForeground(Color.WHITE);
							System.out.println(hvac.aqiWarning);
						}
                        
                        Timer timer = new Timer();
        		    	TimerTask timerTask = new TimerTask() {

        					@Override
        					public void run() {
        						
        						
        						
        						
        						
        						if(hvac.mode == "AUTO")
        						{
        							
        							if(hvac.heaterStatus)
        							{
        								
        								if(hvac.temp < 21)
        								{
        									
        									hvac.temp = hvac.temp + 0.004761904;
        									
        								}
        								else
        								{
        									hvac.turnHeaterOff();
        								}
        							}
        							
        							if(hvac.acStatus && hvac.acCompressor)
        							{
        								if(hvac.temp > 21)
        								{
        									 hvac.temp = hvac.temp - 0.0019444;
        								}
        								else
        									hvac.turnCompressorOff();
        							}
        							else
        					 			hnav.currentACTemp.setText(" ");
        							
        							
        							if(hvac.startNavigation)
        							{
        								if(hvac.humid < 37.5)
        								{
        									hvac.increaseHumidity();
        								}
        								else if(hvac.humid > 56.25)
        								{
        									hvac.decreaseHumidity();
        								}
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
        				    		
        				    		if(hvac.startNavigation)
        				    		{
        				    			if(hvac.humid < 30)
        								{
        				    				hvac.increaseHumidity();
        								}
        								else if(hvac.humid > 40)
        								{
        									hvac.decreaseHumidity();
        								}
        				    		}
        						}
        						else
        						{
        							if(hvac.acStatus && hvac.acCompressor)
        							{
        								if(hvac.temp >= hvac.acTemp)
        								{
        									hvac.temp = hvac.temp - 0.0019444;
        								}
        							}
        							
        							if(hvac.startNavigation)
        							{
        								if(hvac.humid < 40)
        								{
        									hvac.increaseHumidity();
        								}
        								else if(hvac.humid > 60)
        								{
        									hvac.decreaseHumidity();
        								}
        							}
        						}
        						
        						
        						hvac.turnExhaustOn();
        						if(hvac.aqi > 100)
        						hvac.setExhaustSpeed((int)((double)(hvac.aqi - 100)*2.88) + 1440);
        						else
        							hvac.setExhaustSpeed( 1440);
        						
        						if(hvac.aqi <= 50)
        						{
        							hvac.setAqiWarning("Good air quality.");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							hnav.aqiStatus.setBackground(Color.GREEN);
        							hnav.aqiStatus.setForeground(Color.WHITE);
        							
        						}
        						else if(hvac.aqi <= 100)
        						{
        							hvac.setAqiWarning("Air Quality is acceptable.");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							hnav.aqiStatus.setBackground(Color.YELLOW);
        							hnav.aqiStatus.setForeground(Color.BLACK);
        							
        							
        						}
        						else if(hvac.aqi <= 150)
        						{
        							hvac.setAqiWarning("Air Quality is unhealty for sensitive groups. "
        									+ "Sensitive group must take necessary precautions.");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							hnav.aqiStatus.setBackground(Color.ORANGE);
        							hnav.aqiStatus.setForeground(Color.WHITE);
        							
        							
        						}
        						else if(hvac.aqi <= 200)
        						{
        							hvac.setAqiWarning("Air Quality is unhealthy for everyone."
        									+ " Faculties should ensure that no classes are being conducted.");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							hnav.aqiStatus.setBackground(Color.RED);
        							hnav.aqiStatus.setForeground(Color.WHITE);
        							
        						}
        						else if(hvac.aqi <= 300)
        						{
        							hvac.setAqiWarning("Health Alert!!! Dont enter the CC3 building if there isnt any need.");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							hnav.aqiStatus.setBackground(new Color(172,95,227));
        							hnav.aqiStatus.setForeground(Color.WHITE);
        							
        						}
        						else
        						{
        							hvac.setAqiWarning("Hazardous Air Quality. Evacuate the CC3 building as soon as possible");
        							hnav.aqiStatus.setText(hvac.aqiWarning);
        							hnav.aqiStatus.setBackground(new Color(135, 45, 45));
        							hnav.aqiStatus.setForeground(Color.WHITE);
        							
        						}
        							
        						
        						
        							
        						hvac.openVentilator();
        					
        						hnav.currentTemp.setText(Double.toString(hvac.temp) + " C");
        						hnav.currentHumidity.setText(Double.toString(hvac.humid));
        						hnav.currentFanSpeed.setText(Integer.toString(hvac.fanSpeed));
        						if(hvac.acStatus)
        							hnav.currentACTemp.setText(Double.toString(hvac.acTemp) + " C");
        						else
        							hnav.currentACTemp.setText(" ");
        						if(hvac.heaterStatus)
        							hnav.currentHeaterTemp.setText(Double.toString(hvac.heaterTemp) + " C");
        						else
        							hnav.currentHeaterTemp.setText(" ");
        						
        						hnav.currentExhaustSpeed.setText(Integer.toString(hvac.exhaustSpeed) + " rpm");
        						hnav.currentAqi.setText(Double.toString(hvac.aqi));
        						
        						    						
        					}
        				
        		    		
        		    	};
        		    	
        		    	
        		    	try {
							SendEmail.sendEmail(hvac.acStatus, hvac.acTemp, hvac.heaterStatus, hvac.heaterTemp, hvac.exhaustSpeed, hvac.fanSpeed
									, hvac.temp, hvac.humid, hvac.aqi, hvac.aqiWarning);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
        		    	
        		    	timer.scheduleAtFixedRate(timerTask, 0, 1000);
        		    	
        		    
        		    	
		    	
                        
				}		
				
				
				
			}
			
    		    			
    	});
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
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
		setACTemp(this.temp);
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




	@Override
	public void turnExhaustOn() {
		// TODO Auto-generated method stub
		this.exhaustStatus = true;
	}




	@Override
	public void turnExhaustOff() {
		// TODO Auto-generated method stub
		this.exhaustStatus = false;
	}




	@Override
	public void setExhaustSpeed(int speed) {
		// TODO Auto-generated method stub
		this.exhaustSpeed = speed;
	}




	@Override
	public void increaseExhaustSpeed() {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void decreaseExhaustSpeed() {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void openVentilator() {
		// TODO Auto-generated method stub
		this.ventilator = true;
	}




	@Override
	public void closeVentilator() {
		// TODO Auto-generated method stub
		this.ventilator = false;
	}




	public String getAqiWarning() {
		return aqiWarning;
	}




	public void setAqiWarning(String aqiWarning) {
		this.aqiWarning = aqiWarning;
	}
	
	






    // TODO overwrite start(), stop() and destroy() methods
}
