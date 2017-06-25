package de.spectrum.gui.java;

import de.spectrum.App;

import javax.swing.*;

/**
 * Java swing component. Just a class to hide the actual swing component. Via this class general data like the size
 * of the component can be read by the client without needing to know what UI this component actually represents.
 */
public class Component {
    protected JFrame ui;
    protected App context;

    public Component(App context) {
        this.context = context;
    }

    public Component(App context, JFrame ui) {
        this.ui = ui;
        this.context = context;
    }

    public JFrame getView() {
        return ui;
    }

    /**
     * Recalculate frame location
     */
    public void validateLocation() {
        // void
    }

    public void setFrameVisibility(boolean isVisible) {
        if (ui != null && ui.isVisible() != isVisible) ui.setVisible(isVisible);
    }

}
