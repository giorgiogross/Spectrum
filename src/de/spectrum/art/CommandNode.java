package de.spectrum.art;

import de.spectrum.App;
import de.spectrum.art.commands.Command;
import de.spectrum.art.commands.SelectionCommand;
import de.spectrum.gui.java.Component;
import de.spectrum.gui.java.NodeAdder;
import de.spectrum.gui.java.NodeSettingsMenu;
import de.spectrum.gui.processing.CommandView;
import de.spectrum.gui.processing.OnClickListener;
import de.spectrum.gui.processing.View;
import de.spectrum.gui.processing.buttons.DeleteButton;
import de.spectrum.gui.processing.buttons.PlusButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * Created by Giorgio on 03.05.17.
 */
public class CommandNode extends Node {
    private NodeAdder adderView;
    private Command command; // exchange this with a "Selection Command" which is used to select and set the real command

    public CommandNode(RootNode root, App context) {
        super(root, context);
        setId(root.getNewChildNodeId());

        // CommandView coordinates will be overridden by parent node...
        final CommandView commandView = new CommandView(0, 0, getId(), context);

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
                adderView = new NodeAdder(CommandNode.this, CommandNode.this.context,
                        CommandNode.this.context.getNodeAdderFrame());
                adderView.setFrameVisibility(true);
                // maybe make this private, override dleete() and hide this view along with all other views when deleted
                // (or when focus changes)
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

        setCommand(new SelectionCommand(context, this));
    }

    public void setCommand(Command command) {
        this.command = command;

        // update UI
        boolean show = false;
        if(getSettingsView() != null) {
            if(getSettingsView().getView().isVisible()) show = true;
            getSettingsView().getView().dispatchEvent(new WindowEvent(getSettingsView().getView(), WindowEvent.WINDOW_CLOSING));
        }
        setSettingsView(new NodeSettingsMenu(context, context.getSettingsFrame(), command.getConfigurationPanel()));
        getSettingsView().getView().setVisible(show);
    }

    @Override
    protected void render() {
        command.execute();
    }

    @Override
    protected void hideCustomUI() {
        getSettingsView().setFrameVisibility(false);
        if(adderView != null) adderView.setFrameVisibility(false);
    }
}
