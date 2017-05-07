package de.spectrum.gui.java;

import javax.swing.*;

/**
 * Java swing component. Just a class to hide the actual swing component. Via this class general data like the size
 * of the component can be read by the client without needing to know what UI this component actually represents.
 */
public class Component {
    protected JPanel ui;

    public Component () {
        ui = new JPanel();
    }

    public JPanel getView() {
        return ui;
    }

}
