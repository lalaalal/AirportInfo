package com.airportinfo;

import com.airportinfo.view.ChartContentView;
import com.airportinfo.view.MainFrame;
import com.airportinfo.view.TestFrame;

public class ChartViewTest {
    public static void main(String[] args) {
        Main.setSystemProperties();
        MainFrame mainFrame = new TestFrame();
        mainFrame.addContentView("ChartContentView", new ChartContentView(mainFrame));
        mainFrame.setContentView("ChartContentView");

        mainFrame.showFrame();
    }
}
