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

        final CommandView commandView = new CommandView(0, 0, context); // coordinates will be overridden by parent node
        commandView.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // show settings menu
                getSettingsView().setFrameVisibility(true);
            }
        });
        setProcessingView(commandView);

        final PlusButton plusButton = new PlusButton(2 * commandView.getWidth() / 3, 2 * commandView.getHeight() / 3,
                commandView.getWidth() / 2, commandView.getHeight() / 2, context);
        plusButton.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // auto-focus this node
                CommandNode.this.context.setFocusedComponent(commandView);

                CommandNode cmdNode = new CommandNode(CommandNode.this.root, CommandNode.this.context);
                // only show the child nodes when this node is focused
                cmdNode.setChildNodeVisibility(commandView.isFocused());
                addNextNode(cmdNode);
                CommandNode.this.root.rearrangeChildNodes();
            }
        });
        commandView.addView(plusButton);

        final DeleteButton deleteButton = new DeleteButton(2 * commandView.getWidth() / 3, 0,
                commandView.getWidth() / 2, commandView.getHeight() / 2, context);
        deleteButton.addOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // un-focus to hide the menu
                CommandNode.this.context.setFocusedComponent(null);
                // mark the node as delted. This will also mark all sub-nodes as deleted
                CommandNode.this.context.onDelete(CommandNode.this.root);
            }
        });
        commandView.addView(deleteButton);
    }

    @Override
    protected void render() {

    }
}
