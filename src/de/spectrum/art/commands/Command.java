package de.spectrum.art.commands;

import de.spectrum.App;
import de.spectrum.art.Node;
import de.spectrum.art.PaintContext;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract command. Superclass of all commands.
 */
public abstract class Command {
    protected App context;
    protected Node attachedNode;
    protected PaintContext paintContext;

    public Command(App context, Node attachedNode) {
        this.context = context;
        this.attachedNode = attachedNode;
        this.paintContext = attachedNode.getRootNode().getPaintContext();
    }

    public abstract JPanel getConfigurationPanel();

    public abstract void execute();

}
