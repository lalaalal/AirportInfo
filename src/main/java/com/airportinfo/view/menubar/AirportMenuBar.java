package com.airportinfo.view.menubar;

import com.airportinfo.util.ThemeManager;
import com.airportinfo.util.Translator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.HashMap;

/**
 * Airport menu bar.
 *
 * @author lalaalal
 */
public class AirportMenuBar extends JMenuBar {
    private final HashMap<String, JMenu> menus = new HashMap<>();
    private final HashMap<String, JMenuItem> items = new HashMap<>();
    private final ThemeManager themeManager = ThemeManager.getInstance();

    public AirportMenuBar() {
        setBorder(BorderFactory.createEmptyBorder());
    }

    /**
     * Add menu with translation key.
     *
     * @param key Translation key - Key should be defined in string.properties
     */
    public void addMenu(String key) {
        JMenu menu = new AirportMenu(Translator.getBundleString(key));
        menus.put(key, menu);
        add(menu);
        updateTheme();
    }

    /**
     * Add menu item with translation key.
     *
     * @param menuKey  Key of menu to insert into
     * @param itemKey  Key of menu item to insert
     * @param listener Mouse listener of menu item
     */
    public void addMenuItem(String menuKey, String itemKey, MouseListener listener) {
        JMenu menu = menus.get(menuKey);
        if (menu == null)
            throw new IllegalArgumentException("Menu key (" + menuKey + ") doesn't exist.");
        JMenuItem item = new JMenuItem(Translator.getBundleString(itemKey));
        item.setPreferredSize(new Dimension(200, item.getPreferredSize().height));
        item.addMouseListener(listener);
        menu.add(item);
        items.put(itemKey, item);
        updateTheme();
    }

    public void updateTheme() {
        UIManager.put("Menu.selectionBackground", themeManager.getColor("MenuItem.hoverBackground"));
        UIManager.put("Menu.selectionForeground", themeManager.getColor("MenuBar.foreground"));
        UIManager.put("MenuItem.selectionBackground", themeManager.getColor("MenuItem.hoverBackground"));
        UIManager.put("MenuItem.selectionForeground", themeManager.getColor("MenuBar.foreground"));
        UIManager.put("MenuItem.shadow", themeManager.getColor("MenuBar.background"));
        SwingUtilities.updateComponentTreeUI(this);
        setBackground(themeManager.getColor("MenuBar.background"));

        for (JMenu menu : menus.values()) {
            menu.setBackground(themeManager.getColor("MenuBar.background"));
            menu.setForeground(themeManager.getColor("MenuBar.foreground"));
        }
        for (JMenuItem item : items.values()) {
            item.setBackground(themeManager.getColor("MenuBar.background"));
            item.setForeground(themeManager.getColor("MenuBar.foreground"));
        }
    }

    public void onLocaleChange() {
        for (String menuKey : menus.keySet()) {
            JMenu menu = menus.get(menuKey);
            menu.setText(Translator.getBundleString(menuKey));
        }

        for (String itemKey : items.keySet()) {
            JMenuItem item = items.get(itemKey);
            item.setText(Translator.getBundleString(itemKey));
        }
    }
}
