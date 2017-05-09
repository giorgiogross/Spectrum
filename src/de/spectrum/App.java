package de.spectrum;

import de.spectrum.art.RootNode;
import de.spectrum.gui.MouseObserver;
import de.spectrum.gui.OnFocusChangedListener;
import de.spectrum.gui.OnMenuActionListener;
import de.spectrum.gui.java.AppController;
import de.spectrum.gui.java.Component;
import processing.core.PApplet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * RootView class for the Spectrum App. Here happens initialization, kick off of rendering, management of UI's and
 * delegation of generative art creation.
 *
 * @author Giorgio
 */
public class App extends PApplet implements OnMenuActionListener {
    /**
     * Retain a reference to all data structures
     */
    private ArrayList<RootNode> roots;
    private ArrayList<OnFocusChangedListener> focusChangedListeners;
    private Component appController; // todo event handler for play pause rewind etc..

    private boolean showUI = false;

    /**
     * Starts up the app
     *
     * @param args arguments
     */
    public static void main(String[] args) {
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
        focusChangedListeners = new ArrayList<OnFocusChangedListener>();

        appController = new AppController(getControlMenuFrame());
    }

    @Override
    public void setup() {
        background(0);
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

        if (!showUI) return; // for performance reasons. App will still work without this line
        // handle processing UI's
        for (RootNode root : roots) {
            root.drawUI();
        }

        // handle render graph preview if necessary
    }

    @Override
    public void keyTyped() {
        if (key == 'm') showUI = !showUI;

        appController.setFrameVisibility(showUI);

        for (RootNode root : roots) {
            if (!showUI) {
                root.setChildNodeVisibility(false);
                root.getMenuView().setFrameVisibility(false);
            } else {
                root.getProcessingView().setVisible(true);
            }
        }

        // remove all focus states
        for (OnFocusChangedListener listener : focusChangedListeners)
            listener.onFocusChanged(null);
    }

    @Override
    public void mouseClicked() {
        int clickedNum = 0;

        for (RootNode root : roots) {
            for (MouseObserver observer : root.getMouseObservers()) {
                if (observer.mouseClicked(mouseX, mouseY)) clickedNum++;
            }
        }

        if (clickedNum == 0) {
            // no view was clicked. Add a new root
            RootNode mRoot = new RootNode(mouseX, mouseY, this);
            mRoot.getProcessingView().setVisible(showUI);
            roots.add(mRoot);
        }
    }

    public void addOnFocusChangedListener(OnFocusChangedListener listener) {
        focusChangedListeners.add(listener);
    }

    public void setFocusedComponent(Component c) {
        for (OnFocusChangedListener listener : focusChangedListeners)
            listener.onFocusChanged(c);
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
        final JFrame frame = new JFrame("Root Node Menu");
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(300, 30);
        frame.setFocusable(false);
        frame.setFocusableWindowState(false);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(false);

        return frame;
    }

    public JFrame getControlMenuFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        final JFrame frame = new JFrame("Spectrum Controller");
        frame.setUndecorated(true);
        frame.setLocation(0, (int) (height - 40));
        frame.setSize((int) width, 40);
        frame.setFocusable(false);
        frame.setFocusableWindowState(false);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(showUI);

        return frame;
    }

    @Override
    public void onRevertOneFrame() {
        for (RootNode root : roots)
            root.decCurrentFrame();
    }

    @Override
    public void onRevertOneFrame(RootNode rootNode) {
        rootNode.decCurrentFrame();
    }

    @Override
    public void onPlay() {

    }

    @Override
    public void onPlay(RootNode rootNode) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onPause(RootNode rootNode) {

    }

    @Override
    public void onSetFPS(int newFPS) {

    }

    @Override
    public void onSetFrameNumber(int num) {

    }

    @Override
    public void onExport() {

    }

    @Override
    public void onSafe() {

    }

    @Override
    public void onEditSettings() {

    }

    @Override
    public void onEditSettings(RootNode node) {

    }
}
