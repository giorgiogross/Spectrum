package de.spectrum.art;

import de.spectrum.App;
import de.spectrum.gui.OnFocusChangedListener;
import de.spectrum.gui.java.Component;
import de.spectrum.gui.java.RootMenu;
import de.spectrum.gui.processing.OnClickListener;
import de.spectrum.gui.processing.RootView;
import de.spectrum.gui.processing.View;
import de.spectrum.gui.processing.buttons.DeleteButton;
import de.spectrum.gui.processing.buttons.PlusButton;

/**
 * Encapsulates all data structures and methods linked with a root node.
 */
public class RootNode extends Node {
    private int currentFrame = 0;

    public RootNode (int xCenter, int yCenter, final App context) {
        super(null, context);

        final RootView rootView = new RootView(xCenter, yCenter, context);
        // todo add delete and addition button, instantiate or delete command nodes and add proper listeners. also do
        // todo this in the command node class
        rootView.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.isFocused()) {
                    v.setFocused(false);
                    getMenuView().setFrameVisibility(false);
                    return;
                }

                context.setFocusedComponent(v);
            }
        });
        registerMouseObserver(rootView);
        setProcessingView(rootView);
        context.addOnFocusChangedListener(new OnFocusChangedListener() {
            @Override
            public void onFocusChanged(Component c) {
                // delegate
                rootView.onFocusChanged(c);

                if(rootView.isFocused()) {
                    // show command node ui
                    setChildNodeVisibility(true);
                } else {
                    // hide command node ui
                    setChildNodeVisibility(false);
                    // re-show this node
                    getProcessingView().setVisible(true);
                }
            }
        });

        final PlusButton plusButton = new PlusButton(2 * rootView.getWidth() / 3, 2 * rootView.getHeight() / 3,
                rootView.getWidth() / 2, rootView.getHeight() / 2, context);
        plusButton.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addNextNode(new CommandNode(RootNode.this, context));
                // todo add UI to command node
            }
        });
        rootView.addView(plusButton);

        final DeleteButton deleteButton = new DeleteButton(2 * rootView.getWidth() / 3, 0,
                rootView.getWidth() / 2, rootView.getHeight() / 2, context);
        deleteButton.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                RootNode.this.context.onDelete(RootNode.this);
            }
        });
        rootView.addView(deleteButton);

        RootMenu menu = new RootMenu(context, this);
        setMenuView(menu);
        context.addOnFocusChangedListener(menu);

        // setSettingsView(settingsView);
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void decCurrentFrame() {
        if(currentFrame > 0) currentFrame--;
    }

    @Override
    protected void render() {
        // do nothing
    }
}
