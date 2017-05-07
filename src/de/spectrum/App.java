package de.spectrum;

import de.spectrum.art.RootNode;
import de.spectrum.gui.MouseObserver;
import de.spectrum.gui.java.Component;
import de.spectrum.gui.processing.View;
import processing.core.PApplet;

import javax.swing.*;
import java.util.ArrayList;

/**
 * RootView class for the Spectrum App. Here happens initialization, kick off of rendering, management of UI's and
 * delegation of generative art creation.
 *
 * @author Giorgio
 */
public class App extends PApplet {
    /**
     * Retain a reference to all data structures
     */
    private ArrayList<RootNode> roots;
    private Component appController; // todo event handler for play pause rewind etc..
    private View currentlyFocused = null;

    private boolean showUI = false;

    /**
     * Starts up the app
     *
     * @param args arguments
     */
    public static void main (String[] args) {
        PApplet.main("de.spectrum.App", args);
    }


    @Override
    public void settings() {
        initDataStructures();

        fullScreen();
        pixelDensity(2);
    }

    private void initDataStructures() {
        roots = new ArrayList<RootNode>();
    }

    @Override
    public void setup() {
        background(0);

        /*final JFrame frame = new JFrame("TitleLessJFrame");
        frame.getContentPane().add(new JLabel(" HEY!!!"));
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 80, 80));
            }
        });
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setVisible(true);*/
    }

    @Override
    public void draw() {
        // handle background
        background(0);

        // handle play/pause state

        // draw all artworks
        for (RootNode root : roots) {
            root.draw();
        }

        if (!showUI) return;
        // handle processing UI's
        for (RootNode root : roots) {
            root.getProcessingView().draw();
        }

        // handle render graph preview if necessary
    }

    @Override
    public void keyTyped() {
        if (key == 'm') showUI = !showUI;
    }

    @Override
    public void mouseClicked() {
        int clickedNum = 0;

        for(RootNode root : roots) {
            for (MouseObserver observer : root.getMouseObservers()) {
                if (observer.mouseClicked(mouseX, mouseY)) clickedNum++;
            }
        }

        if(clickedNum == 0) {
            // no view was clicked. Add a new root
            RootNode mRoot = new RootNode(mouseX, mouseY, this);
            roots.add(mRoot);
        }
    }

    public void setFocusedView(View v) {
        if(currentlyFocused != null) {
            currentlyFocused.setFocused(false);
        }
        v.setFocused(true);
        this.currentlyFocused = v;
    }

    /**
     * Instantiates a frame with the desired size and position which can be used by clients to include their own ui in
     * there.
     *
     * @return the frame
     */
    public JFrame getSettingsFrame() {
        return null;
    }

    public JFrame getRootMenuFrame() {
        return null;
    }

    public JFrame getControlMenuFrame() {
        return null;
    }

}
