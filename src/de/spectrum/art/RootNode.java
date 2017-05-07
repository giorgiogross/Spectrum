package de.spectrum.art;

import de.spectrum.App;
import de.spectrum.gui.processing.RootView;

/**
 * Encapsulates all data structures and methods linked with a root node.
 */
public class RootNode extends Node {
    private int currentFrame = 0;

    public RootNode (int xCenter, int yCenter, final App context) {
        super(null, context);

        RootView mRoot = new RootView(xCenter, yCenter, context);
        registerMouseObserver(mRoot);
        setProcessingView(mRoot);

        // setMenuView(menuView);
        // setSettingsView(settingsView);
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    @Override
    protected void render() {
        // do nothing
    }
}
