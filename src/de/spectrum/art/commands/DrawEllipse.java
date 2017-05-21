package de.spectrum.art.commands;

import de.spectrum.App;
import de.spectrum.art.Node;

import javax.swing.*;

/**
 * Created by Giorgio on 20.05.17.
 */
public class DrawEllipse extends Command {
    private int width;
    private int height;

    public DrawEllipse(App context, Node attachedNode) {
        super(context, attachedNode);
    }

    @Override
    public JPanel getConfigurationPanel() {
        // todo create UI and manipulate width and height options
        return null;
    }

    @Override
    public void execute() {
        context.stroke(paintContext.getCursor().getColor().getRGB());

        if(paintContext.getCursor().isFill()) context.fill(paintContext.getCursor().getColor().getRGB());
        else context.noFill();

        context.ellipse(
                paintContext.getCursor().getX(),
                paintContext.getCursor().getY(),
                width,
                height
        );
    }
}
