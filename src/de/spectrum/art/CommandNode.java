package de.spectrum.art;

import de.spectrum.App;
import de.spectrum.gui.processing.CommandView;
import de.spectrum.gui.processing.OnClickListener;
import de.spectrum.gui.processing.View;

/**
 * Created by Giorgio on 03.05.17.
 */
public class CommandNode extends Node {

    public CommandNode(RootNode root, App context) {
        super(root, context);
        // todo pass coordinates xCenter and yCenter and align the node properly with other nodes from the root node

        CommandView commandView = new CommandView(0, 0, context); // coordinates will be overridden
        commandView.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // show settings menu
                getSettingsView().setFrameVisibility(true);
            }
        });
        setProcessingView(commandView);


        // todo add buttons to add or delete command nodes and add proper listeners.

    }

    @Override
    protected void render() {

    }
}
