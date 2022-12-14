package com.airportinfo.swing;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * JTextField bordered with border.
 *
 * @author lalaalal
 */
public class BorderedTextField extends JTextField {
    @Override
    public void updateUI() {
        super.updateUI();
        Border defaultBorder = getBorder();
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), defaultBorder));
    }
}
