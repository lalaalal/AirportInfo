package com.airportinfo.view.table;

import com.airportinfo.Airport;
import com.airportinfo.view.AirportView;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * An abstract view class handling airports table.
 *
 * @author JumoKookbob
 */
public class AirportTableView extends AirportView {
    private JPanel panel;
    private final DefaultTableModel tableModel = new DefaultTableModel(HEADER, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable table;
    private JScrollPane scrollPane;
    private static final String[] HEADER = {"EnglishName", "KoreanName", "IATA", "ICAO", "Region", "EnglishCountryName", "KoreanCountryName", "EnglishCityName"};

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void updateView() {
        while (tableModel.getRowCount() > 0)
            tableModel.removeRow(0);

        for (Airport airport : airports) {
            tableModel.addRow(airport.toArray());
        }
    }

    private void createUIComponents() {
        table = new JTable(tableModel);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPane = new JScrollPane();
        panel.add(scrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(false);
        scrollPane.setViewportView(table);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
