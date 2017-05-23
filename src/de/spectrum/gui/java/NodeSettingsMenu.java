package de.spectrum.gui.java;

import de.spectrum.App;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Giorgio on 23.05.17.
 */
public class NodeSettingsMenu extends Component {
    private JScrollPane scroller = null;
    private JPanel configurationPanel = null;

    public NodeSettingsMenu(App context, JFrame ui, JPanel configurationPanel) {
        super(context, ui);
        this.configurationPanel = configurationPanel;

        getView().setLayout(new BorderLayout());

        scroller = new JScrollPane();
        replaceConfigurationPanel(configurationPanel);
        scroller.setPreferredSize(new Dimension(getView().getWidth(), getView().getHeight()));

        getView().add(scroller, BorderLayout.CENTER);
    }

    public void replaceConfigurationPanel(JPanel configurationPanel) {
        this.configurationPanel = configurationPanel;
        scroller.setViewportView(configurationPanel);
    }

}
