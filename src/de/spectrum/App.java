package de.spectrum;

import de.spectrum.art.Node;
import de.spectrum.art.RootNode;
import de.spectrum.gui.MouseObserver;
import de.spectrum.gui.OnFocusChangedListener;
import de.spectrum.gui.OnMenuActionListener;
import de.spectrum.gui.java.AppController;
import de.spectrum.gui.java.Component;
import de.spectrum.gui.java.RootMenu;
import de.spectrum.gui.java.UiCreationHelper;
import processing.core.PApplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

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
    protected ArrayList<RootNode> generatedRoots;
    private ArrayList<OnFocusChangedListener> focusChangedListeners;
    private Component appController; // todo event handler for play pause rewind etc..

    private boolean showUI = false;
    private boolean isPaused = false;
    private int rootNodeIdCounter = 0;

    private int FPS = 30;

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
        generatedRoots = new ArrayList<RootNode>();
        focusChangedListeners = new ArrayList<OnFocusChangedListener>();

        appController = new AppController(this, getControlMenuFrame());
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
        if(!isPaused) {
            // draw all artworks
            for (RootNode root : roots) {
                root.draw(new ArrayList<Node>());
            }
            for (RootNode root : generatedRoots) {
                root.draw(new ArrayList<Node>());
            }
        }

        if (!showUI) return; // for performance reasons. App will still work without this line
        // handle processing UI's
        for (Iterator<RootNode> iter = roots.iterator(); iter.hasNext(); ) {
            RootNode root = iter.next();
            if(root.isMarkedAsDeleted()) {
                // remove the obsolete node
                roots.remove(root);
                return;
            }

            root.drawUI(new ArrayList<Node>());
        }
    }

    @Override
    public void keyTyped() {
        if (key == 'm') {
            showUI = !showUI;
            if(showUI) onPause();
            else onPlay();
        }

        appController.setFrameVisibility(showUI);

        for (RootNode root : roots) {
            if (!showUI) {
                root.setChildNodeVisibility(false, new ArrayList<Node>());
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
            for (MouseObserver observer : root.getMouseObservers(new ArrayList<Node>())) {
                if (observer.mouseClicked(mouseX, mouseY)) clickedNum++;
            }
        }

        if (clickedNum == 0 && showUI) {
            // no view was clicked. Add a new root
            RootNode mRoot = new RootNode(mouseX, mouseY, this);
            mRoot.getProcessingView().setVisible(showUI);
            roots.add(mRoot);

            setFocusedComponent(null);
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
    public JFrame getSettingsFrame(String title) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        final JFrame frame = new JFrame(title);
        frame.setLocationRelativeTo(null);
        frame.setLocation((int)width - UiCreationHelper.SETTINGS_UI_WIDTH, (int)height - UiCreationHelper.SETTINGS_UI_HEIGHT);
        frame.setSize(UiCreationHelper.SETTINGS_UI_WIDTH, UiCreationHelper.SETTINGS_UI_HEIGHT);
        frame.setFocusable(false);
        frame.setAlwaysOnTop(true);
        frame.setVisible(false);

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowIconified(WindowEvent wEvt) {
            }

            @Override
            public void windowDeactivated(WindowEvent wEvt) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }

        });

        return frame;
    }

    public JFrame getRootMenuFrame() {
        final JFrame frame = new JFrame("Root Node Menu");
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setSize(200, 60);
        frame.setFocusable(false);
        frame.setFocusableWindowState(false);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(false);

        return frame;
    }

    public JFrame getNodeAdderFrame() {
        final JFrame frame = new JFrame("Add Node");
        frame.setLocationRelativeTo(null);
        frame.setSize(240, 55);
        frame.setFocusable(false);
        frame.setAlwaysOnTop(true);
        frame.setVisible(false);

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowIconified(WindowEvent wEvt) {
            }

            @Override
            public void windowDeactivated(WindowEvent wEvt) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }

        });

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
        frame.setVisible(showUI);

        return frame;
    }

    public void addGeneratedRootNode (RootNode generatedRoot) {
        this.generatedRoots.add(generatedRoot);
    }

    public ArrayList<RootNode> getRootNodes() {
        return roots;
    }

    public int getFPS() {
        return FPS;
    }

    public void setFPS(int FPS) {
        this.FPS = FPS;
    }

    public int getNewRootNodeId() {
        return rootNodeIdCounter++;
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
        isPaused = false;
    }

    @Override
    public void onPlay(RootNode rootNode) {
    }

    @Override
    public void onPause() {
        isPaused = true;
    }

    @Override
    public void onPause(RootNode rootNode) {

    }

    @Override
    public void onDelete() {
        setFocusedComponent(null);
        focusChangedListeners = new ArrayList<OnFocusChangedListener>();

        for (Node n : roots) n.delete();
        for (Node n : generatedRoots) n.delete();
    }

    @Override
    public void onDelete(RootNode rootNode) {
        rootNode.delete();
    }

    @Override
    public void onSetLayer(RootNode rootNode, int layer) {
        if(layer < 0) return;

        rootNode.setLayer(layer);
        sortRootsArray();

        ((RootMenu)rootNode.getMenuView()).updateLayerNumber();
    }

    @Override
    public void onSetFPS(int newFPS) {
        if(newFPS < 1) return;

        setFPS(newFPS);
        ((AppController) appController).updateFPSPNumber();

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

    private void sortRootsArray() {
        Collections.sort(roots, new Comparator<RootNode>() {
            @Override
            public int compare(RootNode o1, RootNode o2) {
                return o1.getLayer() - o2.getLayer();
            }
        });
    }
}
