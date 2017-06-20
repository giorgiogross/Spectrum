package de.spectrum.gui.java;

import de.spectrum.App;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Giorgio on 23.05.17.
 */
public class NodeSettingsMenu extends Component {
    private JScrollPane scroller = null;
    private java.awt.Component configurationPanel = null;

    public NodeSettingsMenu(App context, JFrame ui, java.awt.Component configurationPanel) {
        super(context, ui);
        this.configurationPanel = configurationPanel;

        scroller = new JScrollPane();
        replaceConfigurationPanel(configurationPanel);
        scroller.setPreferredSize(new Dimension(getView().getWidth(), getView().getHeight()));

        getView().add(scroller);
    }

    public void replaceConfigurationPanel(java.awt.Component configurationPanel, String title) {
        getView().setTitle(title);
        replaceConfigurationPanel(configurationPanel);
    }

    public void replaceConfigurationPanel(java.awt.Component configurationPanel) {
        this.configurationPanel = configurationPanel;
        scroller.setViewportView(configurationPanel);
    }

}
