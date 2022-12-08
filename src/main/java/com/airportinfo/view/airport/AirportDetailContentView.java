package com.airportinfo.view.airport;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.airportinfo.controller.AirportController;
import com.airportinfo.model.Airport;
import com.airportinfo.util.GoogleMapAPI;
import com.airportinfo.view.ComponentView;

/**
 * 
 * AirportDetailContentView panel making class
 * SetAirport() --> updateView() --> makePanel()
 * 
 * - setAirport()
 * Set information of airport with received index value
 * 
 * - updateView() 
 * Bring name of airport from array
 * 
 * - makePanel()
 * panel making method
 * sorry for trash design...
 * 
 * @author ShinHeeYoun
 *
 */



public class AirportDetailContentView extends ComponentView {
	private JPanel panel;
	private Airport airport;
	private String airportName = "Anaa Airport";
	private String airportInfo = "Place for information";

	@Override
	public JPanel getPanel() {
		return this.panel;
	}
	
	public void setAirport(Airport airport) throws Exception {
        this.airport = airport;
        // String airportName°ú airportInfo¸¦ ¹Ù²ãÁà¾ßÇÔ
        
        updateView();
    }
	
	public void updateView() throws Exception {
		String[] keys = Airport.getLocalizedAttributeNames();
        String[] values = airport.toArray();
        airportName = values[0];  
        makePanel();
    }
	
	public void makePanel() throws Exception {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		// Font
		Font font1 = new Font("¸¼Àº °íµñ", Font.BOLD, 20);
		Font font2 = new Font("¸¼Àº °íµñ", Font.PLAIN, 12);
		/**
		 * Label/component making
		 */
		// Title
		JLabel TitleLabel = new JLabel();
		TitleLabel.setText(airportName);
		TitleLabel.setFont(font1);
			
		// Information
		JTextArea InformationLabel = new JTextArea();
		InformationLabel.setText(airportInfo); 
		InformationLabel.setLineWrap(true);
		InformationLabel.setEditable(false);
		InformationLabel.setFont(font2);
			
		// Info
		JPanel InfoLabel = new JPanel();
		//InfoLabel.setLayout(null);
		AirportController airportController = new AirportController();
        airportController.loadFromDB();
        AirportDetailView airportDetailView = new AirportDetailView();
        airportDetailView.setAirport(airport);
        JPanel DetailView = new JPanel();
        DetailView = airportDetailView.getPanel();
        InfoLabel.add(DetailView);
        
		
		// "Images" Text
		JLabel ImageLabel = new JLabel();
		ImageLabel.setText("Images");
		ImageLabel.setFont(font1);
		
		// Images
		JPanel ImgContainer = new JPanel(new GridLayout(0,3, 30, 0));

		JLabel pic1 = new JLabel(imageViewer("https://www.shutterstock.com/image-vector/sample-red-square-grunge-stamp-260nw-338250266.jpg"));
		JLabel pic2 = new JLabel(imageViewer("https://media.istockphoto.com/id/486242045/vector/sample-red-3d-realistic-paper-speech-bubble-isolated-on-white.jpg?s=612x612&w=0&k=20&c=H7a_5jtJvmrr_VtdvHpb4C8D4BLue9Hr1IlTvtG_Khw="));
		JLabel pic3 = new JLabel(imageViewer("https://www.shutterstock.com/image-vector/sample-stamp-rubber-style-red-260nw-1811246308.jpg"));
		ImgContainer.add(pic1);
		ImgContainer.add(pic2);
		ImgContainer.add(pic3);
        
        // Map 
        JPanel MapContainer = new JPanel();
        MapContainer.setLayout(new FlowLayout());     
        String location = airportName;
        GoogleMapAPI googleAPI = new GoogleMapAPI();
        googleAPI.downloadMap(location);
        googleAPI.setSizeA(380, 380);
		JLabel googleMap = new JLabel(googleAPI.getMap(location));
		googleAPI.fileDelete(location);
		MapContainer.add(googleMap);

		/**
		 * Location Setting
		 */
		// Title
		panel.add(TitleLabel);
		TitleLabel.setBounds(30, 10, 200, 50);
		// Information
		panel.add(InformationLabel);
		InformationLabel.setBounds(30, 70, 400, 100);
		// Info
		panel.add(InfoLabel);
		InfoLabel.setBounds(30, 180, 400, 215);
		// Image text
		panel.add(ImageLabel);
		ImageLabel.setBounds(30, 380, 200, 100);
		// Images
		panel.add(ImgContainer);
		ImgContainer.setBounds(30, 460, 600, 200);
		// Map
		panel.add(MapContainer);
		MapContainer.setBounds(450, 20, 380, 380);
		
		this.panel = panel;
	}	
	
	public ImageIcon imageViewer(String Stringurl) {
		String location = "a";
		try {
			URL url = new URL(Stringurl);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(location);
			byte[] b = new byte[2048];
			int length;
			while((length = is.read(b))!= -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new ImageIcon((new ImageIcon(location)).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));
	}
}
