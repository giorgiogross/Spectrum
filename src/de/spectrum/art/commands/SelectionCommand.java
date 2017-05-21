package de.spectrum.art.commands;

import de.spectrum.App;
import de.spectrum.art.Node;

import javax.swing.*;

/**
 * Command used as placeholder. Shows UI to select actual Command to be used with the attached Node.
 */
public class SelectionCommand extends Command {

    public SelectionCommand(App context, Node attachedNode) {
        super(context, attachedNode);
    }

    @Override
    public JPanel getConfigurationPanel() {
        // call ((CommandNode)attachedNode).setSettingsView(...); wehn command selected
        return new JPanel();
    }

    @Override
    public void execute() {
        // do nothing
    }
}
