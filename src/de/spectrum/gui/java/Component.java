package de.spectrum.gui.java;

import javax.swing.*;

/**
 * Java swing component. Just a class to hide the actual swing component. Via this class general data like the size
 * of the component can be read by the client without needing to know what UI this component actually represents.
 */
public class Component {
    protected JFrame ui;

    public Component() {
    }

    public Component(JFrame ui) {
        this.ui = ui;
    }

    public JFrame getView() {
        return ui;
    }

    public void setFrameVisibility(boolean isVisible) {
        if (ui != null) ui.setVisible(isVisible);
    }

}
