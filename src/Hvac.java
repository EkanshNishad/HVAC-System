/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.Applet;

/**
 *
 * @author DELL
 */
public class Hvac extends Applet {

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    public void init() {
        // TODO start asynchronous download of heavy resources
    }
    
    public static void main(String args[])
    {
    	HvacController hcontrol = new HvacController();
    	HvacNavigation hnav = new HvacNavigation();
    	hcontrol.setVisible(true);
    	hnav.setVisible(true);
    }

    // TODO overwrite start(), stop() and destroy() methods
}
