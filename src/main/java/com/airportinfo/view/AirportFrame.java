package com.airportinfo.view;

import com.airportinfo.controller.AirportController;
import com.airportinfo.controller.UserController;
import com.airportinfo.util.Translator;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Locale;

/**
 * Main frame of AirportInfo.
 *
 * @author lalaalal
 */
public class AirportFrame extends MainFrame {
    public static final String AIRPORT_DETAIL_VIEW = "airport_detail_view";
    private JPanel contentPanel;
    private JPanel toolbarPanel;
    private JPanel sidebarPanel;
    private JPanel rootPanel;
    private final AirportToolbar airportToolBar = new AirportToolbar();
    private final AirportSidebar airportSideBar;
    private final AirportController airportController = new AirportController();
    private final UserController userController = new UserController();

    public AirportFrame() {
        super(Translator.getBundleString("application_name"), 1280, 720);

        airportSideBar = new AirportSidebar(this);
        $$$setupUI$$$();

        setContentPane(rootPanel);
        initToolbar();
    }

    private void initToolbar() {
        airportToolBar.addLabelWithTranslation("toggle_theme", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                toggleTheme();
            }
        });

        airportToolBar.addLabelWithTranslation("english", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                changeLocale(Locale.ENGLISH);
            }
        });
        airportToolBar.addLabelWithTranslation("korean", new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                changeLocale(Locale.KOREAN);
            }
        });
    }

    /**
     * Call after setVisible(true).
     */
    public void load() {
        String title = Translator.getBundleString("error");
        try {
            airportController.loadFromDB();
        } catch (SQLException e) {
            String message = Translator.getBundleString("cannot_load");
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            String message = Translator.getBundleString("contact_developer");
            JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
        }
    }

    public AirportController getAirportController() {
        return airportController;
    }

    public UserController getUserController() {
        return userController;
    }

    @Override
    public void setTheme(AppTheme theme) {
        super.setTheme(theme);
        if (airportToolBar != null)
            airportToolBar.onThemeChange(theme);
        if (airportSideBar != null)
            airportSideBar.onThemeChange(theme);
    }

    @Override
    public void changeLocale(Locale locale) {
        super.changeLocale(locale);
        if (airportToolBar != null)
            airportToolBar.onLocaleChange(locale);
        if (airportSideBar != null)
            airportSideBar.onLocaleChange(locale);
    }

    @Override
    protected void changeContent(JPanel content) {
        contentPanel.removeAll();
        contentPanel.add(content, BorderLayout.CENTER);
    }

    private void createUIComponents() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        sidebarPanel = airportSideBar.getPanel();
        toolbarPanel = airportToolBar.getPanel();
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
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.add(toolbarPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        panel1.add(contentPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        rootPanel.add(sidebarPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
