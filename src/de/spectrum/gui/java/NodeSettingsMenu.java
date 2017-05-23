package de.spectrum.gui.java;

import de.spectrum.App;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Giorgio on 23.05.17.
 */
public class NodeSettingsMenu extends Component {

    public NodeSettingsMenu(App context, JFrame ui, JPanel configurationPanel) {
        super(context, ui);

        getView().setLayout(new BorderLayout());

        JScrollPane scroller = new JScrollPane(configurationPanel);
        scroller.setPreferredSize(new Dimension(getView().getWidth(), getView().getHeight()));

        getView().add(scroller, BorderLayout.CENTER);


    }

}
