package de.spectrum.art;

import de.spectrum.App;
import de.spectrum.gui.processing.CommandView;
import de.spectrum.gui.processing.OnClickListener;
import de.spectrum.gui.processing.View;
import de.spectrum.gui.processing.buttons.DeleteButton;
import de.spectrum.gui.processing.buttons.PlusButton;

/**
 * Created by Giorgio on 03.05.17.
 */
public class CommandNode extends Node {

    public CommandNode(RootNode root, App context) {
        super(root, context);
        setId(root.getNewChildNodeId());

        final CommandView commandView = new CommandView(0, 0, getId(), context); // coordinates will be overridden by parent node
        commandView.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // show settings menu
                getSettingsView().setFrameVisibility(true);
            }
        });
        registerMouseObserver(commandView);
        setProcessingView(commandView);

        final PlusButton plusButton = new PlusButton(commandView.getWidth() / 2, commandView.getHeight() / 2,
                commandView.getWidth() / 2, commandView.getHeight() / 2, context);
        plusButton.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CommandNode cmdNode = new CommandNode(CommandNode.this.root, CommandNode.this.context);
                addNextNode(cmdNode);
                CommandNode.this.root.rearrangeChildNodes();
            }
        });
        commandView.addView(plusButton);

        final DeleteButton deleteButton = new DeleteButton(commandView.getWidth() / 2, 0,
                commandView.getWidth() / 2, commandView.getHeight() / 2, context);
        deleteButton.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // mark the node as deleted. This will also mark all sub-nodes as deleted
                CommandNode.this.root.deleteCommandNode(CommandNode.this);
            }
        });
        commandView.addView(deleteButton);
    }

    @Override
    protected void render() {

    }
}
