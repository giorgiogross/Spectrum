
import interfascia.GUIController;
import interfascia.IFButton;
import interfascia.IFLabel;
import processing.core.PApplet;

import javax.swing.*;

/**
 * Root class for the Spectrum App. Here happens initialization, kick off of rendering, management of UI's and delegation
 * of generative art creation.
 *
 * @author Giorgio
 */
public class App extends PApplet {

    GUIController c;
    IFButton b1, b2;
    IFLabel l;

    /**
     * Starts up the app
     *
     * @param args arguments
     */
    public static void main (String[] args) {
        PApplet.main("App", args);
    }

    @Override
    public void settings() {
        fullScreen();
    }

    @Override
    public void setup() {
        background(155);

        c = new GUIController (this);

        b1 = new IFButton ("Green", 40, 40, 40, 17);
        b2 = new IFButton ("Blue", 120, 40, 40, 17);

        c.add (b1);
        c.add (b2);

    }

    @Override
    public void draw() {

    }
}
