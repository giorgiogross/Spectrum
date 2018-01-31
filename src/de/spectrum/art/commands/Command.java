package de.spectrum.art.commands;

import de.spectrum.App;
import de.spectrum.art.Node;
import de.spectrum.art.PaintContext;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract command. Superclass of all commands.
 *
 * @author Giorgio Gross <lars.ordsen@gmail.com>
 */
public abstract class Command {
    protected App context;
    protected Node attachedNode;
    protected PaintContext paintContext;
    private String title = "Command";

    public Command(App context, Node attachedNode, String title) {
        this.context = context;
        this.attachedNode = attachedNode;
        this.paintContext = attachedNode.getRootNode().getPaintContext();
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public abstract Component getConfigurationPanel();

    public abstract void execute();

}
