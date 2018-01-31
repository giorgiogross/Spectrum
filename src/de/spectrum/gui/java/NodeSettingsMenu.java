package de.spectrum.gui.java;

import de.spectrum.App;
import de.spectrum.art.CommandNode;
import de.spectrum.art.Node;
import de.spectrum.art.commands.Command;
import de.spectrum.art.commands.CommandRegistry;
import de.spectrum.art.commands.SelectionCommand;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * @author Giorgio Gross <lars.ordsen@gmail.com>
 */
public class NodeSettingsMenu extends Component {
    private JScrollPane scroller = null;
    private java.awt.Component selectionPanel = null;
    private java.awt.Component configurationPanel = null;

    public NodeSettingsMenu(App context, JFrame ui, Command selectionCommand, java.awt.Component configurationPanel) {
        super(context, ui);
        this.selectionPanel = selectionCommand.getConfigurationPanel();
        this.configurationPanel = configurationPanel;

        initScroller();
    }

    private void initScroller() {
        scroller = new JScrollPane();
        replaceConfigurationPanel(configurationPanel);
        scroller.setPreferredSize(new Dimension(getView().getWidth(), getView().getHeight()));

        getView().add(scroller);
    }

    public NodeSettingsMenu(App context, JFrame ui, java.awt.Component configurationPanel) {
        super(context, ui);
        this.configurationPanel = configurationPanel;
        initScroller();
    }

    public void replaceConfigurationPanel(java.awt.Component configurationPanel, String title) {
        getView().setTitle(title);
        replaceConfigurationPanel(configurationPanel);
    }

    public void replaceConfigurationPanel(java.awt.Component configurationPanel) {
        Box content = Box.createVerticalBox();
        if (selectionPanel != null) content.add(selectionPanel);
        content.add(configurationPanel);
        scroller.setViewportView(content);
    }

}
