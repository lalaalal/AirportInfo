package com.airportinfo.view;

import com.airportinfo.model.MouseReleaseListener;
import com.airportinfo.util.ThemeManager;
import com.airportinfo.util.Translator;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Airport toolbar.
 *
 * @author lalaalal
 */
public class AirportToolbar extends ComponentView {
    private static final Border LABEL_BORDER = BorderFactory.createEmptyBorder(0, 20, 0, 20);
    private static final Border SEPARATOR_BORDER = BorderFactory.createEmptyBorder(0, 20, 0, 20);
    private JPanel panel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private final ArrayList<JLabel> labels = new ArrayList<>();
    private final HashMap<String, JLabel> translationLabels = new HashMap<>();
    private final ThemeManager themeManager = ThemeManager.getInstance();

    public AirportToolbar() {
        $$$setupUI$$$();
        addThemeChangeListener(theme -> {
            panel.setBackground(themeManager.getColor("Toolbar.background"));
            leftPanel.setBackground(themeManager.getColor("Toolbar.background"));
            rightPanel.setBackground(themeManager.getColor("Toolbar.background"));
            for (JLabel label : labels)
                label.setForeground(themeManager.getColor("Toolbar.foreground"));
        });
        addLocaleChangeListener(locale -> {
            for (String key : translationLabels.keySet()) {
                JLabel label = translationLabels.get(key);
                label.setText(Translator.getBundleString(key));
            }
        });
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionBeforeUIUpdate(AppTheme theme) {
        UIManager.put("Label.disabledForeground", themeManager.getColor("Custom.disabledForeground"));
    }

    private JLabel addLabel(JPanel panel, Border border, String key, Consumer<MouseEvent> action) {
        JLabel label = new JLabel(Translator.getBundleString(key));
        label.setBorder(border);
        label.setForeground(themeManager.getColor("Toolbar.foreground"));
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (action != null)
            label.addMouseListener(new MouseReleaseListener(action));
        panel.add(label);
        labels.add(label);
        translationLabels.put(key, label);

        return label;
    }

    /**
     * Add label to left with mouse action.
     *
     * @param key    Label text or translation key
     * @param action Mouse action for label
     * @return Created Label
     */
    public JLabel addLabel(String key, Consumer<MouseEvent> action) {
        return addLabel(leftPanel, LABEL_BORDER, key, action);
    }

    /**
     * Add a separator to left.
     */
    public void addSeparator() {
        addLabel(leftPanel, SEPARATOR_BORDER, "|", null);
    }

    /**
     * Add label to right with mouse action.
     *
     * @param key    Label text or translation key
     * @param action Mouse action for label
     * @return Created Label
     */
    public JLabel addLabelRight(String key, Consumer<MouseEvent> action) {
        return addLabel(rightPanel, LABEL_BORDER, key, action);
    }

    public void addSeparatorRight() {
        addLabel(rightPanel, SEPARATOR_BORDER, "|", null);
    }

    private void createUIComponents() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new FlowLayout());
        rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout());
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
        panel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel.setBackground(new Color(-4605511));
        panel.setForeground(new Color(-4605511));
        leftPanel.setBackground(new Color(-4605511));
        panel.add(leftPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final Spacer spacer1 = new Spacer();
        panel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        rightPanel.setBackground(new Color(-4605511));
        panel.add(rightPanel, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
