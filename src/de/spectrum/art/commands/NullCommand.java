package de.spectrum.art.commands;

import de.spectrum.App;
import de.spectrum.art.Node;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Giorgio on 25.06.17.
 */
public class NullCommand extends Command {

    public static final String DESCRIPTION = "None";

    public NullCommand(App context, Node attachedNode) {
        super(context, attachedNode, "Select");
    }

    @Override
    public Component getConfigurationPanel() {
        return Box.createHorizontalBox();
    }

    @Override
    public void execute() {

    }
}
