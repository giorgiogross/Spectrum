package de.spectrum.art;

import de.spectrum.App;
import de.spectrum.gui.processing.OnClickListener;
import de.spectrum.gui.processing.RootView;
import de.spectrum.gui.processing.View;
import de.spectrum.gui.processing.buttons.PlusButton;

/**
 * Encapsulates all data structures and methods linked with a root node.
 */
public class RootNode extends Node {
    private int currentFrame = 0;

    public RootNode (int xCenter, int yCenter, final App context) {
        super(null, context);

        RootView mRoot = new RootView(xCenter, yCenter, context);
        // this belongs into the settings view
        PlusButton plusButton = new PlusButton(mRoot.getWidth() - 30, mRoot.getHeight() - 30, 30, 30, context);
        plusButton.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.setFocusedView(v);
            }
        });
        mRoot.addView(plusButton);
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
