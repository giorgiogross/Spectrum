package de.spectrum.art.commands;

import de.spectrum.App;
import de.spectrum.art.Node;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Giorgio on 20.05.17.
 */
public class DrawEllipse extends Command {
    public static final String DESCRIPTION = "Ellipse";

    private int width = 50;
    private int height = 50;

    public DrawEllipse(App context, Node attachedNode) {
        super(context, attachedNode);
    }

    @Override
    public JPanel getConfigurationPanel() {
        JPanel content = new JPanel(new BorderLayout());
        content.add(new JLabel(DESCRIPTION), BorderLayout.NORTH);

        return content;
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
