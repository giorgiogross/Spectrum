package de.spectrum.art.commands;

import de.spectrum.App;
import de.spectrum.art.CommandNode;
import de.spectrum.art.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

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
        JPanel panel = new JPanel(new FlowLayout());

        ArrayList<String> cmdNames = CommandRegistry.GetCommands();
        cmdNames.add(0, "Select...");

        final JComboBox cbSelect = new JComboBox(cmdNames.toArray());
        cbSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbSelect.getSelectedIndex() == 0) return;
                try {
                    ((CommandNode)attachedNode).setCommand(
                            CommandRegistry.GetClass((String)cbSelect.getSelectedItem())
                                    .getDeclaredConstructor(App.class, Node.class).newInstance(context, attachedNode)
                    );
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(cbSelect);

        return panel;
    }

    @Override
    public void execute() {
        // do nothing
    }
}
