package com.airportinfo;

import com.airportinfo.controller.AirportController;
import com.airportinfo.view.MainFrame;
import com.airportinfo.view.TestContentView;
import com.airportinfo.view.TestFrame;
import com.airportinfo.view.airport.AirportDetailContentView;

/**
 * Test class for viewing AiportDetailContentViewTest
 *  
 * int indexWantToSee = 4 <-- if you change this value
 * you can see the panel showing information of that index 
 * 
 * @author ShinHeeYoun
 *
 */

public class AirportDetailContentViewTest  {
	public static void main(String[] args) throws Exception {
		
		int indexWantToSee=11;
		
		
		AirportController airportController = new AirportController();
        airportController.loadFromDB();

        MainFrame mainFrame = new TestFrame();
        TestContentView contentView = new TestContentView();
              
        AirportDetailContentView airportDetailContentView = new AirportDetailContentView();
        
        airportDetailContentView.setAirport(airportController.getAirports()[indexWantToSee]);
        contentView.setPanel(airportDetailContentView.getPanel());
        
        mainFrame.addContentView("AirportDetailView", contentView);
        mainFrame.setContentView("AirportDetailView");
        mainFrame.setVisible(true);
        
	}
}
